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
import com.ryctabo.hulkstore.core.domain.CategoryData;
import com.ryctabo.hulkstore.database.repository.CategoryDao;
import com.ryctabo.hulkstore.generator.CategoryGenerator;
import com.ryctabo.hulkstore.service.converter.CategoryConverter;
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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Gustavo Pacheco (ryctabo at gmail.com)
 * @version 1.0-SNAPSHOT
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringTestConfig.class)
public class CategoryServiceTest {

    private CategoryDao repository;

    private CategoryService service;

    @Autowired
    public void setRepository(CategoryDao repository) {
        this.repository = repository;
    }

    @Before
    public void setUp() {
        this.service = new CategoryServiceImpl(repository, new CategoryConverter());
    }

    @Test
    public void testGetAllItems() {
        assertEquals(Collections.emptyList(), this.service.get());
    }

    @Test
    public void testGetItem() {
        CategoryData category = this.service.get(1L);

        assertNotNull(category);

        assertEquals(new Long(1L), category.getId());
        assertEquals("Developer", category.getName());
    }

    @Test(expected = NotFoundException.class)
    public void testGetThrowNotFound() {
        CategoryDao repository = mock(CategoryDao.class);
        when(repository.find(1L)).thenReturn(null);

        this.service = new CategoryServiceImpl(repository, new CategoryConverter());
        this.service.get(1L);
    }

    @Test(expected = BadRequestException.class)
    public void testGetThrowBadRequest() {
        this.service.get(-1L);
    }

    @Test
    public void testAdd() {
        CategoryData category = CategoryGenerator.getCategoryData();
        CategoryData response = this.service.add(category);

        assertNotNull(response);

        assertEquals("Developer", response.getName());
    }

    @Test(expected = BadRequestException.class)
    public void testAddThrowBadRequest() {
        this.service.add(new CategoryData());
    }

    @Test
    public void testUpdate() {
        CategoryData category = CategoryGenerator.getCategoryData();
        CategoryData response = this.service.update(1L, category);

        assertNotNull(response);

        assertEquals("Developer", response.getName());
    }

    @Test(expected = BadRequestException.class)
    public void testUpdateThrowBadRequest() {
        this.service.update(1L, new CategoryData());
    }

    @Test
    public void testDelete() {
        CategoryData category = this.service.delete(1L);

        assertNotNull(category);

        assertEquals(new Long(1L), category.getId());
        assertEquals("Developer", category.getName());
    }

    @Test(expected = NotFoundException.class)
    public void testDeleteThrowNotFound() {
        CategoryDao repository = mock(CategoryDao.class);
        when(repository.delete(1L)).thenReturn(null);

        this.service = new CategoryServiceImpl(repository, new CategoryConverter());
        this.service.delete(1L);
    }

    @Test(expected = BadRequestException.class)
    public void testDeleteThrowBadRequest() {
        this.service.delete(-1L);
    }
}
