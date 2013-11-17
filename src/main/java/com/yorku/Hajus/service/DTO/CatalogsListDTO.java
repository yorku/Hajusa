package com.yorku.Hajus.service.DTO;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "catalogsListDTO")
public class CatalogsListDTO {

	private List<CatalogDTO> catalogs;
	
	public CatalogsListDTO() {
		catalogs = new ArrayList<CatalogDTO>();
	}

	public void addCatalog(CatalogDTO catalog) {
		catalogs.add(catalog);
	}
	
	public List<CatalogDTO> getCatalogs() {
		return catalogs;
	}

	public void setCatalogs(List<CatalogDTO> catalogs) {
		this.catalogs = catalogs;
	}
}