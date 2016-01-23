## Setup Production Database

`CREATE DATABASE salon;`
`CREATE TABLE stylists(id serial PRIMARY KEY, first_name varchar, last_name varchar);`
`CREATE TABLE clients(id serial PRIMARY KEY, first_name varchar, last_name varchar, stylist_id int references stylists(id));`

## Setup Test Database

`CREATE DATABASE salon_test WITH TEMPLATE salon;`
