CREATE TABLE "inventory"
(
    id serial PRIMARY KEY,
    sku_code varchar(255),
    quantity int,
    created_at timestamp(6) with time zone
);