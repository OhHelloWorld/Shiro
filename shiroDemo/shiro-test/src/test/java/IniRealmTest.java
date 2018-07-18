import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

public class IniRealmTest {

    @Test
    public void testIniRealm(){

        //构建iniRealm
        IniRealm iniRealm = new IniRealm("classpath:user.ini");

        //构建SecurityManager环境
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(iniRealm);

        //主体提交认证请求
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken("jiarui","123456");
        subject.login(token);

        System.out.println("isAuthenticated:" + subject.isAuthenticated());

//        subject.logout();
//
//        System.out.println("isAuthenticated:" + subject.isAuthenticated());

        subject.checkRole("admin");

        subject.checkPermissions("delete-user","add-user");
    }
}
