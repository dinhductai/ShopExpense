-- Tạo database

-- DROP DATABASE IF EXISTS shopexpense;

CREATE DATABASE shopexpense;
USE shopexpense;

-- Tạo bảng user (quản lý thông tin người dùng đăng nhập)
CREATE TABLE user (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE
);

-- Tạo bảng categories (danh mục chi tiêu)
CREATE TABLE categories (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE
);

-- Tạo bảng expenses (chi tiết chi tiêu)
CREATE TABLE expenses (
    id INT AUTO_INCREMENT PRIMARY KEY,
    amount DOUBLE NOT NULL,
    description TEXT,
    expense_date DATE,                           -- ngày thực tế phát sinh chi tiêu
    payment_method VARCHAR(50),                  -- phương thức thanh toán
    location VARCHAR(255),                       -- địa điểm chi tiêu
    note TEXT,                                   -- ghi chú thêm
    category_id INT NOT NULL,                    -- khóa ngoại tới danh mục
    user_id INT,                                 -- người dùng tạo khoản chi này
    created_at DATE,        -- ngày nhập liệu
    FOREIGN KEY (category_id) REFERENCES categories(id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE SET NULL
);

-- Chèn dữ liệu mẫu vào bảng user
INSERT INTO user (username, password, email)
VALUES ('admin', 'admin', 'admin@gmail.com');

-- Chèn dữ liệu mẫu vào bảng categories
INSERT INTO categories (name) VALUES 
('Food'),
('Transport'),
('Entertainment'),
('Utilities');

-- Chèn dữ liệu mẫu vào bảng expenses
INSERT INTO expenses (
    amount, description, expense_date, payment_method, location, note,
    category_id, user_id, created_at
) VALUES
(120.50, 'Lunch with colleagues', '2025-04-01', 'Cash', 'Highlands Coffee', 'Friday treat', 1, 1, '2025-04-01'),
(25.00, 'Grab bike ride', '2025-04-02', 'E-wallet', 'District 1 to District 3', NULL, 2, 1, '2025-04-02'),
(200.00, 'Netflix monthly subscription', '2025-04-01', 'Credit Card', 'Online', 'Auto-renewal', 3, 1, '2025-04-01'),
(750.00, 'Electricity bill', '2025-04-03', 'Bank Transfer', 'EVN HCMC', 'High due to aircon', 4, 1, '2025-04-03'),
(60.00, 'Dinner with family', '2025-04-04', 'Cash', 'Phở Hòa Pasteur', NULL, 1, 1, '2025-04-04'),
(18.00, 'Bus fare to school', '2025-04-05', 'Cash', 'Bus No.19', 'Morning rush hour', 2, 1, '2025-04-05'),
(350.00, 'Concert ticket', '2025-04-06', 'E-wallet', 'Cultural Palace', 'BlackPink concert', 3, 1, '2025-04-06'),
(980.00, 'Water bill', '2025-04-07', 'Bank Transfer', 'Cấp Nước Sài Gòn', NULL, 4, 1, '2025-04-07'),
(45.00, 'Breakfast and coffee', '2025-04-08', 'Cash', 'Cộng Cafe', 'Early meeting', 1, 1, '2025-04-08'),
(32.00, 'Taxi to airport', '2025-04-09', 'Cash', 'Tan Son Nhat Airport', 'Early flight', 2, 1, '2025-04-09');

-- Chèn dữ liệu mẫu vào bảng expenses
INSERT INTO expenses (
    amount, description, expense_date, payment_method, location, note,
    category_id, user_id, created_at
) VALUES
(120.50, 'Lunch with colleagues', '2025-04-01', 'Cash', 'Highlands Coffee', 'Friday treat', 1, 1, '2025-04-01'),
(25.00, 'Grab bike ride', '2025-04-02', 'E-wallet', 'District 1 to District 3', NULL, 2, 1, '2025-04-02'),
(200.00, 'Netflix monthly subscription', '2025-04-01', 'Credit Card', 'Online', 'Auto-renewal', 3, 1, '2025-04-01'),
(750.00, 'Electricity bill', '2025-04-03', 'Bank Transfer', 'EVN HCMC', 'High due to aircon', 4, 1, '2025-04-03'),
(60.00, 'Dinner with family', '2025-04-04', 'Cash', 'Phở Hòa Pasteur', NULL, 1, 1, '2025-04-04'),
(18.00, 'Bus fare to school', '2025-04-05', 'Cash', 'Bus No.19', 'Morning rush hour', 2, 1, '2025-04-05'),
(350.00, 'Concert ticket', '2025-04-06', 'E-wallet', 'Cultural Palace', 'BlackPink concert', 3, 1, '2025-04-06'),
(980.00, 'Water bill', '2025-04-07', 'Bank Transfer', 'Cấp Nước Sài Gòn', NULL, 4, 1, '2025-04-07'),
(45.00, 'Breakfast and coffee', '2025-04-08', 'Cash', 'Cộng Cafe', 'Early meeting', 1, 1, '2025-04-08'),
(32.00, 'Taxi to airport', '2025-04-09', 'Cash', 'Tan Son Nhat Airport', 'Early flight', 2, 1, '2025-04-09');

-- Chèn dữ liệu mẫu vào bảng expenses
INSERT INTO expenses (
    amount, description, expense_date, payment_method, location, note,
    category_id, user_id, created_at
) VALUES
(120.50, 'Lunch with colleagues', '2025-04-01', 'Cash', 'Highlands Coffee', 'Friday treat', 1, 1, '2025-04-01'),
(25.00, 'Grab bike ride', '2025-04-02', 'E-wallet', 'District 1 to District 3', NULL, 2, 1, '2025-04-02'),
(200.00, 'Netflix monthly subscription', '2025-04-01', 'Credit Card', 'Online', 'Auto-renewal', 3, 1, '2025-04-01'),
(750.00, 'Electricity bill', '2025-04-03', 'Bank Transfer', 'EVN HCMC', 'High due to aircon', 4, 1, '2025-04-03'),
(60.00, 'Dinner with family', '2025-04-04', 'Cash', 'Phở Hòa Pasteur', NULL, 1, 1, '2025-04-04'),
(18.00, 'Bus fare to school', '2025-04-05', 'Cash', 'Bus No.19', 'Morning rush hour', 2, 1, '2025-04-05'),
(350.00, 'Concert ticket', '2025-04-06', 'E-wallet', 'Cultural Palace', 'BlackPink concert', 3, 1, '2025-04-06'),
(980.00, 'Water bill', '2025-04-07', 'Bank Transfer', 'Cấp Nước Sài Gòn', NULL, 4, 1, '2025-04-07'),
(45.00, 'Breakfast and coffee', '2025-04-08', 'Cash', 'Cộng Cafe', 'Early meeting', 1, 1, '2025-04-08'),
(32.00, 'Taxi to airport', '2025-04-09', 'Cash', 'Tan Son Nhat Airport', 'Early flight', 2, 1, '2025-04-09');


