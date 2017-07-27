package com.shsc.dao;

import java.util.List;

import com.shsc.model.UserProfile;

public interface UserProfileDao {
	 
    List<UserProfile> findAll();
     
    UserProfile findByType(String type);
     
    UserProfile findById(int id);
}
