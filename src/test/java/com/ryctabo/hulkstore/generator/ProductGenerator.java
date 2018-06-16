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

package com.ryctabo.hulkstore.generator;

import com.ryctabo.hulkstore.core.domain.ProductData;
import com.ryctabo.hulkstore.database.entity.Product;

import java.time.LocalDateTime;

/**
 * @author Gustavo Pacheco (ryctabo at gmail.com)
 * @version 1.0-SNAPSHOT
 */
public class ProductGenerator {

    public static Product getEntity() {
        Product product = new Product();

        product.setId(1L);
        product.setName("Product 1");
        product.setPrice(100f);
        product.setCategory(CategoryGenerator.getEntity());
        product.setCreated(LocalDateTime.now());
        product.setUpdated(LocalDateTime.now());

        return product;
    }

    public static ProductData getData() {
        ProductData product = new ProductData();

        product.setId(1L);
        product.setName("Product 1");
        product.setPrice(100f);
        product.setCategory(CategoryGenerator.getData());
        product.setCreated(LocalDateTime.now());
        product.setUpdated(LocalDateTime.now());

        return product;
    }

}
