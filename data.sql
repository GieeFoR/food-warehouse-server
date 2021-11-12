insert into "USER" (
    "USER_ID",
    "USERNAME",
    "PASSWORD",
    "PERMISSION",
    "E_MAIL"
)
values (
           1,
           'Admin',
           '$2a$10$fRMvxz8..l/vL0PNuZzaAehkH.OblwBxvP0QsmrP3ovtvM0uzdao.',
           'Admin',
           'admin@example.com'
       ), (
           2,
           'Admin2',
           '$2a$10$fRMvxz8..l/vL0PNuZzaAehkH.OblwBxvP0QsmrP3ovtvM0uzdao.',
           'Admin',
           'admin2@example.com'
       ), (
           3,
           'Supplier',
           '$2a$10$CrjbGAfKP0aLp1m/cyF3Yu7QgFhnfSPEUIq4SQtsbjyrYDA7kAOtq',
           'Supplier',
           'supplier@example.com'
       ), (
           11,
           'Manager',
           '$2a$10$mc4ibJleoC.aDQonjHB7DewryevBIeaVggCiM4mzie9UZepcHD0WO',
           'Manager',
           'manager@example.com'
       ), (
           12,
           'Customer',
           '$2a$10$G9MyJZCKY6qQvDr6p/E27ulXpkp4Uq7hLKlezRLEjW/J0IzP3Rsnq',
           'Customer',
           'customer@example.com'
       ), (
           13,
           'Supplier2',
           '$2a$10$tvTp.Il1wH5OE42vSrwWsegsIBsVfb3pv0zHoazjci1G2eIfsaKu6',
           'Supplier',
           'supplier2@example.com'
       ), (
           14,
           'Customer1',
           '$2a$10$4Zh07XKhiGrYO0v7NbR5RuwEqb8VsefRVTLFoM7AH0o7kk9EwipVe',
           'Customer',
           'customer1@example.com'
       );
insert into "EMPLOYEE" (
    "EMPLOYEE_ID",
    "USER_ID",
    "NAME",
    "SURNAME",
    "POSITION",
    "SALARY"
)
values (
           1,
           1,
           'Jan',
           'Kowalski',
           'Administrator główny',
           15000.00
       ), (
           2,
           2,
           'Albert',
           'Kanak',
           'Administrator',
           10000.00
       ), (
           3,
           3,
           'Krystian',
           'Wójcik',
           'Kierowca',
           7000.00
       ), (
           4,
           11,
           'Tomasz',
           'Dudzik',
           'Manager',
           10000.00
       ), (
           5,
           13,
           'Paweł',
           'Pabiańczyk',
           'Kierowca',
           7000.00
       );
insert into "CAR" (
    "CAR_ID",
    "DRIVER_ID",
    "BRAND",
    "MODEL",
    "YEAR_OF_PROD",
    "REGISTRATION_NO",
    "INSURANCE_EXP",
    "INSPECTION_EXP"
)
values (
           1,
           3,
           'Fiat',
           'Ducato',
           2016,
           'KRK 12322',
           '2022-11-11',
           '2022-02-03'
       ), (
           2,
           5,
           'Renault',
           'Master',
           2017,
           'KRK 45687',
           '2022-09-24',
           '2022-01-16'
       ), (
           5,
           1,
           'Fiat',
           'Punto',
           1999,
           'KRK 12313',
           '2022-01-22',
           '2022-01-22'
       );
insert into "MESSAGE" (
    "MESSAGE_ID",
    "SENDER_ID",
    "RECEIVER_ID",
    "CONTENT",
    "MESSAGE_STATE",
    "SEND_DATE",
    "READ_DATE"
)
values (
           6,
           4,
           4,
           'Wiadomość testowa 1',
           'READ',
           '2021-09-16',
           '2021-09-16'
       ), (
           7,
           4,
           4,
           'Wiadomość testowa 2',
           'READ',
           '2021-09-16',
           '2021-09-16'
       ), (
           8,
           4,
           4,
           'Wiadomość testowa 3',
           'READ',
           '2021-09-16',
           '2021-09-16'
       ), (
           9,
           4,
           4,
           'Wiadomość testowa 4',
           'SENT',
           '2021-09-16',
           null
       ), (
           10,
           4,
           4,
           'Wiadomość testowa 5',
           'SENT',
           '2021-09-16',
           null
       ), (
           11,
           4,
           4,
           'Test 6',
           'SENT',
           '2021-09-16',
           null
       );
