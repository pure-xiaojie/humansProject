package com.human.service;

import com.human.mapper.HrMapper;
import com.human.mapper.HrRoleMapper;
import com.human.pojo.Hr;
import com.human.utils.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Li JieGui
 * @date 2020/11/25 21:29
 */
@Service
public class UserService implements UserDetailsService {

    @Autowired
    private HrMapper hrMapper;

    @Autowired
    private HrRoleMapper hrRoleMapper;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Hr hr = hrMapper.loadUserByUsername(userName);

        if(hr == null) {
            throw new UsernameNotFoundException("用户名不存在！");
        }
        //登录成功，查询用户具有的角色保存
        hr.setRoles(hrMapper.getRolesById(hr.getId()));
        return hr;
    }

    /**
     * 获取hr用户列表
     * @param keywords 模糊查询
     * @return
     */
    public List<Hr> getHrList(String keywords) {
        //获取当前登录用户id
        Integer id = UserUtil.getCurrentHr().getId();

        return hrMapper.getHrListById(keywords,id);
    }

    /**
     * 更新hr对象
     * @param hr
     * @return
     */
    public Integer updateHr(Hr hr) {
        return hrMapper.updateByPrimaryKeySelective(hr);
    }

    /**
     * 更新用户角色
     * @param hrid
     * @param rids
     * @return
     */
    public boolean updateHrRole(Integer hrid, Integer[] rids) {
        //先删除原有的
        hrRoleMapper.deleteByHrid(hrid);

        //插入更新的
        return hrRoleMapper.insertHrRole(hrid,rids)==rids.length;
    }

    /**
     * 删除用户
     * @param id
     * @return
     */
    public boolean deleteHrAndRole(Integer id) {
        return hrMapper.deleteByPrimaryKey(id) == 1;
    }

    /**
     * 获取hr列表
     * @return
     */
    public List<Hr> getUserListExceptCurrentUser() {
        return hrMapper.getUserListById(UserUtil.getCurrentHr().getId());
    }

    /**
     * 修改密码
     * @param oldPass  旧密码
     * @param newPass  新密码
     * @param id 用户id
     * @return
     */
    public boolean updateHrPasswd(String oldPass, String newPass, Integer id) {
        //查询到该用户
        Hr hr = hrMapper.selectByPrimaryKey(id);

        //判断旧密码与数据库密码是否一致
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (encoder.matches(oldPass, hr.getPassword())) {
            String encodePass = encoder.encode(newPass);
            //更新密码
            Integer result = hrMapper.updatePasswd(id, encodePass);
            if (result == 1) {
                return true;
            }
        }
        return false;
    }

    /**
     * 修改用户头像
     * @param url
     * @param id
     * @return
     */
    public Integer updateUserFace(Integer id,String url) {
        return hrMapper.updateUserFace(id, url);
    }
}
