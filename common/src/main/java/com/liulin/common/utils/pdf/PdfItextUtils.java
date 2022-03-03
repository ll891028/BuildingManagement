package com.liulin.common.utils.pdf;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSONObject;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.action.PdfAction;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import com.itextpdf.layout.property.VerticalAlignment;
import com.liulin.common.utils.DateUtils;
import com.liulin.common.utils.file.AwsFileUtils;
import org.junit.platform.commons.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.util.Date;

public class PdfItextUtils {

    private static final Logger log = LoggerFactory.getLogger(PdfItextUtils.class);

    public static void addHeaderToPdf(PdfDocument pdfDoc,String logoFilePath,String rightText) {
        pdfDoc.addEventHandler(PdfDocumentEvent.END_PAGE,
                new DemoHeaderFooterHandler(logoFilePath,rightText));
    }

    public static void addTitle(Float titleFontSize,Table table,String serviceName ) {
        Color color = new DeviceRgb(255,100,20);
        Cell title=new Cell(1,8).add(new Paragraph(serviceName)).setTextAlignment(TextAlignment.CENTER).setBackgroundColor(color);
        Cell Row=new Cell().add(new Paragraph("Row").setFontSize(titleFontSize)).setTextAlignment(TextAlignment.CENTER);
        Cell Issues=new Cell().add(new Paragraph("Issues").setFontSize(titleFontSize)).setTextAlignment(TextAlignment.CENTER);
        Cell Task_Type=new Cell().add(new Paragraph("Task Type").setFontSize(titleFontSize)).setTextAlignment(TextAlignment.CENTER);
        Cell Description=new Cell().add(new Paragraph("Description").setFontSize(titleFontSize)).setTextAlignment(TextAlignment.CENTER);
        Cell Date_Raised=new Cell().add(new Paragraph(" Date Raised").setFontSize(titleFontSize)).setTextAlignment(TextAlignment.CENTER);
        Cell Status=new Cell().add(new Paragraph("Status").setFontSize(titleFontSize)).setTextAlignment(TextAlignment.CENTER);
        Cell Photos=new Cell(1,2).add(new Paragraph("Photos").setFontSize(titleFontSize)).setTextAlignment(TextAlignment.CENTER);
        table.addCell(title);
        table.addCell(Row);
        table.addCell(Issues);
        table.addCell(Task_Type);
        table.addCell(Description);
        table.addCell(Date_Raised);
        table.addCell(Status);
        table.addCell(Photos);
    }

