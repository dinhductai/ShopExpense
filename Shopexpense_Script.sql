-- Tạo database

DROP DATABASE IF EXISTS shopexpense;

CREATE DATABASE shopexpense;
USE shopexpense;

-- Tạo bảng user (quản lý thông tin người dùng đăng nhập)
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL, 
    role VARCHAR(20) NOT NULL DEFAULT 'EMPLOYEE', 
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE products (
    id INT AUTO_INCREMENT PRIMARY KEY,
    product_code VARCHAR(20) NOT NULL UNIQUE,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    price DECIMAL(10, 2) NOT NULL,
    stock_quantity INT NOT NULL DEFAULT 0,
    specifications TEXT, -- Thông số kỹ thuật (RAM, dung lượng, v.v.)
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE customers (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    address VARCHAR(255),
    phone VARCHAR(20) NOT NULL UNIQUE,
    email VARCHAR(100) UNIQUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE discounts (
    id INT AUTO_INCREMENT PRIMARY KEY,
    code VARCHAR(20) NOT NULL UNIQUE,
    discount_value DECIMAL(10, 2) NOT NULL, -- Giá trị giảm (số tiền hoặc %)
    discount_type VARCHAR(20) NOT NULL, -- PERCENTAGE hoặc FIXED_AMOUNT
    start_date DATETIME NOT NULL,
    end_date DATETIME NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE', -- ACTIVE hoặc EXPIRED
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE orders (
    id INT AUTO_INCREMENT PRIMARY KEY,
    order_code VARCHAR(20) NOT NULL UNIQUE,
    customer_id INT NOT NULL,
    user_id INT NOT NULL, -- Nhân viên tạo đơn hàng
    discount_id INT, -- Mã giảm giá áp dụng (nếu có)
    total_amount DECIMAL(10, 2) NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'PENDING', -- PENDING, CONFIRMED, SHIPPED, DELIVERED, CANCELLED
    order_date DATE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (customer_id) REFERENCES customers(id),
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (discount_id) REFERENCES discounts(id)
);

CREATE TABLE order_details (
    id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT NOT NULL,
    product_id INT NOT NULL,
    quantity INT NOT NULL,
    unit_price DECIMAL(10, 2) NOT NULL, -- Giá tại thời điểm mua
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (order_id) REFERENCES orders(id),
    FOREIGN KEY (product_id) REFERENCES products(id)
);


-- Chèn 20 bản ghi mới vào bảng users
INSERT INTO users (username, password, role) VALUES
('admin', '21232f297a57a5a743894a0e4a801fc3', 'ADMIN'),
('admin2', 'c81e728d9d4c2f636f067f89cc14862c', 'ADMIN'),
('employee1', '5d4e8733e426e42fd0f3fd7f93d29ddd', 'EMPLOYEE'),
('employee2', '6d2b12a4a350e3b2e0e09d7c1aa4a4a9', 'EMPLOYEE'),
('employee3', '2db8d136f9cd24ebaf37ae4c7fc33e2c', 'EMPLOYEE'),
('employee4', 'e6d974561c574da88e8b4e53c6b4ae60', 'EMPLOYEE'),
('employee5', 'c6b5c86f0d7caea73b5f76fb8e4b601b', 'EMPLOYEE'),
('employee6', '33e4a8c2d7b1f4e5f7b6e8c7e5a4b3c2', 'EMPLOYEE'),
('employee7', 'f5e6d7c8e9f0a1b2c3d4e5f6a7b8c9d0', 'EMPLOYEE'),
('employee8', 'a1b2c3d4e5f6a7b8c9d0e1f2a3b4c5d6', 'EMPLOYEE'),
('employee9', 'b2c3d4e5f6a7b8c9d0e1f2a3b4c5d6e7', 'EMPLOYEE'),
('employee10', 'c3d4e5f6a7b8c9d0e1f2a3b4c5d6e7f8', 'EMPLOYEE'),
('employee11', 'd4e5f6a7b8c9d0e1f2a3b4c5d6e7f8a9', 'EMPLOYEE'),
('employee12', 'e5f6a7b8c9d0e1f2a3b4c5d6e7f8a9b0', 'EMPLOYEE'),
('employee13', 'f6a7b8c9d0e1f2a3b4c5d6e7f8a9b0c1', 'EMPLOYEE'),
('employee14', 'a7b8c9d0e1f2a3b4c5d6e7f8a9b0c1d2', 'EMPLOYEE'),
('employee15', 'c4ca4238a0b923820dcc509a6f75849b', 'EMPLOYEE'),
('employee16', 'eccbc87e4b5ce2fe28308fd9f2a7baf3', 'EMPLOYEE'),
('employee17', 'a87ff679a2f3e71d9181a67b7542122c', 'EMPLOYEE'),
('employee18', 'e4da3b7fbbce2345d7772b0674a318d5', 'EMPLOYEE');

-- Chèn 20 bản ghi mới vào bảng products
INSERT INTO products (product_code, name, description, price, stock_quantity, specifications) VALUES
('IPHONE15', 'iPhone 15', 'iPhone 15 128GB, Camera 48MP', 899.00, 50, 'RAM: 6GB, Storage: 128GB'),
('SAMSUNGZ5', 'Samsung Galaxy Z Fold 6', 'Samsung Z Fold 6 256GB, Foldable', 1699.00, 20, 'RAM: 12GB, Storage: 256GB'),
('XIAOMI14', 'Xiaomi 14', 'Xiaomi 14 256GB, 5G', 699.00, 30, 'RAM: 8GB, Storage: 256GB'),
('OPPOA81', 'Oppo A81', 'Oppo A81 128GB, Camera 50MP', 349.00, 40, 'RAM: 6GB, Storage: 128GB'),
('VIVOY38', 'Vivo Y38', 'Vivo Y38 128GB, 5G', 379.00, 35, 'RAM: 6GB, Storage: 128GB'),
('NOKIAX33', 'Nokia X33', 'Nokia X33 256GB, Camera 50MP', 529.00, 25, 'RAM: 8GB, Storage: 256GB'),
('HUAWEIP63', 'Huawei P63', 'Huawei P63 512GB, Camera 48MP', 749.00, 20, 'RAM: 8GB, Storage: 512GB'),
('SONYXPERIA3', 'Sony Xperia 3', 'Sony Xperia 3 256GB, Camera 12MP', 919.00, 15, 'RAM: 12GB, Storage: 256GB'),
('GOOGLEPIXEL10', 'Google Pixel 10', 'Google Pixel 10 128GB, Camera 50MP', 719.00, 30, 'RAM: 8GB, Storage: 128GB'),
('ASUSROG8', 'Asus ROG Phone 8', 'Asus ROG Phone 8 512GB, Gaming', 1129.00, 10, 'RAM: 16GB, Storage: 512GB'),
('REALME13', 'Realme 13', 'Realme 13 256GB, Camera 108MP', 419.00, 50, 'RAM: 8GB, Storage: 256GB'),
('MOTOG56', 'Moto G56', 'Moto G56 128GB, 5G', 299.00, 60, 'RAM: 4GB, Storage: 128GB'),
('ONEPLUS13', 'OnePlus 13', 'OnePlus 13 256GB, Camera 50MP', 819.00, 25, 'RAM: 12GB, Storage: 256GB'),
('LENOVOK16', 'Lenovo K16', 'Lenovo K16 128GB, Camera 48MP', 369.00, 40, 'RAM: 6GB, Storage: 128GB'),
('TCL42SE', 'TCL 42 SE', 'TCL 42 SE 128GB, Camera 50MP', 219.00, 70, 'RAM: 4GB, Storage: 128GB'),
('ZTEAXON52', 'ZTE Axon 52', 'ZTE Axon 52 256GB, Camera 64MP', 669.00, 20, 'RAM: 12GB, Storage: 256GB'),
('ALCATEL5X', 'Alcatel 5X', 'Alcatel 5X 128GB, Camera 48MP', 249.00, 55, 'RAM: 4GB, Storage: 128GB'),
('INFINIXNOTE32', 'Infinix Note 32', 'Infinix Note 32 256GB, Camera 64MP', 319.00, 45, 'RAM: 8GB, Storage: 256GB'),
('TECNO22', 'Tecno 22', 'Tecno 22 128GB, Camera 50MP', 289.00, 50, 'RAM: 6GB, Storage: 128GB'),
('BLACKVIEWA102', 'Blackview A102', 'Blackview A102 128GB, Camera 48MP', 209.00, 60, 'RAM: 4GB, Storage: 128GB');

-- Chèn 20 bản ghi mới vào bảng customers
INSERT INTO customers (name, address, phone, email) VALUES
('Nguyen Van A', '123 Tran Phu, Hanoi', '0901234561', 'nguyenvana1@gmail.com'),
('Tran Thi B', '456 Le Loi, Hanoi', '0909876541', 'tranthib1@gmail.com'),
('Le Van C', '789 Nguyen Trai, Hanoi', '0912345671', 'levanc1@gmail.com'),
('Pham Thi D', '321 Hoang Dieu, Hanoi', '0923456781', 'phamthid1@gmail.com'),
('Hoang Van E', '654 Ba Trieu, Hanoi', '0934567891', 'hoangvane1@gmail.com'),
('Nguyen Thi F', '987 Hai Ba Trung, Hanoi', '0945678901', 'nguyenthif1@gmail.com'),
('Tran Van G', '123 Ly Thuong Kiet, Hanoi', '0956789011', 'tranvang1@gmail.com'),
('Vu Thi H', '456 Le Duan, Hanoi', '0967890121', 'vuthih1@gmail.com'),
('Bui Van I', '789 Tran Hung Dao, Hanoi', '0978901231', 'buivani1@gmail.com'),
('Dang Thi K', '321 Ton Duc Thang, Hanoi', '0989012341', 'dangthik1@gmail.com'),
('Do Van L', '654 Nguyen Hue, Hanoi', '0990123451', 'dovanl1@gmail.com'),
('Ngo Thi M', '987 Pham Van Dong, Hanoi', '0901234562', 'ngothim1@gmail.com'),
('Ly Van N', '123 Vo Van Tan, Hanoi', '0912345672', 'lyvann1@gmail.com'),
('Duong Thi O', '456 Nguyen Van Cu, Hanoi', '0923456782', 'duongthio1@gmail.com'),
('Trinh Van P', '789 Hoang Quoc Viet, Hanoi', '0934567892', 'trinhvanp1@gmail.com'),
('Phan Thi Q', '321 Lac Long Quan, Hanoi', '0945678902', 'phanthiq1@gmail.com'),
('Nguyen Van R', '654 Kim Ma, Hanoi', '0956789012', 'nguyenvanr1@gmail.com'),
('Le Thi S', '987 Lang Ha, Hanoi', '0967890122', 'lethis1@gmail.com'),
('Hoang Van T', '123 Thai Ha, Hanoi', '0978901232', 'hoangvant1@gmail.com'),
('Pham Thi U', '456 Trung Kinh, Hanoi', '0989012342', 'phamthiu1@gmail.com');

-- Chèn 20 bản ghi mới vào bảng discounts
INSERT INTO discounts (code, discount_value, discount_type, start_date, end_date, status) VALUES
('SALE5', 5.00, 'PERCENTAGE', '2025-06-01 00:00:00', '2025-06-30 23:59:59', 'ACTIVE'),
('FIXED25', 25.00, 'FIXED_AMOUNT', '2025-06-01 00:00:00', '2025-06-30 23:59:59', 'ACTIVE'),
('SUMMER10', 10.00, 'PERCENTAGE', '2025-07-01 00:00:00', '2025-07-31 23:59:59', 'ACTIVE'),
('SAVE50', 50.00, 'FIXED_AMOUNT', '2025-07-01 00:00:00', '2025-07-31 23:59:59', 'ACTIVE'),
('FALL15', 15.00, 'PERCENTAGE', '2025-09-01 00:00:00', '2025-09-30 23:59:59', 'ACTIVE'),
('DEAL100', 100.00, 'FIXED_AMOUNT', '2025-09-01 00:00:00', '2025-09-30 23:59:59', 'ACTIVE'),
('WINTER20', 20.00, 'PERCENTAGE', '2025-12-01 00:00:00', '2025-12-31 23:59:59', 'ACTIVE'),
('SAVE150', 150.00, 'FIXED_AMOUNT', '2025-12-01 00:00:00', '2025-12-31 23:59:59', 'ACTIVE'),
('SPRING25', 25.00, 'PERCENTAGE', '2026-03-01 00:00:00', '2026-03-31 23:59:59', 'ACTIVE'),
('CUT200', 200.00, 'FIXED_AMOUNT', '2026-03-01 00:00:00', '2026-03-31 23:59:59', 'ACTIVE'),
('PROMO30', 30.00, 'PERCENTAGE', '2026-06-01 00:00:00', '2026-06-30 23:59:59', 'ACTIVE'),
('SAVE75', 75.00, 'FIXED_AMOUNT', '2026-06-01 00:00:00', '2026-06-30 23:59:59', 'ACTIVE'),
('HOTDEAL12', 12.00, 'PERCENTAGE', '2026-08-01 00:00:00', '2026-08-31 23:59:59', 'ACTIVE'),
('DEAL80', 80.00, 'FIXED_AMOUNT', '2026-08-01 00:00:00', '2026-08-31 23:59:59', 'ACTIVE'),
('SCHOOL18', 18.00, 'PERCENTAGE', '2026-09-01 00:00:00', '2026-09-30 23:59:59', 'ACTIVE'),
('SAVE120', 120.00, 'FIXED_AMOUNT', '2026-09-01 00:00:00', '2026-09-30 23:59:59', 'ACTIVE'),
('BLACKFRIDAY35', 35.00, 'PERCENTAGE', '2026-11-01 00:00:00', '2026-11-30 23:59:59', 'ACTIVE'),
('CUT300', 300.00, 'FIXED_AMOUNT', '2026-11-01 00:00:00', '2026-11-30 23:59:59', 'ACTIVE'),
('CYBERMONDAY22', 22.00, 'PERCENTAGE', '2026-12-01 00:00:00', '2026-12-31 23:59:59', 'ACTIVE'),
('YEAREND250', 250.00, 'FIXED_AMOUNT', '2026-12-01 00:00:00', '2026-12-31 23:59:59', 'ACTIVE');

-- Chèn 20 bản ghi mới vào bảng orders
INSERT INTO orders (order_code, customer_id, user_id, discount_id, total_amount, status, order_date) VALUES
('ORD001', 1, 1, 1, 854.05, 'PENDING', '2025-06-01'), -- iPhone 15 với mã giảm giá 5% (899 - 5% = 854.05)
('ORD002', 2, 2, 2, 1674.00, 'CONFIRMED', '2025-06-02'), -- Samsung Z Fold 6 với mã giảm giá 25 (1699 - 25 = 1674.00)
('ORD003', 3, 3, 3, 629.10, 'SHIPPED', '2025-06-03'), -- Xiaomi 14 với mã giảm giá 10% (699 - 10% = 629.10)
('ORD004', 4, 4, 4, 299.00, 'DELIVERED', '2025-06-04'), -- Oppo A81 với mã giảm giá 50 (349 - 50 = 299.00)
('ORD005', 5, 5, 5, 321.30, 'PENDING', '2025-06-05'), -- Vivo Y38 với mã giảm giá 15% (379 - 15% = 321.30)
('ORD006', 6, 6, 6, 429.00, 'CONFIRMED', '2025-06-06'), -- Nokia X33 với mã giảm giá 100 (529 - 100 = 429.00)
('ORD007', 7, 7, 7, 599.20, 'SHIPPED', '2025-06-07'), -- Huawei P63 với mã giảm giá 20% (749 - 20% = 599.20)
('ORD008', 8, 8, 8, 769.00, 'DELIVERED', '2025-06-08'), -- Sony Xperia 3 với mã giảm giá 150 (919 - 150 = 769.00)
('ORD009', 9, 9, 9, 539.25, 'PENDING', '2025-06-09'), -- Google Pixel 10 với mã giảm giá 25% (719 - 25% = 539.25)
('ORD010', 10, 10, 10, 929.00, 'CONFIRMED', '2025-06-10'), -- Asus ROG Phone 8 với mã giảm giá 200 (1129 - 200 = 929.00)
('ORD011', 11, 11, 11, 293.30, 'SHIPPED', '2025-06-11'), -- Realme 13 với mã giảm giá 30% (419 - 30% = 293.30)
('ORD012', 12, 12, 12, 224.00, 'DELIVERED', '2025-06-12'), -- Moto G56 với mã giảm giá 75 (299 - 75 = 224.00)
('ORD013', 13, 13, 13, 720.72, 'PENDING', '2025-06-13'), -- OnePlus 13 với mã giảm giá 12% (819 - 12% = 720.72)
('ORD014', 14, 14, 14, 289.00, 'CONFIRMED', '2025-06-14'), -- Lenovo K16 với mã giảm giá 80 (369 - 80 = 289.00)
('ORD015', 15, 15, 15, 180.90, 'SHIPPED', '2025-06-15'), -- TCL 42 SE với mã giảm giá 18% (219 - 18% = 180.90)
('ORD016', 16, 16, 16, 549.00, 'DELIVERED', '2025-06-16'), -- ZTE Axon 52 với mã giảm giá 120 (669 - 120 = 549.00)
('ORD017', 17, 17, 17, 162.15, 'PENDING', '2025-06-17'), -- Alcatel 5X với mã giảm giá 35% (249 - 35% = 162.15)
('ORD018', 18, 18, 18, 19.00, 'CONFIRMED', '2025-06-18'), -- Infinix Note 32 với mã giảm giá 300 (319 - 300 = 19.00)
('ORD019', 19, 19, 19, 225.42, 'SHIPPED', '2025-06-19'), -- Tecno 22 với mã giảm giá 22% (289 - 22% = 225.42)
('ORD020', 20, 20, 20, 159.00, 'DELIVERED', '2025-06-20'); -- Blackview A102 với mã giảm giá 250 (209 - 250 = 159.00)

-- Chèn 20 bản ghi mới vào bảng order_details
INSERT INTO order_details (order_id, product_id, quantity, unit_price) VALUES
(1, 1, 1, 899.00), -- iPhone 15
(2, 2, 1, 1699.00), -- Samsung Z Fold 6
(3, 3, 1, 699.00), -- Xiaomi 14
(4, 4, 1, 349.00), -- Oppo A81
(5, 5, 1, 379.00), -- Vivo Y38
(6, 6, 1, 529.00), -- Nokia X33
(7, 7, 1, 749.00), -- Huawei P63
(8, 8, 1, 919.00), -- Sony Xperia 3
(9, 9, 1, 719.00), -- Google Pixel 10
(10, 10, 1, 1129.00), -- Asus ROG Phone 8
(11, 11, 1, 419.00), -- Realme 13
(12, 12, 1, 299.00), -- Moto G56
(13, 13, 1, 819.00), -- OnePlus 13
(14, 14, 1, 369.00), -- Lenovo K16
(15, 15, 1, 219.00), -- TCL 42 SE
(16, 16, 1, 669.00), -- ZTE Axon 52
(17, 17, 1, 249.00), -- Alcatel 5X
(18, 18, 1, 319.00), -- Infinix Note 32
(19, 19, 1, 289.00), -- Tecno 22
(20, 20, 1, 209.00); -- Blackview A102
