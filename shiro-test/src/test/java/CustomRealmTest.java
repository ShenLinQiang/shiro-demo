import com.slq.shiro.realm.CustomRealm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

public class CustomRealmTest {
    @Test
    public void testuthentication() {
        CustomRealm customRealm = new CustomRealm();

        //customRealm 加密
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        matcher.setHashAlgorithmName("md5");
        matcher.setHashIterations(1);
        customRealm.setCredentialsMatcher(matcher);

        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(customRealm);
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("mark", "123456");
        subject.login(token);
        System.out.println("isAuthenticated:" + subject.isAuthenticated());

        //检查用户是否具有角色
        subject.checkRoles( "admin");
        //检查用户权限
        subject.checkPermission("admin:select");
    }
}
