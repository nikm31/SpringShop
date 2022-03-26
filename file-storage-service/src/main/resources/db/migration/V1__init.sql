create table product_images
(
    id         bigserial primary key,
    product_id varchar(255),
    image_path varchar(255),
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp
);

insert into product_images (product_id, image_path)
values (1, 'img1.jpg'),
       (2, 'img1.jpg'),
       (3, 'img2.jpg'),
       (1, 'img2.jpg');

CREATE TABLE file_info_metadata
(
    id         bigserial primary key,
    hash       uuid,
    filename   varchar(255),
    sub_type   bigserial,
    user_id    varchar(255),
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp
);

CREATE TABLE user_files
(
    user_id varchar(255),
    file_id bigint not null references file_info_metadata (id)
);
