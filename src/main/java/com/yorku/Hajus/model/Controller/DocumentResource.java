package com.yorku.Hajus.model.Controller;

import javassist.NotFoundException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yorku.Hajus.model.Exception.CustomValidationException;
import com.yorku.Hajus.model.Service.DocumentService;
import com.yorku.Hajus.service.DTO.DocumentDTO;
import com.yorku.Hajus.service.DTO.DocumentsListDTO;

@Controller
@RequestMapping(value    = "/document",
                produces = "application/json; charset=UTF-8")
public final class DocumentResource {
	
	private final DocumentService documentService;
	
	@Autowired
	public DocumentResource(DocumentService documentService) {
		this.documentService = documentService;
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/add_one")
	@ResponseBody
	public DocumentDTO save_document(@Valid @RequestBody DocumentDTO documentDTO) throws NotFoundException, CustomValidationException {
		
		return documentService.save_document(documentDTO);
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/add_several")
	@ResponseBody
	public DocumentsListDTO save_documents(@Valid @RequestBody DocumentsListDTO documentsListDTO) throws NotFoundException, CustomValidationException {
		
		return documentService.save_documents(documentsListDTO);
	}
}