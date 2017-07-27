package com.shsc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
 
import com.shsc.dao.UserDao;
import com.shsc.model.User;
 
@Service("userService")
@Transactional
public class UserServiceImpl implements UserService{
 
    @Autowired
    private UserDao dao;
 
    @Autowired
    private PasswordEncoder passwordEncoder;
 
     
    public void save(User user){
    	System.out.println("UserServiceImpl - save");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        dao.save(user);
    }
    
    public User findById(Long id) {
    	System.out.println("UserServiceImpl - findById");
        return dao.findById(id);
    }
 
    public User findBySso(String sso) {
    	System.out.println("UserServiceImpl - findBySso");
        return dao.findBySSO(sso);
    }
 
}