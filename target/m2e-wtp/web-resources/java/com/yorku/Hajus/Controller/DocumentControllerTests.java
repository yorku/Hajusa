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
import com.yorku.Hajus.model.Exception.CustomValidationException;
import com.yorku.Hajus.model.Service.DocumentService;
import com.yorku.Hajus.service.DTO.CatalogDTO;
import com.yorku.Hajus.service.DTO.DocumentDTO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:test-context-service.xml", 
		                           "classpath:test-context.xml"})
@WebAppConfiguration
public class DocumentControllerTests {

	/* Test parameters */
	private final int    document_id    = 1;
	private final String description    = "Seletus";
	private final String document_name  = "Mingi dokument";
	private final String too_long_document_name = "ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo";
	private final String too_long_description   = "ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo";
	
	private final int    catalog_id     = 4;
	
    private MockMvc mockMvc;

    @Autowired
    private DocumentService documentServiceMock;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setUp() {
        
        Mockito.reset(documentServiceMock);
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void test_addCatalog_ValidationFail() throws Exception {

    	CatalogDTO catalog = CatalogDTO.getBuilder()
    			.id(1)
    			.build();
    	
        DocumentDTO document = DocumentDTO.getBuilder(too_long_document_name)
			                 .id(document_id)
			                 .description(too_long_description)
			                 .catalog(catalog)
			                 .build();

        mockMvc.perform(post("/document/add_one")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(document)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.errors", hasSize(2)))
//                .andExpect(jsonPath("$.errors[*].path", containsInAnyOrder("title", "description")));
                .andExpect(jsonPath("$.errors[*]", containsInAnyOrder(
                        "name: Name lenght must be between 3 and 20!",
                        "description: Description lenght must be between 5 and 25!"
                )));

        verifyZeroInteractions(documentServiceMock);
    }
    
    @Test
    public void test_addDocument() throws Exception {
    	
    	CatalogDTO catalog = CatalogDTO.getBuilder()
                .id(catalog_id)
                .build();
    	
        DocumentDTO documentDTO = DocumentDTO.getBuilder(document_name)
                .created_date(new Date())
                .description(description)
                .catalog(catalog)
                .build();

        DocumentDTO added_document = DocumentDTO.getBuilder(document_name)
        		.id(document_id)
                .created_date(new Date())
                .description(description)
                .catalog(catalog)
                .build();

        when(documentServiceMock.save_document(any(DocumentDTO.class))).thenReturn(added_document);

        mockMvc.perform(post("/document/add_one")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(documentDTO))
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(document_id)))
                .andExpect(jsonPath("$.description", is(description)))
                .andExpect(jsonPath("$.name", is(document_name)))
                .andExpect(jsonPath("$.catalog.id", is(catalog_id)));

        ArgumentCaptor<DocumentDTO> dtoCaptor = ArgumentCaptor.forClass(DocumentDTO.class);
        verify(documentServiceMock, times(1)).save_document(dtoCaptor.capture());
        verifyNoMoreInteractions(documentServiceMock);

        DocumentDTO dtoArgument = dtoCaptor.getValue();
        assertNull(dtoArgument.getId());
//        assertThat(dtoArgument.getDocument_count(), is("description"));
//        assertThat(dtoArgument.getName(), is("title"));
    }
    
    @Test
    public void test_addDocument_WrongCatalogId() throws Exception {
    	
    	CatalogDTO catalog = CatalogDTO.getBuilder()
                .id(catalog_id)
                .build();
    	
        DocumentDTO documentDTO = DocumentDTO.getBuilder(document_name)
                .created_date(new Date())
                .description(description)
                .catalog(catalog)
                .build();
    	
        when(documentServiceMock.save_document(any(DocumentDTO.class))).thenThrow(new CustomValidationException("FAIL"));

        mockMvc.perform(post("/document/add_one"))
                .andExpect(status().isBadRequest());

        verify(documentServiceMock, times(1)).save_document(documentDTO);
        verifyNoMoreInteractions(documentServiceMock);
    }
}