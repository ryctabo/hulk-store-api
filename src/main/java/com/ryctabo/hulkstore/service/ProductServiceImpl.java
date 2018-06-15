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

import com.ryctabo.hulkstore.core.domain.ErrorMessage;
import com.ryctabo.hulkstore.core.domain.ListResponse;
import com.ryctabo.hulkstore.core.domain.ProductData;
import com.ryctabo.hulkstore.core.share.OrderType;
import com.ryctabo.hulkstore.database.entity.Category;
import com.ryctabo.hulkstore.database.entity.Product;
import com.ryctabo.hulkstore.database.repository.CategoryDao;
import com.ryctabo.hulkstore.database.repository.ProductDao;
import com.ryctabo.hulkstore.service.converter.ProductConverter;
import com.ryctabo.hulkstore.service.exception.IllegalDtoException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import javax.inject.Inject;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Gustavo Pacheco (ryctabo at gmail.com)
 * @version 1.0-SNAPSHOT
 */
@Service
@Scope(WebApplicationContext.SCOPE_REQUEST)
public class ProductServiceImpl implements ProductService {

    private final ProductDao productDao;

    private final CategoryDao categoryDao;

    private final ProductConverter converter;

    @Inject
    public ProductServiceImpl(ProductDao repository, CategoryDao categoryDao, ProductConverter converter) {
        this.productDao = repository;
        this.categoryDao = categoryDao;
        this.converter = converter;
    }

    private Product getEntity(Long id) {
        if (id == null || id <= 0)
            throw new BadRequestException("The product ID is required and it must be greater than 0.");

        Product product = this.productDao.find(id);
        if (product == null) {
            final String FORMAT = "The product with ID %d was not found.";
            throw new NotFoundException(String.format(FORMAT, id));
        }

        return product;
    }

    private void validateProductData(ProductData data) {
        ErrorMessage errorMessage = new ErrorMessage();

        if (data.getName() == null || data.getName().isEmpty())
            errorMessage.addMessage("The product name is required.");

        if (data.getPrice() == null || data.getPrice() <= 0)
            errorMessage.addMessage("The price is required and it must be greater than 0.");

        if (data.getCategory() != null) {
            Long categoryId = data.getCategory().getId();
            if (categoryId == null || categoryId <= 0) {
                errorMessage.addMessage("The category ID is required and it must be greater than 0.");
            } else if (this.categoryDao.find(categoryId) == null) {
                final String FORMAT = "The category with ID %d was not found.";
                errorMessage.addMessage(String.format(FORMAT, categoryId));
            }
        } else {
            errorMessage.addMessage("The product category can't be null, this property is required.");
        }

        if (!errorMessage.getMessages().isEmpty())
            throw new IllegalDtoException(errorMessage);
    }

    @Override
    public ListResponse<ProductData> get(String search, Long categoryId, String orderBy,
                                         OrderType orderType, int start, int size) {
        List<ProductData> items = this.productDao
                .find(search, categoryId, orderBy, orderType, start, size)
                .stream()
                .map(this.converter::convertToDto)
                .collect(Collectors.toList());

        long total = this.productDao.count(search, categoryId);
        return new ListResponse<>(items, total);
    }

    @Override
    public ProductData get(Long id) {
        return this.converter.convertToDto(getEntity(id));
    }

    @Override
    public ProductData add(ProductData data) {
        this.validateProductData(data);

        Product product = this.converter.convertToEntity(data);
        product.setCreated(LocalDateTime.now());

        return this.converter.convertToDto(productDao.save(product));
    }

    @Override
    public ProductData update(Long id, ProductData data) {
        this.validateProductData(data);
        Product product = this.getEntity(id);

        product.setName(data.getName());
        product.setPrice(data.getPrice());

        Category category = new Category();
        category.setId(data.getCategory().getId());
        product.setCategory(category);

        product.setUpdated(LocalDateTime.now());

        return this.converter.convertToDto(productDao.save(product));
    }

}
