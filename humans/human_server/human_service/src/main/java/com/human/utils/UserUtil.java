package com.human.utils;

import com.human.pojo.Hr;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author Li JieGui
 * @date 2020/12/3 16:36
 * 用户工具类，获取登录用户信息
 */
public class UserUtil {
    public static Hr getCurrentHr() {
        return ((Hr) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }
}
