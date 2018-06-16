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

package com.ryctabo.hulkstore.service.converter;

import com.ryctabo.hulkstore.core.domain.CategoryData;
import com.ryctabo.hulkstore.database.entity.Category;
import com.ryctabo.hulkstore.generator.CategoryGenerator;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Gustavo Pacheco (ryctabo at gmail.com)
 * @version 1.0-SNAPSHOT
 */
public class CategoryConverterTest {

    private static CategoryConverter converter;

    @BeforeClass
    public static void setUp() {
        converter = new CategoryConverter();
    }

    @Test
    public void testConvertToEntity() {
        CategoryData category = CategoryGenerator.getData();
        Category entity = converter.convertToEntity(category);
        assertEquals(category.getName(), entity.getName());
    }

    @Test
    public void testConvertToData() {
        Category category = CategoryGenerator.getEntity();
        CategoryData data = converter.convertToDto(category);
        assertEquals(category.getName(), data.getName());
    }

}
