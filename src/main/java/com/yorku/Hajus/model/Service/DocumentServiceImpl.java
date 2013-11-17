package com.yorku.Hajus.model.Service;

import java.util.Date;
import java.util.List;

import javassist.NotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yorku.Hajus.model.DAO.CatalogDAO;
import com.yorku.Hajus.model.DAO.DocumentDAO;
import com.yorku.Hajus.model.Data.Catalog;
import com.yorku.Hajus.model.Data.DataAssembler;
import com.yorku.Hajus.model.Data.Document;
import com.yorku.Hajus.model.Exception.CustomValidationException;
import com.yorku.Hajus.model.Validator.DocumentValidator;
import com.yorku.Hajus.service.DTO.DTOAssembler;
import com.yorku.Hajus.service.DTO.DocumentDTO;
import com.yorku.Hajus.service.DTO.DocumentsListDTO;

@Service("documentService")
final class DocumentServiceImpl implements DocumentService {

	@Autowired private DocumentDAO   documentDAO;
	@Autowired private CatalogDAO    catalogDAO;
	@Autowired private DTOAssembler  dtoAssembler;
	@Autowired private DataAssembler dataAssembler;
	@Autowired private DocumentValidator documentValidator;

	@Override
	@Transactional
	public DocumentDTO save_document(DocumentDTO documentDTO) throws CustomValidationException, NotFoundException {
		
		Document document = dataAssembler.composeDocument(documentDTO);
		documentValidator.validate(document);
		
		Catalog catalog = catalogDAO.getCatalogByName(documentDTO.getCatalog().getName());
		catalog.setDocument_count(catalog.getDocument_count() + 1);
		catalogDAO.updateCatalog(catalog);
	
		document.setCatalog(catalog);
		document.setCreated_date(new Date());
		return dtoAssembler.composeDocumentDTO(documentDAO.save_document(document));
	}

	@Override
	@Transactional
	public DocumentsListDTO save_documents(DocumentsListDTO documentsListDTO) throws CustomValidationException, NotFoundException {

		List<DocumentDTO> documents = documentsListDTO.getDocuments();
		DocumentsListDTO saved_documents = new DocumentsListDTO();
		
		for (DocumentDTO document: documents) {
			saved_documents.addDocument(save_document(document));
		}
		
		return saved_documents;
	}
}