package com.yorku.Hajus.model.DAO;

import com.yorku.Hajus.model.Data.Document;

public interface DocumentDAO {

	public Document save_document   (Document document);
	public Document getDocumentById (int     catalog_id);
}