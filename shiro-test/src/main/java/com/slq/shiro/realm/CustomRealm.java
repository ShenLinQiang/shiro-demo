package com.slq.shiro.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CustomRealm extends AuthorizingRealm {

    Map<String, String> userMap = new HashMap<String, String>();
    Set<String> roles = new HashSet<String>();

    {
        userMap.put("mark", "c73c3e52f3fc0b765a4c3d9dcd4288a1");
        roles.add("admin");
        super.setName("customRealm");
    }

    //授权
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String userName = (String) principals.getPrimaryPrincipal();
        HashSet<String> userSet = new HashSet<String>();
        userSet.add("admin:select");
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.setStringPermissions(userSet);
        simpleAuthorizationInfo.setRoles(roles);
        return simpleAuthorizationInfo;
    }

    //认证
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String userName = (String) token.getPrincipal();
        String password = userMap.get(userName);
        if (password == null) {
            return null;
        }
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo("mark", password, "customRealm");
        simpleAuthenticationInfo.setCredentialsSalt(ByteSource.Util.bytes("mikasa"));
        return simpleAuthenticationInfo;
    }

    public static void main(String[] args) {
        Md5Hash md5Hash = new Md5Hash("123456","mikasa");
        System.out.println(md5Hash.toString());
    }
}
