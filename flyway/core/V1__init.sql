create table categories
(
    id         bigserial primary key,
    title      varchar(255),
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp
);

insert into categories (title)
values ('Процессор'),
       ('Оперативная память');

create table products
(
    id          bigserial primary key,
    title       varchar(255),
    price       int,
    category_id bigint references categories (id),
    image_path  varchar(255),
    created_at  timestamp default current_timestamp,
    updated_at  timestamp default current_timestamp
);

insert into products (title, price, category_id, image_path)
values ('Intel Core i7-12700KF', 35999.81, 1, 'img1.jpg'),
       ('Intel Core i9-11900K', 41399.71, 1, 'img1.jpg'),
       ('AMD Ryzen 9 5900X', 46999.61, 1, 'img1.jpg'),
       ('Dell [370-AEVP] 64 ГБ', 61000.51, 2, 'img2.jpg'),
       ('JRam [JAL1G800D2] 1 ГБ', 750.41, 2, 'img2.jpg'),
       ('Intel Core i3-12100KF', 12600.31, 1, 'img1.jpg'),
       ('Intel Core i5-12600K', 25000.21, 1, 'img1.jpg'),
       ('Kingston [KIL1G800D2] 16 ГБ', 4500.11, 2, 'img2.jpg'),
       ('Samsung [KIL1G800D2] 8 ГБ', 2500.90, 2, 'img2.jpg'),
       ('JRam [JAL1G800D2] 1 ГБ', 750.80, 2, 'img2.jpg'),
       ('Dell [370-AEVP] 64 ГБ', 66000.70, 2, 'img2.jpg'),
       ('JRam [JAL1G800D2] 1 ГБ', 950.50, 2, 'img2.jpg'),
       ('Intel Core i3-12100KF', 17600.40, 1, 'img1.jpg'),
       ('Intel Core i5-12600K', 21000.30, 1, 'img1.jpg'),
       ('Intel Core i7-12700KF', 38999.20, 1, 'img1.jpg'),
       ('Dell [370-AEVP] 64 ГБ', 62000.10, 2, 'img2.jpg');

create table orders
(
    id         bigserial primary key,
    user_id    varchar(255),
    address    varchar(255),
    phone      varchar(255),
    price      integer,
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp
);

create table order_items
(
    id                bigserial primary key,
    order_id          bigint references orders (id),
    product_id        bigint references products (id),
    quantity          integer,
    price_per_product integer,
    price             integer,
    created_at        timestamp default current_timestamp,
    updated_at        timestamp default current_timestamp
);