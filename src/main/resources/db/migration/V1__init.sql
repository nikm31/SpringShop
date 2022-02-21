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
values ('Intel Core i7-12700KF', 35999, 1, 'img1.jpg'),
       ('Intel Core i9-11900K', 41399, 1, 'img1.jpg'),
       ('AMD Ryzen 9 5900X', 46999, 1, 'img1.jpg'),
       ('Dell [370-AEVP] 64 ГБ', 61000, 2, 'img2.jpg'),
       ('JRam [JAL1G800D2] 1 ГБ', 750, 2, 'img2.jpg'),
       ('Intel Core i3-12100KF', 12600, 1, 'img1.jpg'),
       ('Intel Core i5-12600K', 25000, 1, 'img1.jpg'),
       ('Kingston [KIL1G800D2] 16 ГБ', 4500, 2, 'img2.jpg'),
       ('Samsung [KIL1G800D2] 8 ГБ', 2500, 2, 'img2.jpg'),
       ('JRam [JAL1G800D2] 1 ГБ', 750, 2, 'img2.jpg'),
       ('Dell [370-AEVP] 64 ГБ', 66000, 2, 'img2.jpg'),
       ('JRam [JAL1G800D2] 1 ГБ', 950, 2, 'img2.jpg'),
       ('Intel Core i3-12100KF', 17600, 1, 'img1.jpg'),
       ('Intel Core i5-12600K', 21000, 1, 'img1.jpg'),
       ('Intel Core i7-12700KF', 38999, 1, 'img1.jpg'),
       ('Dell [370-AEVP] 64 ГБ', 62000, 2, 'img2.jpg');

create table product_images
(
    id         bigserial primary key,
    product_id bigint references products (id),
    image_path       varchar(255),
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp
);

insert into product_images (product_id, image_path)
values (1, 'img1.jpg'),
       (2, 'img1.jpg'),
       (3, 'img2.jpg'),
       (1, 'img2.jpg');

create table users
(
    id              bigserial primary key,
    username        varchar(30) not null,
    password        varchar(80) not null,
    email           varchar(50) unique,
    secret_question varchar(255),
    secret_answer   varchar(255),
    created_at      timestamp default current_timestamp,
    updated_at      timestamp default current_timestamp
);

create table roles
(
    id         bigserial primary key,
    name       varchar(50) not null,
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp
);

create table privileges
(
    id         bigserial primary key,
    name       varchar(50) not null,
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp
);

CREATE TABLE users_roles
(
    user_id bigint not null references users (id),
    role_id bigint not null references roles (id),
    primary key (user_id, role_id)
);

CREATE TABLE roles_privileges
(
    role_id      bigint not null references roles (id),
    privilege_id bigint not null references privileges (id),
    primary key (role_id, privilege_id)
);

insert into privileges (name)
values ('DELETE'),
       ('WRITE'),
       ('READ');

insert into roles (name)
values ('ROLE_USER'),
       ('ROLE_ADMIN');

insert into users (username, password, email, secret_question, secret_answer)
values ('user', '$2a$04$Fx/SX9.BAvtPlMyIIqqFx.hLY2Xp8nnhpzvEEVINvVpwIPbA3v/.i', 'bob_johnson@gmail.com', 'машина',
        'моя'),
       ('admin', '$2a$04$Fx/SX9.BAvtPlMyIIqqFx.hLY2Xp8nnhpzvEEVINvVpwIPbA3v/.i', 'john_johnson@gmail.com', 'машина',
        'моя');

insert into users_roles (user_id, role_id)
values (1, 1),
       (2, 2);

insert into roles_privileges (role_id, privilege_id)
values (1, 3),
       (2, 1);

CREATE TABLE file_info_metadata
(
    id         bigserial primary key,
    hash       uuid,
    filename   varchar(255),
    sub_type   bigserial,
    user_id    bigserial references users (id),
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp
);

CREATE TABLE user_files
(
    user_id bigint not null references users (id),
    file_id bigint not null references file_info_metadata (id),
    primary key (user_id, file_id)
);

create table orders
(
    id         bigserial primary key,
    user_id    bigint references users (id),
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
