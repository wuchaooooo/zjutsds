package com.wuchaooooo.service.zjutsds.config;

import com.wuchaooooo.service.zjutsds.interceptor.CustomAuthenticationProvider;
import com.wuchaooooo.service.zjutsds.interceptor.CustomUserDetailsService;
import com.wuchaooooo.service.zjutsds.interceptor.LoginSuccessHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, jsr250Enabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    protected final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private CustomAuthenticationProvider customAuthenticationProvider;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .authorizeRequests()
                //允许所有用户访问的url
                .antMatchers("/login", "/signup", "/images/**", "/materializecss/**", "/vendor/**", "/**/*.js", "/**/*.css", "/**/*.html", "/**/*.json").permitAll()
                //其他地址的访问均需验证权限
                .anyRequest().authenticated()
                .and()
                    .formLogin()
                    .usernameParameter("username")
                    .passwordParameter("password")
                    .loginPage("/login")
                    .successHandler(loginSuccessHandler())
                    .failureUrl("/login?error")
                    .permitAll()
                .and()
                    .sessionManagement()
                        //配置同一用户同时允许2个终端登录。当第二个终端尝试登录时，第一个将被挤掉线，然后重新返回登录页面。
                        .maximumSessions(2).expiredUrl("/login").and()
                .and()
                    .logout()
                    .deleteCookies("JSESSIONID")
                    .logoutSuccessUrl("/login")
                .and()
                    .csrf().disable();

    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService);
        auth.authenticationProvider(customAuthenticationProvider);
        // 不删除凭据，以便记住用户
        auth.eraseCredentials(false);
    }

    @Bean
    public LoginSuccessHandler loginSuccessHandler() {
        return new LoginSuccessHandler();
    }


}
