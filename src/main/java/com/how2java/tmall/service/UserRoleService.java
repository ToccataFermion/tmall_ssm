package com.how2java.tmall.service;

import com.how2java.tmall.pojo.User;

public interface UserRoleService {

    public void setRoles(User user, long[] roleIds);
    public void deleteByUser(long userId);
    public void deleteByRole(long roleId);
}
