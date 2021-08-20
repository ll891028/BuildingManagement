import com.liulin.LiuLinApplication;
import com.liulin.web.controller.common.MailService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author: LinLiu
 * @Date: 2021/8/19 3:18 下午
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LiuLinApplication.class)
public class MailTest {

    @Autowired
    private MailService mailService;

    @Test
    public void sendMailTest(){
        mailService.sendSimpleMail();
    }
}
