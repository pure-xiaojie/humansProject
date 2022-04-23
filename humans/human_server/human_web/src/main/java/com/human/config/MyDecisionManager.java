package com.human.config;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * @author Li JieGui
 * @date 2020/12/1 10:11
 * 2判断当前用户是否具备当前请求所需要的角色
 * 具备则过，否则不过
 */
@Component
public class MyDecisionManager implements AccessDecisionManager {
    /**
     * @param authentication 当前登录用户信息
     * @param o
     * @param configAttributes  需要的角色，Filter返回的对象
     * @throws AccessDeniedException
     * @throws InsufficientAuthenticationException
     */
    @Override
    public void decide(Authentication authentication, Object o, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
        //遍历需要的角色，判断是否跟用户的角色匹配
        for (ConfigAttribute configAttribute : configAttributes) {
            //获取所需要的角色
            String needRole = configAttribute.getAttribute();

            if("ROLE_LOGIN".equals(needRole)) {
                if(authentication instanceof AnonymousAuthenticationToken) {
                    throw new AccessDeniedException("尚未登录，请登录！");
                }else {
                    //登录即可访问的请求，直接返回放行
                    return;
                }
            }

            //获取当前登录用户的角色
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
          
            for (GrantedAuthority authority : authorities) {
                //具备任一角色，返回
                if(authority.getAuthority().equals(needRole)) {
                    return;
                }
            }
        }
        //不具备所需要的角色，不能访问
        throw new AccessDeniedException("权限不足，请联系管理员！");
    }

    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
