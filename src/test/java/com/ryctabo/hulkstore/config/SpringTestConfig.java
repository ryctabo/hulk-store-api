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

package com.ryctabo.hulkstore.config;

import com.ryctabo.hulkstore.database.repository.CategoryDao;
import com.ryctabo.hulkstore.database.repository.CategoryRepositoryStub;
import com.ryctabo.hulkstore.database.repository.ProductDao;
import com.ryctabo.hulkstore.database.repository.ProductRepositoryStub;
import com.ryctabo.hulkstore.service.CategoryService;
import com.ryctabo.hulkstore.service.CategoryServiceStub;
import com.ryctabo.hulkstore.service.ProductService;
import com.ryctabo.hulkstore.service.ProductServiceStub;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Gustavo Pacheco (ryctabo at gmail.com)
 * @version 1.0-SNAPSHOT
 */
@Configuration
public class SpringTestConfig {

    @Bean
    public CategoryDao getCategoryRepository() {
        return new CategoryRepositoryStub();
    }

    @Bean
    public CategoryService getCategoryService() {
        return new CategoryServiceStub();
    }

    @Bean
    public ProductDao getProductRepository() {
        return new ProductRepositoryStub();
    }

    @Bean
    public ProductService getProductService() {
        return new ProductServiceStub();
    }

}
