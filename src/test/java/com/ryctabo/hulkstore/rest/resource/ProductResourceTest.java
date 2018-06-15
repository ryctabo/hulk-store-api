/*
 * The MIT License
 *
 * Copyright (c) 2018 Gustavo Pacheco
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is furnished
 * to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.ryctabo.hulkstore.rest.resource;

import com.ryctabo.hulkstore.config.SpringTestConfig;
import com.ryctabo.hulkstore.core.domain.ListResponse;
import com.ryctabo.hulkstore.core.domain.ProductData;
import com.ryctabo.hulkstore.generator.ProductGenerator;
import com.ryctabo.hulkstore.rest.bean.ProductBean;
import com.ryctabo.hulkstore.service.ProductService;
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

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Gustavo Pacheco (ryctabo at gmail.com)
 * @version 1.0-SNAPSHOT
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringTestConfig.class)
public class ProductResourceTest {

    private ProductService service;

    private ProductResource resource;

    @Autowired
    public void setService(ProductService service) {
        this.service = service;
    }

    @Before
    public void setUp() {
        this.resource = new ProductResource(service);
    }

    @Test
    public void testGetAllItems() {
        ListResponse<ProductData> result = this.resource.get(new ProductBean());

        assertNotNull(result);

        assertEquals(Collections.emptyList(), result.getItems());
        assertEquals(0L, result.getTotal());
    }

    @Test
    public void testGetItem() {
        ProductData result = this.resource.get(1L);

        assertNotNull(result);

        assertEquals(new Long(1L), result.getId());
    }

    @Test
    public void testPost() {
        UriInfo uriInfo = mock(UriInfo.class);
        when(uriInfo.getAbsolutePathBuilder()).thenReturn(new JerseyUriBuilder());
        Response response = this.resource.post(ProductGenerator.getData(), uriInfo);

        assertNotNull(response);

        assertEquals(201, response.getStatus());
    }

    @Test
    public void testPut() {
        ProductData data = ProductGenerator.getData();
        ProductData result = this.resource.put(1L, data);
        assertSame(data, result);
    }

}
