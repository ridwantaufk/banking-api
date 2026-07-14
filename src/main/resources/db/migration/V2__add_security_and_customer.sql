-- Customer (dipisah dari account)
CREATE TABLE IF NOT EXISTS customers (
    id BIGSERIAL PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    phone VARCHAR(20),
    created_at TIMESTAMP NOT NULL
);

-- User (untuk login)
CREATE TABLE IF NOT EXISTS users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    customer_id BIGINT,
    enabled BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL,
    CONSTRAINT fk_user_customer FOREIGN KEY (customer_id) REFERENCES customers (id)
);

-- Role
CREATE TABLE IF NOT EXISTS roles (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(50) UNIQUE NOT NULL
);

-- Permission
CREATE TABLE IF NOT EXISTS permissions (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) UNIQUE NOT NULL
);

-- Many-to-Many User-Role
CREATE TABLE IF NOT EXISTS user_roles (
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    CONSTRAINT fk_user_role_user FOREIGN KEY (user_id) REFERENCES users (id),
    CONSTRAINT fk_user_role_role FOREIGN KEY (role_id) REFERENCES roles (id)
);

-- Many-to-Many Role-Permission
CREATE TABLE IF NOT EXISTS role_permissions (
    role_id BIGINT NOT NULL,
    permission_id BIGINT NOT NULL,
    PRIMARY KEY (role_id, permission_id),
    CONSTRAINT fk_role_perm_role FOREIGN KEY (role_id) REFERENCES roles (id),
    CONSTRAINT fk_role_perm_perm FOREIGN KEY (permission_id) REFERENCES permissions (id)
);

-- Tambahkan kolom customer_id ke accounts
ALTER TABLE accounts ADD COLUMN customer_id BIGINT;

ALTER TABLE accounts
ADD CONSTRAINT fk_account_customer FOREIGN KEY (customer_id) REFERENCES customers (id);

-- Insert data default
INSERT INTO
    roles (name)
VALUES ('ROLE_USER'),
    ('ROLE_ADMIN')
ON CONFLICT DO NOTHING;

INSERT INTO
    permissions (name)
VALUES ('ACCOUNT_READ'),
    ('ACCOUNT_WRITE'),
    ('TRANSFER'),
    ('ADMIN_ALL')
ON CONFLICT DO NOTHING;

-- Admin permission to role ADMIN
INSERT INTO
    role_permissions (role_id, permission_id)
SELECT r.id, p.id
FROM roles r, permissions p
WHERE
    r.name = 'ROLE_ADMIN'
    AND p.name = 'ADMIN_ALL'
ON CONFLICT DO NOTHING;