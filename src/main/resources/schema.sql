CREATE TABLE `USER` (
                        `USER_ID` INT NOT NULL AUTO_INCREMENT,
                        `USERNAME` VARCHAR(32) NOT NULL UNIQUE,
                        `PASSWORD` VARCHAR(64) NOT NULL,
                        `PERMISSION` VARCHAR(64) NOT NULL,
                        `E_MAIL` VARCHAR(32) NOT NULL UNIQUE,
                        PRIMARY KEY(`USER_ID`),
                        UNIQUE (`USERNAME`, `E_MAIL`)
);

CREATE TABLE `EMPLOYEE` (
                            `EMPLOYEE_ID` INT UNSIGNED NOT NULL AUTO_INCREMENT,
                            `USER_ID` INT UNSIGNED NOT NULL,
                            `NAME` VARCHAR(32) NOT NULL,
                            `SURNAME` VARCHAR(32) NOT NULL,
                            `POSITION` VARCHAR(32) NOT NULL,
                            `SALARY` FLOAT(2) NOT NULL,
                            PRIMARY KEY(`EMPLOYEE_ID`)
);

CREATE TABLE `CAR` (
                       `CAR_ID` INT UNSIGNED NOT NULL AUTO_INCREMENT,
                       `DRIVER_ID` INT UNSIGNED NOT NULL,
                       `BRAND` VARCHAR(32) NOT NULL,
                       `MODEL` VARCHAR(32) NOT NULL,
                       `YEAR_OF_PROD` INT UNSIGNED NOT NULL,
                       `REGISTRATION_NO` VARCHAR(16) NOT NULL,
                       `INSURANCE_EXP` DATE NOT NULL,
                       `INSPECTION_EXP` DATE NOT NULL,
                       PRIMARY KEY(`CAR_ID`)
);

CREATE TABLE `MESSAGE` (
                           `MESSAGE_ID` INT UNSIGNED NOT NULL AUTO_INCREMENT,
                           `SENDER_ID` INT UNSIGNED NOT NULL,
                           `RECEIVER_ID` INT UNSIGNED NOT NULL,
                           `CONTENT` TEXT NOT NULL,
                           `MESSAGE_STATE` VARCHAR(64) NOT NULL,
                           `SEND_DATE` DATE NOT NULL,
                           `READ_DATE` DATE NULL,
                           PRIMARY KEY(`MESSAGE_ID`)
);

CREATE TABLE `PAYMENT_TYPE` (
                                `PAYMENT_TYPE_ID` INT UNSIGNED NOT NULL AUTO_INCREMENT,
                                `PAYMENT_TYPE` VARCHAR(32) NOT NULL,
                                PRIMARY KEY(`PAYMENT_TYPE_ID`)
);

CREATE TABLE `PAYMENT` (
                           `PAYMENT_ID` INT UNSIGNED NOT NULL AUTO_INCREMENT,
                           `PAYMENT_TYPE_ID` INT UNSIGNED NOT NULL,
                           `PAYMENT_VALUE` FLOAT(2) NOT NULL,
                            `PAYMENT_STATE` VARCHAR(64) NOT NULL,
                            PRIMARY KEY(`PAYMENT_ID`)
);

CREATE TABLE `ADDRESS` (
                           `ADDRESS_ID` INT UNSIGNED NOT NULL AUTO_INCREMENT,
                           `COUNTRY` VARCHAR(32) NOT NULL,
                           `TOWN` VARCHAR(128) NOT NULL,
                           `POSTAL_CODE` VARCHAR(16) NOT NULL,
                           `BUILDING_NO` VARCHAR(16) NOT NULL,
                           `STREET` VARCHAR(64) NULL,
                           `APARTMENT_NO` VARCHAR(16) NULL,
                           PRIMARY KEY(`ADDRESS_ID`)
);

CREATE TABLE `CUSTOMER` (
                            `CUSTOMER_ID` INT UNSIGNED NOT NULL AUTO_INCREMENT,
                            `USER_ID` INT UNSIGNED NOT NULL,
                            `ADDRESS_ID` INT UNSIGNED NOT NULL,
                            `NAME` VARCHAR(32) NOT NULL,
                            `SURNAME` VARCHAR(64) NOT NULL,
                            `FIRM_NAME` VARCHAR(256) NULL,
                            `TELEPHONE_NO` VARCHAR(32) NOT NULL,
                            `TAX_ID` VARCHAR(32) NULL,
                            `DISCOUNT` INT UNSIGNED NULL,
                            PRIMARY KEY(`CUSTOMER_ID`)
);

CREATE TABLE `DELIVERY` (
                            `DELIVERY_ID` INT UNSIGNED NOT NULL AUTO_INCREMENT,
                            `ADDRESS_ID` INT UNSIGNED NOT NULL,
                            `SUPPLIER_ID` INT UNSIGNED NOT NULL,
                            `REMOVAL_FROM_STORAGE_DATE` DATE NULL,
                            `DELIVERY_DATE` DATE NULL,
                            PRIMARY KEY(`DELIVERY_ID`)
);

