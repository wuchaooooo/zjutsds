package com.wuchaooooo.service.zjutsds.interceptor;


import com.wuchaooooo.service.zjutsds.pojo.po.PUser;
import com.wuchaooooo.service.zjutsds.pojo.vo.VUser;
import com.wuchaooooo.service.zjutsds.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired  //数据库服务类
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        VUser user = userService.getUser(userName);

        if (user == null) {
            throw new UsernameNotFoundException("UserName " + userName + " not found");
        }

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole().toUpperCase()));
        return new org.springframework.security.core.userdetails.User(user.getUserName(),
                user.getPassword(), authorities);
    }

}

