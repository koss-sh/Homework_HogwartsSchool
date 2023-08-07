-- Описание структуры: у каждого человека есть машина.
-- Причем несколько человек могут пользоваться одной машиной.
-- У каждого человека есть имя, возраст и признак того,что у него есть права (или их нет).
-- У каждой машины есть марка, модель и стоимость.

create table cars (
    id bigserial primary key ,
    brand varchar(20) not null ,
    model varchar(20) not null ,
    price int check ( price > 0 ) not null
);

create table drivers (
    id bigserial primary key ,
    name varchar(50) not null ,
    age int check ( age >= 18 ) not null ,
    has_license boolean,
    car_id bigint references cars(id)
)