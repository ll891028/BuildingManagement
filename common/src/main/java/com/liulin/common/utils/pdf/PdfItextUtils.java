package com.liulin.common.utils.pdf;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSONObject;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
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


}
