package com.wuchaooooo.service.zjutsds.controller;

import com.wuchaooooo.service.zjutsds.pojo.po.PUser;
import com.wuchaooooo.service.zjutsds.pojo.vo.VCareer;
import com.wuchaooooo.service.zjutsds.pojo.vo.VMajor;
import com.wuchaooooo.service.zjutsds.pojo.vo.VResult;
import com.wuchaooooo.service.zjutsds.pojo.vo.VUser;
import com.wuchaooooo.service.zjutsds.service.QuestionsService;
import com.wuchaooooo.service.zjutsds.service.UserService;
import com.wuchaooooo.service.zjutsds.utils.AuthUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Map;

/**
 * Created by wuchaooooo on 07/06/2017.
 */
@Controller
public class QuestionsController {
    @Autowired
    private QuestionsService questionsService;
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/questions", method = RequestMethod.GET)
    public String listQuestions(
            Map<String, Object> model) {
        VUser vUser = userService.getUser(AuthUtils.getAuthUser().getUserName());
        int isActivated = vUser.getIsActivated();
        if (isActivated == 0) {
            return "questions-notactive";
        }
        return "questions";
    }

    @RequestMapping(value = "/questions", method = RequestMethod.POST)
    public String getResult(
            VResult vResult,
            Map<String, Object> model) {
        String sdsName = questionsService.getSdsName(vResult);
        vResult.setSdsName(sdsName);
        questionsService.saveResult(vResult);
        VCareer vCareer = questionsService.getCareer(sdsName);
        List<VMajor> vMajorList = questionsService.listMajor(sdsName);
        model.put("career", vCareer);
        model.put("majors", vMajorList);
        return "result";
    }

    @RequestMapping(value = "/demo", method = RequestMethod.GET)
    public String demo(Map<String, Object> model) {
        VCareer vCareer = questionsService.getCareer("EIS");
        List<VMajor> vMajorList = questionsService.listMajor("EIS");
        model.put("career", vCareer);
        model.put("majors", vMajorList);
        return "result";
    }

    @RequestMapping(value = "/result", method = RequestMethod.GET)
    public String getResult(Map<String, Object> model) {
        VUser vUser = userService.getUser(AuthUtils.getAuthUser().getUserName());
        String sdsName = vUser.getSdsName();
        if (sdsName == null) {
            return "result-null";
        }
        VCareer vCareer = questionsService.getCareer(sdsName);
        List<VMajor> vMajorList = questionsService.listMajor(sdsName);
        model.put("career", vCareer);
        model.put("majors", vMajorList);
        return "result";
    }

    @RequestMapping(value = "/1", method = RequestMethod.GET)
    public String show() {
        return "1";
    }
}
