package com.yorku.Hajus.model.Data;

import org.springframework.stereotype.Service;

import com.yorku.Hajus.service.DTO.CatalogDTO;
import com.yorku.Hajus.service.DTO.DocumentDTO;

@Service("dataAssembler")
public class DataAssembler {

	public Catalog composeCatalog(CatalogDTO catalogDTO) {
		
//		return Catalog.getBuilder(catalogDTO.getName())
//			   .id(catalogDTO.getId())
//			   .created_date(catalogDTO.getCreated_date())
//			   .document_count(catalogDTO.getDocument_count())
//			   .build();
		
		Catalog catalog = new Catalog();
		catalog.setId(catalogDTO.getId());
		catalog.setName(catalogDTO.getName());
		catalog.setCreated_date(catalogDTO.getCreated_date());
		catalog.setDocument_count(catalogDTO.getDocument_count());
		
		return catalog;
	}
	
	public Document composeDocument(DocumentDTO documentDTO) {
		
		return Document.getBuilder()
			   .name(documentDTO.getName())
			   .id(documentDTO.getId())
			   .description(documentDTO.getDescription())
			   .created_date(documentDTO.getCreated_date())
			   .catalog(composeCatalog(documentDTO.getCatalog()))
			   .build();
	}
}