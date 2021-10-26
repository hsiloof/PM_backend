package com.jinax.pm_backend.Controller;


import com.jinax.pm_backend.Service.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
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
    private final UserDetailsService userDetailsService;

    public UserController( LoginService loginService, UserDetailsService userDetailsService) {
        this.loginService = loginService;
        this.userDetailsService = userDetailsService;
    }


    @ApiOperation("登录")
    @ResponseBody
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(String identification,String password){
        LOGGER.info("get user, identification Id is: {}, pw is: {}",identification,password);
        String token = loginService.login(identification, password);
        if (token == null) {
            return new ResponseEntity<>(null,HttpStatus.FORBIDDEN);
        }
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        tokenMap.put("tokenHead", tokenHead);
//        MyUserDetails userDetails = (MyUserDetails) userDetailsService.loadUserByUsername(identification);
        return new ResponseEntity<Map<String, String>>(tokenMap, HttpStatus.OK);
    }
    @ApiOperation("注册")
    @ResponseBody
    @PostMapping("/register")
    public ResponseEntity<String> register(String identification,String password){
        LOGGER.info("get user, identification Id is: {}, pw is: {}",identification,password);
//        MyUserDetails userDetails = (MyUserDetails) userDetailsService.loadUserByUsername(identification);
        return new ResponseEntity<String>("success",HttpStatus.OK);
    }
}
