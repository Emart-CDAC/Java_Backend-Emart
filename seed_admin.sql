-- Admin Seed Data
-- Run this to create the default admin user

USE emart;

-- Insert admin user (password is 'admin' encrypted with BCrypt)
INSERT INTO admin (email, password, active) 
VALUES ('admin@emart.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhCa', true);

-- Verify
SELECT * FROM admin;
