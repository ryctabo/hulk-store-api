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

import com.ryctabo.hulkstore.database.AbstractRepository;
import com.ryctabo.hulkstore.database.entity.Stock;
import com.ryctabo.hulkstore.database.entity.StockPK;
import com.ryctabo.hulkstore.database.entity.StockType;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * @author Gustavo Pacheco (ryctabo at gmail.com)
 * @version 1.0-SNAPSHOT
 */
@Repository
public class StockRepository extends AbstractRepository<Stock, StockPK> implements StockDao {

    public StockRepository() {
        super(Stock.class);
    }

    @Override
    public List<Stock> find(long productId, StockType type, int start, int size) {
        EntityManager entityManager = getEntityManager();
        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<Stock> query = cb.createQuery(Stock.class);

            Root<Stock> stock = query.from(Stock.class);

            Predicate p1 = cb.equal(stock.get("product").get("id"), productId);
            if (type != null) {
                Predicate p2 = cb.equal(stock.get("type"), type);
                query.where(p1, p2);
            } else {
                query.where(p1);
            }

            return entityManager.createQuery(query)
                    .setFirstResult(start)
                    .setMaxResults(size)
                    .getResultList();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public long count(long productId, StockType type) {
        EntityManager entityManager = getEntityManager();
        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<Long> query = cb.createQuery(Long.class);

            Root<Stock> stock = query.from(Stock.class);

            query.select(cb.count(stock));

            Predicate p1 = cb.equal(stock.get("product").get("id"), productId);
            if (type != null) {
                Predicate p2 = cb.equal(stock.get("type"), type);
                query.where(p1, p2);
            } else {
                query.where(p1);
            }

            return entityManager.createQuery(query).getSingleResult();
        } finally {
            entityManager.close();
        }
    }

}
