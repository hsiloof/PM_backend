package com.jinax.pm_backend.Controller;

import com.jinax.pm_backend.Component.CommonResult;
import com.jinax.pm_backend.Entity.User;
import com.jinax.pm_backend.Repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Map;
import java.util.Objects;

@SpringBootTest
class UserControllerTest {
    @Autowired
    UserController userController;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    User storeUser = null;

    private void compare(CommonResult<User> result, User user, String msg){
        assert msg.equals(result.getMsg());
        User resultUser = result.getData();
        assert resultUser.getUsername().equals(user.getUsername());
        assert Objects.equals(resultUser.getId(), user.getId());
        assert passwordEncoder.matches(user.getPw(),resultUser.getPw());
//        assert resultUser.getPw().equals(passwordEncoder.encode(user.getPw()));
        assert resultUser.getMail().equals(user.getMail());
        assert Objects.equals(resultUser.getRole(), user.getRole());
        assert resultUser.getSignature().equals(user.getSignature());
    }
    private void compare(CommonResult<User> result, String msg){
        assert msg.equals(result.getMsg());
    }


    @BeforeEach
    void addTestUser(){
        User user = new User();
        user.setUsername("tmpUser");
        user.setPw(passwordEncoder.encode("123"));
        user.setMail("test@qq.com");
        user.setSignature("hello");
        user.setRole((short)1);
        storeUser = userRepository.save(user);
        storeUser.setPw("123");
    }
    @Test
    void login() {
        CommonResult<Map<String,String>> result =userController.login(storeUser.getUsername(),"1");
        assert result.getMsg().equals("用户名或密码错误");
        result = userController.login(storeUser.getUsername(),"123");
        assert result.getMsg().equals("登录成功");
    }

    @Test
    void register() {
        User user = new User();
        user.setUsername("testUser");
        user.setPw("123");
        user.setMail("test@qq.com");
        user.setSignature("hello1");
        CommonResult<String> result = userController.register(user);
        assert result.getMsg().equals("注册失败");
        user.setUsername("tmpUser1");
        result = userController.register(user);
        assert result.getMsg().equals("注册成功");
    }

    @Test
    void getUserInfos() {
        CommonResult<User> result = userController.getUserInfos(0);
        compare(result,"无该用户");
        result = userController.getUserInfos(storeUser.getId());
        compare(result,storeUser,"成功");
    }

    @Test
    void updateUserInfos() {
        CommonResult<User> result;
        result = userController.updateUserInfos(new User());
        compare(result,"更新失败");
        storeUser.setSignature("hello1");
        storeUser.setMail("test1@qq.com");
        storeUser.setPw("1234");
        result = userController.updateUserInfos(storeUser);
        compare(result,storeUser,"成功");
    }
    @AfterEach
    void deleteTestUser(){
        userRepository.deleteById(storeUser.getId());
    }
}