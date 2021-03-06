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

package com.ryctabo.hulkstore.rest.mapper;

import com.ryctabo.hulkstore.service.exception.IllegalDtoException;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * The <strong>IllegalDtoExceptionMapper</strong> class provides a
 * handler for when the {@link IllegalDtoException} is throw.
 *
 * @author Gustavo Pacheco (ryctabo at gmail.com)
 * @version 1.0
 */
@Provider
public class IllegalDtoExceptionMapper implements ExceptionMapper<IllegalDtoException> {

    /**
     * Method captures the {@link IllegalDtoException} and returns
     * a response object with the data corresponding to the error thrown.
     *
     * @param exception the illegal DTO exception
     * @return response object
     */
    @Override
    public Response toResponse(IllegalDtoException exception) {
        return Response.status(Response.Status.BAD_REQUEST.getStatusCode())
                .entity(exception.getErrorMessage())
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

}
