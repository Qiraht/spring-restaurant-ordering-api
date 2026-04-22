--liquibase formatted sql
--changeset Qiraht:1
--comment: order_items table creation

CREATE TABLE IF NOT EXISTS order_items (
    id UUID NOT NULL PRIMARY KEY DEFAULT gen_random_uuid(),
    order_id UUID NOT NULL REFERENCES orders(id) ON DELETE CASCADE,
    menu_id UUID NOT NULL REFERENCES menus(id) ON DELETE CASCADE,
    quantity INTEGER NOT NULL,
    price_at_sale NUMERIC(15,2) NOT NULL,
    created_at TIMESTAMP(3) NOT NULL DEFAULT now(),
    updated_at TIMESTAMP(3) NOT NULL DEFAULT now()
);