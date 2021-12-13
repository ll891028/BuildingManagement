import com.alibaba.fastjson.JSONObject;
import com.liulin.LiuLinApplication;
import com.liulin.framework.web.service.MailService;
import com.liulin.system.domain.dto.AreaDto;
import com.liulin.system.service.IBuildingLevelService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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

}
