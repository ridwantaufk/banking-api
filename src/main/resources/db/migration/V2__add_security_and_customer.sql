-- ======================================================
-- V2__add_security_and_customer.sql (FIXED)
-- ======================================================

-- 1. CREATE TABLE customers
CREATE TABLE IF NOT EXISTS customers (
    id BIGSERIAL PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    phone VARCHAR(20),
    created_at TIMESTAMP NOT NULL
);

-- 2. CREATE TABLE users
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

-- 3. CREATE TABLE roles
CREATE TABLE IF NOT EXISTS roles (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(50) UNIQUE NOT NULL
);

-- 4. CREATE TABLE permissions
CREATE TABLE IF NOT EXISTS permissions (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) UNIQUE NOT NULL
);

-- 5. CREATE TABLE user_roles
CREATE TABLE IF NOT EXISTS user_roles (
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    CONSTRAINT fk_user_role_user FOREIGN KEY (user_id) REFERENCES users (id),
    CONSTRAINT fk_user_role_role FOREIGN KEY (role_id) REFERENCES roles (id)
);

-- 6. CREATE TABLE role_permissions
CREATE TABLE IF NOT EXISTS role_permissions (
    role_id BIGINT NOT NULL,
    permission_id BIGINT NOT NULL,
    PRIMARY KEY (role_id, permission_id),
    CONSTRAINT fk_role_perm_role FOREIGN KEY (role_id) REFERENCES roles (id),
    CONSTRAINT fk_role_perm_perm FOREIGN KEY (permission_id) REFERENCES permissions (id)
);

-- 7. Tambah kolom customer_id ke accounts (pakai DO block untuk cek)
DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM information_schema.columns 
                   WHERE table_schema = 'public' 
                   AND table_name = 'accounts' 
                   AND column_name = 'customer_id') THEN
        ALTER TABLE accounts ADD COLUMN customer_id BIGINT;
    END IF;
END $$;

-- 8. Tambah constraint (pakai DO block)
DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM information_schema.table_constraints 
                   WHERE table_schema = 'public' 
                   AND table_name = 'accounts' 
                   AND constraint_name = 'fk_account_customer') THEN
        ALTER TABLE accounts ADD CONSTRAINT fk_account_customer 
            FOREIGN KEY (customer_id) REFERENCES customers(id);
    END IF;
END $$;

-- 9. Insert default roles
INSERT INTO
    roles (name)
VALUES ('ROLE_USER'),
    ('ROLE_ADMIN')
ON CONFLICT (name) DO NOTHING;

-- 10. Insert default permissions
INSERT INTO
    permissions (name)
VALUES ('ACCOUNT_READ'),
    ('ACCOUNT_WRITE'),
    ('TRANSFER'),
    ('ADMIN_ALL')
ON CONFLICT (name) DO NOTHING;

-- 11. Assign ADMIN_ALL permission to ROLE_ADMIN
INSERT INTO
    role_permissions (role_id, permission_id)
SELECT r.id, p.id
FROM roles r, permissions p
WHERE
    r.name = 'ROLE_ADMIN'
    AND p.name = 'ADMIN_ALL'
ON CONFLICT (role_id, permission_id) DO NOTHING;