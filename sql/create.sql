CREATE TABLE public.products (
    id bigserial NOT NULL,
    "name" varchar(255) NOT NULL,
    sku varchar(255) NULL,
    price numeric(38, 2) NOT NULL,
    status varchar(255) NOT NULL,
    created_at timestamp NOT NULL,
    updated_at timestamp NOT NULL,
    CONSTRAINT products_pkey PRIMARY KEY (id),
    CONSTRAINT products_sku_key UNIQUE (sku)
);

CREATE TABLE public.inventory (
    id bigserial NOT NULL,
    product_id int8 NOT NULL,
    available_quantity int4 NOT NULL,
    updated_at timestamp NOT NULL,
    CONSTRAINT inventory_pkey PRIMARY KEY (id),
    CONSTRAINT inventory_product_id_key UNIQUE (product_id)
);

ALTER TABLE public.inventory
    ADD CONSTRAINT fk_inventory_product
    FOREIGN KEY (product_id) REFERENCES public.products(id);
