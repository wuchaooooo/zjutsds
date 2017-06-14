package com.wuchaooooo.service.zjutsds.utils;

import com.wuchaooooo.service.zjutsds.pojo.vo.VUser;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Created by wuchaooooo on 07/04/2017.
 */
public class AuthUtils {
    private static ApplicationContext applicationContext;

    public static void setApplicationContext(ApplicationContext context) {
        applicationContext = context;
    }

    public static int getAuthUserId() {
        SecurityContext ctx = SecurityContextHolder.getContext();
        Authentication auth = ctx.getAuthentication();
        if (auth.getPrincipal() instanceof VUser) {
            VUser user = (VUser) auth.getPrincipal();
            return user.getId();
        }
        return 0;
    }

    public static VUser getAuthUser() {
        SecurityContext ctx = SecurityContextHolder.getContext();
        Authentication auth = ctx.getAuthentication();
        if (auth.getPrincipal() instanceof VUser) {
            VUser user = (VUser) auth.getPrincipal();
            return user;
        }
        return null;
    }

    public static String getAuthUserRole() {
        SecurityContext ctx = SecurityContextHolder.getContext();
        Authentication auth = ctx.getAuthentication();
        if (auth.getPrincipal() instanceof VUser) {
            VUser user = (VUser) auth.getPrincipal();
            String role = "ROLE_" + user.getRole().toUpperCase();
            return role;
        }
        return null;
    }
}
