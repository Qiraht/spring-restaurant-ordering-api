--liquibase formatted sql
--changeset Qiraht:1
--comment: carts table creation

CREATE TABLE IF NOT EXISTS carts (
    id UUID NOT NULL PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    status VARCHAR(10) NOT NULL CHECK ( status IN ('ACTIVE', 'CHECKED_OUT') ),
    created_at TIMESTAMP(3) NOT NULL DEFAULT now(),
    updated_at TIMESTAMP(3) NOT NULL DEFAULT now()
);