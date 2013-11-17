package com.yorku.Hajus.service.DTO;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "documentsListDTO")
public class DocumentsListDTO {

	private List<DocumentDTO> documents;

	public DocumentsListDTO() {
		documents = new ArrayList<DocumentDTO>();
	}
	
	public void addDocument(DocumentDTO document) {
		documents.add(document);
	}
	
	public List<DocumentDTO> getDocuments() {
		return documents;
	}

	public void setDocuments(List<DocumentDTO> documents) {
		this.documents = documents;
	}
}