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

package com.ryctabo.hulkstore.database.repository;

import com.ryctabo.hulkstore.core.share.OrderType;
import com.ryctabo.hulkstore.database.entity.Product;
import com.ryctabo.hulkstore.generator.ProductGenerator;

import java.util.Collections;
import java.util.List;

/**
 * @author Gustavo Pacheco (ryctabo at gmail.com)
 * @version 1.0-SNAPSHOT
 */
public class ProductRepositoryStub implements ProductDao {

    @Override
    public List<Product> find(String search, Long categoryId, String orderBy,
                              OrderType orderType, int start, int size) {
        return Collections.emptyList();
    }

    @Override
    public long count(String search, Long categoryId) {
        return 0L;
    }

    @Override
    public List<Product> find() {
        return Collections.emptyList();
    }

    @Override
    public Product find(Long id) {
        return ProductGenerator.getEntity();
    }

    @Override
    public Product save(Product entity) {
        return entity;
    }

    @Override
    public Product delete(Long id) {
        return ProductGenerator.getEntity();
    }

}
