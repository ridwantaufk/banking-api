-- V1__create_accounts_and_transactions.sql

CREATE TABLE IF NOT EXISTS accounts (
    id BIGSERIAL PRIMARY KEY,
    account_number VARCHAR(20) UNIQUE NOT NULL,
    account_holder_name VARCHAR(255) NOT NULL,
    balance DECIMAL(19, 2) NOT NULL,
    created_at TIMESTAMP NOT NULL
);

CREATE TABLE IF NOT EXISTS transactions (
    id BIGSERIAL PRIMARY KEY,
    source_account_id BIGINT NOT NULL,
    destination_account_id BIGINT NOT NULL,
    amount DECIMAL(19, 2) NOT NULL,
    transfer_date TIMESTAMP NOT NULL,
    CONSTRAINT fk_source_account FOREIGN KEY (source_account_id) REFERENCES accounts (id),
    CONSTRAINT fk_dest_account FOREIGN KEY (destination_account_id) REFERENCES accounts (id)
);