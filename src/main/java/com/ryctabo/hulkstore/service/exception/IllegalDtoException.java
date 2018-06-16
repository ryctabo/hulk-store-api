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

package com.ryctabo.hulkstore.service.exception;

import com.ryctabo.hulkstore.core.domain.ErrorMessage;

/**
 * The <strong>IllegalDtoException</strong> class contains all methods
 * to throw an exception from the services.
 *
 * @author Gustavo Pacheco (ryctabo at gmail.com)
 * @version 1.0
 */
public class IllegalDtoException extends RuntimeException {

    /** The error message, this property will be responded as a data transfer object. */
    private final ErrorMessage errorMessage;

    /** The generic error message. */
    private static final String GENERIC_ERROR = "The service that attends the requests " +
            "has had an error without specification.";

    /**
     * Create a new instance of {@link IllegalDtoException} without detail message.
     */
    public IllegalDtoException() {
        super(GENERIC_ERROR);
        this.errorMessage = new ErrorMessage();
        this.errorMessage.addMessage(GENERIC_ERROR);
    }

    /**
     * Create an instance of {@link IllegalDtoException} with the detail message.
     *
     * @param message error message
     */
    public IllegalDtoException(String message) {
        super(message);
        this.errorMessage = new ErrorMessage();
        this.errorMessage.addMessage(message);
    }

    /**
     * Create a new instance of {@link IllegalDtoException} specifying the error message
     * in a data transfer object.
     *
     * @param errorMessage data transfer object
     */
    public IllegalDtoException(ErrorMessage errorMessage) {
        super("The service that attends the requests has had multiple errors.");
        this.errorMessage = errorMessage;
    }

    /**
     * Get error message.
     *
     * @return error message.
     */
    public ErrorMessage getErrorMessage() {
        return errorMessage;
    }

}
