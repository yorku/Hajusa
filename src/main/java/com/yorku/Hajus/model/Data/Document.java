package com.yorku.Hajus.model.Data;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "document")
public class Document {
	
	private int     id;
	private String  name;
	private String  description;
	private Catalog catalog;
	private Date    created_date;
	
	public Document() {}
	
	@Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	public int     getId()           { return id;           }
	
	@Column(name = "name", nullable = false)
	public String  getName()         { return name;         }
	
	@Column(name = "description", nullable = false)
	public String  getDescription()  { return description;  }
	
	@Column(name = "created_date", nullable = false)
	public Date    getCreated_date() { return created_date; }
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "catalog_id")
	public Catalog getCatalog()      { return catalog;      }
	
	public void setId           (int     id)           { this.id           = id;           }
	public void setName         (String  name)         { this.name         = name;         }
	public void setDescription  (String  description)  { this.description  = description;  }
	public void setCreated_date (Date    created_date) { this.created_date = created_date; }
	public void setCatalog      (Catalog catalog)      { this.catalog      = catalog;      }
	
	public static Builder getBuilder() {
        return new Builder();
    }
	
	public static class Builder {

        private Document built;

        public Builder() {
            built = new Document();
        }

        public Document build() {
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
        
        public Builder catalog(Catalog catalog) {
            built.catalog = catalog;
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
    }
}