insert into "PAYMENT_TYPE" ("PAYMENT_TYPE_ID", "PAYMENT_TYPE")
values (
           1,
           'Za pobraniem gotówką'
       ), (
           2,
           'Za pobraniem kartą'
       ), (
           3,
           'Przelew natychmiastowy'
       );
insert into "PAYMENT" ("PAYMENT_ID", "PAYMENT_TYPE_ID", "PAYMENT_VALUE", "PAYMENT_STATE")
values (
           43,
           3,
           10000.00,
           'COMPLETED'
       ), (
           44,
           3,
           3520.00,
           'REJECTED'
       ), (
           45,
           1,
           3520.00,
           'WAITING FOR PAYMENT'
       ), (
           46,
           1,
           55000.00,
           'IN PROGRESS'
       ), (
           47,
           2,
           20000.00,
           'IN PROGRESS'
       ), (
           48,
           1,
           1600.00,
           'IN PROGRESS'
       ), (
           49,
           2,
           1600.00,
           'IN PROGRESS'
       ), (
           50,
           3,
           1904.00,
           'COMPLETED'
       );
insert into "ADDRESS" (
    "ADDRESS_ID",
    "COUNTRY",
    "TOWN",
    "POSTAL_CODE",
    "BUILDING_NO",
    "STREET",
    "APARTMENT_NO"
)
values (
           1,
           'PL',
           'Kraków',
           '12-123',
           '321',
           'Biznesowa',
           '1'
       ), (
           2,
           'PL',
           'Warszawa',
           '00-000',
           '43',
           'Łączna, za pocztą',
           null
       ), (
           3,
           'PL',
           'Zadupie',
           '34-567',
           '765',
           'Polna',
           null
       ), (
           4,
           'PL',
           'Orły',
           '37-716',
           '1546',
           'Małkowice',
           null
       ), (
           5,
           'PL',
           'Kraków',
           '12-123',
           '123',
           'Przemysłowa',
           null
       ), (
           6,
           'PL',
           'Kraków',
           '12-123',
           '124',
           'Przemysłowa',
           null
       ), (
           7,
           'PL',
           'Kraków',
           '12-123',
           '125',
           'Przemysłowa',
           null
       ), (
           8,
           'PL',
           'Kraków',
           '12-124',
           '123',
           'Wiejska',
           null
       ), (
           9,
           'PL',
           'Limanowa',
           '32-321',
           '12',
           'Polna',
           null
       ), (
           10,
           'PL',
           'Skawina',
           '54-456',
           '55',
           'Prosta',
           null
       ), (
           11,
           'PL',
           'Kraków',
           '45-456',
           '15',
           'Kopernika',
           null
       );
insert into "CUSTOMER" (
    "CUSTOMER_ID",
    "USER_ID",
    "ADDRESS_ID",
    "NAME",
    "SURNAME",
    "FIRM_NAME",
    "TELEPHONE_NO",
    "TAX_ID",
    "DISCOUNT"
)
values (
           1,
           12,
           1,
           'Bartosz',
           'Sawicki',
           'NOKIA',
           '111222333',
           '1122334455',
           0
       ), (
           2,
           14,
           4,
           'Kamil',
           'Bałaban',
           'GR Bałaban',
           '691564987',
           null,
           0
       );
insert into "DELIVERY" (
    "DELIVERY_ID",
    "ADDRESS_ID",
    "SUPPLIER_ID",
    "REMOVAL_FROM_STORAGE_DATE",
    "DELIVERY_DATE"
)
values (
           26,
           11,
           3,
           null,
           null
       ), (
           27,
           4,
           5,
           null,
           null
       ), (
           28,
           4,
           3,
           null,
           null
       ), (
           29,
           4,
           5,
           null,
           null
       ), (
           30,
           4,
           3,
           null,
           null
       ), (
           31,
           4,
           5,
           null,
           null
       ), (
           32,
           1,
           3,
           null,
           null
       );