    public static void addContent(Float titleFontSize, Table table, JSONObject jsonObject) throws MalformedURLException {
        Cell row=new Cell().add(new Paragraph(jsonObject.getString("row")).setFontSize(titleFontSize)).setTextAlignment(TextAlignment.CENTER);
        Cell issues=new Cell().add(new Paragraph(jsonObject.getString("name")).setFontSize(titleFontSize)).setTextAlignment(TextAlignment.CENTER);
        Cell taskType=new Cell().add(new Paragraph(jsonObject.getString("taskTypeText")).setFontSize(titleFontSize)).setTextAlignment(TextAlignment.CENTER);
        Cell description=new Cell();
        if(StringUtils.isNotBlank(jsonObject.getString("description"))){
            description.add(new Paragraph(jsonObject.getString("description")).setFontSize(titleFontSize)).setTextAlignment(TextAlignment.CENTER);
        }
        Cell dateRaised=new Cell().add(new Paragraph(DateUtil.format(jsonObject.getDate("dateRaised"), "dd-MM-yyyy")).setFontSize(titleFontSize)).setTextAlignment(TextAlignment.CENTER);
        Cell status=new Cell().add(new Paragraph(jsonObject.getInteger("status") == 2 ? "Pending" : "Closed").setFontSize(titleFontSize)).setTextAlignment(TextAlignment.CENTER);
        Cell photo1=new Cell();
        Cell photo2=new Cell();
        if(StringUtils.isNotBlank(jsonObject.getString("image1"))){
            Image image = new Image(ImageDataFactory.create(jsonObject.getString("image1")));
            image.setHeight(50);
            image.setWidth(50);
            photo1=new Cell()
                    .add(image);
        }
        if(StringUtils.isNotBlank(jsonObject.getString("image2"))){
            Image image = new Image(ImageDataFactory.create(jsonObject.getString("image2")));
            image.setHeight(50);
            image.setWidth(50);
            photo2=new Cell()
                    .add(image);
        }

        table.addCell(row);
        table.addCell(issues);
        table.addCell(taskType);
        table.addCell(description);
        table.addCell(dateRaised);
        table.addCell(status);
        table.addCell(photo1);
        table.addCell(photo2);
    }
    public static void addWorkOrderTitle(Float titleFontSize,Table table,String serviceName ) {
        Color color = new DeviceRgb(255,100,20);
        Cell title=new Cell(1,8).add(new Paragraph(serviceName)).setTextAlignment(TextAlignment.CENTER).setBackgroundColor(color);
        Cell Row=new Cell().add(new Paragraph("Row").setFontSize(titleFontSize)).setTextAlignment(TextAlignment.CENTER);
        Cell Issues=new Cell().add(new Paragraph("Issues").setFontSize(titleFontSize)).setTextAlignment(TextAlignment.CENTER);
        Cell Task_Type=new Cell().add(new Paragraph("Task Type").setFontSize(titleFontSize)).setTextAlignment(TextAlignment.CENTER);
        Cell Description=new Cell().add(new Paragraph("Description").setFontSize(titleFontSize)).setTextAlignment(TextAlignment.CENTER);
        Cell Date_Raised=new Cell().add(new Paragraph(" Date Raised").setFontSize(titleFontSize)).setTextAlignment(TextAlignment.CENTER);
        Cell Status=new Cell().add(new Paragraph("Status").setFontSize(titleFontSize)).setTextAlignment(TextAlignment.CENTER);
        Cell Photos=new Cell(1,2).add(new Paragraph("Photos").setFontSize(titleFontSize)).setTextAlignment(TextAlignment.CENTER);
        table.addCell(title);
        table.addCell(Row);
        table.addCell(Issues);
        table.addCell(Task_Type);
        table.addCell(Description);
        table.addCell(Date_Raised);
        table.addCell(Status);
        table.addCell(Photos);
    }
    public static void addWorkOrderContent(Float titleFontSize, Table table, JSONObject jsonObject) throws MalformedURLException {
         Cell row=new Cell().add(new Paragraph(jsonObject.getString("row")).setFontSize(titleFontSize)).setTextAlignment(TextAlignment.CENTER);
        Cell issues=new Cell().add(new Paragraph(jsonObject.getString("name")).setFontSize(titleFontSize)).setTextAlignment(TextAlignment.CENTER);
        Cell taskType=new Cell().add(new Paragraph(jsonObject.getString("taskTypeText")).setFontSize(titleFontSize)).setTextAlignment(TextAlignment.CENTER);
        Cell description=new Cell();
        if(StringUtils.isNotBlank(jsonObject.getString("description"))){
            description.add(new Paragraph(jsonObject.getString("description")).setFontSize(titleFontSize)).setTextAlignment(TextAlignment.CENTER);
        }
        Cell dateRaised=new Cell().add(new Paragraph(DateUtil.format(jsonObject.getDate("dateRaised"), "dd-MM-yyyy")).setFontSize(titleFontSize)).setTextAlignment(TextAlignment.CENTER);
        Cell status=new Cell().add(new Paragraph(jsonObject.getInteger("status") == 2 ? "Pending" : "Closed").setFontSize(titleFontSize)).setTextAlignment(TextAlignment.CENTER);
        Cell photo1=new Cell();
        Cell photo2=new Cell();
        if(StringUtils.isNotBlank(jsonObject.getString("image1"))){
            Image image = new Image(ImageDataFactory.create(jsonObject.getString("image1")));
            image.setHeight(50);
            image.setWidth(50);
            photo1=new Cell()
                    .add(image);
        }
        if(StringUtils.isNotBlank(jsonObject.getString("image2"))){
            Image image = new Image(ImageDataFactory.create(jsonObject.getString("image2")));
            image.setHeight(50);
            image.setWidth(50);
            photo2=new Cell()
                    .add(image);
        }

        table.addCell(row);
        table.addCell(issues);
        table.addCell(taskType);
        table.addCell(description);
        table.addCell(dateRaised);
        table.addCell(status);
        table.addCell(photo1);
        table.addCell(photo2);
    }