CREATE TABLE `ORDER` (
                         `ORDER_ID` INT UNSIGNED NOT NULL AUTO_INCREMENT,
                         `PAYMENT_ID` INT UNSIGNED NOT NULL,
                         `CUSTOMER_ID` INT UNSIGNED NOT NULL,
                         `DELIVERY_ID` INT UNSIGNED NOT NULL,
                         `COMMENT` TEXT NULL,
                         `ORDER_STATE` VARCHAR(64) NOT NULL,
                         PRIMARY KEY(`ORDER_ID`)
);

CREATE TABLE `STORAGE` (
                           `STORAGE_ID` INT UNSIGNED NOT NULL AUTO_INCREMENT,
                           `ADDRESS_ID` INT UNSIGNED NOT NULL,
                           `MANAGER_ID` INT UNSIGNED NOT NULL,
                           `STORAGE_NAME` VARCHAR(32) NOT NULL,
                           `CAPACITY` INT UNSIGNED NOT NULL,
                           `IS_COLD_STORAGE` VARCHAR(1) NOT NULL,
                           PRIMARY KEY(`STORAGE_ID`)
);

CREATE TABLE `MAKER` (
                         `MAKER_ID` INT UNSIGNED NOT NULL AUTO_INCREMENT,
                         `ADDRESS_ID` INT UNSIGNED NOT NULL,
                         `FIRM_NAME` VARCHAR(256) NOT NULL,
                         `TELEPHONE_NO` VARCHAR(12) NOT NULL,
                         `E_MAIL` VARCHAR(32) NOT NULL,
                         PRIMARY KEY(`MAKER_ID`)
);

CREATE TABLE `PRODUCT` (
                           `PRODUCT_ID` INT UNSIGNED NOT NULL AUTO_INCREMENT,
                           `MAKER_ID` INT UNSIGNED NOT NULL,
                           `PRODUCT_NAME` VARCHAR(32) NOT NULL,
                           `CATEGORY` VARCHAR(32) NOT NULL,
                           `NEED_COLD_STORAGE` VARCHAR(1) NOT NULL,
                           `BUY_PRICE` FLOAT(2) NOT NULL,
                            `SELL_PRICE` FLOAT(2) NOT NULL,
                            PRIMARY KEY(`PRODUCT_ID`)
);

CREATE TABLE `PRODUCT_BATCH` (
                                 `BATCH_ID` INT UNSIGNED NOT NULL AUTO_INCREMENT,
                                 `PRODUCT_ID` INT UNSIGNED NOT NULL,
                                 `BATCH_NO` INT UNSIGNED NOT NULL,
                                 `EAT_BY_DATE` DATE NOT NULL,
                                 `DISCOUNT` INT UNSIGNED NULL,
                                 `PACKAGES_QUANTITY` INT UNSIGNED NOT NULL,
                                 PRIMARY KEY(`BATCH_ID`)
);

CREATE TABLE `PRODUCT_IN_STORAGE` (
                                      `BATCH_ID` INT UNSIGNED NOT NULL,
                                      `STORAGE_ID` INT UNSIGNED NOT NULL,
                                      `QUANTITY` INT UNSIGNED NOT NULL,
                                      PRIMARY KEY(`BATCH_ID`, `STORAGE_ID`)
);

CREATE TABLE `PRODUCT_ORDER` (
                                 `ORDER_ID` INT UNSIGNED NOT NULL,
                                 `BATCH_ID` INT UNSIGNED NOT NULL,
                                 `QUANTITY` INT UNSIGNED NOT NULL,
                                 PRIMARY KEY(`ORDER_ID`, `BATCH_ID`)
);

CREATE TABLE `COMPLAINT` (
                             `COMPLAINT_ID` INT UNSIGNED NOT NULL AUTO_INCREMENT,
                             `ORDER_ID` INT UNSIGNED NOT NULL,
                             `CONTENT` TEXT  NOT NULL,
                             `SEND_DATE` DATE NOT NULL,
                             `COMPLAINT_STATE` VARCHAR(64) NOT NULL,
                             `DECISION` TEXT NULL,
                             `DECISION_DATE` DATE NULL,
                             PRIMARY KEY(`COMPLAINT_ID`)
);

ALTER TABLE `EMPLOYEE`
    ADD CONSTRAINT FK_EMPLOYEE_USER
        FOREIGN KEY (`USER_ID`) REFERENCES `USER`(`USER_ID`);

ALTER TABLE `CAR`
    ADD CONSTRAINT FK_CAR_DRIVER
        FOREIGN KEY (`DRIVER_ID`) REFERENCES `EMPLOYEE`(`EMPLOYEE_ID`);

