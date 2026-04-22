--liquibase formatted sql
--changeset Qiraht:1
--comment: menus table creation

CREATE TABLE IF NOT EXISTS menus (
    id UUID NOT NULL PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(50) NOT NULL,
    description TEXT,
    price NUMERIC(15,2) NOT NULL,
    stock INTEGER NOT NULL,
    created_at TIMESTAMP(3) NOT NULL DEFAULT now(),
    updated_at TIMESTAMP(3) NOT NULL DEFAULT now(),
    deleted_at TIMESTAMP(3) NULL
);