    public static boolean genWorkOrderPdf(String pdfPath,String managerName,String contactEmail,String contactNumber,
                                       String spn,String spnLogo,String buildingName,
                                       String buildingAddress,String serviceName,Date createTime,Date dueBy,String priority,
                                       String taskName,String description,String media1,String media2,String workOrderNumber,
                                       String companyName,String supplierName,String supplierContactNumber,String supplierEmail,String keyName) {
        boolean flag = true;
        File file = new File(pdfPath);
        if(!file.getParentFile().exists()){
            file.getParentFile().mkdirs();
        }
        Document doc = null;
        try {
            PdfWriter writer = new PdfWriter(file);
            PdfDocument pdfDoc = new PdfDocument(writer);
            doc = new Document(pdfDoc);
            Float titleFontSize = 10f;
            //创建总表形式（一行四格）
            UnitValue[] unitValue = new UnitValue[]{
                    UnitValue.createPercentValue((float) 25),
                    UnitValue.createPercentValue((float) 25),
                    UnitValue.createPercentValue((float) 25),
                    UnitValue.createPercentValue((float) 25),
            };
            Table table = new Table(unitValue);
            Color color = new DeviceRgb(255,100,20);
            Color colorWhite = new DeviceRgb(255,255,255);
            Image logoImg = null;
            Image media1Img = null;
            Image media2Img = null;
            if(StringUtils.isNotBlank(spnLogo)){
                ImageData imageData = ImageDataFactory.create(spnLogo);
                logoImg = new Image(imageData);
            }
            if(StringUtils.isNotBlank(media1)){
                ImageData media1Data = ImageDataFactory.create(media1);
                media1Img = new Image(media1Data);
            }
            if(StringUtils.isNotBlank(media2)){
                ImageData media2Data = ImageDataFactory.create(media2);
                media2Img = new Image(media2Data);
            }

            logoImg.setAutoScale(true);
//            logoImg.scaleToFit(200f,200f);
//            logoImg.setMargins(40, 0, 0, 0);
            Border titleBorder =new SolidBorder(colorWhite,1f);

            Cell companyLogo=new Cell(5,2).setVerticalAlignment(VerticalAlignment.MIDDLE).setBorder(Border.NO_BORDER).setHeight(100f);
            companyLogo.add(logoImg);
            Cell workOrderTitle=new Cell(1,1).add(new Paragraph("WO REQUEST #")).setTextAlignment(TextAlignment.LEFT).setBackgroundColor(color).setBorder(titleBorder);
            UnitValue[] innerTableUnitValue = new UnitValue[]{
                    UnitValue.createPercentValue((float) 100),

            };
            Table workOrderTable = new Table(innerTableUnitValue);
            Paragraph workOrderPara = new Paragraph(workOrderNumber);
            Cell workOrderTextInner=new Cell(1,1).add(workOrderPara).setWidth(200f).setTextAlignment(TextAlignment.CENTER);
            workOrderTable.addCell(workOrderTextInner);
            Cell workOrderText=new Cell(1,1).add(workOrderTable).setBorder(Border.NO_BORDER);
            table.addCell(companyLogo);
            table.addCell(workOrderTitle);
            table.addCell(workOrderText);

            Cell dateRequestedTitle=new Cell(1,1).add(new Paragraph("DATE REQUESTED")).setTextAlignment(TextAlignment.LEFT).setBackgroundColor(color).setBorder(titleBorder);
            Paragraph dateRequestedPara = new Paragraph(DateUtils.parseDateToStr("dd/MM/yyyy",createTime));
            Cell dateRequestedTextInner=new Cell(1,1).add(dateRequestedPara).setWidth(200f).setTextAlignment(TextAlignment.CENTER);
            Table dateRequestedTable = new Table(innerTableUnitValue);
            dateRequestedTable.addCell(dateRequestedTextInner);
            Cell dateRequestedText=new Cell(1,1).add(dateRequestedTable).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER);
            table.addCell(dateRequestedTitle);
            table.addCell(dateRequestedText);

            Cell dueByTitle=new Cell(1,1).add(new Paragraph("DUE BY")).setTextAlignment(TextAlignment.LEFT).setBackgroundColor(color).setBorder(titleBorder);
            Paragraph dueByPara = new Paragraph(DateUtils.parseDateToStr("dd/MM/yyyy",dueBy));
            Cell dueByTextInner=new Cell(1,1).add(dueByPara).setWidth(200f).setTextAlignment(TextAlignment.CENTER);
            Table dueByTable = new Table(innerTableUnitValue);
            dueByTable.addCell(dueByTextInner);
            Cell dueByText=new Cell(1,1).add(dueByTable).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER);
            table.addCell(dueByTitle);
            table.addCell(dueByText);

