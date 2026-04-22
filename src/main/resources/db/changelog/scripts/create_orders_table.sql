--liquibase formatted sql
--changeset Qiraht:1
--comment: orders table creation

CREATE TABLE IF NOT EXISTS orders (
    id UUID NOT NULL PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    status VARCHAR(10) NOT NULL CHECK ( status IN ('ONGOING', 'COMPLETED') ),
    total_amount NUMERIC(15,2) NOT NULL,
    created_at TIMESTAMP(3) NOT NULL DEFAULT now(),
    updated_at TIMESTAMP(3) NOT NULL DEFAULT now()
);