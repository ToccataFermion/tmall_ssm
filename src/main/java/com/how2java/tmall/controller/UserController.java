package com.how2java.tmall.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.how2java.tmall.pojo.Role;
import com.how2java.tmall.service.RoleService;
import com.how2java.tmall.service.UserRoleService;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.how2java.tmall.pojo.User;
import com.how2java.tmall.service.UserService;
import com.how2java.tmall.util.Page;


@Controller
@RequestMapping("")
public class UserController {
    @Autowired
    UserService userService;


    @Autowired
    UserRoleService userRoleService;

    @Autowired
    RoleService roleService;

    @RequestMapping("admin_user_list")
    public String list(Model model, Page page){
        PageHelper.offsetPage(page.getStart(),page.getCount());
        List<User> us= userService.list();
        int total = (int) new PageInfo<>(us).getTotal();
        page.setTotal(total);
        Map<User,List<Role>> user_roles=new HashMap<>();
        for ( User user:us) {
            List<Role> roles=roleService.listRoles(user);
            user_roles.put(user,roles);
        }
        model.addAttribute("user_roles",user_roles);
        model.addAttribute("us", us);
        model.addAttribute("page", page);

        return "admin/listUser";
    }

    @RequestMapping("admin_listUser")
    public String list(Model model){
        List<User> us= userService.list();
        model.addAttribute("us", us);
        Map<User,List<Role>> user_roles = new HashMap<>();
        for (User user : us) {
            List<Role> roles=roleService.listRoles(user);
            user_roles.put(user, roles);
        }
        model.addAttribute("user_roles", user_roles);

        return "admin/listUser";
    }
    @RequestMapping("admin_editUser")
    public String edit(Model model,long id){
        List<Role> rs = roleService.list();
        model.addAttribute("rs", rs);
        User user =userService.get((int)id);
        model.addAttribute("user", user);

        List<Role> roles =roleService.listRoles(user);
        model.addAttribute("currentRoles", roles);

        return "admin/editUser";
    }
    @RequestMapping("admin_deleteUser")
    public String delete(Model model,long id){
        userService.delete((int)id);
        return "redirect:admin_user_list";
    }
    @RequestMapping("admin_updateUser")
    public String update(User user,long[] roleIds){
        userRoleService.setRoles(user,roleIds);

        String password=user.getPassword();
        //如果在修改的时候没有设置密码，就表示不改动密码
        if(user.getPassword().length()!=0) {
            String salt = new SecureRandomNumberGenerator().nextBytes().toString();
            int times = 2;
            String algorithmName = "md5";
            String encodedPassword = new SimpleHash(algorithmName,password,salt,times).toString();
            user.setSalt(salt);
            user.setPassword(encodedPassword);
        }
        else
            user.setPassword(null);

        userService.update(user);

        return "redirect:admin_user_list";

    }

    @RequestMapping("admin_addUser")
    public String add(Model model,String name, String password){

        String salt = new SecureRandomNumberGenerator().nextBytes().toString();
        int times = 2;
        String algorithmName = "md5";

        String encodedPassword = new SimpleHash(algorithmName,password,salt,times).toString();

        User u = new User();
        u.setName(name);
        u.setPassword(encodedPassword);
        u.setSalt(salt);
        userService.add(u);

        return "redirect:admin_user_list";
    }


}