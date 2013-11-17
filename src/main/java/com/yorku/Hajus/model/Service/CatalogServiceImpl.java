package com.yorku.Hajus.model.Service;

import java.util.Date;

import javassist.NotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yorku.Hajus.model.DAO.CatalogDAO;
import com.yorku.Hajus.model.Data.Catalog;
import com.yorku.Hajus.model.Data.DataAssembler;
import com.yorku.Hajus.service.DTO.CatalogDTO;
import com.yorku.Hajus.service.DTO.DTOAssembler;

@Service("catalogService")
final class CatalogServiceImpl implements CatalogService {

	@Autowired private CatalogDAO    catalogDAO;
	@Autowired private DTOAssembler  dtoAssembler;
	@Autowired private DataAssembler dataAssembler;
	
	@Override
	@Transactional
	public CatalogDTO getCatalogByName(String catalog_name) throws NotFoundException {
		return dtoAssembler.composeCatalogDTO(catalogDAO.getCatalogByName(catalog_name));
	}

	@Override
	@Transactional
	public CatalogDTO getCatalogById(int catalog_id) throws NotFoundException {
		return dtoAssembler.composeCatalogDTO(catalogDAO.getCatalogById(catalog_id));
	}

	@Override
	@Transactional
	public CatalogDTO addCatalog(CatalogDTO catalogDTO) {
		
		Catalog catalog = dataAssembler.composeCatalog(catalogDTO);	
		catalog.setCreated_date(new Date());
		return dtoAssembler.composeCatalogDTO(catalogDAO.addCatalog(catalog));
	}
}