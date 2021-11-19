package com.liulin.web.controller.moduler;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.liulin.common.annotation.Log;
import com.liulin.common.config.LiulinConfig;
import com.liulin.common.config.ServerConfig;
import com.liulin.common.constant.Constants;
import com.liulin.common.core.controller.BaseController;
import com.liulin.common.core.domain.AjaxResult;
import com.liulin.common.core.page.TableDataInfo;
import com.liulin.common.enums.BusinessType;
import com.liulin.common.utils.ShiroUtils;
import com.liulin.common.utils.StringUtils;
import com.liulin.common.utils.uuid.UUID;
import com.liulin.system.domain.ReportDto;
import com.liulin.system.domain.Schedule;
import com.liulin.system.service.IAttachmentService;
import com.liulin.system.service.IScheduleService;
import com.spire.xls.FileFormat;
import com.spire.xls.Workbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: LinLiu
 * @Date: 2021/9/26 9:55 上午
 */
@Controller
@RequestMapping("/event/report")
public class ReportController extends BaseController {

    private String prefix = "event/report";

    @Autowired
    private IScheduleService scheduleService;

    @Autowired
    private ServerConfig serverConfig;

    @Autowired
    private IAttachmentService attachmentService;

    @RequiresPermissions("event:report:view")
    @GetMapping()
    public String carSpace() {
        return prefix + "/report";
    }

    /**
     * 查询report列表
     */
    @RequiresPermissions("event:report:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(ReportDto reportDto) {
        startPage();
        reportDto.setBuildingId(ShiroUtils.getSysUser().getBuilding().getDeptId());
        List<ReportDto> list = scheduleService.selectReportList(reportDto);
        return getDataTable(list);
    }


    /**
     * 导出schedule列表
     */
    @RequiresPermissions("event:report:export")
    @Log(title = "schedule", businessType = BusinessType.EXPORT)
    @PostMapping("/export2")
    @ResponseBody
    public AjaxResult export2(ReportDto reportDto) {
        reportDto.setBuildingId(ShiroUtils.getSysUser().getBuilding().getDeptId());
        List<ReportDto> list = scheduleService.selectReportList(reportDto);
        //通过工具类创建writer
        String prefix = serverConfig.getUrl() + Constants.RESOURCE_PREFIX + "/upload";
        String url = prefix + "/report.xlsx";
        // 上传文件路径
//        String filePath = LiulinConfig.getUploadPath();


        String fileName = UUID.fastUUID() + "_report.xlsx";
        String downloadPath = LiulinConfig.getDownloadPath() + fileName;
        ExcelWriter writer = ExcelUtil.getWriter(downloadPath);
        CellStyle cellStyle = writer.getHeadCellStyle();
        cellStyle.setFillBackgroundColor(IndexedColors.RED.getIndex());
        String lastService = "";
        ArrayList<Map<String, Object>> rows = new ArrayList<>();
        int i = 0;
        for (ReportDto dto : list) {
            if (!lastService.equals(dto.getService())) {
                if (StringUtils.isNotEmpty(lastService)) {
                    writer.write(rows, true);
                    rows = new ArrayList<>();
                }
                i++;
                lastService = dto.getService();
                List<String> row1 = CollUtil.newArrayList("Row", "Issues", "description", "Date Raised", "Status");
                writer.setColumnStyle(i, cellStyle);
                writer.merge(row1.size() - 1, lastService);

            }
            Map<String, Object> row = new LinkedHashMap<>();
            row.put("Row", dto.getRow());
            row.put("Issues", dto.getName());
            row.put("description", dto.getDescription());
            row.put("Date Raised", DateUtil.format(dto.getDateRaised(), "dd-MM-yyyy"));
            row.put("Status", dto.getStatus() == Schedule.PENDING ? "Pending" : "Closed");

            String attachmentUrls = "";
//            if(StringUtils.isNotEmpty(dto.getAttachmentIds())){
//                String[] attachmentIdStrArray = dto.getAttachmentIds().split(",");
//                int [] attachmentIdArray =
//                        Arrays.asList(attachmentIdStrArray).stream().mapToInt(Integer::parseInt).toArray();
//                List<Attachment> attachments = attachmentService.selectAttachmentByIds(attachmentIdArray);
//                if(CollectionUtils.isNotEmpty(attachments)){
//
//                    for (Attachment attachment : attachments) {
//
////                            InputStream stream = URLUtil.getStream(new URL(attachment.getAttachmentUrl()));
////                            OutputStream os = new FileOutputStream("/Users/liulin/upload/upload/2021/08/03/5b992ed8-fcba-4ef7-90f2");
//                        String fileNameTemp =attachment.getFileName();
//                        String downloadPathTemp = LiulinConfig.getDownloadPath();
////                        downloadFromUrl(attachment.getAttachmentUrl(),downloadPathTemp);
//                        File f = new File(downloadPathTemp+getFileNameFromUrl(attachment.getAttachmentUrl()));
//                        URL httpurl = null;
//                        System.out.println(attachment.getAttachmentUrl());
//                        try {
//                            httpurl = new URL("https://pic.ntimg.cn/20110811/8029346_082444436000_2.jpg");
//                            org.apache.commons.io.FileUtils
//                                    .copyURLToFile(httpurl, f);
//                        } catch (MalformedURLException e) {
//                            e.printStackTrace();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//
//
//                        byte[] bytes1 = FileUtil.readBytes(f);
////                            byte[] bytes = IoUtil.write(os);
//                        writePic(writer, 5, i+2, bytes1, HSSFWorkbook.PICTURE_TYPE_PNG);
//
//
//                    }
//                }
//            }
//            row.put("Attachments", dto.getAttachmentIds());
            rows.add(row);
            i++;
        }

        //一次性写出内容，强制输出标题
        writer.write(rows, true);
        writer.autoSizeColumnAll();
        writer.close();
//        String url = serverConfig.getUrl() + fileName;
        //关闭writer，释放内存

        //转换excel到pdf
        Workbook workbook = new Workbook();

        workbook.loadFromFile(downloadPath);

        //Set worksheets to fit to page when converting

        workbook.getConverterSetting().setSheetFitToPage(true);

        //Save the resulting document to a specified path
        String pdfName = UUID.fastUUID() + "_report.pdf";
        workbook.saveToFile(LiulinConfig.getDownloadPath() + pdfName, FileFormat.PDF);

        return AjaxResult.success(pdfName);
    }

