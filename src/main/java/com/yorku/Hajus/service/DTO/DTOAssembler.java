package com.yorku.Hajus.service.DTO;

import java.util.List;

import org.springframework.stereotype.Service;

import com.yorku.Hajus.model.Data.Catalog;
import com.yorku.Hajus.model.Data.Document;

@Service("dtoAssembler")
public class DTOAssembler {
	
	public CatalogDTO composeCatalogDTO(Catalog catalog) {
		
		CatalogDTO catalogDTO = new CatalogDTO();
		catalogDTO.setId(catalog.getId());
		catalogDTO.setName(catalog.getName());
		catalogDTO.setCreated_date(catalog.getCreated_date());
		catalogDTO.setDocument_count(catalog.getDocument_count());
		
		return catalogDTO;
	}
	
	public CatalogsListDTO composeCatalogsListDTO(List<Catalog> catalogs) {
		
		CatalogsListDTO catalogsListDTO = new CatalogsListDTO();
		
		for (Catalog catalog: catalogs) {
			CatalogDTO cat = composeCatalogDTO(catalog);
			System.out.println(cat);
			catalogsListDTO.addCatalog(cat);
		}
		
		return catalogsListDTO;
	}
	
	public DocumentDTO composeDocumentDTO(Document document) {
		
		DocumentDTO documentDTO = new DocumentDTO();
		documentDTO.setId(document.getId());
		documentDTO.setName(document.getName());
		documentDTO.setDescription(document.getDescription());
		documentDTO.setCreated_date(document.getCreated_date());
		documentDTO.setCatalog(composeCatalogDTO(document.getCatalog()));
		
		return documentDTO;
	}
	
	public DocumentsListDTO composeDocumentsListDTO(List<Document> documents) {
		
		DocumentsListDTO documentListDTO = new DocumentsListDTO();
		
		for (Document document: documents) {
			documentListDTO.addDocument(composeDocumentDTO(document));
		}
		
		return documentListDTO;
	}
}