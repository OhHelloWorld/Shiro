package com.imooc.shiro.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CustomRealm extends AuthorizingRealm {

    //模拟数据库用户表
    Map<String,String> map = new HashMap<String, String>();
    {
        map.put("jiarui","f341ee98a4cd32d2a2b7a3c7a9c0e05e");
    }

    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String username = (String) principalCollection.getPrimaryPrincipal();
        //从数据库中获得角色
        Set<String> roles = getRolesByUsername(username);
        //从数据库中获得权限
        Set<String> permissions = getPermissionByUsername(username);
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.setRoles(roles);
        authorizationInfo.setStringPermissions(permissions);
        return authorizationInfo;
    }

    //模拟从数据库中获得权限信息
    private Set<String> getPermissionByUsername(String username) {
        Set<String> permissions = new HashSet<String>();
        permissions.add("delete-user");
        permissions.add("update-user");
        return permissions;
    }

    //模拟从数据库中获得角色信息
    private Set<String> getRolesByUsername(String username) {
        Set<String> roles = new HashSet<String>();
        roles.add("admin");
        roles.add("user");
        return roles;
    }

    //认证方法
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //从主体传过来的认证信息中获取用户名
        String username = (String) authenticationToken.getPrincipal();
        //从数据库中获得凭证
        String password = getPasswordByUsername(username);

        if(password == null){
            return null;
        }

        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(username,password,"customRealm");
        authenticationInfo.setCredentialsSalt(ByteSource.Util.bytes("jiarui"));
        return authenticationInfo;
    }

    //模拟数据库
    private String getPasswordByUsername(String username) {
        return map.get(username);
    }
}
