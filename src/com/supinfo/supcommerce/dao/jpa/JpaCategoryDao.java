package com.supinfo.supcommerce.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import com.supinfo.supcommerce.dao.CategoryDao;
import com.supinfo.supcommerce.entity.Category;

/**
 * Category Dao implementation dedied to Persistence through JPA.
 * 
 * @author Elka
 * @version %I% , %G%
 * @since SupCommerce 4.4
 */
public class JpaCategoryDao implements CategoryDao {
	
	private static final String			ALL_CATEGORIES_QUERY	= "allCategories";
	
	private final EntityManagerFactory	emf;
	
	/**
	 * @param emf
	 */
	public JpaCategoryDao(EntityManagerFactory emf) {
		this.emf = emf;
	}
	
	@Override
	public Category addCategory(Category category) {
		final EntityManager em = this.emf.createEntityManager();
		final EntityTransaction transaction = em.getTransaction();
		
		try {
			transaction.begin();
			em.persist(category);
			transaction.commit();
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
			}
			em.close();
		}
		return category;
	}
	
	@Override
	public Category findCategoryById(Long id) {
		final EntityManager em = this.emf.createEntityManager();
		try {
			return em.find(Category.class, id);
		} finally {
			em.close();
		}
	}
	
	@Override
	public List<Category> getAllCategories() {
		final EntityManager em = this.emf.createEntityManager();
		try {
			TypedQuery<Category> query = em.createNamedQuery(ALL_CATEGORIES_QUERY, Category.class);
			return query.getResultList();
		} finally {
			em.close();
		}
	}
	
}
