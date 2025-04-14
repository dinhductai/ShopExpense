-- Tạo database
CREATE DATABASE shopexpense;
USE shopexpense;

-- Drop database shopexpense;

-- Tạo bảng managers (lưu thông tin đăng nhập của manager)
CREATE TABLE user (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);

-- Tạo bảng categories (lưu danh mục chi tiêu)
CREATE TABLE categories (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE
);

-- Tạo bảng expenses (lưu giao dịch chi tiêu)
CREATE TABLE expenses (
    id INT AUTO_INCREMENT PRIMARY KEY,
    amount DECIMAL(10, 2) NOT NULL,
    description TEXT,
    category_id INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (category_id) REFERENCES categories(id) ON DELETE CASCADE
);

-- Chèn dữ liệu mẫu
-- Dữ liệu cho bảng managers
INSERT INTO user (username, password) VALUES ('admin', 'password123');

-- Dữ liệu cho bảng categories
INSERT INTO categories (name) VALUES ('Food');
INSERT INTO categories (name) VALUES ('Transport');
INSERT INTO categories (name) VALUES ('Entertainment');
INSERT INTO categories (name) VALUES ('Utilities');

-- Dữ liệu cho bảng expenses
INSERT INTO expenses (amount, description, category_id) VALUES (50.00, 'Lunch at restaurant', 1);
INSERT INTO expenses (amount, description, category_id) VALUES (15.00, 'Bus fare', 2);
INSERT INTO expenses (amount, description, category_id) VALUES (30.00, 'Movie tickets', 3);
INSERT INTO expenses (amount, description, category_id) VALUES (100.00, 'Electricity bill', 4);