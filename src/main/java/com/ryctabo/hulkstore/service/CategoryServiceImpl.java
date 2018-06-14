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

import com.ryctabo.hulkstore.core.domain.CategoryData;
import com.ryctabo.hulkstore.database.entity.Category;
import com.ryctabo.hulkstore.database.repository.CategoryDao;
import com.ryctabo.hulkstore.service.converter.CategoryConverter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Gustavo Pacheco (ryctabo at gmail.com)
 * @version 1.0-SNAPSHOT
 */
@Service
@Scope(WebApplicationContext.SCOPE_REQUEST)
public class CategoryServiceImpl implements CategoryService {

    private final CategoryDao repository;

    private final CategoryConverter converter;

    @Inject
    public CategoryServiceImpl(CategoryDao repository, CategoryConverter converter) {
        this.repository = repository;
        this.converter = converter;
    }

    private Category getEntity(Long id) {
        if (id == null || id <= 0)
            throw new BadRequestException("The category is required and must be greater than 0.");

        Category category = this.repository.find(id);
        if (category == null) {
            final String FORMAT = "The category with ID %d was not found.";
            throw new NotFoundException(String.format(FORMAT, id));
        }
        return category;
    }

    @Override
    public List<CategoryData> get() {
        return this.repository.find().stream()
                .map(converter::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryData get(Long id) {
        return this.converter.convertToDto(getEntity(id));
    }

    @Override
    public CategoryData add(@NotNull CategoryData data) {
        if (data.getName() == null || data.getName().trim().length() == 0)
            throw new BadRequestException("The category name is required.");

        Category category = this.repository.save(this.converter.convertToEntity(data));
        return this.converter.convertToDto(category);
    }

    @Override
    public CategoryData update(Long id, CategoryData data) {
        if (data.getName() == null || data.getName().trim().length() == 0)
            throw new BadRequestException("The category name is required.");

        Category category = getEntity(id);
        category.setName(data.getName());

        return this.converter.convertToDto(this.repository.save(category));
    }

    @Override
    public CategoryData delete(Long id) {
        if (id == null || id <= 0)
            throw new BadRequestException("The category is required and must be greater than 0.");

        Category category = this.repository.delete(id);
        if (category == null) {
            final String FORMAT = "The category with ID %d was not found.";
            throw new NotFoundException(String.format(FORMAT, id));
        }
        return this.converter.convertToDto(category);
    }

}
