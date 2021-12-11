package com.jinax.pm_backend.Controller;


import com.jinax.pm_backend.Component.CommonResult;
import com.jinax.pm_backend.Component.MyUserDetails;
import com.jinax.pm_backend.Entity.User;
import com.jinax.pm_backend.Exception.InvalidUserException;
import com.jinax.pm_backend.Service.LoginService;
import com.jinax.pm_backend.Service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Role;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : chara
 */
@Api(tags = "UserController")
@RestController
@RequestMapping("/user")
public class UserController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;
    private final LoginService loginService;
    private final UserService userService;
    private final UserDetailsService userDetailsService;

    public UserController(LoginService loginService, UserService userService, UserDetailsService userDetailsService) {
        this.loginService = loginService;
        this.userService = userService;
        this.userDetailsService = userDetailsService;
    }


    @ApiOperation("登录")
    @ResponseBody
    @PostMapping("/login")
    public CommonResult<Map<String, String>> login(String username, String password){
        LOGGER.info("login, username is: {}, pw is: {}",username,password);
        String token = loginService.login(username, password);
        if (token == null) {
            return CommonResult.failResult(null, "用户名或密码错误");
        }
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        tokenMap.put("tokenHead", tokenHead);
        MyUserDetails userDetails = (MyUserDetails) userDetailsService.loadUserByUsername(username);
        tokenMap.put("id", String.valueOf(userDetails.getId()));
        return CommonResult.successResult(tokenMap, "登录成功");
    }
    @ApiOperation("注册")
    @ResponseBody
    @PostMapping("/register")
    public CommonResult<String> register(User user){
        LOGGER.info("register, user is: {}",user);
        try {
            User userCreate = userService.createUser(user);
//            完成登录,不一定需要
//            MyUserDetails userDetails = new MyUserDetails(userCreate);
//            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (InvalidUserException e) {
            return CommonResult.failResult("注册失败","注册失败");
        }
        return CommonResult.successResult("注册成功","注册成功");
    }

    @ApiOperation("获取用户信息")
    @ResponseBody
    @GetMapping("/user/{id}")
    public CommonResult<User> getUserInfos(@PathVariable("id") Integer id){
        LOGGER.info("getUserInfos, id is: {}",id);
        User userById = userService.getUserById(id);
        if(userById == null){
            return CommonResult.failResult(null,"无该用户");
        }
        return CommonResult.successResult(userById,"成功");
    }

    @Transactional
    @ApiOperation("更新用户信息")
    @ResponseBody
    @PutMapping("/user/update")
    public CommonResult<User> updateUserInfos(User user){
        LOGGER.info("updateUserInfos, user is {}",user);
        User userUpdated = null;
        try {
            userUpdated = userService.updateUser(user);
        } catch (InvalidUserException e) {
            return CommonResult.failResult(null,"更新失败");
        }
        return CommonResult.successResult(userUpdated,"成功");
    }
}