insert into "ORDER" (
    "ORDER_ID",
    "PAYMENT_ID",
    "CUSTOMER_ID",
    "DELIVERY_ID",
    "COMMENT",
    "ORDER_STATE",
    "ORDER_DATE"
)
values (
           19,
           43,
           2,
           26,
           '',
           'DELIVERED',
           '2021-09-16'
       ), (
           20,
           44,
           2,
           27,
           '',
           'DELIVERED',
           '2021-09-16'
       ), (
           21,
           46,
           2,
           28,
           '',
           'DELIVERED',
           '2021-09-16'
       ), (
           22,
           47,
           2,
           29,
           '',
           'REGISTERED',
           '2021-10-16'
       ), (
           23,
           48,
           2,
           30,
           '',
           'REGISTERED',
           '2021-10-16'
       ), (
           24,
           49,
           2,
           31,
           '',
           'REGISTERED',
           '2021-10-16'
       ), (
           25,
           50,
           1,
           32,
           '',
           'PENDING',
           '2021-10-23'
       );
insert into "STORAGE" (
    "STORAGE_ID",
    "ADDRESS_ID",
    "MANAGER_ID",
    "STORAGE_NAME",
    "CAPACITY",
    "IS_COLD_STORAGE"
)
values (
           2,
           5,
           4,
           'Magazyn A',
           2000,
           0
       ), (
           3,
           6,
           4,
           'Magazyn B',
           3000,
           0
       ), (
           4,
           7,
           4,
           'Magazyn C',
           1000,
           1
       );
insert into "MAKER" (
    "MAKER_ID",
    "ADDRESS_ID",
    "FIRM_NAME",
    "TELEPHONE_NO",
    "E_MAIL"
)
values (
           2,
           8,
           'Producent A',
           '123456789',
           'producent_a@example.com'
       ), (
           3,
           9,
           'Producent B',
           '987654321',
           'producent_b@example.com'
       ), (
           4,
           10,
           'Producent C',
           '654789321',
           'producent_c@example.com'
       );
insert into "PRODUCT" (
    "PRODUCT_ID",
    "MAKER_ID",
    "PRODUCT_NAME",
    "SHORT_DESCRIPTION",
    "LONG_DESCRIPTION",
    "CATEGORY",
    "NEED_COLD_STORAGE",
    "BUY_PRICE",
    "SELL_PRICE",
    "IMAGE"
)
values (
           5,
           2,
           'Ziemniaki',
           'Oferujemy bardzo smaczne ziemniaki.',
           'Odmiana: GALA\r\nDobra do wykorzystania w sałatkach, typ AB.\r\n\r\nNabyte bezpośrednio u producenta ziemniaków. \r\nKraj pochodzenia: Polska.\r\nIlość na europalecie: 1000kg',
           'Warzywa',
           0,
           1200.00,
           1600.00,
           1
       );
insert into "PRODUCT" (
    "PRODUCT_ID",
    "MAKER_ID",
    "PRODUCT_NAME",
    "SHORT_DESCRIPTION",
    "LONG_DESCRIPTION",
    "CATEGORY",
    "NEED_COLD_STORAGE",
    "BUY_PRICE",
    "SELL_PRICE",
    "IMAGE"
)
values (
           6,
           3,
           'Marchewka',
           'Słodka i soczysta marchew, idealna na soki.',
           'Ilość na europalecie: 800kg',
           'Warzywa',
           0,
           1600.00,
           2000.00,
           1
       );
insert into "PRODUCT" (
    "PRODUCT_ID",
    "MAKER_ID",
    "PRODUCT_NAME",
    "SHORT_DESCRIPTION",
    "LONG_DESCRIPTION",
    "CATEGORY",
    "NEED_COLD_STORAGE",
    "BUY_PRICE",
    "SELL_PRICE",
    "IMAGE"
)
values (
           7,
           4,
           'Sok jabłkowy',
           'Tłoczony ze świeżych, ekologicznych owoców.',
           'Wyciśnięty sok z jabłek 100% bez żadnych dodatków.\n\nZachowuje świeżość do 30 dni po otwarciu pod warunkiem, że zawór jest zalany sokiem.\nNa jednej europalecie znajduje się 250.',
           'Napoje',
           0,
           1500.00,
           2500.00,
           1
       );
insert into "PRODUCT" (
    "PRODUCT_ID",
    "MAKER_ID",
    "PRODUCT_NAME",
    "SHORT_DESCRIPTION",
    "LONG_DESCRIPTION",
    "CATEGORY",
    "NEED_COLD_STORAGE",
    "BUY_PRICE",
    "SELL_PRICE",
    "IMAGE"
)
values (
           8,
           2,
           'Crunchips',
           'Chipsy marki Crunchips o smaku ser - cebula',
           'Chipsy ziemniaczane o smaku ser - cebula. \nWaga jednego opakowania 150g.\nIlość opakowań na europalecie: 400',
           'Przekąski',
           0,
           1600.00,
           2000.00,
           1
       );
