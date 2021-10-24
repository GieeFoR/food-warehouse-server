drop table if exists COMPLAINT;
drop table if exists PRODUCT_ORDER;
drop table if exists PRODUCT_IN_STORAGE;
drop table if exists PRODUCT_BATCH;
drop table if exists PRODUCT;
drop table if exists MAKER;
drop table if exists STORAGE;
drop table if exists "ORDER";
drop table if exists DELIVERY;
drop table if exists CUSTOMER;
drop table if exists ADDRESS;
drop table if exists PAYMENT;
drop table if exists PAYMENT_TYPE;
drop table if exists MESSAGE;
drop table if exists CAR;
drop table if exists EMPLOYEE;
drop table if exists USER;

create table USER
(
    USER_ID    INT auto_increment
        primary key,
    USERNAME   VARCHAR(32) not null
        unique,
    PASSWORD   VARCHAR(64) not null,
    PERMISSION VARCHAR(32) not null,
    E_MAIL     VARCHAR(32) not null
        unique,
    unique (USERNAME, E_MAIL)
);

create table EMPLOYEE
(
    EMPLOYEE_ID INT auto_increment
        primary key,
    USER_ID     INT         not null,
    NAME        VARCHAR(32) not null,
    SURNAME     VARCHAR(32) not null,
    POSITION    VARCHAR(32) not null,
    SALARY      FLOAT       not null,
    constraint FK_EMPLOYEE_USER
        foreign key (USER_ID) references USER (USER_ID)
);

create table CAR
(
    CAR_ID          INT auto_increment
        primary key,
    DRIVER_ID       INT      not null,
    BRAND           VARCHAR(32) not null,
    MODEL           VARCHAR(32) not null,
    YEAR_OF_PROD    INT      not null,
    REGISTRATION_NO VARCHAR(16) not null,
    INSURANCE_EXP   DATE        not null,
    INSPECTION_EXP  DATE        not null,
    constraint FK_CAR_DRIVER
        foreign key (DRIVER_ID) references EMPLOYEE (EMPLOYEE_ID)
);

create table MESSAGE
(
    MESSAGE_ID    INT auto_increment
        primary key,
    SENDER_ID     INT      not null,
    RECEIVER_ID   INT      not null,
    CONTENT       CLOB        not null,
    MESSAGE_STATE VARCHAR(32) not null,
    SEND_DATE     DATE        not null,
    READ_DATE     DATE,
    constraint FK_MESSAGE_RECEIVER
        foreign key (RECEIVER_ID) references EMPLOYEE (EMPLOYEE_ID),
    constraint FK_MESSAGE_SENDER
        foreign key (SENDER_ID) references EMPLOYEE (EMPLOYEE_ID)
);

create table PAYMENT_TYPE
(
    PAYMENT_TYPE_ID INT auto_increment
        primary key,
    PAYMENT_TYPE    VARCHAR(32) not null
);

create table PAYMENT
(
    PAYMENT_ID      INT auto_increment
        primary key,
    PAYMENT_TYPE_ID INT      not null,
    PAYMENT_VALUE   FLOAT       not null,
    PAYMENT_STATE   VARCHAR(32) not null,
    constraint FK_PAYMENT_TYPE
        foreign key (PAYMENT_TYPE_ID) references PAYMENT_TYPE (PAYMENT_TYPE_ID)
);

create table ADDRESS
(
    ADDRESS_ID   INT auto_increment
        primary key,
    COUNTRY      VARCHAR(32)  not null,
    TOWN         VARCHAR(128) not null,
    POSTAL_CODE  VARCHAR(16)  not null,
    BUILDING_NO  VARCHAR(16)  not null,
    STREET       VARCHAR(64),
    APARTMENT_NO VARCHAR(16)
);

create table CUSTOMER
(
    CUSTOMER_ID  INT auto_increment
        primary key,
    USER_ID      INT      not null,
    ADDRESS_ID   INT      not null,
    NAME         VARCHAR(32) not null,
    SURNAME      VARCHAR(64) not null,
    FIRM_NAME    VARCHAR(256),
    TELEPHONE_NO VARCHAR(32) not null,
    TAX_ID       VARCHAR(32),
    DISCOUNT     INT,
    constraint FK_CUSTOMER_ADDRESS
        foreign key (ADDRESS_ID) references ADDRESS (ADDRESS_ID),
    constraint FK_CUSTOMER_USER
        foreign key (USER_ID) references USER (USER_ID)
);

create table DELIVERY
(
    DELIVERY_ID               INT auto_increment
        primary key,
    ADDRESS_ID                INT not null,
    SUPPLIER_ID               INT not null,
    REMOVAL_FROM_STORAGE_DATE DATE,
    DELIVERY_DATE             DATE,
    constraint FK_DELIVER_ADDRESS
        foreign key (ADDRESS_ID) references ADDRESS (ADDRESS_ID),
    constraint FK_DELIVER_SUPPLIER
        foreign key (SUPPLIER_ID) references EMPLOYEE (EMPLOYEE_ID)
);

