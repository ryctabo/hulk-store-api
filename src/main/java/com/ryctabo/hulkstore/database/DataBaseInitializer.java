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

import com.ryctabo.hulkstore.database.entity.Category;
import com.ryctabo.hulkstore.database.repository.CategoryDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

/**
 * @author Gustavo Pacheco (ryctabo at gmail.com)
 * @version 1.0-SNAPSHOT
 */
@Component
public class DataBaseInitializer {

    /**
     * It's use to record all events of this class.
     */
    private static final Logger LOG = LogManager.getLogger(DataBaseInitializer.class);

    /**
     * An instance of {@link CategoryDao}.
     */
    private final CategoryDao categoryDao;

    /**
     * Create a new instance of {@link DataBaseInitializer} with the following
     * parameters.
     *
     * @param categoryDao category repository
     */
    @Inject
    public DataBaseInitializer(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    /**
     * This method is executed to initialize the data
     * in the database.
     */
    @PostConstruct
    public void setUp() {
        LOG.debug("Initializing the main data in the database...");
        this.createDefaultCategories();
        LOG.debug("The main data of the database has been created.");
    }

    /**
     * This method create the main categories in the database.
     */
    private void createDefaultCategories() {
        Category dcComics = new Category("DC Comics");
        this.categoryDao.save(dcComics);

        Category marvel = new Category("Marvel");
        this.categoryDao.save(marvel);

        Category communityDesigns = new Category("Community designs");
        this.categoryDao.save(communityDesigns);
    }

}
