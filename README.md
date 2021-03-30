# food-warehouse-server

## Brief Description

We want to design an application that will support and facilitate the process of managing a food warehouse.
This is the `server-side` of our application. Client side of project can be found [here](https://github.com/A640/food-warehouse-client)

The aim of the project is to design and create a management system for
food warehouse. It should facilitate warehouse management
through the possibility of checking the inventory levels of individual products,
handling orders, issuing invoices, displaying alerts on situations such as e.g.  low inventory levels of a given product or ending expiration date, generating sell statistics, avoiding food expiry by automatic discount applying for products with short expiration dates,
simplifying and improving contact with the customer by enabling him to place and manage orders
directly in the program as well as tracking the status of every order.

## Table of contents

- [Food warehouse project description](#food-warehouse-project-description)
  - [Introduction](#introduction)
  - [Creators and their roles in project](#creators-and-their-roles-in-project)
  - [Technologies](#technologies)
  - [Features](#features)
      - [As guest user](#as-guest-user)
      - [As customer](#as-customer)
      - [As employee](#as-employee)
      - [As manager](#as-manager)
      - [As admin](#as-admin)
      - [As delivery man](#as-delivery-man)
      - [System features](#system-features)
- [Installation and usage](#installation-and-usage)
- [Screenshots](#screenshots)
 


# Food warehouse project description

## Introduction

This project was created as final project for The Object-Oriented Technology and Database Systems classes. It was created by 2 people in about a month (in our free time).


## Creators and their roles in project

**[Albert Kanak](https://github.com/A640)**

- front-end side of project ([client app](https://github.com/A640/food-warehouse-client))
- help with database (planning and help with managing)

**[Kamil Bałaban](https://github.com/GieeFoR)**
- back-end side of project ([server app](https://github.com/A640/food-warehouse-server))
- database


## Technologies

#### Front-end

- web programming (JavaScript + HTML + CSS)
- Vue.js
- Vuex store
- Vue router

#### Back-end

- Java
- Spring Boot
- REST API

#### Database

- MySQL database
- PL/SQL Procedures


## Features

#### As guest user

- register
- view the offer of products in warehouse
- search for a product
- view product details

#### As customer

- order products from warehouses' e-store
- check order status
- view order history
- view order details
- submitting a complaint
- cancel order (if order completing has not started yet)

#### As employee

- enter inbound delivery to the system
- check the stock of a product
- view orders
- receive automatic system alerts (`low stock of a product`, `ending expiry date of a product`)
- change order status (change to: `order completing`, `ready for delivery`)
- send and receive messages to other employees (employees, managers, admins and delivery men)


#### As manager

- perform CRUD operations on `vehicles`, `warehouses`, `food suppliers`, `products`
- view employees and customers details
- view statistics
- view orders
- receive automatic system alerts (`low stock of a product`, `ending expiry date of a product`)
- send and receive messages to other employees (employees, managers, admins, and delivery men)
- issue a decision for complaint


#### As admin

- perform CRUD operations on any entity
- manually start system tasks
- send and receive messages to other employees (employees, managers, admins and delivery men)


#### As delivery man

- view assigned vehicle details
- view assigned order delivery details
- change order status (change to: `out for delivery`, `delivered`, `returned`)
- send and receive messages to other employees (employees, managers, admins and delivery men)

#### System features

- filter products in store and search for a product
- automatically apply discount for a product batch with short expiration term
- automatically generate statistics (e.g. `best-selling products`, `profit from sales`, `number of orders over time`)
- automatically generate alerts (e.g. `short expiration date`, `low inventory level of a product`, `product expired`)
- automatically remove expired products from store
- give hints when user enters data (e.g. some data autocompletion when entering new batch data)





## Installation and usage

To use this project you will need Java `JDK 14.0.2` or higher.

1. Clone this repository or download zip
2. Install dependencies with Gradle
3. To run this project use:
```
gradlew bootRun
```



## Screenshots
### UI is currently in Polish
  
**E-store page**
[![click here to view image](https://github.com/A640/food-warehouse-client/blob/assets/screenshots/store.png)](https://github.com/A640/food-warehouse-client/blob/assets/screenshots/store.png?raw=true)

  
**Product details**
[![click here to view image](https://github.com/A640/food-warehouse-client/blob/assets/screenshots/product%20details.png)](https://github.com/A640/food-warehouse-client/blob/assets/screenshots/product%20details.png?raw=true)

  
**Product details mobile**
[![click here to view image](https://github.com/A640/food-warehouse-client/blob/assets/screenshots/product%20details%20mobile.png)](https://github.com/A640/food-warehouse-client/blob/assets/screenshots/product%20details%20mobile.png?raw=true)

  
**Cart**
[![click here to view image](https://github.com/A640/food-warehouse-client/blob/assets/screenshots/cart%201.png)](https://github.com/A640/food-warehouse-client/blob/assets/screenshots/cart%201.png?raw=true)
[![click here to view image](https://github.com/A640/food-warehouse-client/blob/assets/screenshots/cart%202.png)](https://github.com/A640/food-warehouse-client/blob/assets/screenshots/cart%202.png?raw=true)
[![click here to view image](https://github.com/A640/food-warehouse-client/blob/assets/screenshots/cart%203.png)](https://github.com/A640/food-warehouse-client/blob/assets/screenshots/cart%203.png?raw=true)

  
**Manager panel**
[![click here to view image](https://github.com/A640/food-warehouse-client/blob/assets/screenshots/manager%20-%20employee.png)](https://github.com/A640/food-warehouse-client/blob/assets/screenshots/manager%20-%20employee.png?raw=true)

  
**Stats**
[![click here to view image](https://github.com/A640/food-warehouse-client/blob/assets/screenshots/stats%201.png)](https://github.com/A640/food-warehouse-client/blob/assets/screenshots/stats%201.png?raw=true)
[![click here to view image](https://github.com/A640/food-warehouse-client/blob/assets/screenshots/stats%202.png)](https://github.com/A640/food-warehouse-client/blob/assets/screenshots/stats%202.png?raw=true)
[![click here to view image](https://github.com/A640/food-warehouse-client/blob/assets/screenshots/stats%203.png)](https://github.com/A640/food-warehouse-client/blob/assets/screenshots/stats%203.png?raw=true)

  
**Product list**
[![click here to view image](https://github.com/A640/food-warehouse-client/blob/assets/screenshots/manager%20-%20products.png)](https://github.com/A640/food-warehouse-client/blob/assets/screenshots/manager%20-%20products.png?raw=true)

  
**Add new product**
[![click here to view image](https://github.com/A640/food-warehouse-client/blob/assets/screenshots/manager%20-%20add%20product.png)](https://github.com/A640/food-warehouse-client/blob/assets/screenshots/manager%20-%20add%20product.png?raw=true)

  
**Admin panel**
[![click here to view image](https://github.com/A640/food-warehouse-client/blob/assets/screenshots/admin%20-%20vehicle.png)](https://github.com/A640/food-warehouse-client/blob/assets/screenshots/admin%20-%20vehicle.png?raw=true)



