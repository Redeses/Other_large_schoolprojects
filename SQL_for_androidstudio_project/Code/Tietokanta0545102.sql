PRAGMA foreign_keys=OFF;
BEGIN TRANSACTION;
CREATE TABLE Bank 
("BankID" varchar(25) PRIMARY KEY NOT NULL,
"address" varchar(30),
"Name" varchar(25));
INSERT INTO Bank VALUES('B1G 84NK','jokinkatu 16','BigBank');
CREATE TABLE customer
("customerID" varchar(25) PRIMARY KEY NOT NULL,
"firstName" varchar(25),
"lastName" varchar(25),
"address" varchar(25),
"email" varchar(25),
"country" varchar(20),
"phoneNumber" integer(15),
"age" integer(3),
"BankID" varchar(25),
CHECK (age>=18),
FOREIGN KEY ("BankID") REFERENCES Bank("BankID")
ON DELETE CASCADE);
INSERT INTO customer VALUES('15BH','Matti','Meikalainen','Jokinkatu 16','meikalainen14@gmail.com','Suomi',123456922,24,'B1G 84NK');
INSERT INTO customer VALUES('1SS8','Joonas','Jokimaki','Helluntai Tie 12','Jojo12@gmail.com','Suomi',98754123,21,'B1G 84NK');
INSERT INTO customer VALUES('15CS','Leena','Leinikainen','Helluntai Tie 16','Leni@gmail.com','Suomi',11111102,45,'B1G 84NK');
CREATE TABLE account
("accountID" varchar(25) PRIMARY KEY NOT NULL,
"customerid" varchar(25),
"currentBalance" float(20), 
"savingsAccount" bool, 
"frozen" bool DEFAULT "0",
FOREIGN KEY ("customerid") REFERENCES customer("customerID")
ON DELETE CASCADE);
INSERT INTO account VALUES('50TTS9','15BH',3000.0,0,0);
INSERT INTO account VALUES('14S70','1SS8',12999.999999999999999,1,0);
INSERT INTO account VALUES('14S77','1SS8',240.0,0,0);
INSERT INTO account VALUES('15S20','15CS',405.00000000000000001,0,0);
CREATE TABLE admin
("workerID" varchar(25) PRIMARY KEY NOT NULL,
"name" varchar(25),
"BankID" varchar(25),
FOREIGN KEY ("BankID") REFERENCES Bank("BankID"));
INSERT INTO admin VALUES('16S','Johanna Uskola','B1G 84NK');
CREATE TABLE accountAdmin
("accountid" varchar(25),
"workerid" varchar(25),
FOREIGN KEY("accountid")
REFERENCES account("accountID"),
FOREIGN KEY("workerid")
REFERENCES admin("workerID")
);
INSERT INTO accountAdmin VALUES('50TTS9','16S');
INSERT INTO accountAdmin VALUES('14S77','16S');
INSERT INTO accountAdmin VALUES('14S70','16S');
INSERT INTO accountAdmin VALUES('50TTS9','16S');
INSERT INTO accountAdmin VALUES('14S77','16S');
INSERT INTO accountAdmin VALUES('14S70','16S');
CREATE TABLE bankCard
("cardNumber" varchar(16) PRIMARY KEY NOT NULL,
"pinCode" varchar(4),
"verificationNumber" varchar(3),
"type" varchar(12),
"onlineLimit" INTEGER,
"cashLimit"INTEGER,
"cardLimit" INTEGER,
"credit" INTEGER,
"accountid" varchar(25),
FOREIGN KEY ("accountid") REFERENCES account("accountID")
);
INSERT INTO bankCard VALUES('5555 1234 2233 1560','1234','000','debit&credit',200,300,400,500,'50TTS9');
INSERT INTO bankCard VALUES('5555 4444 3320 5602','1111','141','debit',150,300,200,0,'14S77');
INSERT INTO bankCard VALUES('5555 1212 3340 5603','1234','000','debit&credit',500,600,550,500,'14S77');
CREATE TABLE AccountEvent
("EventID" INTEGER PRIMARY KEY NOT NULL,
"Date" varchar(25), 
"accountid" varchar(25),
"toFromWhere" varchar(25),
FOREIGN KEY ("accountid") REFERENCES account("accountID"));
CREATE TABLE logIn
("user" varchar(25) PRIMARY KEY NOT NULL,
"password" varchar(30),
"ID" varchar(25),
"admin" bool default 0
);
INSERT INTO logIn VALUES('Meika15','salasana123','15BH',0);
INSERT INTO logIn VALUES('Admin123','admin123','16S',1);
INSERT INTO logIn VALUES('HeiHei','Hei123','1SS8',0);
INSERT INTO logIn VALUES('Lene','Lene123','15CS',0);
COMMIT;