            Cell priorityTitle=new Cell(1,1).add(new Paragraph("PRIORITY")).setTextAlignment(TextAlignment.LEFT).setBackgroundColor(color).setBorder(titleBorder);
            Paragraph priorityPara = new Paragraph(priority);
            Cell priorityInner=new Cell(1,1).add(priorityPara).setWidth(200f).setTextAlignment(TextAlignment.CENTER);
            Table priorityTable = new Table(innerTableUnitValue);
            priorityTable.addCell(priorityInner);
            Cell priorityText=new Cell(1,1).add(priorityTable).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER);
            table.addCell(priorityTitle);
            table.addCell(priorityText);

            Cell AssignedToTitle=new Cell(1,2).add(new Paragraph("ASSIGNED TO ")).setTextAlignment(TextAlignment.LEFT).setBackgroundColor(color).setMinHeight(30).setBorder(titleBorder);
            table.addCell(AssignedToTitle);
            Float leadingSize = 0.7f;
            Paragraph p1 = new Paragraph(companyName);
            p1.setMultipliedLeading(leadingSize);
            Cell cell1=new Cell(1,2).add(p1).setTextAlignment(TextAlignment.LEFT).setBorder(Border.NO_BORDER).setFontSize(11f).setFontColor(color);
            table.addCell(cell1);

            Paragraph p2 = new Paragraph(supplierName);
            p2.setMultipliedLeading(leadingSize);
            Cell cell1_1=new Cell(1,2).add(p2).setTextAlignment(TextAlignment.LEFT).setBorder(Border.NO_BORDER).setFontSize(11f);
            table.addCell(cell1_1);


            Paragraph p3 = new Paragraph(buildingName+" - "+spn);
            p3.setMultipliedLeading(leadingSize);
            Cell cell2=new Cell(1,2).add(p3).setTextAlignment(TextAlignment.LEFT).setBorder(Border.NO_BORDER).setFontSize(11f);
            table.addCell(cell2);

            Paragraph p4 = new Paragraph("Mob: "+supplierContactNumber);
            p4.setMultipliedLeading(leadingSize);
            Cell cell2_1=new Cell(1,2).add(p4).setTextAlignment(TextAlignment.LEFT).setBorder(Border.NO_BORDER).setFontSize(11f);
            table.addCell(cell2_1);

            Paragraph p5 = new Paragraph(buildingAddress);
            p5.setMultipliedLeading(leadingSize);
            Cell cell3=new Cell(1,2).add(p5).setTextAlignment(TextAlignment.LEFT).setBorder(Border.NO_BORDER).setFontSize(11f);
            table.addCell(cell3);
            if(supplierEmail==null){
                supplierEmail="";
            }
            Paragraph p6 = new Paragraph("Email: "+supplierEmail);
            p6.setMultipliedLeading(leadingSize);
            Cell cell3_1=new Cell(1,2).add(p6).setTextAlignment(TextAlignment.LEFT).setBorder(Border.NO_BORDER).setFontSize(11f);
            table.addCell(cell3_1);

            Paragraph p7 = new Paragraph("Site Contact");
            p7.setMultipliedLeading(leadingSize);
            Cell cell4=new Cell(1,4).add(p7).setTextAlignment(TextAlignment.LEFT).setBorder(Border.NO_BORDER).setFontSize(11f).setFontColor(color);
            table.addCell(cell4);

            Paragraph p8 = new Paragraph(managerName);
            p8.setMultipliedLeading(leadingSize);
            Cell cell5=new Cell(1,4).add(p8).setTextAlignment(TextAlignment.LEFT).setBorder(Border.NO_BORDER).setFontSize(11f);
            table.addCell(cell5);

            Paragraph p9 = new Paragraph(contactNumber);
            p9.setMultipliedLeading(leadingSize);
            Cell cell6=new Cell(1,4).add(p9).setTextAlignment(TextAlignment.LEFT).setBorder(Border.NO_BORDER);
            table.addCell(cell6);

            Paragraph p10 = new Paragraph(contactEmail);
            p10.setMultipliedLeading(leadingSize);
            PdfAction act = PdfAction.createURI(contactEmail);
            Cell cell7=new Cell(1,4).add(p10.setAction(act)).setTextAlignment(TextAlignment.LEFT).setBorder(Border.NO_BORDER).setFontSize(11f);
            table.addCell(cell7);

            Paragraph p11 = new Paragraph("\n");
            p11.setMultipliedLeading(leadingSize);
            Cell cell8=new Cell(1,4).add(p11).setTextAlignment(TextAlignment.LEFT).setBorder(Border.NO_BORDER);
            table.addCell(cell8);

            Cell jobInformation=new Cell(1,4).add(new Paragraph("SERVICE INFORMATION")).setTextAlignment(TextAlignment.LEFT).setBackgroundColor(color).setBorder(Border.NO_BORDER);
            table.addCell(jobInformation);

            Paragraph p12 = new Paragraph("Service");
            p12.setMultipliedLeading(leadingSize);
            Cell cell9=new Cell(1,4).add(p12).setTextAlignment(TextAlignment.LEFT).setBorder(Border.NO_BORDER).setFontSize(11f).setFontColor(color);
            table.addCell(cell9);

            Paragraph p13 = new Paragraph(serviceName);
            p13.setMultipliedLeading(leadingSize);
            Cell cell10=new Cell(1,4).add(p13).setTextAlignment(TextAlignment.LEFT).setBorder(Border.NO_BORDER).setFontSize(11f);
            table.addCell(cell10);

//            Paragraph p14 = new Paragraph("\n");
//            p14.setMultipliedLeading(leadingSize);
//            Cell cell11=new Cell(1,4).add(p14).setTextAlignment(TextAlignment.LEFT).setBorder(Border.NO_BORDER);
//            table.addCell(cell11);

