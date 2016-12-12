import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * ProjectName: QJZ
 * PackageName: PACKAGE_NAME
 * User: C0dEr
 * Date: 2016-11-24
 * Time: 15:27
 * Description:
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("classpath:spring-hibernate.xml")
public class test {
    @Test
    public void select() {

    }

}
