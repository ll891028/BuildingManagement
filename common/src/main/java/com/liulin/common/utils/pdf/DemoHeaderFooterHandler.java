package com.liulin.common.utils.pdf;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.events.Event;
import com.itextpdf.kernel.events.IEventHandler;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TabAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.VerticalAlignment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;

public class DemoHeaderFooterHandler implements IEventHandler {

    private static final Logger log = LoggerFactory.getLogger(DemoHeaderFooterHandler.class);


    private final String imgLogo;

    private final String rightText;

    public DemoHeaderFooterHandler(final String imgLogo,final String rightText) {
        this.imgLogo = imgLogo;
        this.rightText = rightText;
    }

    @Override
    public void handleEvent(final Event event) {
        try {
            final PdfDocumentEvent docEvent = (PdfDocumentEvent) event;
            final PdfDocument pdfDoc = docEvent.getDocument();
            final Document doc = new Document(pdfDoc);
            final PdfPage page = docEvent.getPage();
            final int pageNumber = pdfDoc.getPageNumber(page);
            final Rectangle pageSize = page.getPageSize();
            final float pdfWidth = pageSize.getWidth();
            final float pdfHeight = pageSize.getHeight();
            final PdfCanvas pdfCanvas = new PdfCanvas(page.newContentStreamBefore(), page.getResources(), pdfDoc);
            final Color lineColor = new DeviceRgb(57, 123, 198);
            pdfCanvas.setLineWidth(1.5f).setStrokeColor(lineColor);
            final float tableWidth = pdfWidth - doc.getRightMargin() - doc.getLeftMargin();
//            doc.setTopMargin(45f);
            // 页眉
            final float x0 = doc.getRightMargin(), y0 = pdfHeight - doc.getTopMargin();
            pdfCanvas.moveTo(x0, y0).lineTo(pdfWidth - doc.getRightMargin(), y0).stroke();
            final Table headerTable = new Table(2);
            //页眉高度
//            headerTable.setHeight(45);
            headerTable.setFixedLayout();
            headerTable.setWidth(tableWidth);

            //页眉图片
            final Cell imgCell = new Cell();
            imgCell.setBorder(Border.NO_BORDER);
            imgCell.add(this.getLogoImage());
            imgCell.setVerticalAlignment(VerticalAlignment.TOP);
            imgCell.setHorizontalAlignment(HorizontalAlignment.LEFT);
            headerTable.addCell(imgCell);

            //添加右边文字信息
            final Paragraph headerRightText = new Paragraph();
            headerRightText.add(rightText);
            headerRightText.setBorder(Border.NO_BORDER);
            headerRightText.setFontSize(7f);
            imgCell.setVerticalAlignment(VerticalAlignment.TOP);
//            headerRightText.setHorizontalAlignment(HorizontalAlignment.RIGHT);
            headerRightText.setTextAlignment(TextAlignment.RIGHT);
            headerTable.addCell(new Cell().add(headerRightText).setBorder(Border.NO_BORDER));

            headerTable.setFixedPosition(doc.getLeftMargin(), pdfHeight - doc.getTopMargin(), tableWidth);
            doc.add(headerTable);

            // 页脚
            pdfCanvas.moveTo(x0, doc.getBottomMargin()).lineTo(pdfWidth - doc.getRightMargin(), doc.getBottomMargin())
                    .stroke();

            final Table table = new Table(2);
            table.setHeight(40);
            table.setFixedLayout();
            table.setWidth(tableWidth);
            table.setHorizontalAlignment(HorizontalAlignment.CENTER);


            final String pageNo = String.format("%d", pageNumber);
            final Paragraph pageNoParagraph = new Paragraph();
            pageNoParagraph.setFontColor(new DeviceRgb(166, 166, 166));
            pageNoParagraph.setFontSize(12f);
            pageNoParagraph.add(new Tab()).addTabStops(new TabStop(1000, TabAlignment.RIGHT));
            pageNoParagraph.add(pageNo);
            table.addCell(new Cell().add(pageNoParagraph).setBorder(Border.NO_BORDER));
            table.setFixedPosition(doc.getLeftMargin(), doc.getBottomMargin() - table.getHeight().getValue(),
                    tableWidth);
            doc.add(table);


        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    private Image getLogoImage() {
        Image image=null;
        try {
            image = new Image(ImageDataFactory.create(this.imgLogo));
            image.scaleToFit(100, 150);
        } catch (final MalformedURLException e) {
            final String errorMessage = "logo图片";
        }
        return image;
    }


}
