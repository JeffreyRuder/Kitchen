# Local Shoes

#### January 29, 2016

#### By Jeffrey Ruder

## Description

Local Shoes tracks the availability of different brands at different shoe stores. Each brand is sold at many stores, and each store carries many brands. Thus, there is a many-to-many relationship between brands and stores.

Local Shoes demonstrates using Spark routes to perform CRUD operations on a PostgreSQL database. It also demonstrates the use of a join table and SQL JOIN clauses to manage a many-to-many relationship.

## Features

* Create, read, update, and delete information from the database about both brands and stores.
* Warning messages display if users attempt to enter duplicate brands or stores.
* Uses Bootstrap's breadcrumb component for easy navigation.
* Linear gradients overlaying background images on the index page.

## Setup

### Install Required Programs

Make sure you have Java, PostgreSQL, and Gradle installed, then clone this repository.

### Import or Create Databases

#### To Import a Database with Sample Data

* Open `postgres` and `psql`
* In `psql`, create a new database named salon: `CREATE DATABASE shoe_stores;`
* Navigate to the project directory in your terminal and populate the database: `psql shoe_stores < shoe_stores.sql`
* Make any necessary changes to the `OWNER` of the shoe_stores database and its tables in `psql`

#### To Create Empty Databases

Use the SQL queries in SQL-QUERIES.md.

#### Alter psql Authentication Information

In `shoe_stores\main\java\DB.java`, change the `null` parameters to the username and password of the `OWNER` of your shoe_stores database, or otherwise alter your psql authentication configuration to allow this program to access the database.

### Run Program

Navigate to your shoe_stores directory and `gradle run`.

## Technologies Used

Java, Spark, JUnit, FluentLenium, Velocity, PostgreSQL, Gradle, Bootstrap

## License

MIT License. See LICENSE.md for details.

## Copyright

Copyright (c) 2016 Jeffrey Ruder
