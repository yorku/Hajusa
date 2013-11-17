package com.yorku.Hajus.DAO;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.sql.SQLException;
import java.util.Date;

import javassist.NotFoundException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yorku.Hajus.model.DAO.DocumentDAO;
import com.yorku.Hajus.model.Data.Catalog;
import com.yorku.Hajus.model.Data.Document;

@TransactionConfiguration(defaultRollback = true)
@Transactional(propagation = Propagation.REQUIRED)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:test-context.xml"})
public class DocumentDAOTests {

	private final int    document_id   = 8;
	private final String document_name = "vfdvdfdvf";
	private final String description   = "fvddvdv";
	
	private final int    catalog_id     = 1;
	private final int    document_count = 0;
	private final String new_doc_name   = "something";
	private final String new_doc_desc   = "selgitus";
	
    @Autowired
    private DocumentDAO documentDAO;

    @Test
    public void testIfDAOExists() {
        assertNotNull(documentDAO);
    }
    
    @Test
    public void test_getDocumentById() throws NotFoundException {
    	
    	Document document = documentDAO.getDocumentById(document_id);
    	assertNotNull(document);
    	assertEquals(document_name, document.getName());
    	assertEquals(document_id,   document.getId());
    	assertEquals(description,   document.getDescription());
    }
    
    @Test
    public void test_addDocument() throws SQLException {
    	
    	Catalog catalog = Catalog.getBuilder()
    			.id(catalog_id)
    			.build();
    	
        Document document = Document.getBuilder()
        		.name(new_doc_name)
        		.description(new_doc_desc)
        		.created_date(new Date())
        		.catalog(catalog)
        		.build();

        documentDAO.save_document(document);
        int document_id = document.getId();
        assertNotNull(document_id);

        Document new_document = documentDAO.getDocumentById(document_id);
        assertNotNull(new_document);
        
        assertEquals(new_doc_name, new_document.getName());
        assertEquals(new_doc_desc, new_document.getDescription());
        assertEquals(catalog.getId(), new_document.getCatalog().getId());
    }
}