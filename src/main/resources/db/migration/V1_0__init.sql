CREATE TABLE xero_tokens(
    id BIGSERIAL PRIMARY KEY,
    refresh_token VARCHAR NOT NULL,
    expiry BIGINT NOT NULL,
    tenant_id VARCHAR NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);

CREATE TABLE invoices(
    id BIGSERIAL PRIMARY KEY,
    external_id VARCHAR NOT NULL,
    type VARCHAR NOT NULL,
    paid BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE accounting_contacts(
    id BIGSERIAL PRIMARY KEY,
    external_id VARCHAR NOT NULL,
    email VARCHAR NOT NULL UNIQUE
);