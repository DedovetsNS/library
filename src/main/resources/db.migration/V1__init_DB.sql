create table author (
id  bigserial  primary key,
birthday timestamp not null,
name varchar(255) not null
);

create table book (
id  bigserial primary key,
in_stock_quantity int4 not null,
name varchar(255) not null,
publisher varchar(255) not null,
total_quantity int4 not null
);

create table book_author (
book_id int8 not null,
author_id int8 not null
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
quantity int4 not null,
book_id int8 not null,
customer_id int8 not null
);

alter table if exists book_author
add constraint author_book_author_fk
foreign key (author_id) references author;

alter table if exists book_author
add constraint book_book_author_fk
foreign key (book_id) references book;

alter table if exists loan
add constraint loan_book_fk
foreign key (book_id) references book;

alter table if exists loan
add constraint loan_customer_fk
foreign key (customer_id) references customer;

create sequence author_id_seq;
alter sequence author_id_seq owner to postgres;

create sequence book_id_seq;
alter sequence book_id_seq owner to postgres;

create sequence customer_id_seq;
alter sequence customer_id_seq owner to postgres;

create sequence hibernate_sequence;
alter sequence hibernate_sequence owner to postgres;

create sequence loan_id_seq;
alter sequence loan_id_seq owner to postgres;