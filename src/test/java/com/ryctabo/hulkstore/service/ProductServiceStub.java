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

import com.ryctabo.hulkstore.core.domain.ListResponse;
import com.ryctabo.hulkstore.core.domain.ProductData;
import com.ryctabo.hulkstore.core.share.OrderType;
import com.ryctabo.hulkstore.generator.ProductGenerator;

import java.util.Collections;

/**
 * @author Gustavo Pacheco (ryctabo at gmail.com)
 * @version 1.0-SNAPSHOT
 */
public class ProductServiceStub implements ProductService {

    @Override
    public ListResponse<ProductData> get(String search, Long categoryId, String orderBy,
                                         OrderType orderType, int start, int size) {
        return new ListResponse<>(Collections.emptyList(), 0L);
    }

    @Override
    public ProductData get(Long id) {
        return ProductGenerator.getData();
    }

    @Override
    public ProductData add(ProductData data) {
        return data;
    }

    @Override
    public ProductData update(Long id, ProductData data) {
        return data;
    }

}
