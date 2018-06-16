# Hulk Store API

## Test Resource
The mapping of the URI path space is presented in the following table:

URI Path | Resource class | HTTP methods
---------|----------------|-------------
/myresource|MyResource|GET

Application is configured by using a java class, which registers javax.ws.rs.core.Application descendant to get classes 
and singletons from it (see class ConfigApp).

## Category Resource
The mapping of the URI path space is presented in the following table:

URI Path | Resource class | HTTP methods
---------|----------------|-------------
/categories|CategoryResource|GET
/categories|CategoryResource|POST
/categories/:id|CategoryResource|GET
/categories/:id|CategoryResource|PUT
/categories/:id|CategoryResource|DELETE

## Product Resource
The mapping of the URI path space is presented in the following table:

URI Path | Resource class | HTTP methods
---------|----------------|-------------
/products|ProductResource|GET
/products|ProductResource|POST
/products/:id|ProductResource|GET
/products/:id|ProductResource|PUT
/products/:id/stocks|StockResource|GET
/products/:id/stocks|StockResource|POST
/products/:id/stocks/:index|StockResource|GET