//            Paragraph p15 = new Paragraph("Job Contact");
//            p15.setMultipliedLeading(leadingSize);
//            Cell cell12=new Cell(1,4).add(p15).setTextAlignment(TextAlignment.LEFT).setBorder(Border.NO_BORDER).setFontSize(11f).setFontColor(color);
//            table.addCell(cell12);
//
//            Paragraph p16 = new Paragraph("205 – Jana Bartolo Mob: Ph: 402452266");
//            p16.setMultipliedLeading(leadingSize);
//            Cell cell13=new Cell(1,4).add(p16).setTextAlignment(TextAlignment.LEFT).setBorder(Border.NO_BORDER).setFontSize(11f);
//            table.addCell(cell13);

            Paragraph p17 = new Paragraph("\n\n");
            p17.setMultipliedLeading(leadingSize);
            Cell cell14=new Cell(1,4).add(p17).setTextAlignment(TextAlignment.LEFT).setBorder(Border.NO_BORDER);
            table.addCell(cell14);

            Cell subject =new Cell(1,4).add(new Paragraph("TASK NAME")).setTextAlignment(TextAlignment.LEFT).setBackgroundColor(color).setBorder(Border.NO_BORDER);
            table.addCell(subject);

            Paragraph p18 = new Paragraph(taskName);
            p18.setMultipliedLeading(leadingSize);
            Cell cell15=new Cell(1,4).add(p18).setTextAlignment(TextAlignment.LEFT).setBorder(Border.NO_BORDER);
            table.addCell(cell15);

            Paragraph p19 = new Paragraph("\n\n");
            p19.setMultipliedLeading(leadingSize);
            Cell cell16=new Cell(1,4).add(p19).setTextAlignment(TextAlignment.LEFT).setBorder(Border.NO_BORDER);
            table.addCell(cell16);

            Cell descriptionCell =new Cell(1,4).add(new Paragraph("DESCRIPTION")).setTextAlignment(TextAlignment.LEFT).setBackgroundColor(color).setBorder(Border.NO_BORDER);
            table.addCell(descriptionCell);

            Paragraph p20 = new Paragraph(description);
            p18.setMultipliedLeading(leadingSize);
            Cell cell17=new Cell(1,4).add(p20).setTextAlignment(TextAlignment.LEFT).setBorder(Border.NO_BORDER);
            table.addCell(cell17);

            Paragraph p21 = new Paragraph("\n\n");
            p21.setMultipliedLeading(leadingSize);
            Cell cell18=new Cell(1,4).add(p21).setTextAlignment(TextAlignment.LEFT).setBorder(Border.NO_BORDER);
            table.addCell(cell18);

            Cell media =new Cell(1,4).add(new Paragraph("MEDIA")).setTextAlignment(TextAlignment.LEFT).setBackgroundColor(color).setBorder(Border.NO_BORDER);
            table.addCell(media);

            Cell media1Cell=new Cell(5,2).setVerticalAlignment(VerticalAlignment.MIDDLE).setBorder(Border.NO_BORDER);
            if(media1Img!=null){
                media1Cell.add(media1Img);
            }
            Cell media2Cell=new Cell(5,2).setVerticalAlignment(VerticalAlignment.MIDDLE).setBorder(Border.NO_BORDER);
            if(media2Img!=null){
                media2Cell.add(media2Img);
            }
            table.addCell(media1Cell);
            table.addCell(media2Cell);

            pdfDoc.addNewPage();
            pdfDoc.setDefaultPageSize(PageSize.A4);
            doc.add(table);

        } catch (FileNotFoundException | MalformedURLException e) {
            e.printStackTrace();
            flag = false;
        }finally {
            if (doc != null) {
                doc.close();
            }
            File fileTmp = new File(spnLogo);
            if(fileTmp.exists()){
                fileTmp.delete();
            }
            File media1Tmp = new File(media1);
            if(media1Tmp.exists()){
                media1Tmp.delete();
            }
            File media2Tmp = new File(media2);
            if(media2Tmp.exists()){
                media2Tmp.delete();
            }
            if(file.exists()){
                AwsFileUtils.putObject(keyName,file);
                file.delete();
            }

            return flag;
        }
    }

    public static void main2(String[] args) {
        File file = new File("C:\\image\\profile\\test.pdf");
        Document doc = null;
        try {
            PdfWriter writer = new PdfWriter(file);
            PdfDocument pdfDoc = new PdfDocument(writer);
            doc = new Document(pdfDoc);
            Float titleFontSize = 10f;
            //创建总表形式（一行四格）
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

            addTitle(titleFontSize,table,"Lift");
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("row","1");
            jsonObject.put("name","2222");
            jsonObject.put("taskTypeText","Task");
            jsonObject.put("description","333");
            jsonObject.put("dateRaised",new Date());
            jsonObject.put("status",2);
            jsonObject.put("image","C:\\image\\profile\\69152d83-a2d0-4cdb-aa16-7bb4de006236.png");
            addContent(titleFontSize,table,jsonObject);
            addContent(titleFontSize,table,jsonObject);
            addContent(titleFontSize,table,jsonObject);
            addContent(titleFontSize,table,jsonObject);
            addTitle(titleFontSize,table,"test");
            addContent(titleFontSize,table,jsonObject);
            addContent(titleFontSize,table,jsonObject);
            addContent(titleFontSize,table,jsonObject);
            addContent(titleFontSize,table,jsonObject);
            addTitle(titleFontSize,table,"test1");
            addContent(titleFontSize,table,jsonObject);
            addContent(titleFontSize,table,jsonObject);
            addContent(titleFontSize,table,jsonObject);
            addContent(titleFontSize,table,jsonObject);
            addTitle(titleFontSize,table,"test2");
            addContent(titleFontSize,table,jsonObject);
            addContent(titleFontSize,table,jsonObject);
            addContent(titleFontSize,table,jsonObject);
            addContent(titleFontSize,table,jsonObject);
            addTitle(titleFontSize,table,"test3");
            addContent(titleFontSize,table,jsonObject);
            addContent(titleFontSize,table,jsonObject);
            addContent(titleFontSize,table,jsonObject);
            addContent(titleFontSize,table,jsonObject);
//            Cell row=new Cell().add(new Paragraph("1").setFontSize(titleFontSize)).setTextAlignment(TextAlignment.CENTER);
//            Cell issues=new Cell().add(new Paragraph("Lift A button").setFontSize(titleFontSize)).setTextAlignment(TextAlignment.CENTER);
//            Cell taskType=new Cell().add(new Paragraph("Task").setFontSize(titleFontSize)).setTextAlignment(TextAlignment.CENTER);
//            Cell description=new Cell().add(new Paragraph("ground floor button falls").setFontSize(titleFontSize)).setTextAlignment(TextAlignment.CENTER);
//            Cell dateRaised=new Cell().add(new Paragraph("14-12-2021").setFontSize(titleFontSize)).setTextAlignment(TextAlignment.CENTER);
//            Cell status=new Cell().add(new Paragraph("Closed").setFontSize(titleFontSize)).setTextAlignment(TextAlignment.CENTER);
//            Image image = new Image(ImageDataFactory.create("C:\\image\\profile\\69152d83-a2d0-4cdb-aa16-7bb4de006236.png"));
//            image.setHeight(50);
//            image.setWidth(50);
//            Cell photo1=new Cell()
//                    .add(image);
//            Cell photo2=new Cell()
//                    .add(image);
//            table.addCell(row);
//            table.addCell(issues);
//            table.addCell(taskType);
//            table.addCell(description);
//            table.addCell(dateRaised);
//            table.addCell(status);
//            table.addCell(photo1);
//            table.addCell(photo2);

            addHeaderToPdf(pdfDoc,"C:\\image\\profile\\2.jpg","spn 1111"+"\n"+"111"+"\n"+"111");
            pdfDoc.addNewPage();
            doc.add(table);
        } catch (FileNotFoundException | MalformedURLException e) {
            e.printStackTrace();
        }finally {
            if (doc != null) {
                doc.close();
            }

        }
    }

    public static void main(String[] args) {
        File file = new File("C:\\image\\profile\\test.pdf");
        Document doc = null;
        try {
            PdfWriter writer = new PdfWriter(file);
            PdfDocument pdfDoc = new PdfDocument(writer);
            doc = new Document(pdfDoc);
            Float titleFontSize = 10f;
            //创建总表形式（一行四格）
            UnitValue[] unitValue = new UnitValue[]{
                    UnitValue.createPercentValue((float) 25),
                    UnitValue.createPercentValue((float) 25),
                    UnitValue.createPercentValue((float) 25),
                    UnitValue.createPercentValue((float) 25),
            };
            Table table = new Table(unitValue);
            Color color = new DeviceRgb(255,100,20);
            Color colorWhite = new DeviceRgb(255,255,255);
            ImageData imageData = ImageDataFactory.create("C:\\image\\profile\\2.jpg");
            Image logoImg = new Image(imageData);
            logoImg.setAutoScale(true);
//            logoImg.scaleToFit(200f,200f);
//            logoImg.setMargins(40, 0, 0, 0);
            Border titleBorder =new SolidBorder(colorWhite,1f);

            Cell companyLogo=new Cell(4,2).setVerticalAlignment(VerticalAlignment.MIDDLE).setBorder(Border.NO_BORDER).setHeight(100f);
            companyLogo.add(logoImg);
            Cell workOrderTitle=new Cell(1,1).add(new Paragraph("WO REQUEST #")).setTextAlignment(TextAlignment.LEFT).setBackgroundColor(color).setBorder(titleBorder);
            UnitValue[] innerTableUnitValue = new UnitValue[]{
                    UnitValue.createPercentValue((float) 100),

            };
            Table workOrderTable = new Table(innerTableUnitValue);
            Paragraph workOrderPara = new Paragraph("256-A");
            Cell workOrderTextInner=new Cell(1,1).add(workOrderPara).setWidth(200f).setTextAlignment(TextAlignment.CENTER);
            workOrderTable.addCell(workOrderTextInner);
            Cell workOrderText=new Cell(1,1).add(workOrderTable).setBorder(Border.NO_BORDER);
            table.addCell(companyLogo);
            table.addCell(workOrderTitle);
            table.addCell(workOrderText);

            Cell dateRequestedTitle=new Cell(1,1).add(new Paragraph("DATE REQUESTED")).setTextAlignment(TextAlignment.LEFT).setBackgroundColor(color).setBorder(titleBorder);
            Paragraph dateRequestedPara = new Paragraph("21/01/2022");
            Cell dateRequestedTextInner=new Cell(1,1).add(dateRequestedPara).setWidth(200f).setTextAlignment(TextAlignment.CENTER);
            Table dateRequestedTable = new Table(innerTableUnitValue);
            dateRequestedTable.addCell(dateRequestedTextInner);
            Cell dateRequestedText=new Cell(1,1).add(dateRequestedTable).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER);
            table.addCell(dateRequestedTitle);
            table.addCell(dateRequestedText);

            Cell dueByTitle=new Cell(1,1).add(new Paragraph("DUE BY")).setTextAlignment(TextAlignment.LEFT).setBackgroundColor(color).setBorder(titleBorder);
            Paragraph dueByPara = new Paragraph("21/01/2022");
            Cell dueByTextInner=new Cell(1,1).add(dueByPara).setWidth(200f).setTextAlignment(TextAlignment.CENTER);
            Table dueByTable = new Table(innerTableUnitValue);
            dueByTable.addCell(dueByTextInner);
            Cell dueByText=new Cell(1,1).add(dueByTable).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER);
            table.addCell(dueByTitle);
            table.addCell(dueByText);

            Cell priorityTitle=new Cell(1,1).add(new Paragraph("PRIORITY")).setTextAlignment(TextAlignment.LEFT).setBackgroundColor(color).setBorder(titleBorder);
            Paragraph priorityPara = new Paragraph("Low");
            Cell priorityInner=new Cell(1,1).add(priorityPara).setWidth(200f).setTextAlignment(TextAlignment.CENTER);
            Table priorityTable = new Table(innerTableUnitValue);
            priorityTable.addCell(priorityInner);
            Cell priorityText=new Cell(1,1).add(priorityTable).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER);
            table.addCell(priorityTitle);
            table.addCell(priorityText);

