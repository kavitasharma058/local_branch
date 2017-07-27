package com.shsc.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="app_user_user_profile")
public class UserUserProfile {
 
    @Id @Column(name="user_id", nullable=false)
    private Long user_id; 
 
    @Column(name="user_profile_id", nullable=false)
    private Long user_profile_id;
     
    @Id @Column(name="user_id", nullable=false)
	public Long getUser_id() {
		return user_id;
	}

	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}

	@Column(name="user_profile_id", nullable=false)
	public Long getUser_profile_id() {
		return user_profile_id;
	}

	public void setUser_profile_id(Long user_profile_id) {
		this.user_profile_id = user_profile_id;
	}

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = (int)(prime * result + user_id);
        result = (int)(prime * result + user_profile_id);
        return result;
    }
 
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof UserProfile))
            return false;
        UserUserProfile other = (UserUserProfile) obj;
        if (user_id != other.user_id)
            return false;
        if (user_profile_id != other.user_profile_id) 
                return false;
        return true;
    }
 
    @Override
    public String toString() {
        return "UserUSerProfile [user_id=" + user_id + ",  user_profile_id=" + user_profile_id  + "]";
    }
     
 
}
