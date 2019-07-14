import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Before;
import org.junit.Test;

public class AuthenticationTest {

    SimpleAccountRealm simpleAccountRealm = new SimpleAccountRealm();

    @Before
    public void addUser() {
        simpleAccountRealm.addAccount("mark", "123456", "admin","user");
    }

    @Test
    public void testuthentication() {
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(simpleAccountRealm);
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("mark", "123456");
        subject.login(token);
        System.out.println("isAuthenticated:" + subject.isAuthenticated());

        //检查用户是否具有角色
        subject.checkRoles("user","admin");
    }
}
