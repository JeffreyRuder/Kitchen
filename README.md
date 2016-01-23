# Style Tracker

#### January 22, 2016

#### By Jeffrey Ruder

## Description

Style Tracker allows the manager of a hair salon to track stylists and clients. Each client is assigned to only one stylist (i.e. there is a one-to-many relationship between stylists and clients.)

Style Tracker demonstrates using Spark routes to perform CRUD operations on a PostgreSQL database.

## Features

* Create, read, update, and delete information from the database about both stylists and clients.
* Every client should be assigned to a stylist. If there are unassigned clients in the database, a warning message displays on the index page prompting users to assign them to a stylist.
* Uses `List.sort` and lambda expressions to sort lists of clients and stylists alphabetically by last name.
* Displays up-to-date statistics about stylists and clients on the top left of each page.
* Uses Bootstrap's breadcrumb component for easy navigation.

## Setup

### Install Required Programs

Make sure you have Java, PostgreSQL, and Gradle installed, then clone this repository.

### Import or Create Databases

#### To Import a Database with Sample Data

* Open `postgres` and `psql`
* In `psql`, create a new database named salon: `CREATE DATABASE salon;`
* Navigate to the project directory in your terminal and populate the database: `psql salon < salon.sql`
* Make any necessary changes to the `OWNER` of the salon database and its tables in `psql`

#### To Create Empty Databases

Use the SQL queries in SQL-QUERIES.md.

#### Alter psql Authentication Information

In `salon\main\java\DB.java`, change the `null` parameters to the username and password of the `OWNER` of your salon database, or otherwise alter your psql authentication configuration to allow this program to access the database.

### Run Program

Navigate to your salon directory and `gradle run`.

## Technologies Used

Java, Spark, JUnit, FluentLenium, Velocity, PostgreSQL, Bootstrap

## License

MIT License. See LICENSE.md for details.

## Copyright

Copyright (c) 2016 Jeffrey Ruder
