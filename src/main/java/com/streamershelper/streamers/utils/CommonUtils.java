package com.streamershelper.streamers.utils;

import com.streamershelper.streamers.model.user.UserRole;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CommonUtils {

    public static List<SimpleGrantedAuthority> buildSimpleGrantedAuthorities(final Set<UserRole> userRoles) {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (UserRole userRole : userRoles) {
            authorities.add(new SimpleGrantedAuthority(userRole.getRole().getName()));
        }
        return authorities;
    }
}
