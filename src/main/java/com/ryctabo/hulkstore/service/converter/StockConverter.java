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

import com.ryctabo.hulkstore.core.domain.StockData;
import com.ryctabo.hulkstore.database.entity.Product;
import com.ryctabo.hulkstore.database.entity.Stock;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

/**
 * @author Gustavo Pacheco (ryctabo at gmail.com)
 * @version 1.0-SNAPSHOT
 */
@Component
@Scope(WebApplicationContext.SCOPE_REQUEST)
public class StockConverter implements DtoConverter<Stock, StockData, StockData> {

    @Override
    public Stock convertToEntity(StockData data) {
        Stock stock = new Stock();

        stock.setType(data.getType());
        stock.setMessage(data.getMessage());
        stock.setAmount(data.getAmount());

        Product product = new Product();
        stock.setProduct(product);

        return stock;
    }

    @Override
    public StockData convertToDto(Stock entity) {
        StockData stock = new StockData();

        stock.setIndex(entity.getId().getIndex());
        stock.setAmount(entity.getAmount());
        stock.setMessage(entity.getMessage());
        stock.setType(entity.getType());
        stock.setCreated(entity.getCreated());

        return stock;
    }

}
