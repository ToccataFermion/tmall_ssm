package com.how2java.tmall.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.how2java.tmall.pojo.Permission;
import com.how2java.tmall.service.PermissionService;
import com.how2java.tmall.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("")
public class PermissionController {
    @Autowired
    PermissionService permissionService;

    @RequestMapping("admin_permission_list")
    public String list(Model model, Page page) {
        List<Permission> ps = permissionService.list();

        PageHelper.offsetPage(page.getStart(),page.getCount());
        int total= (int)new PageInfo<>(ps).getTotal();
        page.setTotal(total);
        model.addAttribute("page",page);
        model.addAttribute("ps", ps);

        return "admin/listPermission";
    }

    @RequestMapping("admin_editPermission")
    public String list(Model model, long id) {
        Permission permission = permissionService.get(id);
        model.addAttribute("permission", permission);
        return "admin/editPermission";
    }

    @RequestMapping("admin_updatePermission")
    public String update(Permission permission) {

        permissionService.update(permission);
        return "redirect:admin_permission_list";
    }

    @RequestMapping("admin_addPermission")
    public String list(Model model, Permission permission) {
        System.out.println(permission.getName());
        System.out.println(permission.getDesc_());
        permissionService.add(permission);
        return "redirect:admin_permission_list";
    }

    @RequestMapping("admin_deletePermission")
    public String delete(Model model, long id) {
        permissionService.delete(id);
        return "redirect:admin_permission_list";
    }
}