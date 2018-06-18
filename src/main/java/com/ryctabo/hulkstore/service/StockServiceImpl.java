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
import com.ryctabo.hulkstore.core.domain.StockData;
import com.ryctabo.hulkstore.database.entity.Product;
import com.ryctabo.hulkstore.database.entity.Stock;
import com.ryctabo.hulkstore.database.entity.StockPK;
import com.ryctabo.hulkstore.database.entity.StockType;
import com.ryctabo.hulkstore.database.repository.ProductDao;
import com.ryctabo.hulkstore.database.repository.StockDao;
import com.ryctabo.hulkstore.service.converter.StockConverter;
import com.ryctabo.hulkstore.service.exception.IllegalDtoException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import javax.inject.Inject;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Gustavo Pacheco (ryctabo at gmail.com)
 * @version 1.0-SNAPSHOT
 */
@Service
@Scope(WebApplicationContext.SCOPE_REQUEST)
public class StockServiceImpl implements StockService {

    private final StockDao stockDao;

    private final ProductDao productDao;

    private final StockConverter converter;

    @Inject
    public StockServiceImpl(StockDao stockDao, ProductDao productDao, StockConverter converter) {
        this.stockDao = stockDao;
        this.productDao = productDao;
        this.converter = converter;
    }

    private void validateProductId(long productId) {
        if (productId <= 0)
            throw new BadRequestException("The product ID must be greater than 0.");

        if (this.productDao.find(productId) == null) {
            final String FORMAT = "The product with ID %d was not found.";
            throw new NotFoundException(String.format(FORMAT, productId));
        }
    }

    private Stock getEntity(long productId, Integer index) {
        if (index == null || index <= 0)
            throw new BadRequestException("The index is required and it must be greater than 0.");

        Stock stock = this.stockDao.find(new StockPK(index, productId));
        if (stock == null) {
            final String FORMAT = "The stock with index %d and product ID %d was not found";
            throw new NotFoundException(String.format(FORMAT, index, productId));
        }
        return stock;
    }

    @Override
    public ListResponse<StockData> get(long productId, StockType type, int start, int size) {
        this.validateProductId(productId);
        List<StockData> items = this.stockDao
                .find(productId, type, start, size)
                .stream()
                .map(this.converter::convertToDto)
                .sorted(Comparator.comparingInt(StockData::getIndex))
                .collect(Collectors.toList());
        long total = this.stockDao.count(productId, type);
        return new ListResponse<>(items, total);
    }

    @Override
    public StockData get(long productId, Integer index) {
        this.validateProductId(productId);
        return this.converter.convertToDto(getEntity(productId, index));
    }

    @Override
    public StockData add(long productId, StockData data) {
        this.validateProductId(productId);

        ErrorMessage errorMessage = new ErrorMessage();

        if (data.getType() == null)
            errorMessage.addMessage("The stock type is required.");
        if (data.getAmount() == null || data.getAmount() < 0)
            errorMessage.addMessage("The stock amount can't be negative.");
        if (data.getMessage() == null || data.getMessage().isEmpty())
            errorMessage.addMessage("The stock message is required.");

        if (!errorMessage.getMessages().isEmpty())
            throw new IllegalDtoException(errorMessage);

        Stock stock = this.converter.convertToEntity(data);
        stock.setCreated(LocalDateTime.now());

        int index = ((int) this.stockDao.count(productId, null)) + 1;
        StockPK stockId = new StockPK(index, productId);
        stock.setId(stockId);

        Product product = new Product();
        product.setId(productId);
        stock.setProduct(product);

        return this.converter.convertToDto(stockDao.save(stock));
    }

}
