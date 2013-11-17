package com.yorku.Hajus.model.Service;

import javassist.NotFoundException;

import com.yorku.Hajus.model.Exception.CustomValidationException;
import com.yorku.Hajus.service.DTO.DocumentDTO;
import com.yorku.Hajus.service.DTO.DocumentsListDTO;

public interface DocumentService {

	/**
     * Saves a new document entry to server database.
     * @param document 					 - The information of the saved document entry.
     * @return 							 - The saved document entry.
     * @throws CustomValidationException - if document information validation fails.
     * @throws NotFoundException         - if given document's wrapper catalog UD has no match in database.
     */
	public DocumentDTO      save_document (DocumentDTO      document)         throws CustomValidationException, NotFoundException;
	
	/**
     * Saves a list of new document entries to server database.
     * @param document 					 - The list of saved document entries' information.
     * @return						     - The list of saved document entries.
     * @throws CustomValidationException - if document information validation fails.
     * @throws NotFoundException         - if given document's wrapper catalog UD has no match in database.
     */
	public DocumentsListDTO save_documents(DocumentsListDTO documentsListDTO) throws CustomValidationException, NotFoundException;
}