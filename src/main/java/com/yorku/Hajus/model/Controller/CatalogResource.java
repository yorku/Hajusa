package com.yorku.Hajus.model.Controller;

import java.net.ConnectException;

import javassist.NotFoundException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yorku.Hajus.model.Service.CatalogService;
import com.yorku.Hajus.service.DTO.CatalogDTO;

@Controller
@RequestMapping(value = "/catalog", produces = "application/json; charset=UTF-8")
public final class CatalogResource {

	private final CatalogService  catalogService;
	
	@Autowired
	public CatalogResource(CatalogService  catalogService) {
		
		this.catalogService  = catalogService;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/get/id/{catalog_id}")
	@ResponseBody
	public CatalogDTO getCatalogById(@PathVariable final int catalog_id) throws NotFoundException {
		
		return catalogService.getCatalogById(catalog_id);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/get/name/{catalog_name}")
	@ResponseBody
	public CatalogDTO getCatalogByName(@PathVariable final String catalog_name) throws NotFoundException {
		
		return catalogService.getCatalogByName(catalog_name);
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/add")
	@ResponseBody
	public CatalogDTO addCatalog(@Valid @RequestBody final CatalogDTO catalogDTO) throws ConnectException {
		
		return catalogService.addCatalog(catalogDTO);
	}
}