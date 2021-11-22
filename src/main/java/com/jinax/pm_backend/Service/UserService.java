package com.jinax.pm_backend.Service;


import com.jinax.pm_backend.Entity.User;
import com.jinax.pm_backend.Exception.InvalidUserException;
import com.jinax.pm_backend.Repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author : chara
 */
@Service
public class UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
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
        if(!verifyCreateUser(user)){
            LOGGER.info("createUser failed, user before create : {}", user);
            throw new InvalidUserException("user invalid");//后续可能不抛异常
        }
        user.setPw(passwordEncoder.encode(user.getPw()));
        user.setRole((short) 1);
        User saveUser = userRepository.save(user);
        LOGGER.info("createUser, user before create : {}, user after create : {}",user,saveUser);
        return saveUser;
    }
    private boolean verifyCreateUser(User user){
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

    public User updateUser(User user) throws InvalidUserException {
        if(user.getId() == null || user.getId() < 0) {
            throw new InvalidUserException("user invalid");//后续可能不抛异常
        }
        User before = userRepository.getOne(user.getId());
        if(user.getMail() != null){
            before.setMail(user.getMail());
        }
        if(user.getSignature() != null){
            before.setSignature(user.getSignature());
        }
        if(user.getPw() != null){
            before.setPw(passwordEncoder.encode(user.getPw()));
        }
        User saveUser = userRepository.save(before);
        LOGGER.info("updateUser, user after update : {}",saveUser);
        return saveUser;
    }


}
