package com.yorku.Hajus.model.DAO;

import javassist.NotFoundException;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.yorku.Hajus.model.Data.Catalog;

@Repository("catalogDAO")
final class CatalogDAOImpl implements CatalogDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	private Session currentSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public Catalog addCatalog(Catalog catalog) {
		currentSession().save(catalog);
		return catalog;
	}
	
	@Override
	public Catalog getCatalogById(int catalog_id) throws NotFoundException {
		
		Catalog catalog = (Catalog) currentSession().get(Catalog.class, catalog_id);
		if (catalog == null) {
			throw new NotFoundException("No catalog found with a given 'ID'!");
		}
		return catalog;
	}

	@Override
	public Catalog getCatalogByName(String catalog_name) throws NotFoundException {
		
		Query query = currentSession().createQuery(
				"FROM Catalog catalog " +
				"WHERE catalog.name = ?");
		query.setString(0, catalog_name);
		
		return (Catalog) query.uniqueResult();
	}

	@Override
	public void updateCatalog(Catalog catalog) {
		currentSession().update(catalog);
	}
}