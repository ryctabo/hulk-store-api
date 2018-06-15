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

package com.ryctabo.hulkstore.core.domain;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * The <strong>ListResponse</strong> class has the functionality of
 * standardizing the responses of a request where a list of elements
 * has to be answered.
 *
 * @param <T> Data type of the list
 * @author Gustavo Pacheco (ryctabo at gmail.com)
 * @version 1.0
 */
@XmlRootElement
public class ListResponse<T> {

    private List<T> items;

    private long total;

    /**
     * Create a new instance of {@link ListResponse}.
     */
    public ListResponse() { }

    /**
     * Create a new instance of {@link ListResponse} from the
     * given parameters.
     *
     * @param items element list
     * @param total total elements
     */
    public ListResponse(List<T> items, long total) {
        this.items = items;
        this.total = total;
    }

    @XmlElement
    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

    @XmlElement
    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

}
