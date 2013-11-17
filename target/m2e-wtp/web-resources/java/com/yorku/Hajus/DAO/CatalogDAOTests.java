package com.yorku.Hajus.DAO;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import javassist.NotFoundException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yorku.Hajus.model.DAO.CatalogDAO;
import com.yorku.Hajus.model.Data.Catalog;

@TransactionConfiguration(defaultRollback = true)
@Transactional(propagation = Propagation.REQUIRED)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:test-context.xml"})
public class CatalogDAOTests {

	private final int    catalog_id     = 1;
	private final String catalog_name   = "See";
	private final int    document_count = 1;
	
    @Autowired
    private CatalogDAO catalogDAO;

    @Test
    public void testIfDAOExists() {
        assertNotNull(catalogDAO);
    }
    
    @Test
    @Rollback(true)
    public void test_getCatalogById() throws NotFoundException {
    	
    	Catalog catalog = catalogDAO.getCatalogById(catalog_id);
    	assertNotNull(catalog);
    	assertEquals(catalog_name,   catalog.getName());
    	assertEquals(catalog_id,     catalog.getId());
    	assertEquals(document_count, catalog.getDocument_count());
    }
}