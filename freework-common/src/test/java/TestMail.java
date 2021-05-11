import com.wjw.common.utils.MailUtils;
import org.junit.Test;

/**
 * @author wjw
 * @date 2021/5/11 19:27
 */
public class TestMail {

    @Test
    public void test(){
        MailUtils.sendMail("975504808@qq.com", "<p>123<p>", "test");
    }
}
