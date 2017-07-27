package com.shsc.service;

import com.shsc.model.User;

public interface UserService {
 
	void save(User user);
	
    User findById(Long id);
     
    User findBySso(String sso);
     
}