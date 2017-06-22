package com.wuchaooooo.service.zjutsds.service;

import com.wuchaooooo.service.zjutsds.dao.CareerDAO;
import com.wuchaooooo.service.zjutsds.dao.MajorDAO;
import com.wuchaooooo.service.zjutsds.dao.UserDAO;
import com.wuchaooooo.service.zjutsds.pojo.po.PCareer;
import com.wuchaooooo.service.zjutsds.pojo.po.PMajor;
import com.wuchaooooo.service.zjutsds.pojo.po.PUser;
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
    private UserDAO userDAO;
    @Autowired
    private CareerDAO careerDAO;
    @Autowired
    private MajorDAO majorDAO;

    public int saveResult(VResult vResult) {
        PUser pUser = new PUser();
        Map<String, Integer> map = getNumOfType(vResult);
        pUser.setR(map.get("R"));
        pUser.setC(map.get("C"));
        pUser.setE(map.get("E"));
        pUser.setS(map.get("S"));
        pUser.setA(map.get("A"));
        pUser.setI(map.get("I"));
        pUser.setSdsName(vResult.getSdsName());
        pUser.setGmtCreateTopic(new Date());
        pUser.setUserName(AuthUtils.getAuthUser().getUserName());
        return userDAO.saveResult(pUser);
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
        if (pCareer != null) {
            BeanUtils.copyProperties(pCareer, vCareer);
        }
        return vCareer;
    }

    public List<VMajor> listMajor(String sdsName) {
        List<String> sdsNameList = equalSdsName(sdsName);
        List<PMajor> pMajorList = new ArrayList<>();
        for (int i = 0; i < sdsNameList.size(); i++) {
            List<PMajor> pMajorList1 = majorDAO.listMajor(sdsNameList.get(i));
            if (pMajorList1 != null) {
                for (PMajor pMajor : pMajorList1) {
                    pMajorList.add(pMajor);
                }
            }
        }
        List<VMajor> vMajorList = new ArrayList<>();
        if (pMajorList != null) {
            for (PMajor pMajor : pMajorList) {
                VMajor vMajor = new VMajor();
                BeanUtils.copyProperties(pMajor, vMajor);
                vMajorList.add(vMajor);
            }
        }
        return vMajorList;
    }

    //有些sdsName没有对应的学校专业，所以等同于其他的sdsName进行学校专业的查找
    private List<String> equalSdsName(String sdsName) {
        List<String> list = new ArrayList<>();
        if (sdsName.equals("RAC")) {
            list.add("RAI");
            return list;
        } else if (sdsName.equals("RAE")) {
            list.add("RAI");
            return list;
        } else if (sdsName.equals("RAS")) {
            list.add("RAI");
            return list;
        } else if (sdsName.equals("RSA")) {
            list.add("SAI");
            list.add("RSI");
            return list;
        } else if (sdsName.equals("RSC")) {
            list.add("RCS");
            list.add("RSE");
            return list;
        } else if (sdsName.equals("REA")) {
            list.add("RES");
            list.add("REI");
            return list;
        } else if (sdsName.equals("RCA")) {
            list.add("RAI");
            list.add("RCI");
            return list;
        } else if (sdsName.equals("CRA")) {
            list.add("RAI");
            list.add("CRS");
            return list;
        } else if (sdsName.equals("CIA")) {
            list.add("CIS");
            list.add("IAS");
            return list;
        } else if (sdsName.equals("CSI")) {
            list.add("CIS");
            return list;
        } else if (sdsName.equals("CEA")) {
            list.add("CES");
            return list;
        } else if (sdsName.equals("CAR")) {
            list.add("RAI");
            list.add("CRS");
            return list;
        } else if (sdsName.equals("CAE")) {
            list.add("CES");
            return list;
        } else if (sdsName.equals("CAS")) {
            list.add("CSA");
            return list;
        } else if (sdsName.equals("CAI")) {
            list.add("AIR");
            return list;
        } else if (sdsName.equals("ECR")) {
            list.add("ERC");
            return list;
        } else if (sdsName.equals("ECA")) {
            list.add("ECS");
            list.add("EAR");
            return list;
        } else if (sdsName.equals("ERA")) {
            list.add("EAR");
            return list;
        } else if (sdsName.equals("EIA")) {
            list.add("IES");
            list.add("EIS");
            return list;
        } else if (sdsName.equals("EAC")) {
            list.add("ECS");
            list.add("EAR");
            return list;
        } else if (sdsName.equals("EAI")) {
            list.add("EAR");
            list.add("IES");
            list.add("EIS");
            return list;
        } else if (sdsName.equals("SCR")) {
            list.add("SRC");
            return list;
        } else if (sdsName.equals("SCA")) {
            list.add("AIE");
            list.add("AIR");
            return list;
        } else if (sdsName.equals("SCI")) {
            list.add("SIC");
            return list;
        } else if (sdsName.equals("SRA")) {
            list.add("SAC");
            return list;
        } else if (sdsName.equals("SAR")) {
            list.add("SAC");
            return list;
        } else if (sdsName.equals("ASR")) {
            list.add("ASE");
            list.add("ASI");
            return list;
        } else if (sdsName.equals("ASC")) {
            list.add("ASE");
            list.add("ASI");
            return list;
        } else if (sdsName.equals("AEC")) {
            list.add("AER");
            return list;
        } else if (sdsName.equals("AIC")) {
            list.add("AIR");
            return list;
        } else if (sdsName.equals("ARC")) {
            list.add("AER");
            return list;
        } else if (sdsName.equals("ARE")) {
            list.add("AER");
            return list;
        } else if (sdsName.equals("ARS")) {
            list.add("AER");
            return list;
        } else if (sdsName.equals("ARI")) {
            list.add("AIR");
            return list;
        } else if (sdsName.equals("ACR")) {
            list.add("AER");
            return list;
        } else if (sdsName.equals("ACE")) {
            list.add("AER");
            return list;
        } else if (sdsName.equals("ACS")) {
            list.add("ASE");
            list.add("ASI");
            return list;
        } else if (sdsName.equals("ACI")) {
            list.add("AIR");
            return list;
        } else if (sdsName.equals("IAC")) {
            list.add("IAR");
            return list;
        } else if (sdsName.equals("IAE")) {
            list.add("IAS");
            return list;
        } else if (sdsName.equals("IER")) {
            list.add("IRE");
            return list;
        } else if (sdsName.equals("IEA")) {
            list.add("IAS");
            return list;
        } else if (sdsName.equals("ICE")) {
            list.add("IEC");
            return list;
        } else if (sdsName.equals("ICS")) {
            list.add("ISC");
            return list;
        } else if (sdsName.equals("ICA")) {
            list.add("IAR");
            return list;
        } else {
            list.add(sdsName);
            return list;
        }
    }

    private List<Map.Entry<String, Integer>> sort(Map<String, Integer> map) {
        //这里将map.entrySet()转换成list
        List<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String, Integer>>(map.entrySet());
        //然后通过比较器来实现排序
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
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
