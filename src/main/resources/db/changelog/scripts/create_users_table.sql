--liquibase formatted sql
--changeset Qiraht:1
--comment: users table creation

CREATE TABLE IF NOT EXISTS users (
    id UUID NOT NULL PRIMARY KEY DEFAULT gen_random_uuid(),
    email VARCHAR(50) NOT NULL UNIQUE,
    first_name VARCHAR(16) NOT NULL,
    last_name VARCHAR(16) NOT NULL,
    password TEXT NOT NULL,
    role VARCHAR(10) NOT NULL CHECK (role IN ('ADMIN', 'USER')),
    created_at TIMESTAMP(3) NOT NULL DEFAULT now(),
    updated_at TIMESTAMP(3) NOT NULL DEFAULT now(),
    deleted_at TIMESTAMP(3) NULL
);