    /**
     * 导出Activity Report列表
     */
    @RequiresPermissions("event:report:export")
    @Log(title = "schedule", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(ReportDto reportDto) {
        reportDto.setBuildingId(ShiroUtils.getSysUser().getBuilding().getDeptId());
        List<ReportDto> list = scheduleService.selectReportList(reportDto);
        //通过工具类创建writer
        String prefix = serverConfig.getUrl() + Constants.RESOURCE_PREFIX + "/upload";
        String url = prefix + "/report.xlsx";
        // 上传文件路径
//        String filePath = LiulinConfig.getUploadPath();


        String fileName = UUID.fastUUID() + "_report.xlsx";
        String downloadPath = LiulinConfig.getDownloadPath() + fileName;
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet();
        sheet.setColumnWidth(0,5*256);
        sheet.setColumnWidth(1,10*256);
        sheet.setColumnWidth(2,15*256);
        sheet.setColumnWidth(3,20*256);
        sheet.setColumnWidth(4,10*256);
        sheet.setColumnWidth(5,10*256);

        String lastService = "";

        int i = 0;
        XSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        setBorder(workbook,cellStyle);
        for (ReportDto dto : list) {
            if (!lastService.equals(dto.getService())) {
                lastService = dto.getService();
                XSSFRow row = sheet.createRow(i);
                XSSFCell cell = row.createCell(0);
                cell.setCellValue(lastService);
                XSSFCellStyle titleStyle = workbook.createCellStyle();
                setTitleStyle(workbook, titleStyle,(short)(11+i));
                cell.setCellStyle(titleStyle);
                XSSFCell cell1 = row.createCell(1);
                cell1.setCellStyle(titleStyle);
                XSSFCell cell2 = row.createCell(2);
                cell2.setCellStyle(titleStyle);
                XSSFCell cell3 = row.createCell(3);
                cell3.setCellStyle(titleStyle);
                XSSFCell cell4 = row.createCell(4);
                cell4.setCellStyle(titleStyle);
                XSSFCell cell5 = row.createCell(5);
                cell5.setCellStyle(titleStyle);

                CellRangeAddress region = new CellRangeAddress(i, i, 0, 5);
                sheet.addMergedRegion(region);
                i++;

                XSSFRow rowTitle = sheet.createRow(i);
                XSSFCell Row = rowTitle.createCell(0);
                Row.setCellStyle(cellStyle);
                Row.setCellValue("Row");
                XSSFCell Issues = rowTitle.createCell(1);
                Issues.setCellStyle(cellStyle);
                Issues.setCellValue("Issues");
                XSSFCell Task_Type = rowTitle.createCell(2);
                Task_Type.setCellStyle(cellStyle);
                Task_Type.setCellValue("Task Type");
                XSSFCell Description = rowTitle.createCell(3);
                Description.setCellStyle(cellStyle);
                Description.setCellValue("Description");
                XSSFCell Date_Raised = rowTitle.createCell(4);
                Date_Raised.setCellStyle(cellStyle);
                Date_Raised.setCellValue("Date Raised");
                XSSFCell Status = rowTitle.createCell(5);
                Status.setCellStyle(cellStyle);
                Status.setCellValue("Status");
                i++;
            }

            XSSFRow rowTitle = sheet.createRow(i);
            XSSFCell Row = rowTitle.createCell(0);
            Row.setCellStyle(cellStyle);
            Row.setCellValue(dto.getRow());
            XSSFCell Issues = rowTitle.createCell(1);
            Issues.setCellStyle(cellStyle);
            Issues.setCellValue(dto.getName());
            XSSFCell Task_Type = rowTitle.createCell(2);
            Task_Type.setCellStyle(cellStyle);
            Task_Type.setCellValue(dto.getTaskTypeText());
            XSSFCell Description = rowTitle.createCell(3);
            Description.setCellStyle(cellStyle);
            Description.setCellValue(dto.getDescription());
            XSSFCell Date_Raised = rowTitle.createCell(4);
            Date_Raised.setCellStyle(cellStyle);
            Date_Raised.setCellValue(DateUtil.format(dto.getDateRaised(), "dd-MM-yyyy"));
            XSSFCell Status = rowTitle.createCell(5);
            Status.setCellStyle(cellStyle);
            Status.setCellValue(dto.getStatus() == Schedule.PENDING ? "Pending" : "Closed");
            i++;
        }
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(downloadPath);
            workbook.write(fileOutputStream);
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        //转换excel到pdf
        Workbook workbookConvert = new Workbook();

        workbookConvert.loadFromFile(downloadPath);

        //Set worksheets to fit to page when converting

        workbookConvert.getConverterSetting().setSheetFitToPage(false);

        //Save the resulting document to a specified path
        String pdfName = UUID.fastUUID() + "_report.pdf";

        workbookConvert.saveToFile(LiulinConfig.getDownloadPath() + pdfName, FileFormat.PDF);

        return AjaxResult.success(pdfName);
    }

    private void writePic(ExcelWriter writer, int x, int y, byte[] pictureData, int picType) {
        Sheet sheet = writer.getSheet();
        Drawing drawing = sheet.createDrawingPatriarch();

        ClientAnchor anchor = drawing.createAnchor(0, 0, 0, 0, x, y, x + 1, y + 1);
        anchor.setAnchorType(ClientAnchor.AnchorType.MOVE_AND_RESIZE);

        int pictureIndex = sheet.getWorkbook().addPicture(pictureData, picType);
        drawing.createPicture(anchor, pictureIndex);
    }

    public static String downloadFromUrl(String url, String dir) {

        try {
            URL httpurl = new URL(url);
            String fileName = getFileNameFromUrl(url);
            System.out.println(fileName);
            File f = new File(dir + fileName);
            org.apache.commons.io.FileUtils
                    .copyURLToFile(httpurl, f);
        } catch (Exception e) {
            e.printStackTrace();
            return "Fault!";
        }
        return "Successful!";
    }

    public static String getFileNameFromUrl(String url) {
        String name = new Long(System.currentTimeMillis()).toString() + ".X";
        int index = url.lastIndexOf("/");
        if (index > 0) {
            name = url.substring(index + 1);
            if (name.trim().length() > 0) {
                return name;
            }
        }
        return name;
    }

    private void setBorder(XSSFWorkbook workbook,XSSFCellStyle style) {
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setWrapText(true);
    }

    private void setTitleStyle(XSSFWorkbook workbook, XSSFCellStyle style,Short i) {
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);

        style.setFillForegroundColor(i);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        XSSFFont font = workbook.createFont();
        font.setFontHeightInPoints((short) 16);
        style.setFont(font);


    }
}
