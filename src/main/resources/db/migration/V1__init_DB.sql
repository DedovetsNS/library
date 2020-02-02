create table author (
id  bigserial  primary key,
birthday timestamp not null,
name varchar(255) not null
);

create table book (
id  bigserial primary key,
in_stock_quantity bigint not null,
name varchar(255) not null,
publisher varchar(255) not null,
total_quantity bigint not null
);

create table book_author (
book_id bigint not null,
author_id bigint not null
);

create table customer (
id  bigserial primary key,
addres varchar(255) not null,
email varchar(255) not null,
first_name varchar(255) not null,
last_name varchar(255) not null,
login varchar(255) not null,
phone varchar(255) not null
);

create table loan (
id  bigserial primary key,
date timestamp not null,
quantity bigint not null,
book_id bigint not null,
customer_id bigint not null
);

