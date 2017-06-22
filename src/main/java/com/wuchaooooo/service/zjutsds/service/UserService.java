package com.wuchaooooo.service.zjutsds.service;

import com.wuchaooooo.service.zjutsds.dao.UserDAO;
import com.wuchaooooo.service.zjutsds.pojo.po.PUser;
import com.wuchaooooo.service.zjutsds.pojo.vo.VUser;
import com.wuchaooooo.service.zjutsds.pojo.vo.VUserInfo;
import com.wuchaooooo.service.zjutsds.utils.AuthUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by wuchaooooo on 08/06/2017.
 */
@Service
public class UserService {
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public VUser getUser(String userName) {
        PUser pUser = userDAO.getUser(userName);
        VUser vUser = new VUser();
        if (pUser != null) {
            BeanUtils.copyProperties(pUser, vUser);
            return vUser;
        }
        return null;
    }

    public VUser getUser(String userName, String password) {
        PUser pUser = userDAO.getUser(userName, password);
        VUser vUser = new VUser();
        BeanUtils.copyProperties(pUser, vUser);
        return vUser;
    }

    public int saveUserInfo(VUserInfo vUserInfo) {
        PUser pUser = new PUser();
        BeanUtils.copyProperties(vUserInfo, pUser);
        pUser.setUserName(vUserInfo.getIdCard());
        pUser.setGmtCreateInfo(new Date());
        return userDAO.saveUserInfo(pUser);
    }

    public List<VUser> listUser() {
        List<PUser> pUserList = userDAO.listUser();
        if (pUserList == null) {
            return null;
        }
        List<VUser> vUserList = new ArrayList<>();
        for (PUser pUser : pUserList) {
            VUser vUser = new VUser();
            BeanUtils.copyProperties(pUser, vUser);
            vUserList.add(vUser);
        }
        return vUserList;
    }

    public int saveUser(VUser vUser) {
        PUser pUser = new PUser();
        BeanUtils.copyProperties(vUser, pUser);
        String password = passwordEncoder.encode("666666");
        pUser.setPassword(password);
        if (AuthUtils.getAuthUser() == null) {
            //新注册学生用户
            pUser.setRole("student");
        }
        return userDAO.saveUser(pUser);
    }

    public int removeUser(int id) {
        return userDAO.removeUser(id);
    }

    public int activateUser(int id) {
        PUser pUser = userDAO.getUser(id);
        if (pUser.getIsActivated() == 1) {
            return userDAO.activateUser(0, id);
        } else {
            return userDAO.activateUser(1, id);
        }
    }

    public boolean haveUserInfo(String userName) {
        PUser pUser = userDAO.getUser(userName);
        if (pUser.getHighSchool() == null) {
            return false;
        }
        return true;
    }
}
