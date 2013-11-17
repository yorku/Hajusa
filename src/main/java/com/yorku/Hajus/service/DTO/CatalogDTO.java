package com.yorku.Hajus.service.DTO;

import java.util.Date;

import javax.validation.constraints.Max;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.NotEmpty;

@XmlRootElement(name = "catalogDTO")
public class CatalogDTO {

	private int    id;
	private Date   created_date;
	
	@NotEmpty(message = "Name cannot be null!")
	@Size(min = 5, max = 25, message = "Catalog name cannot be bigger than 25 and smaller than 5!")
	private String name;
	
	@Max(value = 10, message = "Document count cant pass 10!")
	private int    document_count;
	
	public CatalogDTO() {}
	public CatalogDTO(String name) {
		this.name = name;
	}
	
	public CatalogDTO(int id) {
		this.id = id;
	}

	public int    getId()             { return id;             }
	public String getName()           { return name;           }
	public Date   getCreated_date()   { return created_date;   }
	public int    getDocument_count() { return document_count; }
	
	public void setId             (int    id)             { this.id             = id;            }
	public void setName           (String name)           { this.name           = name;          }
	public void setCreated_date   (Date   parsed)         { this.created_date   = parsed;        }
	public void setDocument_count (int    document_count) { this.document_count = document_count;}
	
	public static Builder getBuilder() {
        return new Builder();
    }
	
	public static Builder getBuilder(String name) {
        return new Builder(name);
    }
	
	public static class Builder {

        private CatalogDTO built;

        public Builder() {
            built = new CatalogDTO();
        }
        
        public Builder(String name) {
            built = new CatalogDTO();
            built.name = name;
        }

        public CatalogDTO build() {
            return built;
        }
        
        public Builder name(String name) {
            built.name = name;
            return this;
        }

        public Builder id(int id) {
            built.id = id;
            return this;
        }
        
        public Builder created_date(Date created_date) {
            built.created_date = created_date;
            return this;
        }
        
        public Builder document_count(int document_count) {
            built.document_count = document_count;
            return this;
        }
    }
}