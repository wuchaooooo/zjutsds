package com.wuchaooooo.service.zjutsds.controller;

import com.wuchaooooo.service.zjutsds.pojo.AjaxRequestResult;
import com.wuchaooooo.service.zjutsds.pojo.vo.VUser;
import com.wuchaooooo.service.zjutsds.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * Created by wuchaooooo on 14/06/2017.
 */
@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public String getUsers(Map<String, Object> model) {
        List<VUser> vUserList = userService.listUser();
        model.put("users", vUserList);
        return "users";
    }

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public String saveUser(VUser vUser) {
        userService.saveUser(vUser);
        return "redirect:/users";
    }

    @RequestMapping(value = "/users/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public AjaxRequestResult removeUser(@PathVariable("id") int id) {
        AjaxRequestResult ajaxRequestResult = new AjaxRequestResult();
        userService.removeUser(id);
        ajaxRequestResult.setSuccess(true);
        return ajaxRequestResult;
    }
}
