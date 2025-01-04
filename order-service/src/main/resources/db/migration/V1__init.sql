CREATE TABLE "orders"
(
    id serial PRIMARY KEY,
    order_number varchar(255),
    sku_code varchar(255),
    price decimal(19,2),
    quantity int,
    created_at timestamp(6) with time zone
);