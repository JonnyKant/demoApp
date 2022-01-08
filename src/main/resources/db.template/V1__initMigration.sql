create table user (
id bigint not null,
address varchar(255),
first_name varchar(255),
last_name varchar(255),
primary key (id)) engine=InnoDB
create table hibernate_sequence (next_val bigint) engine=InnoDB
insert into hibernate_sequence values ( 1 )
create table order_product (
orders_of_shop_id bigint not null,
product_id bigint not null) engine=InnoDB
create table orders_of_shop (
id bigint not null,
status bit,
user_id bigint,
primary key (id)) engine=InnoDB
create table product (
id bigint not null, image varchar(255),
manufacturer varchar(255),
name varchar(255),
price varchar(255),
weight float,
primary key (id)) engine=InnoDB
alter table order_product add constraint FKhnfgqyjx3i80qoymrssls3kno foreign key (product_id)
references product (id)
alter table order_product add constraint FK2icro32m8ouqtfyo188vdjvh4 foreign key (orders_of_shop_id)
references orders_of_shop (id)
alter table orders_of_shop add constraint FKe84f2qcvao50j8tjmm5sl2yfe foreign key (user_id)
references user (id)