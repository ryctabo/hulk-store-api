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
import com.ryctabo.hulkstore.database.entity.Product;
import com.ryctabo.hulkstore.database.repository.CategoryDao;
import com.ryctabo.hulkstore.database.repository.ProductDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.time.LocalDateTime;

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
     * An instance of {@link ProductDao}.
     */
    private final ProductDao productDao;

    /**
     * Create a new instance of {@link DataBaseInitializer} with the following
     * parameters.
     *
     * @param categoryDao category repository
     * @param productDao  product repository
     */
    @Inject
    public DataBaseInitializer(CategoryDao categoryDao, ProductDao productDao) {
        this.categoryDao = categoryDao;
        this.productDao = productDao;
    }

    /**
     * This method is executed to initialize the data
     * in the database.
     */
    @PostConstruct
    public void setUp() {
        LOG.info("Initializing the main data in the database...");
        this.createDefaultCategories();
        this.createProducts();
        LOG.info("The main data of the database has been created.");
    }

    /**
     * This method create the products in the database.
     */
    private void createProducts() {
        this.productDao.save(new Product("Marvel Sweater",
                25f,
                new Category(2),
                LocalDateTime.now()));
        this.productDao.save(new Product("Marvel Sweater Limited Edition",
                41f,
                new Category(2),
                LocalDateTime.now()));
        this.productDao.save(new Product("DC Sweater",
                25f,
                new Category(1),
                LocalDateTime.now()));
        this.productDao.save(new Product("DC Sweater Limited Edition",
                31f,
                new Category(1),
                LocalDateTime.now()));
        this.productDao.save(new Product("Marvel Short",
                38f,
                new Category(2),
                LocalDateTime.now()));
        this.productDao.save(new Product("Marvel Short Limited Edition",
                45f,
                new Category(2),
                LocalDateTime.now()));
        this.productDao.save(new Product("DC Short",
                35f,
                new Category(1),
                LocalDateTime.now()));
        this.productDao.save(new Product("DC Short Limited Edition",
                45f,
                new Category(1),
                LocalDateTime.now()));
        this.productDao.save(new Product("Marvel Coffee Cup",
                13f,
                new Category(2),
                LocalDateTime.now()));
        this.productDao.save(new Product("Marvel Coffee Cup Limited Edition",
                16f,
                new Category(2),
                LocalDateTime.now()));
        this.productDao.save(new Product("DC Coffee Cup",
                12f,
                new Category(1),
                LocalDateTime.now()));
        this.productDao.save(new Product("DC Coffee Cup Limited Edition",
                15f,
                new Category(1),
                LocalDateTime.now()));
        this.productDao.save(new Product("Custom Coffee Cup",
                10f,
                new Category(3),
                LocalDateTime.now()));
        this.productDao.save(new Product("Custom Coffee Cup Limited Edition",
                11f,
                new Category(3),
                LocalDateTime.now()));
        this.productDao.save(new Product("Fortnite Coffee Cup",
                12f,
                new Category(3),
                LocalDateTime.now()));
        this.productDao.save(new Product("Fortnite Coffee Cup Limited Edition",
                15f,
                new Category(3),
                LocalDateTime.now()));
        this.productDao.save(new Product("Fortnite Sweater",
                28f,
                new Category(3),
                LocalDateTime.now()));
        this.productDao.save(new Product("Fortnite Sweater Limited Edition",
                32f,
                new Category(3),
                LocalDateTime.now()));
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
