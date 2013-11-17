package com.yorku.Hajus.model.Controller;

public interface ServerConfig {

	public static final String BASE_URL                   = "http://localhost:8080/Hajus";
	
	/* DOCUMENT METHODS */
	/**
     * Saves a new document entry to server database.
     * @param  {DocumentDTO document} - The information of the to be saved document entry.
     * @return {DocumentDTO document} - The saved document entry.
     * @throws MethodArgumentNotValidException - if data validation fails. 
     * @throws CustomValidationException       - if second tier validation fails.
     * @throws ConstraintViolationException    - if database tables' constraints are violated.
     */
	public static final String ADD_DOCUMENT_URI           = "/document/add_one";
	
	/**
     * Saves a list of new document entries to server database.
     * @param  {DocumentsListDTO documentsList} - The list of the to be saved document entries'.
     * @return {DocumentsListDTO documentsList}	- The list of saved document entries.
     * @throws MethodArgumentNotValidException  - if data validation fails. 
     * @throws CustomValidationException        - if second tier validation fails.
     * @throws ConstraintViolationException     - if database tables' constraints are violated.
     */
	public static final String ADD_LIST_OF_DOCUMENTS_URI  = "/document/add_several";
	
	/**
     * Gets a document with a given document ID.
     * @param  {int document_id} 	  - Wanted document's ID.
     * @return {DocumentDTO document} - Document with a given ID.
     * @throws NotFoundException      - if no data with given name is found. 
     */
	public static final String GET_DOCUMENT_BY_ID_URI     = "/document/get/{document_id}";
	
	
	/* CATALOG METHODS */
	/**
     * Gets a catalog with a given catalog ID.
     * @param  {int catalog_id} 	- Wanted catalog's ID.
     * @return {CatalogDTO catalog} - Catalog with a given ID.
     * @throws NotFoundException    - if no data with given name is found. 
     */
	public static final String GET_CATALOG_BY_ID_URI      = "/catalog/get/id/{catalog_id}";
	
	/**
     * Gets a catalog with a given catalog name.
     * @param  {String catalog_name} - Wanted catalog's name.
     * @return {CatalogDTO catalog}  - Catalog with a given name.
     * @throws NotFoundException     - if no data with given name is found. 
     */
	public static final String GET_CATALOG_BY_NAME_URI    = "/catalog/get/name/{catalog_name}";
	
	/**
     * Saves a new catalog entry to server database.
     * @param  {CatalogDTO catalog} - The information of the to be saved catalog entry.
     * @return {CatalogDTO catalog} - The saved catalog entry.
     * @throws MethodArgumentNotValidException - if data validation fails. 
     * @throws CustomValidationException       - if second tier validation fails.
     * @throws ConstraintViolationException    - if database tables' constraints are violated.
     */
	public static final String ADD_CATALOG_URI            = "/catalog/add";
	
	
	/* ERRORS */
	public static final String MethodArgumentNotValidException  = "Method argument not valid";
	public static final String CustomValidationException        = "Data validation failed!";
	public static final String NotFoundException			    = "Data not found!";
	public static final String ConstraintViolationException     = "Constraint violation!";
	public static final String CannotCreateTransactionException = "Service unavailable!";
}