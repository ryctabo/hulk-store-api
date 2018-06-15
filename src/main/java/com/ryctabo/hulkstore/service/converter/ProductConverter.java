package com.ryctabo.hulkstore.service.converter;

import com.ryctabo.hulkstore.core.domain.ProductData;
import com.ryctabo.hulkstore.database.entity.Category;
import com.ryctabo.hulkstore.database.entity.Product;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import javax.inject.Inject;

/**
 * @author Gustavo Pacheco (ryctabo at gmail.com)
 * @version 1.0-SNAPSHOT
 */
@Component
@Scope(WebApplicationContext.SCOPE_REQUEST)
public class ProductConverter implements DtoConverter<Product, ProductData, ProductData> {

    private final CategoryConverter categoryConverter;

    @Inject
    public ProductConverter(CategoryConverter categoryConverter) {
        this.categoryConverter = categoryConverter;
    }

    @Override
    public Product convertToEntity(ProductData data) {
        Product product = new Product();

        product.setName(data.getName());
        product.setPrice(data.getPrice());

        Category category = new Category();
        category.setId(data.getCategory().getId());
        product.setCategory(category);

        return product;
    }

    @Override
    public ProductData convertToDto(Product entity) {
        ProductData product = new ProductData();

        product.setId(entity.getId());
        product.setName(entity.getName());
        product.setCategory(this.categoryConverter.convertToDto(entity.getCategory()));
        product.setCreated(entity.getCreated());
        product.setUpdated(entity.getUpdated());

        return product;
    }

}
