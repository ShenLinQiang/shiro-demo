import com.alibaba.druid.pool.DruidDataSource;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

public class JdbcRealmTest {
    DruidDataSource druidDataSource = new DruidDataSource();
    {
        druidDataSource.setUrl("jdbc:mysql://localhost:3306/mikasa");
        druidDataSource.setUsername("root");
        druidDataSource.setPassword("root");
    }


    @Test
    public void testuthentication() {
        JdbcRealm jdbcRealm = new JdbcRealm();
        jdbcRealm.setDataSource(druidDataSource);
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(jdbcRealm);
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("mark", "123456");
        subject.login(token);
        System.out.println("isAuthenticated:" + subject.isAuthenticated());

        String sql = "select password from users where username = ?"; //认证
        jdbcRealm.setAuthenticationQuery(sql);

        String sql1 = "select role_name from user_roles where username=?"; //角色授权
        jdbcRealm.setUserRolesQuery(sql1);

        String sql2 = "select permission_name from roles_permission where role_name = ?"; //权限授权
        jdbcRealm.setPermissionsQuery(sql2);

        //检查用户是否具有角色
        subject.checkRoles("user", "admin");

        //检查用户权限
        jdbcRealm.setPermissionsLookupEnabled(true);
        subject.checkPermission("user:select");
    }
}
