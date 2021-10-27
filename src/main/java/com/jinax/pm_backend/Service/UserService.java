package com.jinax.pm_backend.Service;


import com.jinax.pm_backend.Entity.User;
import com.jinax.pm_backend.Exception.InvalidUserException;
import com.jinax.pm_backend.Repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

/**
 * @author : chara
 */
@Service
public class UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserById(int id){
        Optional<User> byId = userRepository.findById(id);
        User user = byId.orElse(null);
        LOGGER.info("getUserById, id : {}, user : {}",id,user);
        return user;
    }

    public User getUserByUserName(String username){
        Optional<User> byId = userRepository.getUserByUsernameEquals(username);
        User user = byId.orElse(null);
        LOGGER.info("getUserByUserName, username : {}, user : {}",username,user);
        return user;
    }

    public User createUser(User user) throws InvalidUserException {
        if(!verifyUser(user)){
            LOGGER.info("createUser failed, user before create : {}", user);
            throw new InvalidUserException("user invalid");//后续可能不抛异常
        }
        user.setRole((short) 0);
        User saveUser = userRepository.save(user);
        LOGGER.info("createUser, user before create : {}, user after create : {}",user,saveUser);
        return saveUser;
    }
    private boolean verifyUser(User user){
        if(user.getUsername() == null || user.getUsername().equals("")){
            return false;
        }
        if(user.getPw() == null || user.getPw().equals("")){
            return false;
        }
        if(user.getMail() == null || user.getMail().equals("")){
            return false;
        }
        return true;
    }

    public User getUser(int id){
        User user;
        try{
            user = userRepository.getOne(id);//该方法如果没找到会抛出异常
            LOGGER.info("getUser success, id : {},user : {}",id, user);
        }catch (Exception e){
            LOGGER.info("getUser fail, id : {}",id);
            return null;
        }
        return user;
    }

}
