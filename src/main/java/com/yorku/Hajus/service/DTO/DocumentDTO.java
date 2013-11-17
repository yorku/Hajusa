package com.yorku.Hajus.service.DTO;

import java.util.Date;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.NotEmpty;

@XmlRootElement(name = "documentDTO")
public class DocumentDTO {
	
	private int     id;
	
	@NotEmpty(message = "Name cannot be null!")
    @Size(min = 3, max = 25, message = "Name lenght must be between 3 and 20!")
	private String  name;
	
	@NotEmpty(message = "Description cannot be null!")
	@Size(min = 5, max = 50, message = "Description lenght must be between 5 and 25!")
	private String  description;
	
	@Valid
	@NotNull(message = "Catalog cannot be null!")
	private CatalogDTO catalog;
	
	private Date    created_date;
	
	public DocumentDTO() {}
	
	public int        getId()           { return id;           }
	public String     getName()         { return name;         }
	public String     getDescription()  { return description;  }
	public Date       getCreated_date() { return created_date; }
	public CatalogDTO getCatalog()      { return catalog;      }
	
	public void setId           (int     id)          { this.id           = id;           }
	public void setName         (String  name)        { this.name         = name;         }
	public void setDescription  (String  description) { this.description  = description;  }
	public void setCreated_date (Date    created_date){ this.created_date = created_date; }
	public void setCatalog      (CatalogDTO catalog)  { this.catalog      = catalog;      }
	
	public static Builder getBuilder() {
        return new Builder();
    }
	
	public static Builder getBuilder(String name) {
        return new Builder(name);
    }
	
	public static class Builder {

        private DocumentDTO built;

        public Builder() {
            built = new DocumentDTO();
        }
        
        public Builder(String name) {
            built = new DocumentDTO();
            built.name = name;
        }

        public DocumentDTO build() {
            return built;
        }

        public Builder id(int id) {
            built.id = id;
            return this;
        }
        
        public Builder name(String name) {
            built.name = name;
            return this;
        }
        
        public Builder description(String description) {
            built.description = description;
            return this;
        }
        
        public Builder created_date(Date created_date) {
            built.created_date = created_date;
            return this;
        }
        
        public Builder catalog(CatalogDTO catalog) {
            built.catalog = catalog;
            return this;
        }
    }
}