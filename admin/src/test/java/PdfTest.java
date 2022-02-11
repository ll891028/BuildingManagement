import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import com.liulin.LiuLinApplication;
import com.liulin.common.core.redis.RedisCache;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.util.concurrent.TimeUnit;

/**
 * @Author: LinLiu
 * @Date: 2021/8/19 3:18 下午
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LiuLinApplication.class)
public class PdfTest {

    @Autowired
    RedisCache redisCache;


    @Test
    public void testWorkOrderPdf(){

        File file = new File("C:\\image\\profile\\test.pdf");
        Document doc = null;
        try {
            PdfWriter writer = new PdfWriter(file);
            PdfDocument pdfDoc = new PdfDocument(writer);
            doc = new Document(pdfDoc);
            Float titleFontSize = 10f;
            //创建总表形式（一行四格）
            UnitValue[] unitValue = new UnitValue[]{
                    UnitValue.createPercentValue((float) 50),
                    UnitValue.createPercentValue((float) 25),
                    UnitValue.createPercentValue((float) 25),
            };
            Table table = new Table(unitValue);

            Color color = new DeviceRgb(255,100,20);

            Image logoImg = new Image(ImageDataFactory.create("C:\\image\\profile\\2.jpg"));
            logoImg.setHeight(200);
            logoImg.setWidth(200);
            Cell companyLogo=new Cell(5,1).add(logoImg);
            Cell title=new Cell(1,1).add(new Paragraph("WO REQUEST #")).setTextAlignment(TextAlignment.CENTER).setBackgroundColor(color);
            table.addCell(companyLogo);
            table.addCell(title);
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

    @Test
    public void getRedisCache(){
        String workOrderIndexStr="";
        Integer workOrderIndex = redisCache.getCacheObject(17 + ":workOrderIndex");
        if(workOrderIndex==null){
            redisCache.setCacheObject(17 + ":workOrderIndex",1,1, TimeUnit.DAYS);
            workOrderIndexStr = "0000";
        }else {
            workOrderIndexStr = String.format("%04d", workOrderIndex);
//            workOrderIndexStr = String.format("%s%04d", workOrderIndex);
            workOrderIndex++;
            redisCache.setCacheObject(17 + ":workOrderIndex",workOrderIndex,1, TimeUnit.DAYS);
        }
        System.out.println(workOrderIndex);
        System.out.println(workOrderIndexStr);
    }



}
