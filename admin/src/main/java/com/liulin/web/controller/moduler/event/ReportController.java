package com.liulin.web.controller.moduler.event;

import cn.hutool.core.date.DateUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.alibaba.fastjson.JSONObject;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.UnitValue;
import com.liulin.common.annotation.Log;
import com.liulin.common.config.LiulinConfig;
import com.liulin.common.config.ServerConfig;
import com.liulin.common.constant.Constants;
import com.liulin.common.core.controller.BaseController;
import com.liulin.common.core.domain.AjaxResult;
import com.liulin.common.core.domain.entity.SysDept;
import com.liulin.common.core.page.TableDataInfo;
import com.liulin.common.enums.BusinessType;
import com.liulin.common.utils.ShiroUtils;
import com.liulin.common.utils.StringUtils;
import com.liulin.common.utils.file.AwsFileUtils;
import com.liulin.common.utils.file.FileTypeUtils;
import com.liulin.common.utils.pdf.PdfItextUtils;
import com.liulin.common.utils.uuid.UUID;
import com.liulin.system.domain.Attachment;
import com.liulin.system.domain.ReportDto;
import com.liulin.system.domain.Schedule;
import com.liulin.system.service.IAttachmentService;
import com.liulin.system.service.IScheduleService;
import com.spire.xls.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
     * 导出Activity Report列表
     */
