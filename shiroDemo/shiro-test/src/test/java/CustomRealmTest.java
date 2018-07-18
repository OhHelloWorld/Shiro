import com.imooc.shiro.realm.CustomRealm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

public class CustomRealmTest {

    @Test
    public void testCustomRealm(){

        CustomRealm customRealm = new CustomRealm();

        DefaultSecurityManager securityManager = new DefaultSecurityManager();
        securityManager.setRealm(customRealm);

        //设置加密
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        //加密算法名称
        matcher.setHashAlgorithmName("md5");
        //加密次数
        matcher.setHashIterations(1);

        customRealm.setCredentialsMatcher(matcher);

        SecurityUtils.setSecurityManager(securityManager);
        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken("jiarui","123456");

        subject.login(token);

        System.out.println("isAuthenticated:" + subject.isAuthenticated());

        subject.checkRoles("admin","user");
        subject.checkPermissions("delete-user","update-user");
    }
}
