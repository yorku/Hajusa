package com.yorku.Hajus.model.DAO;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.yorku.Hajus.model.Data.Document;

@Repository("documentDAO")
final class DocumentDAOImpl implements DocumentDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	private Session currentSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public Document save_document(Document document) {
		
		currentSession().save(document);
		return document;
	}

	@Override
	public Document getDocumentById(int document_id) {
		return (Document) currentSession().get(Document.class, document_id);
	}
}