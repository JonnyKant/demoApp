create table order_product (orders_of_shop_id bigint not null, product_id bigint not null) engine=InnoDB
create table orders_of_shop (id bigint not null, status bit, description varchar(255), payment_description varchar(255), payment_status bit, user_id bigint, primary key (id)) engine=InnoDB
create table product (id bigint not null, description varchar(255), image varchar(255), name varchar(255), price integer, primary key (id)) engine=InnoDB
create table user (id bigint not null, first_name varchar(255), last_name varchar(255), active bit not null, email varchar(255) not null, password varchar(255) not null, primary key (id)) engine=InnoDB
create table user_role (user_id bigint not null, roles varchar(255)) engine=InnoDB