create table "ORDER"
(
    ORDER_ID    INT auto_increment
        primary key,
    PAYMENT_ID  INT      not null,
    CUSTOMER_ID INT      not null,
    DELIVERY_ID INT      not null,
    COMMENT     CLOB,
    ORDER_STATE VARCHAR(32) not null,
    ORDER_DATE  DATE        not null,
    constraint FK_ORDER_CUSTOMER
        foreign key (CUSTOMER_ID) references CUSTOMER (CUSTOMER_ID),
    constraint FK_ORDER_DELIVERY
        foreign key (DELIVERY_ID) references DELIVERY (DELIVERY_ID),
    constraint FK_ORDER_PAYMENT
        foreign key (PAYMENT_ID) references PAYMENT (PAYMENT_ID)
);

create table STORAGE
(
    STORAGE_ID      INT auto_increment
        primary key,
    ADDRESS_ID      INT      not null,
    MANAGER_ID      INT      not null,
    STORAGE_NAME    VARCHAR(32) not null,
    CAPACITY        INT      not null,
    IS_COLD_STORAGE BOOLEAN     not null,
    constraint FK_STORAGE_ADDRESS
        foreign key (ADDRESS_ID) references ADDRESS (ADDRESS_ID),
    constraint FK_STORAGE_MANAGER
        foreign key (MANAGER_ID) references EMPLOYEE (EMPLOYEE_ID)
);

create table MAKER
(
    MAKER_ID     INT auto_increment
        primary key,
    ADDRESS_ID   INT       not null,
    FIRM_NAME    VARCHAR(256) not null,
    TELEPHONE_NO VARCHAR(12)  not null,
    E_MAIL       VARCHAR(32)  not null,
    constraint FK_MAKER_ADDRESS
        foreign key (ADDRESS_ID) references ADDRESS (ADDRESS_ID)
);

create table PRODUCT
(
    PRODUCT_ID        INT auto_increment
        primary key,
    MAKER_ID          INT      not null,
    PRODUCT_NAME      VARCHAR(32) not null,
    SHORT_DESCRIPTION VARCHAR(64),
    LONG_DESCRIPTION  CLOB,
    CATEGORY          VARCHAR(32) not null,
    NEED_COLD_STORAGE BOOLEAN     not null,
    BUY_PRICE         FLOAT       not null,
    SELL_PRICE        FLOAT       not null,
    IMAGE             CLOB        not null,
    constraint FK_PRODUCT_MAKER
        foreign key (MAKER_ID) references MAKER (MAKER_ID)
);

create table PRODUCT_BATCH
(
    BATCH_ID          INT auto_increment
        primary key,
    PRODUCT_ID        INT not null,
    BATCH_NO          INT not null,
    EAT_BY_DATE       DATE   not null,
    DISCOUNT          INT,
    PACKAGES_QUANTITY INT not null,
    constraint FK_BATCH_PRODUCT
        foreign key (PRODUCT_ID) references PRODUCT (PRODUCT_ID)
);

create table PRODUCT_IN_STORAGE
(
    BATCH_ID   INT not null,
    STORAGE_ID INT not null,
    QUANTITY   INT not null,
    primary key (BATCH_ID, STORAGE_ID),
    constraint FK_PRODUCT_IN_STORAGE_BATCH
        foreign key (BATCH_ID) references PRODUCT_BATCH (BATCH_ID),
    constraint FK_PRODUCT_IN_STORAGE_STORAGE
        foreign key (STORAGE_ID) references STORAGE (STORAGE_ID)
);

create table PRODUCT_ORDER
(
    ORDER_ID INT not null,
    BATCH_ID INT not null,
    QUANTITY INT not null,
    primary key (ORDER_ID, BATCH_ID),
    constraint FK_PRODUCT_ORDER_BATCH
        foreign key (BATCH_ID) references PRODUCT_BATCH (BATCH_ID),
    constraint FK_PRODUCT_ORDER_ORDER
        foreign key (ORDER_ID) references "ORDER" (ORDER_ID)
);

create table COMPLAINT
(
    COMPLAINT_ID    INT auto_increment
        primary key,
    ORDER_ID        INT      not null,
    CONTENT         CLOB        not null,
    SEND_DATE       DATE        not null,
    COMPLAINT_STATE VARCHAR(32) not null,
    DECISION        CLOB,
    DECISION_DATE   DATE,
    constraint FK_COMPLAINT_ORDER
        foreign key (ORDER_ID) references "ORDER" (ORDER_ID)
);



