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
import com.ryctabo.hulkstore.core.domain.ProductData;
import com.ryctabo.hulkstore.rest.bean.ProductBean;
import com.ryctabo.hulkstore.service.ProductService;
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
@Path("products")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Scope(WebApplicationContext.SCOPE_REQUEST)
public class ProductResource {

    private final ProductService service;

    @Inject
    public ProductResource(ProductService service) {
        this.service = service;
    }

    @GET
    public ListResponse<ProductData> get(@BeanParam ProductBean bean) {
        return this.service.get(bean.getSearch(),
                bean.getCategoryId(),
                bean.getOrderBy(),
                bean.getOrderType(),
                bean.getStart(),
                bean.getSize());
    }

    @POST
    public Response post(ProductData data, @Context UriInfo uriInfo) {
        ProductData product = this.service.add(data);
        String newID = product.getId().toString();
        URI location = uriInfo.getAbsolutePathBuilder().path(newID).build();
        return Response.created(location)
                .entity(product)
                .build();
    }

    @GET
    @Path("{id}")
    public ProductData get(@PathParam("id") Long id) {
        return this.service.get(id);
    }

    @PUT
    @Path("{id}")
    public ProductData put(@PathParam("id") Long id, ProductData data) {
        return this.service.update(id, data);
    }

}