//            Cell AssignedToTitle=new Cell(1,2).add(new Paragraph("ASSIGNED TO")).setTextAlignment(TextAlignment.LEFT).setBackgroundColor(color).setMinHeight(30).setBorder(titleBorder);
//            table.addCell(AssignedToTitle);
            Float leadingSize = 0.7f;
            Paragraph p1 = new Paragraph("Milestone Facilities Management");
            p1.setMultipliedLeading(leadingSize);
            Cell cell1=new Cell(1,2).add(p1).setTextAlignment(TextAlignment.LEFT).setBorder(Border.NO_BORDER).setFontSize(11f).setFontColor(color);
            table.addCell(cell1);

            Paragraph p2 = new Paragraph("Binah Construction");
            p2.setMultipliedLeading(leadingSize);
            Cell cell1_1=new Cell(1,2).add(p2).setTextAlignment(TextAlignment.LEFT).setBorder(Border.NO_BORDER).setFontSize(11f);
            table.addCell(cell1_1);


            Paragraph p3 = new Paragraph("Mile One Apartments - SP 99816");
            p3.setMultipliedLeading(leadingSize);
            Cell cell2=new Cell(1,2).add(p3).setTextAlignment(TextAlignment.LEFT).setBorder(Border.NO_BORDER).setFontSize(11f);
            table.addCell(cell2);

            Paragraph p4 = new Paragraph("Mob: 0448987804");
            p4.setMultipliedLeading(leadingSize);
            Cell cell2_1=new Cell(1,2).add(p4).setTextAlignment(TextAlignment.LEFT).setBorder(Border.NO_BORDER).setFontSize(11f);
            table.addCell(cell2_1);

            Paragraph p5 = new Paragraph("12 Stanley Street, Kogarah NSW 2217");
            p5.setMultipliedLeading(leadingSize);
            Cell cell3=new Cell(1,2).add(p5).setTextAlignment(TextAlignment.LEFT).setBorder(Border.NO_BORDER).setFontSize(11f);
            table.addCell(cell3);

            Paragraph p6 = new Paragraph("Ph: ");
            p6.setMultipliedLeading(leadingSize);
            Cell cell3_1=new Cell(1,2).add(p6).setTextAlignment(TextAlignment.LEFT).setBorder(Border.NO_BORDER).setFontSize(11f);
            table.addCell(cell3_1);

            Paragraph p7 = new Paragraph("Site Contact");
            p7.setMultipliedLeading(leadingSize);
            Cell cell4=new Cell(1,4).add(p7).setTextAlignment(TextAlignment.LEFT).setBorder(Border.NO_BORDER).setFontSize(11f).setFontColor(color);
            table.addCell(cell4);

            Paragraph p8 = new Paragraph("Joseph Zhu");
            p8.setMultipliedLeading(leadingSize);
            Cell cell5=new Cell(1,4).add(p8).setTextAlignment(TextAlignment.LEFT).setBorder(Border.NO_BORDER).setFontSize(11f);
            table.addCell(cell5);

            Paragraph p9 = new Paragraph("\n");
            p9.setMultipliedLeading(leadingSize);
            Cell cell6=new Cell(1,4).add(p9).setTextAlignment(TextAlignment.LEFT).setBorder(Border.NO_BORDER);
            table.addCell(cell6);

            Paragraph p10 = new Paragraph("joseph@milestonefm.com.au");
            p10.setMultipliedLeading(leadingSize);
            PdfAction act = PdfAction.createURI("joseph@milestonefm.com.au");
            Cell cell7=new Cell(1,4).add(p10.setAction(act)).setTextAlignment(TextAlignment.LEFT).setBorder(Border.NO_BORDER).setFontSize(11f);
            table.addCell(cell7);

            Paragraph p11 = new Paragraph("\n");
            p11.setMultipliedLeading(leadingSize);
            Cell cell8=new Cell(1,4).add(p11).setTextAlignment(TextAlignment.LEFT).setBorder(Border.NO_BORDER);
            table.addCell(cell8);

            Cell jobInformation=new Cell(1,4).add(new Paragraph("SERVICE INFORMATION")).setTextAlignment(TextAlignment.LEFT).setBackgroundColor(color).setBorder(Border.NO_BORDER);
            table.addCell(jobInformation);

            Paragraph p12 = new Paragraph("Service");
            p12.setMultipliedLeading(leadingSize);
            Cell cell9=new Cell(1,4).add(p12).setTextAlignment(TextAlignment.LEFT).setBorder(Border.NO_BORDER).setFontSize(11f).setFontColor(color);
            table.addCell(cell9);

            Paragraph p13 = new Paragraph("Private Lot");
            p13.setMultipliedLeading(leadingSize);
            Cell cell10=new Cell(1,4).add(p13).setTextAlignment(TextAlignment.LEFT).setBorder(Border.NO_BORDER).setFontSize(11f);
            table.addCell(cell10);

            Paragraph p14 = new Paragraph("\n");
            p14.setMultipliedLeading(leadingSize);
            Cell cell11=new Cell(1,4).add(p14).setTextAlignment(TextAlignment.LEFT).setBorder(Border.NO_BORDER);
            table.addCell(cell11);

            Paragraph p15 = new Paragraph("Job Contact");
            p15.setMultipliedLeading(leadingSize);
            Cell cell12=new Cell(1,4).add(p15).setTextAlignment(TextAlignment.LEFT).setBorder(Border.NO_BORDER).setFontSize(11f).setFontColor(color);
            table.addCell(cell12);

            Paragraph p16 = new Paragraph("205 – Jana Bartolo Mob: Ph: 402452266");
            p16.setMultipliedLeading(leadingSize);
            Cell cell13=new Cell(1,4).add(p16).setTextAlignment(TextAlignment.LEFT).setBorder(Border.NO_BORDER).setFontSize(11f);
            table.addCell(cell13);

            Paragraph p17 = new Paragraph("\n\n");
            p17.setMultipliedLeading(leadingSize);
            Cell cell14=new Cell(1,4).add(p17).setTextAlignment(TextAlignment.LEFT).setBorder(Border.NO_BORDER);
            table.addCell(cell14);

            Cell subject =new Cell(1,4).add(new Paragraph("TASK NAME")).setTextAlignment(TextAlignment.LEFT).setBackgroundColor(color).setBorder(Border.NO_BORDER);
            table.addCell(subject);

            Paragraph p18 = new Paragraph("Maintenance Request 205");
            p18.setMultipliedLeading(leadingSize);
            Cell cell15=new Cell(1,4).add(p18).setTextAlignment(TextAlignment.LEFT).setBorder(Border.NO_BORDER);
            table.addCell(cell15);

            Paragraph p19 = new Paragraph("\n\n");
            p19.setMultipliedLeading(leadingSize);
            Cell cell16=new Cell(1,4).add(p19).setTextAlignment(TextAlignment.LEFT).setBorder(Border.NO_BORDER);
            table.addCell(cell16);

            Cell description =new Cell(1,4).add(new Paragraph("DESCRIPTION")).setTextAlignment(TextAlignment.LEFT).setBackgroundColor(color).setBorder(Border.NO_BORDER);
            table.addCell(description);

            Paragraph p20 = new Paragraph("Hailer dish washing machine alarm code warning for drain pump / water inlet valve open and\n" +
                    "blocked.");
            p18.setMultipliedLeading(leadingSize);
            Cell cell17=new Cell(1,4).add(p20).setTextAlignment(TextAlignment.LEFT).setBorder(Border.NO_BORDER);
            table.addCell(cell17);

            Paragraph p21 = new Paragraph("\n\n");
            p21.setMultipliedLeading(leadingSize);
            Cell cell18=new Cell(1,4).add(p21).setTextAlignment(TextAlignment.LEFT).setBorder(Border.NO_BORDER);
            table.addCell(cell18);

            Cell media =new Cell(1,4).add(new Paragraph("MEDIA")).setTextAlignment(TextAlignment.LEFT).setBackgroundColor(color).setBorder(Border.NO_BORDER);
            table.addCell(media);

            Cell media1=new Cell(5,2).setVerticalAlignment(VerticalAlignment.MIDDLE).setBorder(Border.NO_BORDER);
            media1.add(logoImg);
            Cell media2=new Cell(5,2).setVerticalAlignment(VerticalAlignment.MIDDLE).setBorder(Border.NO_BORDER);
            media2.add(logoImg);
            table.addCell(media1);
            table.addCell(media2);

            pdfDoc.addNewPage();
            pdfDoc.setDefaultPageSize(PageSize.A4);
            doc.add(table);

        } catch (FileNotFoundException | MalformedURLException e) {
            e.printStackTrace();
        }finally {
            if (doc != null) {
                doc.close();
            }


        }
    }


}
