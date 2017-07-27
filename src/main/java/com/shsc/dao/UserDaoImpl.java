package com.shsc.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
//import org.springframework.transaction.annotation.Transactional;
//import javax.transaction.Transactional;

import com.shsc.model.*;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
 
@Repository("userDao")
public class UserDaoImpl implements UserDao {

	@PersistenceContext
	private EntityManager entityManager;

	private Logger logger = Logger.getLogger(UserDaoImpl.class);
	
	@Transactional
	public void save(User user) {
		entityManager.persist(user);
    }
	
	@Transactional
    public User findById(Long id) {
    	
		try {
			entityManager.getEntityManagerFactory().getCache().evictAll();
			CriteriaBuilder builder = entityManager.getCriteriaBuilder();
			CriteriaQuery<User> criteriaQuery = builder.createQuery(User.class);
			Root<User> userRoot = criteriaQuery.from(User.class);
			
			criteriaQuery.select(userRoot);
			criteriaQuery.where(builder.equal(userRoot.get(User_.id), id));

			System.out.print("User id : "+entityManager.createQuery(criteriaQuery).setMaxResults(1).getSingleResult());
			return entityManager.createQuery(criteriaQuery).setMaxResults(1).getSingleResult();

		} catch (Exception e) {
			System.out.print("Exception in UserDaoImpl.getById:: " + e.getLocalizedMessage());
			logger.error("Exception in UserDaoImpl.getById:: " + e.getLocalizedMessage());
			return null;
		}
    }
 
    @Transactional
    public User findBySSO(String sso) {
		try {
			entityManager.getEntityManagerFactory().getCache().evictAll();
			CriteriaBuilder builder = entityManager.getCriteriaBuilder();			
			CriteriaQuery<User> criteriaQuery = builder.createQuery(User.class);			
			Root<User> userRoot = criteriaQuery.from(User.class);
			
			criteriaQuery.select(userRoot);			
			criteriaQuery.where(builder.equal(userRoot.get(User_.ssoId), sso));

			return entityManager.createQuery(criteriaQuery).setMaxResults(1).getSingleResult();
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.print("Exception in UserDaoImpl.findBySSO: " + e.getLocalizedMessage());
			logger.error("Exception in UserDaoImpl.findBySSO: " + e.getLocalizedMessage());
			return null;
		}
    }     
}