-- drop table if exists "COMPLAINT";
-- drop table if exists "PRODUCT_ORDER";
-- drop table if exists "PRODUCT_IN_STORAGE";
-- drop table if exists "PRODUCT_BATCH";
-- drop table if exists "PRODUCT";
-- drop table if exists "MAKER";
-- drop table if exists "STORAGE";
-- drop table if exists "ORDER";
-- drop table if exists "DELIVERY";
-- drop table if exists "CUSTOMER";
-- drop table if exists "ADDRESS";
-- drop table if exists "PAYMENT";
-- drop table if exists "PAYMENT_TYPE";
-- drop table if exists "MESSAGE";
-- drop table if exists "CAR";
-- drop table if exists "EMPLOYEE";
-- drop table if exists "USER";
--
-- create table "USER" (
--                         "USER_ID" bigint not null generated by default as identity,
--                         "USERNAME" varchar(32) not null,
--                         "PASSWORD" varchar(64) not null,
--                         "PERMISSION" varchar(32) not null,
--                         "E_MAIL" varchar(32) not null,
--                         unique ("USERNAME"),
--                         unique ("E_MAIL"),
--                         primary key ("USER_ID"),
--                         unique ("USERNAME", "E_MAIL")
-- );
-- create table "EMPLOYEE" (
--                             "EMPLOYEE_ID" bigint not null generated by default as identity,
--                             "USER_ID" bigint not null,
--                             "NAME" varchar(32) not null,
--                             "SURNAME" varchar(32) not null,
--                             "POSITION" varchar(32) not null,
--                             "SALARY" float not null,
--                             primary key ("EMPLOYEE_ID")
-- );
-- create table "CAR" (
--                        "CAR_ID" bigint not null generated by default as identity,
--                        "DRIVER_ID" bigint not null,
--                        "BRAND" varchar(32) not null,
--                        "MODEL" varchar(32) not null,
--                        "YEAR_OF_PROD" bigint not null,
--                        "REGISTRATION_NO" varchar(16) not null,
--                        "INSURANCE_EXP" date not null,
--                        "INSPECTION_EXP" date not null,
--                        primary key ("CAR_ID")
-- );
-- create table "MESSAGE" (
--                            "MESSAGE_ID" bigint not null generated by default as identity,
--                            "SENDER_ID" bigint not null,
--                            "RECEIVER_ID" bigint not null,
--                            "CONTENT" clob not null,
--                            "MESSAGE_STATE" varchar(32) not null,
--                            "SEND_DATE" date not null,
--                            "READ_DATE" date null,
--                            primary key ("MESSAGE_ID")
-- );
-- create table "PAYMENT_TYPE" (
--                                 "PAYMENT_TYPE_ID" bigint not null generated by default as identity,
--                                 "PAYMENT_TYPE" varchar(32) not null,
--                                 primary key ("PAYMENT_TYPE_ID")
-- );
-- create table "PAYMENT" (
--                            "PAYMENT_ID" bigint not null generated by default as identity,
--                            "PAYMENT_TYPE_ID" bigint not null,
--                            "PAYMENT_VALUE" float not null,
--                            "PAYMENT_STATE" varchar(32) not null,
--                            primary key ("PAYMENT_ID")
-- );
-- create table "ADDRESS" (
--                            "ADDRESS_ID" bigint not null generated by default as identity,
--                            "COUNTRY" varchar(32) not null,
--                            "TOWN" varchar(128) not null,
--                            "POSTAL_CODE" varchar(16) not null,
--                            "BUILDING_NO" varchar(16) not null,
--                            "STREET" varchar(64) null,
--                            "APARTMENT_NO" varchar(16) null,
--                            primary key ("ADDRESS_ID")
-- );
-- create table "CUSTOMER" (
--                             "CUSTOMER_ID" bigint not null generated by default as identity,
--                             "USER_ID" bigint not null,
--                             "ADDRESS_ID" bigint not null,
--                             "NAME" varchar(32) not null,
--                             "SURNAME" varchar(64) not null,
--                             "FIRM_NAME" varchar(256) null,
--                             "TELEPHONE_NO" varchar(32) not null,
--                             "TAX_ID" varchar(32) null,
--                             "DISCOUNT" bigint null,
--                             primary key ("CUSTOMER_ID")
-- );
-- create table "DELIVERY" (
--                             "DELIVERY_ID" bigint not null generated by default as identity,
--                             "ADDRESS_ID" bigint not null,
--                             "SUPPLIER_ID" bigint not null,
--                             "REMOVAL_FROM_STORAGE_DATE" date null,
--                             "DELIVERY_DATE" date null,
--                             primary key ("DELIVERY_ID")
-- );
-- create table "ORDER" (
--                          "ORDER_ID" bigint not null generated by default as identity,
--                          "PAYMENT_ID" bigint not null,
--                          "CUSTOMER_ID" bigint not null,
--                          "DELIVERY_ID" bigint not null,
--                          "COMMENT" clob null,
--                          "ORDER_STATE" varchar(32) not null,
--                          "ORDER_DATE" date not null,
--                          primary key ("ORDER_ID")
-- );
-- create table "STORAGE" (
--                            "STORAGE_ID" bigint not null generated by default as identity,
--                            "ADDRESS_ID" bigint not null,
--                            "MANAGER_ID" bigint not null,
--                            "STORAGE_NAME" varchar(32) not null,
--                            "CAPACITY" bigint not null,
--                            "IS_COLD_STORAGE" boolean not null,
--                            primary key ("STORAGE_ID")
-- );
-- create table "MAKER" (
--                          "MAKER_ID" bigint not null generated by default as identity,
--                          "ADDRESS_ID" bigint not null,
--                          "FIRM_NAME" varchar(256) not null,
--                          "TELEPHONE_NO" varchar(12) not null,
--                          "E_MAIL" varchar(32) not null,
--                          primary key ("MAKER_ID")
-- );
-- create table "PRODUCT" (
--                            "PRODUCT_ID" bigint not null generated by default as identity,
--                            "MAKER_ID" bigint not null,
--                            "PRODUCT_NAME" varchar(32) not null,
--                            "SHORT_DESCRIPTION" varchar(64) null,
--                            "LONG_DESCRIPTION" clob null,
--                            "CATEGORY" varchar(32) not null,
--                            "NEED_COLD_STORAGE" boolean not null,
--                            "BUY_PRICE" float not null,
--                            "SELL_PRICE" float not null,
--                            "IMAGE" clob not null,
--                            primary key ("PRODUCT_ID")
-- );
-- create table "PRODUCT_BATCH" (
--                                  "BATCH_ID" bigint not null generated by default as identity,
--                                  "PRODUCT_ID" bigint not null,
--                                  "BATCH_NO" bigint not null,
--                                  "EAT_BY_DATE" date not null,
--                                  "DISCOUNT" bigint null,
--                                  "PACKAGES_QUANTITY" bigint not null,
--                                  primary key ("BATCH_ID")
-- );
-- create table "PRODUCT_IN_STORAGE" (
--                                       "BATCH_ID" bigint not null,
--                                       "STORAGE_ID" bigint not null,
--                                       "QUANTITY" bigint not null,
--                                       primary key ("BATCH_ID", "STORAGE_ID")
-- );
-- create table "PRODUCT_ORDER" (
--                                  "ORDER_ID" bigint not null,
--                                  "BATCH_ID" bigint not null,
--                                  "QUANTITY" bigint not null,
--                                  primary key ("ORDER_ID", "BATCH_ID")
-- );
-- create table "COMPLAINT" (
--                              "COMPLAINT_ID" bigint not null generated by default as identity,
--                              "ORDER_ID" bigint not null,
--                              "CONTENT" clob not null,
--                              "SEND_DATE" date not null,
--                              "COMPLAINT_STATE" varchar(32) not null,
--                              "DECISION" clob null,
--                              "DECISION_DATE" date null,
--                              primary key ("COMPLAINT_ID")
-- );
-- alter table "EMPLOYEE"
--     add constraint FK_EMPLOYEE_USER
--         foreign key ("USER_ID")
--             references "USER" ("USER_ID");
-- alter table "CAR"
--     add constraint FK_CAR_DRIVER
--         foreign key ("DRIVER_ID")
--             references "EMPLOYEE" ("EMPLOYEE_ID");
-- alter table "MESSAGE"
--     add constraint FK_MESSAGE_SENDER
--         foreign key ("SENDER_ID")
--             references "EMPLOYEE" ("EMPLOYEE_ID");
-- alter table "MESSAGE"
--     add constraint FK_MESSAGE_RECEIVER
--         foreign key ("RECEIVER_ID")
--             references "EMPLOYEE" ("EMPLOYEE_ID");
-- alter table "PAYMENT"
--     add constraint FK_PAYMENT_TYPE
--         foreign key ("PAYMENT_TYPE_ID")
--             references "PAYMENT_TYPE" ("PAYMENT_TYPE_ID");
-- alter table "CUSTOMER"
--     add constraint FK_CUSTOMER_USER
--         foreign key ("USER_ID")
--             references "USER" ("USER_ID");
-- alter table "CUSTOMER"
--     add constraint FK_CUSTOMER_ADDRESS
--         foreign key ("ADDRESS_ID")
--             references "ADDRESS" ("ADDRESS_ID");
-- alter table "DELIVERY"
--     add constraint FK_DELIVER_ADDRESS
--         foreign key ("ADDRESS_ID")
--             references "ADDRESS" ("ADDRESS_ID");
-- alter table "DELIVERY"
--     add constraint FK_DELIVER_SUPPLIER
--         foreign key ("SUPPLIER_ID")
--             references "EMPLOYEE" ("EMPLOYEE_ID");
-- alter table "ORDER"
--     add constraint FK_ORDER_PAYMENT
--         foreign key ("PAYMENT_ID")
--             references "PAYMENT" ("PAYMENT_ID");
-- alter table "ORDER"
--     add constraint FK_ORDER_CUSTOMER
--         foreign key ("CUSTOMER_ID")
--             references "CUSTOMER" ("CUSTOMER_ID");
-- alter table "ORDER"
--     add constraint FK_ORDER_DELIVERY
--         foreign key ("DELIVERY_ID")
--             references "DELIVERY" ("DELIVERY_ID");
-- alter table "STORAGE"
--     add constraint FK_STORAGE_ADDRESS
--         foreign key ("ADDRESS_ID")
--             references "ADDRESS" ("ADDRESS_ID");
-- alter table "STORAGE"
--     add constraint FK_STORAGE_MANAGER
--         foreign key ("MANAGER_ID")
--             references "EMPLOYEE" ("EMPLOYEE_ID");
-- alter table "MAKER"
--     add constraint FK_MAKER_ADDRESS
--         foreign key ("ADDRESS_ID")
--             references "ADDRESS" ("ADDRESS_ID");
-- alter table "PRODUCT"
--     add constraint FK_PRODUCT_MAKER
--         foreign key ("MAKER_ID")
--             references "MAKER" ("MAKER_ID");
-- alter table "PRODUCT_BATCH"
--     add constraint FK_BATCH_PRODUCT
--         foreign key ("PRODUCT_ID")
--             references "PRODUCT" ("PRODUCT_ID");
-- alter table "PRODUCT_IN_STORAGE"
--     add constraint FK_PRODUCT_IN_STORAGE_BATCH
--         foreign key ("BATCH_ID")
--             references "PRODUCT_BATCH" ("BATCH_ID");
-- alter table "PRODUCT_IN_STORAGE"
--     add constraint FK_PRODUCT_IN_STORAGE_STORAGE
--         foreign key ("STORAGE_ID")
--             references "STORAGE" ("STORAGE_ID");
-- alter table "PRODUCT_ORDER"
--     add constraint FK_PRODUCT_ORDER_ORDER
--         foreign key ("ORDER_ID")
--             references "ORDER" ("ORDER_ID");
-- alter table "PRODUCT_ORDER"
--     add constraint FK_PRODUCT_ORDER_BATCH
--         foreign key ("BATCH_ID")
--             references "PRODUCT_BATCH" ("BATCH_ID");
-- alter table "COMPLAINT"
--     add constraint FK_COMPLAINT_ORDER
--         foreign key ("ORDER_ID")
--             references "ORDER" ("ORDER_ID");
--
-- -- DROP TABLE IF EXISTS `COMPLAINT`;
-- -- DROP TABLE IF EXISTS `PRODUCT_ORDER`;
-- -- DROP TABLE IF EXISTS `PRODUCT_IN_STORAGE`;
-- -- DROP TABLE IF EXISTS `PRODUCT_BATCH`;
-- -- DROP TABLE IF EXISTS `PRODUCT`;
-- -- DROP TABLE IF EXISTS `MAKER`;
-- -- DROP TABLE IF EXISTS `STORAGE`;
-- -- DROP TABLE IF EXISTS `ORDER`;
-- -- DROP TABLE IF EXISTS `DELIVERY`;
-- -- DROP TABLE IF EXISTS `CUSTOMER`;
-- -- DROP TABLE IF EXISTS `ADDRESS`;
-- -- DROP TABLE IF EXISTS `PAYMENT`;
-- -- DROP TABLE IF EXISTS `PAYMENT_TYPE`;
-- -- DROP TABLE IF EXISTS `MESSAGE`;
-- -- DROP TABLE IF EXISTS `CAR`;
-- -- DROP TABLE IF EXISTS `EMPLOYEE`;
-- -- DROP TABLE IF EXISTS `USER`;
-- --
-- --
-- --
-- -- CREATE TABLE `USER` (
-- --                         `USER_ID` INT UNSIGNED NOT NULL AUTOINCREMENT,
-- --                         `USERNAME` VARCHAR(32) NOT NULL UNIQUE,
-- --                         `PASSWORD` VARCHAR(64) NOT NULL,
-- --                         `PERMISSION` VARCHAR(32) NOT NULL,
-- --                         `E_MAIL` VARCHAR(32) NOT NULL UNIQUE,
-- --                         PRIMARY KEY(`USER_ID`),
-- --                         UNIQUE (`USERNAME`, `E_MAIL`)
-- -- );
-- --
-- -- CREATE TABLE `EMPLOYEE` (
-- --                             `EMPLOYEE_ID` INT UNSIGNED NOT NULL AUTOINCREMENT,
-- --                             `USER_ID` INT UNSIGNED NOT NULL,
-- --                             `NAME` VARCHAR(32) NOT NULL,
-- --                             `SURNAME` VARCHAR(32) NOT NULL,
-- --                             `POSITION` VARCHAR(32) NOT NULL,
-- --                             `SALARY` FLOAT(2) NOT NULL,
-- --     PRIMARY KEY(`EMPLOYEE_ID`)
-- -- );
-- --
-- -- CREATE TABLE `CAR` (
-- --                        `CAR_ID` INT UNSIGNED NOT NULL AUTOINCREMENT,
-- --                        `DRIVER_ID` INT UNSIGNED NOT NULL,
-- --                        `BRAND` VARCHAR(32) NOT NULL,
-- --                        `MODEL` VARCHAR(32) NOT NULL,
-- --                        `YEAR_OF_PROD` INT UNSIGNED NOT NULL,
-- --                        `REGISTRATION_NO` VARCHAR(16) NOT NULL,
-- --                        `INSURANCE_EXP` DATE NOT NULL,
-- --                        `INSPECTION_EXP` DATE NOT NULL,
-- --                        PRIMARY KEY(`CAR_ID`)
-- -- );
-- --
-- -- CREATE TABLE `MESSAGE` (
-- --                            `MESSAGE_ID` INT UNSIGNED NOT NULL AUTOINCREMENT,
-- --                            `SENDER_ID` INT UNSIGNED NOT NULL,
-- --                            `RECEIVER_ID` INT UNSIGNED NOT NULL,
-- --                            `CONTENT` TEXT NOT NULL,
-- --                            `MESSAGE_STATE` VARCHAR(32) NOT NULL,
-- --                            `SEND_DATE` DATE NOT NULL,
-- --                            `READ_DATE` DATE NULL,
-- --                            PRIMARY KEY(`MESSAGE_ID`)
-- -- );
-- --
-- -- CREATE TABLE `PAYMENT_TYPE` (
-- --                                 `PAYMENT_TYPE_ID` INT UNSIGNED NOT NULL AUTOINCREMENT,
-- --                                 `PAYMENT_TYPE` VARCHAR(32) NOT NULL,
-- --                                 PRIMARY KEY(`PAYMENT_TYPE_ID`)
-- -- );
-- --
-- -- CREATE TABLE `PAYMENT` (
-- --                            `PAYMENT_ID` INT UNSIGNED NOT NULL AUTOINCREMENT,
-- --                            `PAYMENT_TYPE_ID` INT UNSIGNED NOT NULL,
-- --                            `PAYMENT_VALUE` FLOAT(2) NOT NULL,
-- --     `PAYMENT_STATE` VARCHAR(32) NOT NULL,
-- --     PRIMARY KEY(`PAYMENT_ID`)
-- -- );
-- --
-- -- CREATE TABLE `ADDRESS` (
-- --                            `ADDRESS_ID` INT UNSIGNED NOT NULL AUTOINCREMENT,
-- --                            `COUNTRY` VARCHAR(32) NOT NULL,
-- --                            `TOWN` VARCHAR(128) NOT NULL,
-- --                            `POSTAL_CODE` VARCHAR(16) NOT NULL,
-- --                            `BUILDING_NO` VARCHAR(16) NOT NULL,
-- --                            `STREET` VARCHAR(64) NULL,
-- --                            `APARTMENT_NO` VARCHAR(16) NULL,
-- --                            PRIMARY KEY(`ADDRESS_ID`)
-- -- );
-- --
-- -- CREATE TABLE `CUSTOMER` (
-- --                             `CUSTOMER_ID` INT UNSIGNED NOT NULL AUTOINCREMENT,
-- --                             `USER_ID` INT UNSIGNED NOT NULL,
-- --                             `ADDRESS_ID` INT UNSIGNED NOT NULL,
-- --                             `NAME` VARCHAR(32) NOT NULL,
-- --                             `SURNAME` VARCHAR(64) NOT NULL,
-- --                             `FIRM_NAME` VARCHAR(256) NULL,
-- --                             `TELEPHONE_NO` VARCHAR(32) NOT NULL,
-- --                             `TAX_ID` VARCHAR(32) NULL,
-- --                             `DISCOUNT` INT UNSIGNED NULL,
-- --                             PRIMARY KEY(`CUSTOMER_ID`)
-- -- );
-- --
-- -- CREATE TABLE `DELIVERY` (
-- --                             `DELIVERY_ID` INT UNSIGNED NOT NULL AUTOINCREMENT,
-- --                             `ADDRESS_ID` INT UNSIGNED NOT NULL,
-- --                             `SUPPLIER_ID` INT UNSIGNED NOT NULL,
-- --                             `REMOVAL_FROM_STORAGE_DATE` DATE NULL,
-- --                             `DELIVERY_DATE` DATE NULL,
-- --                             PRIMARY KEY(`DELIVERY_ID`)
-- -- );
-- --
-- -- CREATE TABLE `ORDER` (
-- --                          `ORDER_ID` INT UNSIGNED NOT NULL AUTOINCREMENT,
-- --                          `PAYMENT_ID` INT UNSIGNED NOT NULL,
-- --                          `CUSTOMER_ID` INT UNSIGNED NOT NULL,
-- --                          `DELIVERY_ID` INT UNSIGNED NOT NULL,
-- --                          `COMMENT` TEXT NULL,
-- --                          `ORDER_STATE` VARCHAR(32) NOT NULL,
-- --                          `ORDER_DATE` DATE NOT NULL,
-- --                          PRIMARY KEY(`ORDER_ID`)
-- -- );
-- --
-- -- CREATE TABLE `STORAGE` (
-- --                            `STORAGE_ID` INT UNSIGNED NOT NULL AUTOINCREMENT,
-- --                            `ADDRESS_ID` INT UNSIGNED NOT NULL,
-- --                            `MANAGER_ID` INT UNSIGNED NOT NULL,
-- --                            `STORAGE_NAME` VARCHAR(32) NOT NULL,
-- --                            `CAPACITY` INT UNSIGNED NOT NULL,
-- --                            `IS_COLD_STORAGE` BOOLEAN NOT NULL,
-- --                            PRIMARY KEY(`STORAGE_ID`)
-- -- );
-- --
-- -- CREATE TABLE `MAKER` (
-- --                          `MAKER_ID` INT UNSIGNED NOT NULL AUTOINCREMENT,
-- --                          `ADDRESS_ID` INT UNSIGNED NOT NULL,
-- --                          `FIRM_NAME` VARCHAR(256) NOT NULL,
-- --                          `TELEPHONE_NO` VARCHAR(12) NOT NULL,
-- --                          `E_MAIL` VARCHAR(32) NOT NULL,
-- --                          PRIMARY KEY(`MAKER_ID`)
-- -- );
-- --
-- -- CREATE TABLE `PRODUCT` (
-- --                            `PRODUCT_ID` INT UNSIGNED NOT NULL AUTOINCREMENT,
-- --                            `MAKER_ID` INT UNSIGNED NOT NULL,
-- --                            `PRODUCT_NAME` VARCHAR(32) NOT NULL,
-- --                            `SHORT_DESCRIPTION` VARCHAR(64) NULL,
-- --                            `LONG_DESCRIPTION` TEXT NULL,
-- --                            `CATEGORY` VARCHAR(32) NOT NULL,
-- --                            `NEED_COLD_STORAGE` BOOLEAN NOT NULL,
-- --                            `BUY_PRICE` FLOAT(2) NOT NULL,
-- --     `SELL_PRICE` FLOAT(2) NOT NULL,
-- --     `IMAGE` CLOB NOT NULL,
-- --     PRIMARY KEY(`PRODUCT_ID`)
-- -- );
-- --
-- -- CREATE TABLE `PRODUCT_BATCH` (
-- --                                  `BATCH_ID` INT UNSIGNED NOT NULL AUTOINCREMENT,
-- --                                  `PRODUCT_ID` INT UNSIGNED NOT NULL,
-- --                                  `BATCH_NO` INT UNSIGNED NOT NULL,
-- --                                  `EAT_BY_DATE` DATE NOT NULL,
-- --                                  `DISCOUNT` INT UNSIGNED NULL,
-- --                                  `PACKAGES_QUANTITY` INT UNSIGNED NOT NULL,
-- --                                  PRIMARY KEY(`BATCH_ID`)
-- -- );
-- --
-- -- CREATE TABLE `PRODUCT_IN_STORAGE` (
-- --                                       `BATCH_ID` INT UNSIGNED NOT NULL,
-- --                                       `STORAGE_ID` INT UNSIGNED NOT NULL,
-- --                                       `QUANTITY` INT UNSIGNED NOT NULL,
-- --                                       PRIMARY KEY(`BATCH_ID`, `STORAGE_ID`)
-- -- );
-- --
-- -- CREATE TABLE `PRODUCT_ORDER` (
-- --                                  `ORDER_ID` INT UNSIGNED NOT NULL,
-- --                                  `BATCH_ID` INT UNSIGNED NOT NULL,
-- --                                  `QUANTITY` INT UNSIGNED NOT NULL,
-- --                                  PRIMARY KEY(`ORDER_ID`, `BATCH_ID`)
-- -- );
-- --
-- -- CREATE TABLE `COMPLAINT` (
-- --                              `COMPLAINT_ID` INT UNSIGNED NOT NULL AUTOINCREMENT,
-- --                              `ORDER_ID` INT UNSIGNED NOT NULL,
-- --                              `CONTENT` TEXT  NOT NULL,
-- --                              `SEND_DATE` DATE NOT NULL,
-- --                              `COMPLAINT_STATE` VARCHAR(32) NOT NULL,
-- --                              `DECISION` TEXT NULL,
-- --                              `DECISION_DATE` DATE NULL,
-- --                              PRIMARY KEY(`COMPLAINT_ID`)
-- -- );
-- --
-- -- ALTER TABLE `EMPLOYEE`
-- --     ADD CONSTRAINT FK_EMPLOYEE_USER
-- --         FOREIGN KEY (`USER_ID`) REFERENCES `USER`(`USER_ID`);
-- --
-- -- ALTER TABLE `CAR`
-- --     ADD CONSTRAINT FK_CAR_DRIVER
-- --         FOREIGN KEY (`DRIVER_ID`) REFERENCES `EMPLOYEE`(`EMPLOYEE_ID`);
-- --
-- -- ALTER TABLE `MESSAGE`
-- --     ADD CONSTRAINT FK_MESSAGE_SENDER
-- --         FOREIGN KEY (`SENDER_ID`) REFERENCES `EMPLOYEE`(`EMPLOYEE_ID`);
-- --
-- -- ALTER TABLE `MESSAGE`
-- --     ADD CONSTRAINT FK_MESSAGE_RECEIVER
-- --         FOREIGN KEY (`RECEIVER_ID`) REFERENCES `EMPLOYEE`(`EMPLOYEE_ID`);
-- --
-- -- ALTER TABLE `PAYMENT`
-- --     ADD CONSTRAINT FK_PAYMENT_TYPE
-- --         FOREIGN KEY (`PAYMENT_TYPE_ID`) REFERENCES `PAYMENT_TYPE`(`PAYMENT_TYPE_ID`);
-- --
-- -- ALTER TABLE `CUSTOMER`
-- --     ADD CONSTRAINT FK_CUSTOMER_USER
-- --         FOREIGN KEY (`USER_ID`) REFERENCES `USER`(`USER_ID`);
-- --
-- -- ALTER TABLE `CUSTOMER`
-- --     ADD CONSTRAINT FK_CUSTOMER_ADDRESS
-- --         FOREIGN KEY (`ADDRESS_ID`) REFERENCES `ADDRESS`(`ADDRESS_ID`);
-- --
-- -- ALTER TABLE `DELIVERY`
-- --     ADD CONSTRAINT FK_DELIVER_ADDRESS
-- --         FOREIGN KEY (`ADDRESS_ID`) REFERENCES `ADDRESS`(`ADDRESS_ID`);
-- --
-- -- ALTER TABLE `DELIVERY`
-- --     ADD CONSTRAINT FK_DELIVER_SUPPLIER
-- --         FOREIGN KEY (`SUPPLIER_ID`) REFERENCES `EMPLOYEE`(`EMPLOYEE_ID`);
-- --
-- -- ALTER TABLE `ORDER`
-- --     ADD CONSTRAINT FK_ORDER_PAYMENT
-- --         FOREIGN KEY (`PAYMENT_ID`) REFERENCES `PAYMENT`(`PAYMENT_ID`);
-- --
-- -- ALTER TABLE `ORDER`
-- --     ADD CONSTRAINT FK_ORDER_CUSTOMER
-- --         FOREIGN KEY (`CUSTOMER_ID`) REFERENCES `CUSTOMER`(`CUSTOMER_ID`);
-- --
-- -- ALTER TABLE `ORDER`
-- --     ADD CONSTRAINT FK_ORDER_DELIVERY
-- --         FOREIGN KEY (`DELIVERY_ID`) REFERENCES `DELIVERY`(`DELIVERY_ID`);
-- --
-- -- ALTER TABLE `STORAGE`
-- --     ADD CONSTRAINT FK_STORAGE_ADDRESS
-- --         FOREIGN KEY (`ADDRESS_ID`) REFERENCES `ADDRESS`(`ADDRESS_ID`);
-- --
-- -- ALTER TABLE `STORAGE`
-- --     ADD CONSTRAINT FK_STORAGE_MANAGER
-- --         FOREIGN KEY (`MANAGER_ID`) REFERENCES `EMPLOYEE`(`EMPLOYEE_ID`);
-- --
-- -- ALTER TABLE `MAKER`
-- --     ADD CONSTRAINT FK_MAKER_ADDRESS
-- --         FOREIGN KEY (`ADDRESS_ID`) REFERENCES `ADDRESS`(`ADDRESS_ID`);
-- --
-- -- ALTER TABLE `PRODUCT`
-- --     ADD CONSTRAINT FK_PRODUCT_MAKER
-- --         FOREIGN KEY (`MAKER_ID`) REFERENCES `MAKER`(`MAKER_ID`);
-- --
-- -- ALTER TABLE `PRODUCT_BATCH`
-- --     ADD CONSTRAINT FK_BATCH_PRODUCT
-- --         FOREIGN KEY (`PRODUCT_ID`) REFERENCES `PRODUCT`(`PRODUCT_ID`);
-- --
-- -- ALTER TABLE `PRODUCT_IN_STORAGE`
-- --     ADD CONSTRAINT FK_PRODUCT_IN_STORAGE_BATCH
-- --         FOREIGN KEY (`BATCH_ID`) REFERENCES `PRODUCT_BATCH`(`BATCH_ID`);
-- --
-- -- ALTER TABLE `PRODUCT_IN_STORAGE`
-- --     ADD CONSTRAINT FK_PRODUCT_IN_STORAGE_STORAGE
-- --         FOREIGN KEY (`STORAGE_ID`) REFERENCES `STORAGE`(`STORAGE_ID`);
-- --
-- -- ALTER TABLE `PRODUCT_ORDER`
-- --     ADD CONSTRAINT FK_PRODUCT_ORDER_ORDER
-- --         FOREIGN KEY (`ORDER_ID`) REFERENCES `ORDER`(`ORDER_ID`);
-- --
-- -- ALTER TABLE `PRODUCT_ORDER`
-- --     ADD CONSTRAINT FK_PRODUCT_ORDER_BATCH
-- --         FOREIGN KEY (`BATCH_ID`) REFERENCES `PRODUCT_BATCH`(`BATCH_ID`);
-- --
-- -- ALTER TABLE `COMPLAINT`
-- --     ADD CONSTRAINT FK_COMPLAINT_ORDER
-- --         FOREIGN KEY (`ORDER_ID`) REFERENCES `ORDER`(`ORDER_ID`);