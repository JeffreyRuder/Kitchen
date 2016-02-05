# YesChef!

#### January 29, 2016

#### By Jeffrey Ruder, Midori Bowen, and Chris Siems

## Description

YesChef! is an integrated point-of-sale and inventory management system for restaurants built as an Epicodus class project.

YesChef! demonstrates object-oriented programming with Java, routing using the Spark web framework, CRUD and database querying with PostgreSQL.

## Features

* Create, manage, and delete ingredients, deliveries, menu items, and orders.
* Creating an order automatically deducts the ingredients required for that order from the inventory.
* Unique views for kitchen, managers, and servers.
* Kitchen can mark orders as 'up' (ready to serve); servers can mark orders as paid or complete.
* Orders can be 're-fired' if they are sent back or ruined and the inventory will be debited again.
* Users can see an '86 list' of dishes that are not available due to lack of ingredients in inventory.
* Creating an order deducts ingredients from the inventory, using SQL queries joining multiple tables.
* Use of MetroUI CSS library provides a more familiar and mobile-first UI.

## Setup

### Install Required Programs

Make sure you have Java, PostgreSQL, and Gradle installed, then clone this repository.

### Import a Database with Sample Data

* Open `postgres` and `psql`
* In `psql`, create a new database named kitchen: `CREATE DATABASE kitchen;`
* Navigate to the project directory in your terminal and populate the database: `psql kitchen < kitchen.sql`
* Make any necessary changes to the `OWNER` of the kitchen database and its tables in `psql`

### Alter psql Authentication Information

In `kitchen\main\java\DB.java`, change the `null` parameters to the username and password of the `OWNER` of your kitchen database, or otherwise alter your psql authentication configuration to allow this program to access the database.

### Run Program

Navigate to your kitchen directory and `gradle run`.

## Technologies Used

Java, Spark, JUnit, FluentLenium, Velocity, PostgreSQL, Gradle, MetroUI CSS library

## License

MIT License. See LICENSE.md for details.

## Copyright

Copyright (c) 2016 Jeffrey Ruder, Chris Siems, and Midori Bowen
