package com.yorku.Hajus.Controller;

import static junit.framework.Assert.assertNull;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;

import javassist.NotFoundException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.yorku.Hajus.TestUtil;
import com.yorku.Hajus.model.Service.CatalogService;
import com.yorku.Hajus.service.DTO.CatalogDTO;
import com.yorku.Hajus.service.DTO.CatalogsListDTO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:test-context-service.xml", 
		                           "classpath:test-context.xml"})
@WebAppConfiguration
public class CatalogControllerTests {
	
	/* Test parameters */
	private final int    catalog_id     = 1;
	private final int    document_count = 5;
	private final String catalog_name   = "Mingi kataloog";
	private final String too_long_catalog_name    = "Name longer than 25 yoloswag420blaze + even longer wooooooord";
	private final int    too_large_document_count = 12;

    private MockMvc mockMvc;

    @Autowired
    private CatalogService catalogServiceMock;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setUp() {
        
        Mockito.reset(catalogServiceMock);
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void test_getCatalogById() throws Exception {
    	
    	CatalogDTO catalog = CatalogDTO.getBuilder()
    						 .name(catalog_name)
                             .id(catalog_id)
			                 .document_count(document_count)
			                 .build();

        when(catalogServiceMock.getCatalogById(catalog_id)).thenReturn(catalog);

        mockMvc.perform(get("/catalog/get/id/{catalog_id}", catalog_id))
                .andExpect(status().isOk())
                //.andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(catalog_id)))
                .andExpect(jsonPath("$.name", is(catalog_name)))
                .andExpect(jsonPath("$.document_count", is(document_count)));

        verify(catalogServiceMock, times(1)).getCatalogById(catalog_id);
        verifyNoMoreInteractions(catalogServiceMock);
    }
    
    @Test
    public void test_getCatalogByName() throws Exception {
    	
    	CatalogDTO catalog = CatalogDTO.getBuilder()
    						 .name(catalog_name)
                             .id(catalog_id)
			                 .document_count(document_count)
			                 .build();

        when(catalogServiceMock.getCatalogByName(catalog_name)).thenReturn(catalog);

        mockMvc.perform(get("/catalog/get/name/{catalog_name}", catalog_name))
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(catalog_id)))
                .andExpect(jsonPath("$.name", is(catalog_name)))
                .andExpect(jsonPath("$.document_count", is(document_count)));

        verify(catalogServiceMock, times(1)).getCatalogByName(catalog_name);
        verifyNoMoreInteractions(catalogServiceMock);
    }
    
    @Test
    public void test_getCatalogById_NotFound() throws Exception {
    	
        when(catalogServiceMock.getCatalogById(catalog_id)).thenThrow(new NotFoundException("FAIL"));

        mockMvc.perform(get("/catalog/get/id/{catalog_id}", catalog_id))
                .andExpect(status().isNotFound());

        verify(catalogServiceMock, times(1)).getCatalogById(catalog_id);
        verifyNoMoreInteractions(catalogServiceMock);
    }
    
    @Test
    public void test_getCatalogsByName_NotFound() throws Exception {
    	
        when(catalogServiceMock.getCatalogByName(catalog_name)).thenThrow(new NotFoundException("FAIL"));

        mockMvc.perform(get("/catalog/get/name/{catalog_name}", catalog_name))
                .andExpect(status().isNotFound());

        verify(catalogServiceMock, times(1)).getCatalogByName(catalog_name);
        verifyNoMoreInteractions(catalogServiceMock);
    }
    
    @Test
    public void test_addCatalog_ValidationFail() throws Exception {

        CatalogDTO catalog = CatalogDTO.getBuilder()
        					 .name(too_long_catalog_name)
			                 .id(catalog_id)
			                 .document_count(too_large_document_count)
			                 .build();

        mockMvc.perform(post("/catalog/add")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(catalog))
        )
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.errors", hasSize(2)))
//                .andExpect(jsonPath("$.fieldErrors[*].path", containsInAnyOrder("title", "description")));
                .andExpect(jsonPath("$.errors[*]", containsInAnyOrder(
                        "name: Catalog name cannot be bigger than 25 and smaller than 5!",
                        "document_count: Document count cant pass 10!"
                )));

        verifyZeroInteractions(catalogServiceMock);
    }
    
    @Test
    public void test_addCatalog() throws Exception {
    	
    	
        CatalogDTO catalogDTO = CatalogDTO.getBuilder()
        		.name(catalog_name)
                .created_date(new Date())
                .document_count(document_count)
                .build();

        CatalogDTO added_catalog = CatalogDTO.getBuilder()
        		.name(catalog_name)
        		.id(catalog_id)
                .created_date(new Date())
                .document_count(document_count)
                .build();

        when(catalogServiceMock.addCatalog(any(CatalogDTO.class))).thenReturn(added_catalog);

        mockMvc.perform(post("/catalog/add")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(catalogDTO))
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(catalog_id)))
                .andExpect(jsonPath("$.document_count", is(document_count)))
                .andExpect(jsonPath("$.name", is(catalog_name)));

        ArgumentCaptor<CatalogDTO> dtoCaptor = ArgumentCaptor.forClass(CatalogDTO.class);
        verify(catalogServiceMock, times(1)).addCatalog(dtoCaptor.capture());
        verifyNoMoreInteractions(catalogServiceMock);

        CatalogDTO dtoArgument = dtoCaptor.getValue();
        assertNull(dtoArgument.getId());
//        assertThat(dtoArgument.getDocument_count(), is("description"));
//        assertThat(dtoArgument.getName(), is("title"));
    }
}