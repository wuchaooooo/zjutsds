package com.wuchaooooo.service.zjutsds.controller;

import com.wuchaooooo.service.zjutsds.pojo.vo.VUser;
import com.wuchaooooo.service.zjutsds.pojo.vo.VUserInfo;
import com.wuchaooooo.service.zjutsds.service.UserService;
import com.wuchaooooo.service.zjutsds.utils.AuthUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * Created by wuchaooooo on 07/06/2017.
 */
@Controller
public class IndexController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String returnLogin() {
        return "redirect:/login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(
            @RequestParam(value = "error", required = false) String error,
            Map<String, Object> model) {
        if (error != null) {
            model.put("error", "用户名或密码错误!");
        }
        return "login";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String signUp() {
        return "signup";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String signUp(String name, String username) {
        VUser vUser = new VUser();
        vUser.setName(name);
        vUser.setUserName(username);
        vUser.setRole("student");
        userService.saveUser(vUser);
        return "redirect:/login";
    }

    @RequestMapping(value = "/userinfo", method = RequestMethod.GET)
    public String getUserInfo() {
        return "user-info";
    }

    @RequestMapping(value = "/userinfo", method = RequestMethod.POST)
    public String saveUserInfo(VUserInfo vUserInfo) {
        vUserInfo.setIdCard(AuthUtils.getAuthUser().getUserName());
        userService.saveUserInfo(vUserInfo);
        return "redirect:/questions";
    }

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() {
        //验证是否有用户信息
        boolean haveUserInfo = userService.haveUserInfo(AuthUtils.getAuthUser().getUserName());
        if (haveUserInfo) {
            //有，跳转至index页面
            return "redirect:/questions";
        } else {
            if (AuthUtils.getAuthUserRole().equals("ROLE_ADMIN")) {
                //管理员登录
                return "redirect:/users";
            }
            //用户登录，跳转链接至填写用户表单
            return "redirect:/userinfo";
        }
    }
}
