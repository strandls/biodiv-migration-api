/**
 * 
 */
package com.strandls.migration.dao;

import javax.inject.Inject;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.strandls.migration.pojo.CustomField;
import com.strandls.migration.util.AbstractDAO;

/**
 * @author Abhishek Rudra
 *
 */
public class CustomFieldDao extends AbstractDAO<CustomField, Long> {

	private final Logger logger = LoggerFactory.getLogger(CustomFieldDao.class);

	/**
	 * @param sessionFactory
	 */
	@Inject
	protected CustomFieldDao(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	@Override
	public CustomField findById(Long id) {
		Session session = sessionFactory.openSession();
		CustomField result = null;
		try {
			result = session.get(CustomField.class, id);
		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			session.close();
		}
		return result;
	}

}
