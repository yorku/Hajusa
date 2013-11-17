package com.yorku.Hajus.model.Validator;

import java.util.Date;

import javassist.NotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yorku.Hajus.model.DAO.CatalogDAO;
import com.yorku.Hajus.model.Data.Catalog;
import com.yorku.Hajus.model.Data.Document;
import com.yorku.Hajus.model.Exception.CustomValidationException;

@Service("documentValidator")
public class DocumentValidator {
	
	@Autowired private CatalogDAO catalogDAO;

	public void validate(Document document) throws CustomValidationException, NotFoundException {
		
		String catalog_name = document.getCatalog().getName();
		if (catalog_name != null && !catalog_name.isEmpty()) {	
			
			Catalog catalog = catalogDAO.getCatalogByName(document.getCatalog().getName());
			if (catalog == null) {
				Catalog new_catalog = document.getCatalog();
				new_catalog.setCreated_date(new Date());
				catalogDAO.addCatalog(new_catalog);
				
			} else if (catalog.getDocument_count() >= 10) {
				throw new CustomValidationException("Kataloogi ei saa sisestada üle 10 dokumendi!");
			}
		}
	}
}