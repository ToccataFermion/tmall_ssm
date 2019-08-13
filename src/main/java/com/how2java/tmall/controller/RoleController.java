package com.how2java.tmall.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.how2java.tmall.pojo.Permission;
import com.how2java.tmall.pojo.Role;
import com.how2java.tmall.service.PermissionService;
import com.how2java.tmall.service.RolePermissionService;
import com.how2java.tmall.service.RoleService;
import com.how2java.tmall.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("")
public class RoleController {
    @Autowired
    RoleService roleService;
    @Autowired
    RolePermissionService rolePermissionService;
    @Autowired
    PermissionService permissionService;

    @RequestMapping("admin_role_list")
    public String list(Model model, Page page){
        List<Role> rs= roleService.list();
        PageHelper.offsetPage(page.getStart(),page.getCount());
        int total =(int)new PageInfo<>(rs).getTotal();
        page.setTotal(total);
        model.addAttribute("rs", rs);
        model.addAttribute("page",page);

        Map<Role,List<Permission>> role_permissions = new HashMap<>();

        for (Role role : rs) {
            List<Permission> ps = permissionService.list(role);
            role_permissions.put(role, ps);
        }
        model.addAttribute("role_permissions", role_permissions);

        return "admin/listRole";
    }
    @RequestMapping("admin_editRole")
    public String list(Model model,long id){
        Role role =roleService.get(id);
        model.addAttribute("role", role);

        List<Permission> ps = permissionService.list();
        model.addAttribute("ps", ps);

        List<Permission> currentPermissions = permissionService.list(role);
        model.addAttribute("currentPermissions", currentPermissions);

        return "admin/editRole";
    }
    @RequestMapping("admin_updateRole")
    public String update(Role role,long[] permissionIds){
        rolePermissionService.setPermissions(role, permissionIds);
        roleService.update(role);
        return "redirect:admin_role_list";
    }

    @RequestMapping("admin_addRole")
    public String list(Model model,Role role){
        System.out.println(role.getName());
        System.out.println(role.getDesc_());
        roleService.add(role);

        return "redirect:admin_role_list";
    }
    @RequestMapping("admin_deleteRole")
    public String delete(Model model,long id){
        roleService.delete(id);
        return "redirect:admin_role_list";
    }

}