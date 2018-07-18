import com.alibaba.druid.pool.DruidDataSource;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

public class JdbcRealmTest {

    String databaseUrl = "jdbc:mysql://localhost:3306/test";
    String username = "root";
    String password = "";

    //设置数据源
    DruidDataSource dataSource = new DruidDataSource();
    {
        dataSource.setUrl(databaseUrl);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
    }


    @Test
    public void testJdbcRealm(){

        //设置realm
        JdbcRealm jdbcRealm = new JdbcRealm();
        jdbcRealm.setDataSource(dataSource);
        //设置权限开关，默认为false,不设置则检查权限时会报错
        jdbcRealm.setPermissionsLookupEnabled(true);

        //可使用自定义的sql认证语句
        String sql1 = "select password from own_user where username = ?";
        jdbcRealm.setAuthenticationQuery(sql1);

        //可使用自定义的sql角色查询语句
        String sql2 = "select role_name from own_user_roles where own_user_id = (select id from own_user where username = ?)";
        jdbcRealm.setUserRolesQuery(sql2);

        //可使用自定义的sql权限查询语句
        String sql3 = "select own_permission from own_roles_permissions where own_role_name = ?";
        jdbcRealm.setPermissionsQuery(sql3);

        //构建SecurityManager环境
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(jdbcRealm);

        //主体提交认证请求
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        Subject subject = SecurityUtils.getSubject();

        //生成用户名密码token
        UsernamePasswordToken token = new UsernamePasswordToken("xiaoming","654321");

        //模拟用户登录
        subject.login(token);

        System.out.println("isAuthenticated:" + subject.isAuthenticated());

//        subject.logout();
//
//        System.out.println("isAuthenticated:" + subject.isAuthenticated());

        //检查是否有角色或者权限
        subject.checkRole("admin");
        subject.checkPermission("delete-user");
    }
}
