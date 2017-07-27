package com.shsc.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.shsc.model.UserProfile;
import com.shsc.model.UserProfile_;

@Repository("userProfileDao")
public class UserProfileDaoImpl implements UserProfileDao{
 
	@PersistenceContext
	private EntityManager entityManager;

	private Logger logger = Logger.getLogger(UserDaoImpl.class);

	//@SuppressWarnings("unchecked")
    public List<UserProfile> findAll(){
		try {
			entityManager.getEntityManagerFactory().getCache().evictAll();
			CriteriaBuilder builder = entityManager.getCriteriaBuilder();
			CriteriaQuery<UserProfile> criteriaQuery = builder.createQuery(UserProfile.class);

			Root<UserProfile> userAttemptRoot = criteriaQuery.from(UserProfile.class);
			criteriaQuery.select(userAttemptRoot);

			//criteriaQuery.where(builder.equal(userAttemptRoot.get(UserProfile_.userByUserId), user));
			criteriaQuery.orderBy(builder.asc(userAttemptRoot.get(UserProfile_.type)));

			return entityManager.createQuery(criteriaQuery).getResultList();
		} catch (Exception e) {
			System.out.print("Exception in UserProfileDaoImpl.findAll: " + e.getLocalizedMessage());
			logger.error("Exception in UserProfileDaoImpl.findAll: " + e.getLocalizedMessage());
			return null;
		}        
    }
     
	@Transactional
    public UserProfile findById(int id) {
		try {
			entityManager.getEntityManagerFactory().getCache().evictAll();
			CriteriaBuilder builder = entityManager.getCriteriaBuilder();
			CriteriaQuery<UserProfile> criteriaQuery = builder.createQuery(UserProfile.class);

			Root<UserProfile> userRoot = criteriaQuery.from(UserProfile.class);
			criteriaQuery.select(userRoot);

			criteriaQuery.where(builder.equal(userRoot.get(UserProfile_.id), id));

			System.out.print("User id : "+entityManager.createQuery(criteriaQuery).setMaxResults(1).getSingleResult());
			return entityManager.createQuery(criteriaQuery).setMaxResults(1).getSingleResult();
		} catch (Exception e) {
			System.out.print("Exception in UserProfileDaoImpl.findById:: " + e.getLocalizedMessage());
			logger.error("Exception in UserProfileDaoImpl.findById:: " + e.getLocalizedMessage());
			return null;
		}
    }
    
	@Transactional
    public UserProfile findByType(String type) {
		try {
			entityManager.getEntityManagerFactory().getCache().evictAll();
			CriteriaBuilder builder = entityManager.getCriteriaBuilder();
			CriteriaQuery<UserProfile> criteriaQuery = builder.createQuery(UserProfile.class);
			Root<UserProfile> userRoot = criteriaQuery.from(UserProfile.class);
			
			criteriaQuery.select(userRoot);
			criteriaQuery.where(builder.equal(userRoot.get(UserProfile_.type), type));
           
			System.out.println("findByType() : "+entityManager.createQuery(criteriaQuery).setMaxResults(1).getSingleResult());
			return entityManager.createQuery(criteriaQuery).setMaxResults(1).getSingleResult();
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.print("Exception in UserProfileDaoImpl.findByType: " + e.getLocalizedMessage());
			logger.error("Exception in UserProfileDaoImpl.findByType: " + e.getLocalizedMessage());
			return null;
		}
    }	
}
