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

package com.ryctabo.hulkstore.database;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaQuery;
import java.io.Serializable;
import java.util.List;

/**
 * The <strong>AbstractRepository</strong> class contains all method
 * to manage entities.
 *
 * @param <T> Entity class.
 * @param <I> Data type of primary key or entity's ID.
 * @author Gustavo Pacheco (ryctabo at gmail.com)
 * @version 1.0-SNAPSHOT
 */
public class AbstractRepository<T extends Serializable, I> implements DataAccessObject<T, I> {

    /**
     * It's use to record all events in this class.
     */
    private static final Logger LOG = LogManager.getLogger(AbstractRepository.class);

    /**
     * Type of class to manage in this class.
     */
    protected final Class<T> entityClass;

    /**
     * The name of persistence unit of datasource for connection in the database.
     */
    private static final String PERSISTENCE_UNIT = "hulk-pu";

    /**
     * The entity manager factory created by persistence unit.
     */
    private static final EntityManagerFactory FACTORY =
            Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);

    /**
     * Create a new instance of {@link AbstractRepository} to manage entities.
     *
     * @param entityClass type of class to manage
     */
    public AbstractRepository(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    /**
     * Get entity manager factory.
     *
     * @return entity manager factory
     */
    public static EntityManagerFactory getEntityManagerFactory() {
        return FACTORY;
    }

    /**
     * This method create an entity manager to manage the
     * main operation of an entity.
     *
     * @return entity manager
     */
    protected EntityManager getEntityManager() {
        return FACTORY.createEntityManager();
    }

    @Override
    public List<T> find() {
        EntityManager entityManager = getEntityManager();
        try {
            CriteriaQuery<T> query = entityManager.getCriteriaBuilder()
                    .createQuery(this.entityClass);
            query.select(query.from(this.entityClass));
            return entityManager.createQuery(query).getResultList();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public T find(I id) {
        if (id == null) return null;
        EntityManager entityManager = getEntityManager();
        try {
            return entityManager.find(this.entityClass, id);
        } finally {
            entityManager.close();
        }
    }

    @Override
    public T save(T entity) {
        if (entity == null) return null;
        EntityManager entityManager = getEntityManager();
        try {
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();
            T entitySaved = entityManager.merge(entity);
            transaction.commit();
            return entitySaved;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public T delete(I id) {
        if (id == null) return null;
        EntityManager entityManager = getEntityManager();
        try {
            T entity = entityManager.find(this.entityClass, id);
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.remove(entity);
            transaction.commit();
            return entity;
        } finally {
            entityManager.close();
        }
    }

}
