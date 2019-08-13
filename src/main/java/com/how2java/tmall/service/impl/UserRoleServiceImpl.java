package com.how2java.tmall.service.impl;

import com.how2java.tmall.mapper.UserRoleMapper;
import com.how2java.tmall.pojo.User;
import com.how2java.tmall.pojo.UserRole;
import com.how2java.tmall.pojo.UserRoleExample;
import com.how2java.tmall.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserRoleServiceImpl implements UserRoleService {
    @Autowired
    UserRoleMapper userRoleMapper;
    @Override
    public void setRoles(User user, long[] roleIds) {
        //删除当前用户所有的角色
        UserRoleExample example= new UserRoleExample();
        example.createCriteria().andUidEqualTo((long)user.getId());
        List<UserRole> urs= userRoleMapper.selectByExample(example);
        for (UserRole userRole : urs)
            userRoleMapper.deleteByPrimaryKey(userRole.getId());

        //设置新的角色关系
        if(null!=roleIds)
            for (long rid : roleIds) {
                UserRole userRole = new UserRole();
                userRole.setRid(rid);
                userRole.setUid((long)user.getId());
                userRoleMapper.insert(userRole);
            }
    }
    @Override
    public void deleteByUser(long userId) {
        UserRoleExample example= new UserRoleExample();
        example.createCriteria().andUidEqualTo(userId);
        List<UserRole> urs= userRoleMapper.selectByExample(example);
        for (UserRole userRole : urs) {
            userRoleMapper.deleteByPrimaryKey(userRole.getId());
        }
    }
    @Override
    public void deleteByRole(long roleId) {
        UserRoleExample example= new UserRoleExample();
        example.createCriteria().andRidEqualTo(roleId);
        List<UserRole> urs= userRoleMapper.selectByExample(example);
        for (UserRole userRole : urs) {
            userRoleMapper.deleteByPrimaryKey(userRole.getId());
        }
    }
}
