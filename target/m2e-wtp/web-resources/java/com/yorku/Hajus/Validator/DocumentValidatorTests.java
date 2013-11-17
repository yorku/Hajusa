package com.yorku.Hajus.Validator;

import static org.junit.Assert.assertNotNull;

import java.util.Date;

import javassist.NotFoundException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yorku.Hajus.model.Data.Catalog;
import com.yorku.Hajus.model.Data.Document;
import com.yorku.Hajus.model.Exception.CustomValidationException;
import com.yorku.Hajus.model.Validator.DocumentValidator;

@TransactionConfiguration(defaultRollback = true)
@Transactional(propagation = Propagation.REQUIRED)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:test-context.xml"})
@WebAppConfiguration
public class DocumentValidatorTests {

	private final String document_name = "test_nimi";
	private final String document_desc = "test_selgitus";
	
	@Autowired
    private DocumentValidator documentValidator;

    @Test
    public void testIfDAOExists() {
        assertNotNull(documentValidator);
    }
    
    @Test(expected = NotFoundException.class)
    public void test_validateDocument_UnvalidId() throws CustomValidationException, NotFoundException {
    	
    	Catalog catalog = Catalog.getBuilder()
    			.id(9999)
    			.build();
    
    	Document document = Document.getBuilder()
    			.name(document_name)
    			.description(document_desc)
    			.created_date(new Date())
    			.catalog(catalog)
    			.build();
    	
    	documentValidator.validate(document);
    }
    
    @Test(expected = CustomValidationException.class)
    public void test_validateDocument_CatalogDocumentCountOverLimit() throws CustomValidationException, NotFoundException {
    	
    	Catalog catalog = Catalog.getBuilder()
    			.id(1)
    			.document_count(13)
    			.build();
    
    	Document document = Document.getBuilder()
    			.name(document_name)
    			.description(document_desc)
    			.created_date(new Date())
    			.catalog(catalog)
    			.build();
    	
    	documentValidator.validate(document);
    }
    
    @Test(expected = CustomValidationException.class)
    public void test_validateDocument_NoCatalogIdNorNameGiven() throws CustomValidationException, NotFoundException {
    	
    	Catalog catalog = Catalog.getBuilder()
    			.id(0)
    			.name("")
    			.build();
    
    	Document document = Document.getBuilder()
    			.name(document_name)
    			.description(document_desc)
    			.created_date(new Date())
    			.catalog(catalog)
    			.build();
    	
    	documentValidator.validate(document);
    }
    
    @Test
    public void test_validateDocument_NoExceptions() throws CustomValidationException, NotFoundException {
    	
    	Catalog catalog = Catalog.getBuilder()
    			.id(1)
    			.build();
    
    	Document document = Document.getBuilder()
    			.name(document_name)
    			.description(document_desc)
    			.created_date(new Date())
    			.catalog(catalog)
    			.build();
    	
    	documentValidator.validate(document);
    }
}