insert into "PRODUCT_BATCH" (
    "BATCH_ID",
    "PRODUCT_ID",
    "BATCH_NO",
    "EAT_BY_DATE",
    "DISCOUNT",
    "PACKAGES_QUANTITY"
)
values (
           10,
           5,
           111111,
           '2021-11-26',
           null,
           1000
       ), (
           11,
           5,
           111112,
           '2021-11-01',
           null,
           1000
       ), (
           12,
           6,
           222222,
           '2021-12-11',
           null,
           800
       ), (
           13,
           6,
           222223,
           '2021-12-10',
           null,
           800
       ), (
           14,
           8,
           333333,
           '2022-07-30',
           null,
           400
       ), (
           15,
           8,
           333334,
           '2021-11-11',
           null,
           400
       ), (
           16,
           7,
           444444,
           '2021-12-28',
           null,
           250
       ), (
           17,
           7,
           444445,
           '2021-11-17',
           null,
           250
       ), (
           18,
           7,
           444443,
           '2021-10-26',
           null,
           250
       );
insert into "PRODUCT_IN_STORAGE" ("BATCH_ID", "STORAGE_ID", "QUANTITY")
values (
           10,
           2,
           1
       ), (
           18,
           3,
           5
       ), (
           12,
           4,
           15
       ), (
           13,
           4,
           15
       ), (
           11,
           2,
           17
       ), (
           10,
           3,
           20
       ), (
           15,
           2,
           20
       ), (
           16,
           3,
           30
       ), (
           17,
           2,
           45
       ), (
           17,
           4,
           80
       ), (
           14,
           2,
           200
       );
insert into "PRODUCT_ORDER" ("ORDER_ID", "BATCH_ID", "QUANTITY")
values (
           19,
           13,
           1
       ), (
           20,
           11,
           1
       ), (
           23,
           11,
           1
       ), (
           24,
           11,
           1
       ), (
           25,
           11,
           1
       ), (
           19,
           11,
           2
       ), (
           20,
           10,
           2
       ), (
           25,
           10,
           2
       ), (
           19,
           10,
           5
       ), (
           21,
           16,
           10
       ), (
           22,
           14,
           10
       ), (
           21,
           14,
           15
       );
insert into "COMPLAINT" (
    "COMPLAINT_ID",
    "ORDER_ID",
    "CONTENT",
    "SEND_DATE",
    "COMPLAINT_STATE",
    "DECISION",
    "DECISION_DATE"
)
values (
           4,
           20,
           'Realizacja zamówienia trwa bardzo długo. Żądam zwrotu kosztów zamówienia!',
           '2021-09-16',
           'ACCEPTED',
           'Ma pan racje',
           '2021-09-17'
       );

