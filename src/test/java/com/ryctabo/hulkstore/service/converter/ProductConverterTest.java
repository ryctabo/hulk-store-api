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

import com.ryctabo.hulkstore.core.domain.ProductData;
import com.ryctabo.hulkstore.database.entity.Product;
import com.ryctabo.hulkstore.generator.CategoryGenerator;
import com.ryctabo.hulkstore.generator.ProductGenerator;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Gustavo Pacheco (ryctabo at gmail.com)
 * @version 1.0-SNAPSHOT
 */
public class ProductConverterTest {

    private static ProductConverter converter;

    @BeforeClass
    public static void setUp() {
        CategoryConverter categoryConverter = mock(CategoryConverter.class);

        when(categoryConverter.convertToDto(any()))
                .thenReturn(CategoryGenerator.getData());

        when(categoryConverter.convertToEntity(any()))
                .thenReturn(CategoryGenerator.getEntity());

        converter = new ProductConverter(categoryConverter);
    }

    @Test
    public void testConvertToEntity() {
        ProductData Product = ProductGenerator.getData();
        Product entity = converter.convertToEntity(Product);
        assertEquals(Product.getName(), entity.getName());
    }

    @Test
    public void testConvertToData() {
        Product Product = ProductGenerator.getEntity();
        ProductData data = converter.convertToDto(Product);
        assertEquals(Product.getName(), data.getName());
    }

}
