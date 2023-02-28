package com.wp.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wp.dao.MusicDao;
import com.wp.dao.UserDao;
import com.wp.domain.Music;
import com.wp.domain.User;
import com.wp.service.IUserService;
import com.wp.util.Code;
import com.wp.util.Result;
import com.wp.util.TokenUtil;
import lombok.extern.log4j.Log4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;

import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 翁鹏
 * @since 2023-02-11
 */
@RestController
@RequestMapping("/user")
@Log4j
public class UserController {
    @Autowired
    IUserService userService;
    @Autowired
    UserDao userDao;



    /**
     * 注册
     * @param map
     * @return
     */
    @PostMapping
    public Result register(/*String username,String password,String checkPassword*/ @RequestBody Map<String,String> map){

        String username=map.get("username");
        String password=map.get("password");
        String checkPassword=map.get("checkPassword");

        Map<String,Object> data=new HashMap();
        System.out.println(username+"  "+password+"  "+checkPassword);

        if (!password.equals(checkPassword)){
            return new  Result(Code.ERR,null,"前后两次密码不一致");
        }
        if (userService.queryUserByName(username)!=null){
            return new  Result(Code.ERR,null,"用户名已经被使用,不能使用");
        }


        User user=new User();
        user.setUsername(username);
        //密码md5加密
        user.setPassword(DigestUtils.md5DigestAsHex(password.getBytes()));

        userDao.insert(user);
        Integer id=userService.queryUserByName(username).getId();

        data.put("id",id);
        data.put("username",username);


        return new Result(Code.OK,data,"success");

    }


    /**
     * 登录
     * @param user
     * @return
     */

    @PostMapping("/login")
    public Result login(@RequestBody User user){

        String username=user.getUsername();
        String password=DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
        Map<String,Object> map=new HashMap();

        //获取一个用户
        Subject subject = SecurityUtils.getSubject();
        // 封装用户的登录数据
        UsernamePasswordToken usernamePasswordToken  = new UsernamePasswordToken(username,password);

        System.out.println("username: "+username);



        try {
            subject.login(usernamePasswordToken );//执行登录的方法，如果没有异常就说明ok了

            String token= TokenUtil.getToken(username,userService.queryUserByName(username).getId());

            map.put("id",userService.queryUserByName(username).getId());
            map.put("username",usernamePasswordToken .getPrincipal());
            map.put("token",token);

            System.out.println(map);
            return new Result(Code.OK,map,"success");
        } catch (UnknownAccountException e) {//用户名不存在
            return new Result(Code.ERR,null,"用户名不存在");
        } catch (IncorrectCredentialsException e) {//密码不存在
            return new Result(Code.ERR,null,"密码错误");
        }
    }







}

