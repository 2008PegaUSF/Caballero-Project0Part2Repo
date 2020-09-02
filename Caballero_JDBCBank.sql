create table "BankInformation" (
"UserID" serial,
"UserName" varchar not null,
"UserPassword" varchar not null,
"LastName" varchar not null,
"FirstName" varchar not null,
"Account1" numeric(8,2),
"Account2" numeric(8,2)
);

create table "BankAdmin" (
"UserID" serial,
"Username" varchar not null,
"UserPassword" varchar not null,
"LastName" varchar not null,
"FirstName" varchar not null);
alter table "BankAdmin" add unique ("Username");
alter table "BankAdmin" not null ("UserID");
GRANT ALL("UserID") ON public."BankAdmin" TO bankaccess1;

alter table "BankInformation" add primary key ("UserID"); -- makes UserID primary key with a serial value so the primary keys will be unique
alter table "BankInformation" add unique ("UserName"); -- makes username only take in unique usernames




--select * from "BankInformation" where "UserName"='firstCustomer';

--insert into "BankInformation" ("UserTypeID", "UserName", "UserPassword", "LastName", "FirstName", "Account1") values(1,'secondCustomer','password','Customer','Second',1445);
--insert into "BankInformation" ("UserTypeID", "UserName", "UserPassword", "LastName", "FirstName", "Account1") values(1,'thirdCustomer','password','Customer','Third',1445);


--update "BankInformation" set "Account1"='1500' where "UserName" = 'secondCustomer'; -- update for deposit and/or withdraw

--update "BankInformation" set "Account1"=null where "UserName" = 'thirdCustomer';

--delete from "BankInformation" where "UserName" = 'admin';