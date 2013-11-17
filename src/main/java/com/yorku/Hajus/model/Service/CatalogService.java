package com.yorku.Hajus.model.Service;

import javassist.NotFoundException;

import com.yorku.Hajus.service.DTO.CatalogDTO;

public interface CatalogService {

	public CatalogDTO getCatalogByName(String catalog_name) throws NotFoundException;
	public CatalogDTO getCatalogById(int catalog_id) throws NotFoundException;
	public CatalogDTO addCatalog(CatalogDTO catalogDTO);
}