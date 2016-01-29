## Setup Production Database

`CREATE DATABASE shoe_stores`
`CREATE TABLE stores (id serial PRIMARY KEY, name varchar);`
`CREATE TABLE brands (id serial PRIMARY KEY, name varchar);`
`CREATE TABLE carries (id serial PRIMARY KEY, store_id int references stores(id), brand_id int references brands(id))`

## Setup Test Database

`CREATE DATABASE shoe_stores_test WITH TEMPLATE shoe_stores;`
