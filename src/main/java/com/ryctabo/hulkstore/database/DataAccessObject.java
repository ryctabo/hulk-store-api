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

import java.io.Serializable;
import java.util.List;

/**
 * The <strong>DataAccessObject</strong> interface provides methods to give you
 * a communication between the database and application.
 *
 * @param <T> Entity class.
 * @param <I> Data type of the primary key or entity's ID.
 * @author Gustavo Pacheco (ryctabo at gmail.com)
 * @version 1.0-SNAPSHOT
 */
public interface DataAccessObject<T extends Serializable, I> {

    /**
     * This method returns a list of entities.
     *
     * @return entities list
     */
    List<T> find();

    /**
     * This method searches the entity that contain the given ID
     * in the database.
     *
     * @param id entity's ID
     * @return entity
     */
    T find(I id);

    /**
     * This method is responsible to store entities in the database and
     * match the persistence context.
     *
     * @param entity object to save
     * @return saved entity
     */
    T save(T entity);

    /**
     * This method is responsible to delete entities from the
     * given ID.
     *
     * @param id entity's ID
     * @return deleted entity
     */
    T delete(I id);

}
