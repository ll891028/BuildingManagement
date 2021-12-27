import com.alibaba.fastjson.JSONObject;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.liulin.LiuLinApplication;
import com.liulin.common.utils.file.AwsFileUtils;
import com.liulin.common.utils.pdf.PdfItextUtils;
import com.liulin.framework.web.service.MailService;
import com.liulin.system.domain.dto.AreaDto;
import com.liulin.system.service.IBuildingLevelService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

/**
 * @Author: LinLiu
 * @Date: 2021/8/19 3:18 下午
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LiuLinApplication.class)
public class MailTest {

    @Autowired
    private MailService mailService;

    @Autowired
    private IBuildingLevelService buildingLevelService;

    @Test
    public void sendMailTest(){
        mailService.sendSimpleMail();
    }

    @Test
    public void testBuildingLevelData(){
        List<AreaDto> list = buildingLevelService.selectAreaData(231L);
        System.out.println(JSONObject.toJSONString(list ));
    }

    @Test
    public void TestSMTP(){
        mailService.sendSMTPMailTest();
    }

    @Test
    public void getKey(){
        String key = AwsFileUtils.getKey("https://gtech-bucket-sy.s3.ap-southeast-2.amazonaws.com/attachments/微信图片_20211221114009.jpg");
        System.out.println(key);
    }

    @Test
    public void itext(){

        File file = new File("C:\\image\\profile\\test.pdf");
        Document doc = null;
        try {
            PdfWriter writer = new PdfWriter(file);
            PdfDocument pdfDoc = new PdfDocument(writer);
            doc = new Document(pdfDoc);
            PdfItextUtils.addHeaderToPdf(pdfDoc,"C:\\image\\profile\\2.jpg","spn 1111"+"\n"+"111"+"\n"+"111");
            pdfDoc.addNewPage();
            Paragraph paragraph = new Paragraph("12331231");
            doc.add(paragraph);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }finally {
            if (doc != null) {
                doc.close();
            }

        }


    }

}
