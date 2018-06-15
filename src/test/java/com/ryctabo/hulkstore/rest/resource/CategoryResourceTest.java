package com.ryctabo.hulkstore.rest.resource;

import com.ryctabo.hulkstore.config.SpringTestConfig;
import com.ryctabo.hulkstore.core.domain.CategoryData;
import com.ryctabo.hulkstore.generator.CategoryGenerator;
import com.ryctabo.hulkstore.service.CategoryService;
import org.glassfish.jersey.uri.internal.JerseyUriBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Gustavo Pacheco (ryctabo at gmail.com)
 * @version 1.0-SNAPSHOT
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SpringTestConfig.class})
public class CategoryResourceTest {

    private CategoryService service;

    private CategoryResource resource;

    private CategoryData data = CategoryGenerator.getData();

    @Autowired
    public void setService(CategoryService service) {
        this.service = service;
    }

    @Before
    public void setUp() {
        this.resource = new CategoryResource(service);
    }

    @Test
    public void testGetAllItems() {
        List<CategoryData> result = this.resource.get();

        assertNotNull(result);

        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testGetItem() {
        CategoryData result = this.resource.get(1L);

        assertNotNull(result);

        assertEquals(new Long(1L), result.getId());
        assertEquals("Developer", result.getName());
    }

    @Test
    public void testPost() {
        UriInfo uriInfo = mock(UriInfo.class);
        when(uriInfo.getAbsolutePathBuilder()).thenReturn(new JerseyUriBuilder());
        Response response = this.resource.post(this.data, uriInfo);

        assertNotNull(response);

        assertEquals(201, response.getStatus());
    }

    @Test
    public void testPut() {
        CategoryData result = this.resource.put(1L, this.data);
        assertSame(this.data, result);
    }

    @Test
    public void testDelete() {
        CategoryData result = this.resource.delete(1L);

        assertNotNull(result);
        assertEquals(new Long(1L), result.getId());
    }
}