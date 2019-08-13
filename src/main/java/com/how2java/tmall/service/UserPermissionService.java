package com.how2java.tmall.service;

import com.how2java.tmall.pojo.Role;

public interface UserPermissionService {

        public void setPermissions(Role role, long[] permissionIds);
        public void deleteByRole(long roleId);
        public void deleteByPermission(long permissionId);

}
