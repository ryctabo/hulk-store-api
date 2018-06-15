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

package com.ryctabo.hulkstore.rest.resource;

import com.ryctabo.hulkstore.core.domain.ListResponse;
import com.ryctabo.hulkstore.core.domain.StockData;
import com.ryctabo.hulkstore.rest.bean.StockBean;
import com.ryctabo.hulkstore.service.StockService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.WebApplicationContext;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;

/**
 * @author Gustavo Pacheco (ryctabo at gmail.com)
 * @version 1.0-SNAPSHOT
 */
@Controller
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Scope(WebApplicationContext.SCOPE_REQUEST)
public class StockResource {

    private StockService service;

    private long productId;

    public void setProductId(long productId) {
        this.productId = productId;
    }

    @Inject
    public void setService(StockService service) {
        this.service = service;
    }

    @GET
    public ListResponse<StockData> get(@BeanParam StockBean bean) {
        return this.service.get(productId, bean.getType(), bean.getStart(), bean.getSize());
    }

    @POST
    public Response post(StockData data, @Context UriInfo uriInfo) {
        StockData stock = this.service.add(this.productId, data);
        String newIndex = stock.getIndex().toString();
        URI location = uriInfo.getAbsolutePathBuilder().path(newIndex).build();
        return Response.created(location)
                .entity(stock)
                .build();
    }

    @GET
    @Path("{index}")
    public StockData get(@PathParam("index") Integer index) {
        return this.service.get(this.productId, index);
    }

}
