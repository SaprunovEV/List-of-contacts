CREATE SCHEMA IF NOT EXISTS contact_schema;

CREATE TABLE IF NOT EXISTS contact_schema.contact (
    id BIGINT primary key,
    first_name VARCHAR(30),
    last_name VARCHAR(30),
    email VARCHAR(30),
    phone VARCHAR(30)
    );