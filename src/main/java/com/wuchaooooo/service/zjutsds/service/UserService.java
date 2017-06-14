package com.wuchaooooo.service.zjutsds.service;

import com.wuchaooooo.service.zjutsds.dao.UserDAO;
import com.wuchaooooo.service.zjutsds.dao.UserInfoDAO;
import com.wuchaooooo.service.zjutsds.pojo.po.PUser;
import com.wuchaooooo.service.zjutsds.pojo.po.PUserInfo;
import com.wuchaooooo.service.zjutsds.pojo.vo.VUser;
import com.wuchaooooo.service.zjutsds.pojo.vo.VUserInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
    private UserInfoDAO userInfoDAO;
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
        PUserInfo pUserInfo = new PUserInfo();
        BeanUtils.copyProperties(vUserInfo, pUserInfo);
        pUserInfo.setGmtCreateInfo(new Date());
        return userInfoDAO.saveUserInfo(pUserInfo);
    }

    public VUserInfo getUserInfo(String userName) {
        PUserInfo pUserInfo = userInfoDAO.getUserInfo(userName);
        VUserInfo vUserInfo = new VUserInfo();
        if (pUserInfo != null) {
            BeanUtils.copyProperties(pUserInfo, vUserInfo);
            return vUserInfo;
        }
        return null;
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
        pUser.setGmtCreate(new Date());
        String password = passwordEncoder.encode("666666");
        pUser.setPassword(password);
        return userDAO.saveUser(pUser);
    }

    public int removeUser(int id) {
        return userDAO.removeUser(id);
    }

    public boolean haveUserInfo(String userName) {
        PUserInfo pUserInfo = userInfoDAO.getUserInfo(userName);
        if (pUserInfo == null) {
            return false;
        }
        return true;
    }
}
