package com.yorku.Hajus.model.DAO;

import javassist.NotFoundException;

import com.yorku.Hajus.model.Data.Catalog;

public interface CatalogDAO {

	public Catalog addCatalog       (Catalog catalog);
	public Catalog getCatalogById   (int     catalog_id)   throws NotFoundException;
	public Catalog getCatalogByName (String  catalog_name) throws NotFoundException;
	public void    updateCatalog    (Catalog catalog);
}