-- -- user
--
-- INSERT INTO `USER` (`USER_ID`, `USERNAME`, `PASSWORD`, `PERMISSION`, `E_MAIL`) VALUES
-- (1, 'Admin','$2a$10$fRMvxz8..l/vL0PNuZzaAehkH.OblwBxvP0QsmrP3ovtvM0uzdao.','Admin','admin@example.com'),
-- (2, 'Admin2','$2a$10$fRMvxz8..l/vL0PNuZzaAehkH.OblwBxvP0QsmrP3ovtvM0uzdao.','Admin','admin2@example.com'),
-- (3, 'Supplier', '$2a$10$CrjbGAfKP0aLp1m/cyF3Yu7QgFhnfSPEUIq4SQtsbjyrYDA7kAOtq', 'Supplier', 'supplier@example.com'),
-- (11, 'Manager', '$2a$10$mc4ibJleoC.aDQonjHB7DewryevBIeaVggCiM4mzie9UZepcHD0WO', 'Manager', 'manager@example.com'),
-- (12, 'Customer', '$2a$10$G9MyJZCKY6qQvDr6p/E27ulXpkp4Uq7hLKlezRLEjW/J0IzP3Rsnq', 'Customer', 'customer@example.com'),
-- (13, 'Supplier2', '$2a$10$tvTp.Il1wH5OE42vSrwWsegsIBsVfb3pv0zHoazjci1G2eIfsaKu6', 'Supplier', 'supplier2@example.com'),
-- (14, 'Customer1', '$2a$10$4Zh07XKhiGrYO0v7NbR5RuwEqb8VsefRVTLFoM7AH0o7kk9EwipVe', 'Customer', 'customer1@example.com');
--
-- -- employee
--
-- INSERT INTO `EMPLOYEE` (`EMPLOYEE_ID`, `USER_ID`, `NAME`, `SURNAME`, `POSITION`, `SALARY`) VALUES
-- (1, 1, 'Jan', 'Kowalski', 'Administrator główny', 15000.00),
-- (2, 2, 'Albert', 'Kanak', 'Administrator', 10000.00),
-- (3, 3, 'Krystian', 'Wójcik', 'Kierowca', 7000.00),
-- (4, 11, 'Tomasz', 'Dudzik', 'Manager', 10000.00),
-- (5, 13, 'Paweł', 'Pabiańczyk', 'Kierowca', 7000.00);
--
-- -- car
--
-- INSERT INTO `CAR` (`CAR_ID`, `DRIVER_ID`, `BRAND`, `MODEL`, `YEAR_OF_PROD`, `REGISTRATION_NO`, `INSURANCE_EXP`, `INSPECTION_EXP`) VALUES
-- (1, 3, 'Fiat', 'Ducato', 2016, 'KRK 12322', '2022-11-11', '2022-02-03'),
-- (2, 5, 'Renault', 'Master', 2017, 'KRK 45687', '2022-09-24', '2022-01-16'),
-- (5, 1, 'Fiat', 'Punto', 1999, 'KRK 12313', '2022-01-22', '2022-01-22');
--
-- -- message
--
-- INSERT INTO `MESSAGE` (`MESSAGE_ID`, `SENDER_ID`, `RECEIVER_ID`, `CONTENT`, `MESSAGE_STATE`, `SEND_DATE`, `READ_DATE`) VALUES
-- (6, 4, 4, 'Wiadomość testowa 1', 'READ', '2021-09-16', '2021-09-16'),
-- (7, 4, 4, 'Wiadomość testowa 2', 'READ', '2021-09-16', '2021-09-16'),
-- (8, 4, 4, 'Wiadomość testowa 3', 'READ', '2021-09-16', '2021-09-16'),
-- (9, 4, 4, 'Wiadomość testowa 4', 'SENT', '2021-09-16', NULL),
-- (10, 4, 4, 'Wiadomość testowa 5', 'SENT', '2021-09-16', NULL),
-- (11, 4, 4, 'Test 6', 'SENT', '2021-09-16', NULL);
--
-- -- payment_type
--
-- INSERT INTO `PAYMENT_TYPE` (`PAYMENT_TYPE_ID`, `PAYMENT_TYPE`) VALUES
-- (1, 'Za pobraniem gotówką'),
-- (2, 'Za pobraniem kartą'),
-- (3, 'Przelew natychmiastowy');
--
-- -- payment
--
-- INSERT INTO `PAYMENT` (`PAYMENT_ID`, `PAYMENT_TYPE_ID`, `PAYMENT_VALUE`, `PAYMENT_STATE`) VALUES
-- (43, 3, 10000.00, 'COMPLETED'),
-- (44, 3, 3520.00, 'REJECTED'),
-- (45, 1, 3520.00, 'WAITING FOR PAYMENT'),
-- (46, 1, 55000.00, 'IN PROGRESS'),
-- (47, 2, 20000.00, 'IN PROGRESS'),
-- (48, 1, 1600.00, 'IN PROGRESS'),
-- (49, 2, 1600.00, 'IN PROGRESS'),
-- (50, 3, 1904.00, 'COMPLETED');
--
-- -- address
--
-- INSERT INTO `ADDRESS` (`ADDRESS_ID`, `COUNTRY`, `TOWN`, `POSTAL_CODE`, `BUILDING_NO`, `STREET`, `APARTMENT_NO`) VALUES
-- (1, 'PL', 'Kraków', '12-123', '321', 'Biznesowa', '1'),
-- (2, 'PL', 'Warszawa', '00-000', '43', 'Łączna, za pocztą', NULL),
-- (3, 'PL', 'Zadupie', '34-567', '765', 'Polna', NULL),
-- (4, 'PL', 'Orły', '37-716', '1546', 'Małkowice', NULL),
-- (5, 'PL', 'Kraków', '12-123', '123', 'Przemysłowa', NULL),
-- (6, 'PL', 'Kraków', '12-123', '124', 'Przemysłowa', NULL),
-- (7, 'PL', 'Kraków', '12-123', '125', 'Przemysłowa', NULL),
-- (8, 'PL', 'Kraków', '12-124', '123', 'Wiejska', NULL),
-- (9, 'PL', 'Limanowa', '32-321', '12', 'Polna', NULL),
-- (10, 'PL', 'Skawina', '54-456', '55', 'Prosta', NULL),
-- (11, 'PL', 'Kraków', '45-456', '15', 'Kopernika', NULL);
--
-- -- customer
--
-- INSERT INTO `CUSTOMER` (`CUSTOMER_ID`, `USER_ID`, `ADDRESS_ID`, `NAME`, `SURNAME`, `FIRM_NAME`, `TELEPHONE_NO`, `TAX_ID`, `DISCOUNT`) VALUES
-- (1, 12, 1, 'Bartosz', 'Sawicki', 'NOKIA', '111222333', '1122334455', 0),
-- (2, 14, 4, 'Kamil', 'Bałaban', 'GR Bałaban', '691564987', NULL, 0);
--
-- -- delivery
--
-- INSERT INTO `DELIVERY` (`DELIVERY_ID`, `ADDRESS_ID`, `SUPPLIER_ID`, `REMOVAL_FROM_STORAGE_DATE`, `DELIVERY_DATE`) VALUES
-- (26, 11, 3, NULL, NULL),
-- (27, 4, 5, NULL, NULL),
-- (28, 4, 3, NULL, NULL),
-- (29, 4, 5, NULL, NULL),
-- (30, 4, 3, NULL, NULL),
-- (31, 4, 5, NULL, NULL),
-- (32, 1, 3, NULL, NULL);
--
-- -- order
--
-- INSERT INTO `ORDER` (`ORDER_ID`, `PAYMENT_ID`, `CUSTOMER_ID`, `DELIVERY_ID`, `COMMENT`, `ORDER_STATE`, `ORDER_DATE`) VALUES
-- (19, 43, 2, 26, '', 'DELIVERED', '2021-09-16'),
-- (20, 44, 2, 27, '', 'DELIVERED', '2021-09-16'),
-- (21, 46, 2, 28, '', 'DELIVERED', '2021-09-16'),
-- (22, 47, 2, 29, '', 'REGISTERED', '2021-10-16'),
-- (23, 48, 2, 30, '', 'REGISTERED', '2021-10-16'),
-- (24, 49, 2, 31, '', 'REGISTERED', '2021-10-16'),
-- (25, 50, 1, 32, '', 'PENDING', '2021-10-23');
--
-- -- storage
--
-- INSERT INTO `STORAGE` (`STORAGE_ID`, `ADDRESS_ID`, `MANAGER_ID`, `STORAGE_NAME`, `CAPACITY`, `IS_COLD_STORAGE`) VALUES
-- (2, 5, 4, 'Magazyn A', 2000, 0),
-- (3, 6, 4, 'Magazyn B', 3000, 0),
-- (4, 7, 4, 'Magazyn C', 1000, 1);
--
-- -- maker
--
-- INSERT INTO `MAKER` (`MAKER_ID`, `ADDRESS_ID`, `FIRM_NAME`, `TELEPHONE_NO`, `E_MAIL`) VALUES
-- (2, 8, 'Producent A', '123456789', 'producent_a@example.com'),
-- (3, 9, 'Producent B', '987654321', 'producent_b@example.com'),
-- (4, 10, 'Producent C', '654789321', 'producent_c@example.com');
--
-- -- product
--
-- INSERT INTO `PRODUCT` (`PRODUCT_ID`, `MAKER_ID`, `PRODUCT_NAME`, `SHORT_DESCRIPTION`, `LONG_DESCRIPTION`, `CATEGORY`, `NEED_COLD_STORAGE`, `BUY_PRICE`, `SELL_PRICE`, `IMAGE`) VALUES
-- (5, 2, 'Ziemniaki', 'Oferujemy bardzo smaczne ziemniaki.', 'Odmiana: GALA\r\nDobra do wykorzystania w sałatkach, typ AB.\r\n\r\nNabyte bezpośrednio u producenta ziemniaków. \r\nKraj pochodzenia: Polska.\r\nIlość na europalecie: 1000kg', 'Warzywa', 0, 1200.00, 1600.00, 1);
-- INSERT INTO `PRODUCT` (`PRODUCT_ID`, `MAKER_ID`, `PRODUCT_NAME`, `SHORT_DESCRIPTION`, `LONG_DESCRIPTION`, `CATEGORY`, `NEED_COLD_STORAGE`, `BUY_PRICE`, `SELL_PRICE`, `IMAGE`) VALUES
-- (6, 3, 'Marchewka', 'Słodka i soczysta marchew, idealna na soki.', 'Ilość na europalecie: 800kg', 'Warzywa', 0, 1600.00, 2000.00, 1);
-- INSERT INTO `PRODUCT` (`PRODUCT_ID`, `MAKER_ID`, `PRODUCT_NAME`, `SHORT_DESCRIPTION`, `LONG_DESCRIPTION`, `CATEGORY`, `NEED_COLD_STORAGE`, `BUY_PRICE`, `SELL_PRICE`, `IMAGE`) VALUES
-- (7, 4, 'Sok jabłkowy', 'Tłoczony ze świeżych, ekologicznych owoców.', 'Wyciśnięty sok z jabłek 100% bez żadnych dodatków.\n\nZachowuje świeżość do 30 dni po otwarciu pod warunkiem, że zawór jest zalany sokiem.\nNa jednej europalecie znajduje się 250.', 'Napoje', 0, 1500.00, 2500.00, 1);
-- INSERT INTO `PRODUCT` (`PRODUCT_ID`, `MAKER_ID`, `PRODUCT_NAME`, `SHORT_DESCRIPTION`, `LONG_DESCRIPTION`, `CATEGORY`, `NEED_COLD_STORAGE`, `BUY_PRICE`, `SELL_PRICE`, `IMAGE`) VALUES
-- (8, 2, 'Crunchips', 'Chipsy marki Crunchips o smaku ser - cebula', 'Chipsy ziemniaczane o smaku ser - cebula. \nWaga jednego opakowania 150g.\nIlość opakowań na europalecie: 400', 'Przekąski', 0, 1600.00, 2000.00, 1);
--
-- -- product_batch
--
-- INSERT INTO `PRODUCT_BATCH` (`BATCH_ID`, `PRODUCT_ID`, `BATCH_NO`, `EAT_BY_DATE`, `DISCOUNT`, `PACKAGES_QUANTITY`) VALUES
-- (10, 5, 111111, '2021-11-26', NULL, 1000),
-- (11, 5, 111112, '2021-11-01', NULL, 1000),
-- (12, 6, 222222, '2021-12-11', NULL, 800),
-- (13, 6, 222223, '2021-12-10', NULL, 800),
-- (14, 8, 333333, '2022-07-30', NULL, 400),
-- (15, 8, 333334, '2021-11-11', NULL, 400),
-- (16, 7, 444444, '2021-12-28', NULL, 250),
-- (17, 7, 444445, '2021-11-17', NULL, 250),
-- (18, 7, 444443, '2021-10-26', NULL, 250);
--
-- -- product_in_storage
--
-- INSERT INTO `PRODUCT_IN_STORAGE` (`BATCH_ID`, `STORAGE_ID`, `QUANTITY`) VALUES
-- (10, 2, 1),
-- (18, 3, 5),
-- (12, 4, 15),
-- (13, 4, 15),
-- (11, 2, 17),
-- (10, 3, 20),
-- (15, 2, 20),
-- (16, 3, 30),
-- (17, 2, 45),
-- (17, 4, 80),
-- (14, 2, 200);
--
-- -- product_order
--
-- INSERT INTO `PRODUCT_ORDER` (`ORDER_ID`, `BATCH_ID`, `QUANTITY`) VALUES
-- (19, 13, 1),
-- (20, 11, 1),
-- (23, 11, 1),
-- (24, 11, 1),
-- (25, 11, 1),
-- (19, 11, 2),
-- (20, 10, 2),
-- (25, 10, 2),
-- (19, 10, 5),
-- (21, 16, 10),
-- (22, 14, 10),
-- (21, 14, 15);
--
-- -- complaint
--
-- INSERT INTO `COMPLAINT` (`COMPLAINT_ID`, `ORDER_ID`, `CONTENT`, `SEND_DATE`, `COMPLAINT_STATE`, `DECISION`, `DECISION_DATE`) VALUES
-- (4, 20, 'Realizacja zamówienia trwa bardzo długo. Żądam zwrotu kosztów zamówienia!', '2021-09-16', 'ACCEPTED', 'Ma pan racje', '2021-09-17');