//    @RequiresPermissions("event:report:export")
//    @Log(title = "schedule", businessType = BusinessType.EXPORT)
//    @PostMapping("/export")
//    @ResponseBody
    public AjaxResult exportReMake(ReportDto reportDto) {
        Long startTime =DateUtil.current();
        reportDto.setBuildingId(ShiroUtils.getSysUser().getBuilding().getDeptId());
        String logoUrl = ShiroUtils.getSysUser().getBuilding().getLogo();
        SysDept building = ShiroUtils.getSysUser().getBuilding();
        List<ReportDto> list = scheduleService.selectReportList(reportDto);
        //通过工具类创建writer
        String prefix = serverConfig.getUrl() + Constants.RESOURCE_PREFIX + "/upload";
        String url = prefix + "/report.xlsx";
        // 上传文件路径
//        String filePath = LiulinConfig.getUploadPath();

        Workbook workbook = new Workbook();
        List<File> needDeleteFile = new ArrayList<>();
        String fileName = UUID.fastUUID() + "_report.xlsx";
        String downloadPath = LiulinConfig.getDownloadPath() + fileName;
//        XSSFWorkbook workbook = new XSSFWorkbook();
//        XSSFSheet sheet = workbook.createSheet();
        //获取第一张工作表（新建的Workbook默认包含3张工作表）
        Worksheet sheet = workbook.getWorksheets().get(0);
        String localPath = LiulinConfig.getProfile()+"/aws/";
        String logoFilePath = AwsFileUtils.amazonS3DownloadingByUrl(logoUrl, localPath);
        File logoFile = new File(logoFilePath);
        needDeleteFile.add(logoFile);
        //加载图片
        BufferedImage image = null;
        try {
            image = ImageIO.read(logoFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //设置图片页眉
        sheet.getPageSetup().setLeftHeaderImage(image);
        sheet.getPageSetup().setLeftHeader("&G");
        sheet.getPageSetup().setLeftHeaderPictureHeight(60);
        sheet.getPageSetup().setLeftHeaderPictureWidth(200);
        StringBuffer rhsb = new StringBuffer();
        rhsb.append("&\"Tahoma\"&9 ");
        rhsb.append(building.getSpn()+" ");
        rhsb.append(building.getDeptName()+"\n");
        rhsb.append("BM Monthly Report"+"\n");
        if(StringUtils.isNotEmpty((String) reportDto.getParams().get("beginStartDate"))||StringUtils.isNotEmpty((String) reportDto.getParams().get("endStartDate"))){
            rhsb.append("("+reportDto.getParams().get("beginStartDate")+"-"+reportDto.getParams().get("endStartDate")+")");
        }

        sheet.getPageSetup().setRightHeader(rhsb.toString());

        //设置显示样式
        sheet.setViewMode(ViewMode.Layout);

        //为第一张工作表设置名称
        sheet.setName("Report");
//        XSSFDrawing drawing = sheet.createDrawingPatriarch();
        sheet.setColumnWidth(1,5);
        sheet.setColumnWidth(2,10);
        sheet.setColumnWidth(3,10);
        sheet.setColumnWidth(4,10);
        sheet.setColumnWidth(5,10);
        sheet.setColumnWidth(6,10);
        sheet.setColumnWidth(7,20);

        String lastService = "";
//        byte[] bytes = ImageUtils.readFileFromAws(logoUrl);
//        byte[] bytes = ImageUtils.getImage("C:\\image\\profile\\69152d83-a2d0-4cdb-aa16-7bb4de006236.png");
        int rowNum = 1;
        int column = 7;
        CellStyle cellStyle = workbook.getStyles().addStyle("data style");
        setBorder(cellStyle);

        CellStyle titleStyle = workbook.getStyles().addStyle("title style");
        titleStyle.setHorizontalAlignment(HorizontalAlignType.Center);
        titleStyle.setVerticalAlignment(VerticalAlignType.Center);
        titleStyle.setColor(Color.gray);
        for (ReportDto dto : list) {
            if (!lastService.equals(dto.getService())) {
                lastService = dto.getService();
                setTitleStyle(sheet,rowNum,column,dto.getService(),titleStyle);
                rowNum+=2;
            }
            sheet.setRowHeight(rowNum,40);

            CellRange Row = sheet.getCellRange(rowNum, 1);
            Row.setStyle(cellStyle);
            Row.setText(String.valueOf(dto.getRow()));

            CellRange Issues = sheet.getCellRange(rowNum, 2);
            Issues.setStyle(cellStyle);
            Issues.setText(dto.getName());

            CellRange Task_Type = sheet.getCellRange(rowNum, 3);
            Task_Type.setStyle(cellStyle);
            Task_Type.setText(dto.getTaskTypeText());

            CellRange Description = sheet.getCellRange(rowNum, 4);
            Description.setStyle(cellStyle);
            Description.setText(dto.getDescription());


            CellRange Date_Raised = sheet.getCellRange(rowNum, 5);
            Date_Raised.setStyle(cellStyle);
            Date_Raised.setText(DateUtil.format(dto.getDateRaised(), "dd-MM-yyyy"));

            CellRange Status = sheet.getCellRange(rowNum, 6);
            Status.setStyle(cellStyle);
            Status.setText(dto.getStatus() == Schedule.PENDING ? "Pending" : "Closed");

            int count = 0;
            //添加图片到工作表的指定位置
            if(StringUtils.isNotEmpty(reportDto.getAttachmentIds())){
                String[] attachmentIds = reportDto.getAttachmentIds().split(",");
                long [] attachmentIdArray =
                        Arrays.asList(attachmentIds).stream().mapToLong(Long::parseLong).toArray();
                List<Attachment> attachments = attachmentService.selectAttachmentByIds(attachmentIdArray);
                for (Attachment attachment : attachments) {
                    if(count==2){
                        break;
                    }
                    if(FileTypeUtils.getFileTypeByExt(attachment.getExt()).equals("image")){
                        String filePathPrefix = LiulinConfig.getProfile()+"/aws/";
                        String filePath = AwsFileUtils.amazonS3DownloadingByUrl(logoUrl, filePathPrefix);
                        File file = new File(filePath);
                        needDeleteFile.add(file);
                        ExcelPicture pic = sheet.getPictures().add(rowNum, 7,filePath);
                        pic.setWidth(40);
                        pic.setHeight(40);
                        pic.setLeftColumnOffset(75+325*count);
                        pic.setTopRowOffset(20);
                        count++;
                    }
                }
            }
            rowNum++;
        }
        CellRange cellRange = sheet.getCellRange(sheet.getFirstRow(), sheet.getFirstColumn(), sheet.getLastRow(), sheet.getLastColumn());
        cellRange.getBorders().setLineStyle(LineStyleType.Thin);
        cellRange.getBorders().getByBordersLineType(BordersLineType.DiagonalDown).setLineStyle(LineStyleType.None);
        cellRange.getBorders().getByBordersLineType(BordersLineType.DiagonalUp).setLineStyle(LineStyleType.None);
        cellRange.getBorders().setColor(Color.black);

        //保存结果文档
        workbook.saveToFile(downloadPath, FileFormat.Version2013);
        long excelEnd = DateUtil.current();
        logger.info("Excel生成耗时:{}ms",excelEnd-startTime);

        //转换excel到pdf
        Workbook workbookConvert = new Workbook();

        workbookConvert.loadFromFile(downloadPath);

        //Set worksheets to fit to page when converting

        workbookConvert.getConverterSetting().setSheetFitToPage(true);

        //Save the resulting document to a specified path
        String pdfName = UUID.fastUUID() + "_report.pdf";

        workbookConvert.saveToFile(LiulinConfig.getDownloadPath() + pdfName, FileFormat.PDF);
        logger.info("Excel转PDF耗时:{}ms",DateUtil.current()-excelEnd);
        //删除临时文件
        if(CollectionUtils.isNotEmpty(needDeleteFile)){
            needDeleteFile.forEach(file->{
                file.delete();
            });
        }


        return AjaxResult.success(pdfName);
    }


    /**
     * 导出Activity Report列表
     */
    @RequiresPermissions("event:report:export")
    @Log(title = "schedule", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult exportItextEdition(ReportDto reportDto) throws MalformedURLException {
        String prefix = serverConfig.getUrl() + Constants.RESOURCE_PREFIX + "/upload";
        String pdfName = UUID.fastUUID() + "_report.pdf";
        String pdfSavePath = LiulinConfig.getDownloadPath() + pdfName;
        //需要删除的临时文件
        List<File> needDeleteFile = new ArrayList<>();
        File pdfFile = new File(pdfSavePath);
        Document doc = null;
        PdfWriter writer = null;
        try {
            writer = new PdfWriter(pdfFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        PdfDocument pdfDoc = new PdfDocument(writer);
        doc = new Document(pdfDoc);
        Long startTime =DateUtil.current();
        reportDto.setBuildingId(ShiroUtils.getSysUser().getBuilding().getDeptId());
        String logoUrl = ShiroUtils.getSysUser().getBuilding().getLogo();
        SysDept building = ShiroUtils.getSysUser().getBuilding();
        List<ReportDto> list = scheduleService.selectReportList(reportDto);
        //通过工具类创建writer

        String localPath = LiulinConfig.getProfile()+"/aws/";
        String logoFilePath = AwsFileUtils.amazonS3DownloadingByUrl(logoUrl, localPath);
        File logoFile = new File(logoFilePath);
        needDeleteFile.add(logoFile);

        UnitValue[] unitValue = new UnitValue[]{
                UnitValue.createPercentValue((float) 5),
                UnitValue.createPercentValue((float) 20),
                UnitValue.createPercentValue((float) 10),
                UnitValue.createPercentValue((float) 20),
                UnitValue.createPercentValue((float) 15),
                UnitValue.createPercentValue((float) 10),
                UnitValue.createPercentValue((float) 10),
                UnitValue.createPercentValue((float) 10)};
        Table table = new Table(unitValue);

        String lastService = "";
        StringBuffer rhsb = new StringBuffer();
        rhsb.append(building.getSpn()+" ");
        rhsb.append(building.getDeptName()+"\n");
        rhsb.append("BM Monthly Report"+"\n");

        Float titleFontSize = 10f;
        for (ReportDto dto : list) {
            if (!lastService.equals(dto.getService())) {
                lastService = dto.getService();
                PdfItextUtils.addTitle(titleFontSize,table,lastService);
            }
            JSONObject data = (JSONObject) JSONObject.toJSON(dto);

            int count=1;
            //添加图片到工作表的指定位置
            if(StringUtils.isNotEmpty(dto.getAttachmentIds())){
                String[] attachmentIds = dto.getAttachmentIds().split(",");
                long [] attachmentIdArray =
                        Arrays.asList(attachmentIds).stream().mapToLong(Long::parseLong).toArray();
                List<Attachment> attachments = attachmentService.selectAttachmentByIds(attachmentIdArray);
                for (Attachment attachment : attachments) {
                    if(count==3){
                        break;
                    }
                    if(FileTypeUtils.getFileTypeByExt(attachment.getExt()).equals("image")){
                        String filePathPrefix = LiulinConfig.getProfile()+"/aws/";
                        String filePath = AwsFileUtils.amazonS3DownloadingByUrl(attachment.getAttachmentUrl(), filePathPrefix);
                        File file = new File(filePath);
                        data.put("image"+count,filePath);
                        needDeleteFile.add(file);
                        count++;
                    }
                }
            }
            PdfItextUtils.addContent(titleFontSize,table,data);

        }
        PdfItextUtils.addHeaderToPdf(pdfDoc,logoFilePath,rhsb.toString());
        pdfDoc.addNewPage();
        doc.add(table);
        logger.info("Excel转PDF耗时:{}ms",DateUtil.current()-startTime);

        if (doc != null) {
            doc.close();
        }
        //删除临时文件
        if(CollectionUtils.isNotEmpty(needDeleteFile)){
            needDeleteFile.forEach(file->{
                file.delete();
            });
        }
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

    private void setBorder(CellStyle style) {
        style.setVerticalAlignment(VerticalAlignType.Center);
        style.setWrapText(true);
    }

    private void setTitleStyle(Worksheet sheet,Integer row,Integer column,String title,CellStyle cellStyle) {
        //合并单元格
        sheet.getRange().get(row,1,row,column).merge();
        //给指定单元格区域设置背景颜色
        sheet.getCellRange(row,1,row,column).getStyle().setColor(Color.gray);
        CellRange cellRange = sheet.getCellRange(row, 1, row, column);
        cellRange.setText(title);
        cellRange.setStyle(cellStyle);

        row++;
        CellRange Row = sheet.getCellRange(row, 1);
        Row.setText("Row");

        CellRange Issues = sheet.getCellRange(row, 2);
        Issues.setText("Issues");

        CellRange Task_Type = sheet.getCellRange(row, 3);
        Task_Type.setText("Task Type");

        CellRange Description = sheet.getCellRange(row, 4);
        Description.setText("Description");

        CellRange Date_Raised = sheet.getCellRange(row, 5);
        Date_Raised.setText("Date Raised");

        CellRange Status = sheet.getCellRange(row, 6);
        Status.setText("Status");

        CellRange Photos = sheet.getCellRange(row, 7);
        Photos.setText("Photos");
    }
}
