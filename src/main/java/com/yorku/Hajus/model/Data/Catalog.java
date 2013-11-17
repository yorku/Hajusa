package com.yorku.Hajus.model.Data;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "catalog")
@XmlRootElement(name = "catalog")
public class Catalog {

	private int    id;
	private String name;
	private Date   created_date;
	private int    document_count;
	
	public Catalog() {}
	public Catalog(String name) {
		this.name = name;
	}
	
	@Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	public int    getId()             { return id;             }
	
	@Column(name = "name", nullable = false)
	public String getName()           { return name;           }
	
	@Column(name = "created_date", nullable = false)
	public Date   getCreated_date()   { return created_date;   }
	
	@Column(name = "document_count", nullable = false)
	public int    getDocument_count() { return document_count; }
	
	public void setId             (int    id)             { this.id             = id;            }
	public void setName           (String name)           { this.name           = name;          }
	public void setCreated_date   (Date   created_date)   { this.created_date   = created_date;  }
	public void setDocument_count (int    document_count) { this.document_count = document_count;}
	
	public static Builder getBuilder() {
        return new Builder();
    }
	
	public static class Builder {

        private Catalog built;

        public Builder() {
            built = new Catalog();
        }

        public Catalog build() {
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