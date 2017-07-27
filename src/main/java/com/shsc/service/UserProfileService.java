package com.shsc.service;

import java.util.List;

import com.shsc.model.UserProfile;
 
public interface UserProfileService {
 
    List<UserProfile> findAll();
     
    UserProfile findByType(String type);
     
    UserProfile findById(int id);
}
