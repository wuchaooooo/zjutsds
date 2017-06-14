package com.wuchaooooo.service.zjutsds.service;

import com.wuchaooooo.service.zjutsds.dao.CareerDAO;
import com.wuchaooooo.service.zjutsds.dao.MajorDAO;
import com.wuchaooooo.service.zjutsds.dao.UserInfoDAO;
import com.wuchaooooo.service.zjutsds.pojo.po.PCareer;
import com.wuchaooooo.service.zjutsds.pojo.po.PMajor;
import com.wuchaooooo.service.zjutsds.pojo.po.PUserInfo;
import com.wuchaooooo.service.zjutsds.pojo.vo.VCareer;
import com.wuchaooooo.service.zjutsds.pojo.vo.VMajor;
import com.wuchaooooo.service.zjutsds.pojo.vo.VResult;
import com.wuchaooooo.service.zjutsds.utils.AuthUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


/**
 * Created by wuchaooooo on 08/06/2017.
 */
@Service
public class QuestionsService {
    @Autowired
    private UserInfoDAO userInfoDAO;
    @Autowired
    private CareerDAO careerDAO;
    @Autowired
    private MajorDAO majorDAO;

    public int saveResult(VResult vResult) {
        PUserInfo pUserInfo = new PUserInfo();
        pUserInfo.setIdCard(AuthUtils.getAuthUser().getUserName());
        Map<String, Integer> map = getNumOfType(vResult);
        pUserInfo.setR(map.get("R"));
        pUserInfo.setC(map.get("C"));
        pUserInfo.setE(map.get("E"));
        pUserInfo.setS(map.get("S"));
        pUserInfo.setA(map.get("A"));
        pUserInfo.setI(map.get("I"));
        pUserInfo.setSdsName(vResult.getSdsName());
        pUserInfo.setGmtCreateTopic(new Date());
        return userInfoDAO.saveResult(pUserInfo);
    }

    //获取前端的问卷答案，计算出各问题回答"是"的题数
    public Map<String, Integer> getNumOfType(VResult vResult) {
        Map<String, Integer> map = new TreeMap<>();
        //R现实型题目 回答"是"的个数
        int r = vResult.getQ1() + vResult.getQ7() + vResult.getQ13() + vResult.getQ19() + vResult.getQ25() + vResult.getQ31() + vResult.getQ37() + vResult.getQ43() + vResult.getQ49() + vResult.getQ55();
        //C常规型题目 回答"是"的个数
        int c = vResult.getQ2() + vResult.getQ8() + vResult.getQ14() + vResult.getQ20() + vResult.getQ26() + vResult.getQ32() + vResult.getQ38() + vResult.getQ44() + vResult.getQ50() + vResult.getQ56();
        //E企业型题目 回答"是"的个数
        int e = vResult.getQ3() + vResult.getQ9() + vResult.getQ15() + vResult.getQ21() + vResult.getQ27() + vResult.getQ33() + vResult.getQ39() + vResult.getQ45() + vResult.getQ51() + vResult.getQ57();
        //S社会型题目 回答"是"的个数
        int s = vResult.getQ4() + vResult.getQ10() + vResult.getQ16() + vResult.getQ22() + vResult.getQ28() + vResult.getQ34() + vResult.getQ40() + vResult.getQ46() + vResult.getQ52() + vResult.getQ58();
        //A艺术型题目 回答"是"的个数
        int a = vResult.getQ5() + vResult.getQ11() + vResult.getQ17() + vResult.getQ23() + vResult.getQ29() + vResult.getQ35() + vResult.getQ41() + vResult.getQ47() + vResult.getQ53() + vResult.getQ59();
        //I研究型题目 回答"是"的个数
        int i = vResult.getQ6() + vResult.getQ12() + vResult.getQ18() + vResult.getQ24() + vResult.getQ30() + vResult.getQ36() + vResult.getQ42() + vResult.getQ48() + vResult.getQ54() + vResult.getQ60();
        map.put("R", r);
        map.put("C", c);
        map.put("E", e);
        map.put("S", s);
        map.put("A", a);
        map.put("I", i);
        return map;
    }

    public String getSdsName(VResult vResult) {
        String sdsName = "";
        Map<String, Integer> map = getNumOfType(vResult);
        List<Map.Entry<String, Integer>> list = sort(map);
        sdsName += list.get(0).getKey();
        sdsName += list.get(1).getKey();
        sdsName += list.get(2).getKey();
        return sdsName;
    }

    public VCareer getCareer(String sdsName) {
        PCareer pCareer = careerDAO.getCareer(sdsName);
        VCareer vCareer = new VCareer();
        BeanUtils.copyProperties(pCareer, vCareer);
        return vCareer;
    }

    public List<VMajor> listMajor(String sdsName) {
        List<PMajor> pMajorList =  majorDAO.listMajor(sdsName);
        List<VMajor> vMajorList = new ArrayList<>();
        for (PMajor pMajor : pMajorList) {
            VMajor vMajor = new VMajor();
            BeanUtils.copyProperties(pMajor, vMajor);
            vMajorList.add(vMajor);
        }
        return vMajorList;
    }


    private List<Map.Entry<String, Integer>> sort(Map<String, Integer> map) {
        //这里将map.entrySet()转换成list
        List<Map.Entry<String,Integer>> list = new ArrayList<Map.Entry<String,Integer>>(map.entrySet());
        //然后通过比较器来实现排序
        Collections.sort(list,new Comparator<Map.Entry<String,Integer>>() {
            //降序排序
            @Override
            public int compare(Map.Entry<String, Integer> o1,
                               Map.Entry<String, Integer> o2) {
                return -o1.getValue().compareTo(o2.getValue());
            }

        });
        return list;
    }
}
