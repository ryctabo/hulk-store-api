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
import com.ryctabo.hulkstore.database.AbstractRepository;
import com.ryctabo.hulkstore.database.entity.Product;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Gustavo Pacheco (ryctabo at gmail.com)
 * @version 1.0-SNAPSHOT
 */
@Repository
public class ProductRepository extends AbstractRepository<Product, Long> implements ProductDao {

    public ProductRepository() {
        super(Product.class);
    }

    private Predicate[] getPredicate(String search, Long categoryId,
                                     CriteriaBuilder cb, Root<Product> product) {
        List<Predicate> predicates = new ArrayList<>();

        if (search != null)
            predicates.add(cb.like(product.get("name"), "%" + search + "%"));

        if (categoryId != null)
            predicates.add(cb.equal(product.get("category").get("id"), categoryId));

        if (!predicates.isEmpty())
            return predicates.toArray(new Predicate[0]);
        return null;
    }

    @Override
    public List<Product> find(String search, Long categoryId, String orderBy,
                              OrderType orderType, int start, int size) {
        EntityManager entityManager = getEntityManager();
        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<Product> query = cb.createQuery(Product.class);

            Root<Product> product = query.from(Product.class);

            Predicate[] predicates = getPredicate(search, categoryId, cb, product);
            if (predicates != null)
                query.where(predicates);

            Path propToOrder = product.get("id");
            if (orderBy != null)
                propToOrder = product.get(orderBy);

            Order order;
            if (orderType == null || orderType == OrderType.ASC)
                order = cb.asc(propToOrder);
            else
                order = cb.desc(propToOrder);

            query.orderBy(order);

            return entityManager.createQuery(query)
                    .setFirstResult(start)
                    .setMaxResults(size)
                    .getResultList();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public long count(String search, Long categoryId) {
        EntityManager entityManager = getEntityManager();
        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<Long> query = cb.createQuery(Long.class);

            Root<Product> product = query.from(Product.class);

            query.select(cb.count(product));

            Predicate[] predicates = getPredicate(search, categoryId, cb, product);
            if (predicates != null)
                query.where(predicates);

            return entityManager.createQuery(query).getSingleResult();
        } finally {
            entityManager.close();
        }
    }

}
