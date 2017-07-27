package com.shsc.dao;

import com.shsc.model.User;

public interface UserDao {
	
	void save(User user);
	
    User findById(Long id);
     
    User findBySSO(String sso);
     
}
