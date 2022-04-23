package com.human.config;

import com.human.pojo.Menu;
import com.human.pojo.Role;
import com.human.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.Collection;
import java.util.List;

/**
 * @author Li JieGui
 * @date 2020/11/28 17:32
 * 提供权限访问
 * 1根据用户请求地址，分析出请求需要的角色
 */
@Component
public class MyFilter implements FilterInvocationSecurityMetadataSource {

    @Autowired
    private MenuService menuService;

    /**
     * 比较工具
     */
    AntPathMatcher antPathMatcher = new AntPathMatcher();


    /**
     * Collection 当前请求所需要的角色
     * @param o
     * @return
     * @throws IllegalArgumentException
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        //获取当前请求地址
        String requestUrl = ((FilterInvocation)o).getRequestUrl();

        //获取所有菜单及所需要的角色
        List<Menu> menus = menuService.getAllMenusWithRole();

        for (Menu menu : menus) {
            //如果匹配成功，获取路径所需要的角色
            if(antPathMatcher.match(menu.getUrl(),requestUrl)) {
                List<Role> roles = menu.getRoles();

                String[] str = new String[roles.size()];

                for (int i = 0; i < roles.size(); i++) {
                    str[i] = roles.get(i).getName();
                }
                return SecurityConfig.createList(str);
            }
        }

        //未匹配到角色，未登录,登录即可访问
        return SecurityConfig.createList("ROLE_LOGIN");
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
