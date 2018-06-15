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

package com.ryctabo.hulkstore.service;

import com.ryctabo.hulkstore.config.SpringTestConfig;
import com.ryctabo.hulkstore.core.domain.ListResponse;
import com.ryctabo.hulkstore.core.domain.ProductData;
import com.ryctabo.hulkstore.database.repository.CategoryDao;
import com.ryctabo.hulkstore.database.repository.ProductDao;
import com.ryctabo.hulkstore.generator.ProductGenerator;
import com.ryctabo.hulkstore.service.converter.ProductConverter;
import com.ryctabo.hulkstore.service.exception.IllegalDtoException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Gustavo Pacheco (ryctabo at gmail.com)
 * @version 1.0-SNAPSHOT
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringTestConfig.class)
public class ProductServiceTest {

    private ProductDao productDao;

    private CategoryDao categoryDao;

    private ProductService service;

    @Autowired
    public void setProductDao(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Autowired
    public void setCategoryDao(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    @Before
    public void setUp() {
        ProductConverter converter = mockProductConverter();
        this.service = new ProductServiceImpl(productDao, categoryDao, converter);
    }

    @Test
    public void testGetAll() {
        ListResponse<ProductData> response = this.service.get(null, null, null, null, 0, 1);

        assertNotNull(response);
        assertEquals(Collections.emptyList(), response.getItems());
        assertEquals(0L, response.getTotal());
    }

    @Test
    public void testGetById() {
        ProductData response = this.service.get(1L);

        assertNotNull(response);
        assertEquals(new Long(1L), response.getId());
    }

    @Test(expected = BadRequestException.class)
    public void testGetThrowBadRequest() {
        this.service.get(0L);
    }

    @Test(expected = NotFoundException.class)
    public void testGetThrowNotFound() {
        ProductDao productDao = mock(ProductDao.class);
        when(productDao.find(1L)).thenReturn(null);

        ProductConverter converter = mockProductConverter();

        this.service = new ProductServiceImpl(productDao, categoryDao, converter);
        this.service.get(1L);
    }

    @Test
    public void testAdd() {
        ProductData response = this.service.add(ProductGenerator.getData());

        assertNotNull(response);

        assertEquals(new Long(1L), response.getId());
    }

    @Test(expected = IllegalDtoException.class)
    public void testAddThrowIllegalDto() {
        this.service.add(new ProductData());
    }

    @Test(expected = IllegalDtoException.class)
    public void testAddThrowIllegalDtoWithCategory() {
        ProductData product = ProductGenerator.getData();
        product.getCategory().setId(0L);
        this.service.add(product);
    }

    @Test(expected = IllegalDtoException.class)
    public void testAddThrowIllegalDtoWithCategoryNotFound() {
        CategoryDao categoryDao = mock(CategoryDao.class);
        when(categoryDao.find(1L)).thenReturn(null);

        this.service = new ProductServiceImpl(productDao, categoryDao, mockProductConverter());
        this.service.add(ProductGenerator.getData());
    }

    @Test
    public void testUpdate() {
        ProductData response = this.service.update(1L, ProductGenerator.getData());

        assertNotNull(response);

        assertEquals(new Long(1L), response.getId());
    }

    private ProductConverter mockProductConverter() {
        ProductConverter converter = mock(ProductConverter.class);

        when(converter.convertToDto(any()))
                .thenReturn(ProductGenerator.getData());
        when(converter.convertToEntity(any()))
                .thenReturn(ProductGenerator.getEntity());

        return converter;
    }
}
