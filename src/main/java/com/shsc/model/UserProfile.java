package com.shsc.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import com.shsc.model.UserProfileType;

@Entity
@Table(name="user_profile")
public class UserProfile {
 
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id; 
 
    @Column(name="type", length=15, unique=true, nullable=false)
    private String type = UserProfileType.USER.getUserProfileType();
     
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }
 
    public void setId(Long id) {
        this.id = id;
    }
 
    @Column(name="type", length=15, unique=true, nullable=false)
    public String getType() {
        return type;
    }
 
    public void setType(String type) {
        this.type = type;
    }
 
 
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = (int)(prime * result + id);
        result = (int)(prime * result + ((type == null) ? 0 : type.hashCode()));
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
        UserProfile other = (UserProfile) obj;
        if (id != other.id)
            return false;
        if (type == null) {
            if (other.type != null)
                return false;
        } else if (!type.equals(other.type))
            return false;
        return true;
    }
 
    @Override
    public String toString() {
        return "UserProfile [id=" + id + ",  type=" + type  + "]";
    }
     
 
}