ALTER TABLE `MESSAGE`
    ADD CONSTRAINT FK_MESSAGE_SENDER
        FOREIGN KEY (`SENDER_ID`) REFERENCES `EMPLOYEE`(`EMPLOYEE_ID`);

ALTER TABLE `MESSAGE`
    ADD CONSTRAINT FK_MESSAGE_RECEIVER
        FOREIGN KEY (`RECEIVER_ID`) REFERENCES `EMPLOYEE`(`EMPLOYEE_ID`);

ALTER TABLE `PAYMENT`
    ADD CONSTRAINT FK_PAYMENT_TYPE
        FOREIGN KEY (`PAYMENT_TYPE_ID`) REFERENCES `PAYMENT_TYPE`(`PAYMENT_TYPE_ID`);

ALTER TABLE `CUSTOMER`
    ADD CONSTRAINT FK_CUSTOMER_USER
        FOREIGN KEY (`USER_ID`) REFERENCES `USER`(`USER_ID`);

ALTER TABLE `CUSTOMER`
    ADD CONSTRAINT FK_CUSTOMER_ADDRESS
        FOREIGN KEY (`ADDRESS_ID`) REFERENCES `ADDRESS`(`ADDRESS_ID`);

ALTER TABLE `DELIVERY`
    ADD CONSTRAINT FK_DELIVER_ADDRESS
        FOREIGN KEY (`ADDRESS_ID`) REFERENCES `ADDRESS`(`ADDRESS_ID`);

ALTER TABLE `DELIVERY`
    ADD CONSTRAINT FK_DELIVER_SUPPLIER
        FOREIGN KEY (`SUPPLIER_ID`) REFERENCES `EMPLOYEE`(`EMPLOYEE_ID`);

ALTER TABLE `ORDER`
    ADD CONSTRAINT FK_ORDER_PAYMENT
        FOREIGN KEY (`PAYMENT_ID`) REFERENCES `PAYMENT`(`PAYMENT_ID`);

ALTER TABLE `ORDER`
    ADD CONSTRAINT FK_ORDER_CUSTOMER
        FOREIGN KEY (`CUSTOMER_ID`) REFERENCES `CUSTOMER`(`CUSTOMER_ID`);

ALTER TABLE `ORDER`
    ADD CONSTRAINT FK_ORDER_DELIVERY
        FOREIGN KEY (`DELIVERY_ID`) REFERENCES `DELIVERY`(`DELIVERY_ID`);

ALTER TABLE `STORAGE`
    ADD CONSTRAINT FK_STORAGE_ADDRESS
        FOREIGN KEY (`ADDRESS_ID`) REFERENCES `ADDRESS`(`ADDRESS_ID`);

ALTER TABLE `STORAGE`
    ADD CONSTRAINT FK_STORAGE_MANAGER
        FOREIGN KEY (`MANAGER_ID`) REFERENCES `EMPLOYEE`(`EMPLOYEE_ID`);

ALTER TABLE `MAKER`
    ADD CONSTRAINT FK_MAKER_ADRES
        FOREIGN KEY (`ADDRESS_ID`) REFERENCES `ADDRESS`(`ADDRESS_ID`);

ALTER TABLE `PRODUCT`
    ADD CONSTRAINT FK_PRODUCT_MAKER
        FOREIGN KEY (`MAKER_ID`) REFERENCES `MAKER`(`MAKER_ID`);

ALTER TABLE `PRODUCT_BATCH`
    ADD CONSTRAINT FK_BATCH_PRODUCT
        FOREIGN KEY (`PRODUCT_ID`) REFERENCES `PRODUCT`(`PRODUCT_ID`);

ALTER TABLE `PRODUCT_IN_STORAGE`
    ADD CONSTRAINT FK_PRODUCT_IN_STORAGE_BATCH
        FOREIGN KEY (`BATCH_ID`) REFERENCES `PRODUCT_BATCH`(`BATCH_ID`);

ALTER TABLE `PRODUCT_IN_STORAGE`
    ADD CONSTRAINT FK_PRODUCT_IN_STORAGE_STORAGE
        FOREIGN KEY (`STORAGE_ID`) REFERENCES `STORAGE`(`STORAGE_ID`);

ALTER TABLE `PRODUCT_ORDER`
    ADD CONSTRAINT FK_PRODUCT_ORDER_ORDER
        FOREIGN KEY (`ORDER_ID`) REFERENCES `ORDER`(`ORDER_ID`);

ALTER TABLE `PRODUCT_ORDER`
    ADD CONSTRAINT FK_PRODUCT_ORDER_BATCH
        FOREIGN KEY (`BATCH_ID`) REFERENCES `PRODUCT_BATCH`(`BATCH_ID`);

ALTER TABLE `COMPLAINT`
    ADD CONSTRAINT FK_COMPLAINT_ORDER
        FOREIGN KEY (`ORDER_ID`) REFERENCES `ORDER`(`ORDER_ID`);