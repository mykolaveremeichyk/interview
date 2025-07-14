-- Optional: Add Duplicate Data for Testing Deduplication Tasks
-- Run this file AFTER the main init.sql to create test scenarios for data quality tasks

-- =================================================================================
-- CUSTOMER DUPLICATES
-- =================================================================================

-- 1. Exact name match with slightly different emails (common scenario)
INSERT INTO customers (first_name, last_name, email, phone, registration_date) VALUES
('Alexander', 'Johnson', 'alexander.johnson.work@emaildup.com', '+1234567999', '2023-01-16'),
('Alexander', 'Johnson', 'alex.johnson@emaildup.com', '+1234567999', '2023-01-17'),

-- 2. Email variations (gmail vs google, different domains)
('Maria', 'Williams', 'maria.williams@gmaildup.com', '+1234567998', '2023-01-18'),
('Maria', 'Williams', 'maria.williams@googledup.com', '+1234567998', '2023-01-19'),

-- 3. Name variations (with/without middle name, nicknames)
('David', 'Brown', 'dave.brown@emaildup.com', '+1234567997', '2023-01-20'),
('Dave', 'Brown', 'david.brown.alt@emaildup.com', '+1234567997', '2023-01-21'),
('David M', 'Brown', 'david.m.brown@emaildup.com', '+1234567997', '2023-01-22'),

-- 4. Phone number formatting differences
('Sarah', 'Jones', 'sarah.jones.duplicate@emaildup.com', '1234567996', '2023-01-23'),
('Sarah', 'Jones', 'sarah.jones.alt@emaildup.com', '(123) 456-7996', '2023-01-24'),

-- 5. Case sensitivity issues
('michael', 'garcia', 'michael.garcia.lower@emaildup.com', '+1234567995', '2023-01-25'),
('MICHAEL', 'GARCIA', 'MICHAEL.GARCIA.UPPER@EMAILDUP.COM', '+1234567995', '2023-01-26');

-- =================================================================================
-- PRODUCT DUPLICATES
-- =================================================================================

-- 1. Exact product name duplicates (different stock levels)
INSERT INTO products (product_name, category, price, stock_quantity) VALUES
('MacBook Pro 16-inch', 'Electronics', 2499.99, 8),
('MacBook Pro 16-inch', 'Electronics', 2449.99, 12), -- Different price
('MacBook Pro 16-inch', 'Electronics', 2499.99, 15), -- Different stock

-- 2. Similar product names (slight variations)
('iPhone 14 Pro', 'Electronics', 999.99, 20),
('iPhone 14 Pro ', 'Electronics', 999.99, 25), -- Trailing space
(' iPhone 14 Pro', 'Electronics', 989.99, 30), -- Leading space

-- 3. Category inconsistencies
('Nike Air Max 270', 'Footwear', 150.00, 40),
('Nike Air Max 270', 'Shoes', 150.00, 35), -- Different category name
('Nike Air Max 270', 'footwear', 150.00, 45), -- Case difference

-- 4. Price variations for same product
('Calvin Klein T-Shirt', 'Clothing', 25.99, 50),
('Calvin Klein T-Shirt', 'Clothing', 24.99, 60), -- Sale price
('Calvin Klein T-Shirt', 'Clothing', 26.99, 55); -- Regular price

-- =================================================================================
-- ORDER DATA INCONSISTENCIES
-- =================================================================================

-- Create orders that might be considered duplicates
-- (same customer, same day, similar amounts - could be accidental double-clicks)
INSERT INTO orders (customer_id, order_date, total_amount, status) VALUES
(1, '2023-06-01 10:30:00', 1299.99, 'pending'),
(1, '2023-06-01 10:31:00', 1299.99, 'pending'), -- 1 minute later, same amount
(1, '2023-06-01 10:35:00', 1299.00, 'cancelled'), -- Slightly different amount

(15, '2023-06-02 14:15:00', 549.99, 'completed'),
(15, '2023-06-02 14:16:00', 549.99, 'cancelled'), -- Duplicate cancelled

(25, '2023-06-03 09:00:00', 199.99, 'shipped'),
(25, '2023-06-03 09:02:00', 199.99, 'pending'); -- Similar timing

-- =================================================================================
-- EMAIL AND PHONE NORMALIZATION SCENARIOS
-- =================================================================================

-- Add customers with email formatting issues for normalization tasks
INSERT INTO customers (first_name, last_name, email, phone, registration_date) VALUES
-- Mixed case emails
('Test', 'User1', 'Test.User1@EmailDup.Com', '+1555000001', '2023-06-01'),
('Test', 'User2', 'test.user2@EMAILDUP.COM', '+1555000002', '2023-06-02'),

-- Gmail +tag variations
('John', 'Doe', 'john.doe@gmaildup.com', '+1555000003', '2023-06-03'),
('John', 'Doe', 'john.doe+shopping@gmaildup.com', '+1555000003', '2023-06-04'),
('John', 'Doe', 'john.doe+work@gmaildup.com', '+1555000003', '2023-06-05'),

-- Phone number formatting variations
('Jane', 'Smith', 'jane.smith1@emaildup.com', '+1-555-000-010', '2023-06-06'),
('Jane', 'Smith', 'jane.smith2@emaildup.com', '(555) 000-010', '2023-06-07'),
('Jane', 'Smith', 'jane.smith3@emaildup.com', '555.000.010', '2023-06-08'),
('Jane', 'Smith', 'jane.smith4@emaildup.com', '5550000010', '2023-06-09');

-- =================================================================================
-- SAMPLE DEDUPLICATION QUERIES (FOR REFERENCE)
-- =================================================================================

/*
-- 1. Find duplicate customers by name and phone
WITH duplicate_customers AS (
    SELECT 
        customer_id,
        first_name,
        last_name,
        phone,
        email,
        registration_date,
        COUNT(*) OVER (PARTITION BY LOWER(TRIM(first_name)), LOWER(TRIM(last_name)), 
                      REGEXP_REPLACE(phone, '[^0-9]', '', 'g')) as duplicate_count,
        ROW_NUMBER() OVER (PARTITION BY LOWER(TRIM(first_name)), LOWER(TRIM(last_name)), 
                          REGEXP_REPLACE(phone, '[^0-9]', '', 'g') 
                          ORDER BY registration_date) as row_num
    FROM customers
)
SELECT * FROM duplicate_customers 
WHERE duplicate_count > 1 
ORDER BY first_name, last_name, registration_date;

-- 2. Find duplicate products by name and category
WITH duplicate_products AS (
    SELECT 
        product_id,
        product_name,
        category,
        price,
        stock_quantity,
        COUNT(*) OVER (PARTITION BY LOWER(TRIM(product_name)), LOWER(TRIM(category))) as duplicate_count,
        ROW_NUMBER() OVER (PARTITION BY LOWER(TRIM(product_name)), LOWER(TRIM(category)) 
                          ORDER BY stock_quantity DESC, price ASC) as row_num
    FROM products
)
SELECT * FROM duplicate_products 
WHERE duplicate_count > 1 
ORDER BY product_name, category;

-- 3. Find potential duplicate orders (same customer, same day, similar amounts)
WITH potential_duplicate_orders AS (
    SELECT 
        o1.order_id as order1_id,
        o2.order_id as order2_id,
        o1.customer_id,
        o1.order_date as order1_date,
        o2.order_date as order2_date,
        o1.total_amount as amount1,
        o2.total_amount as amount2,
        o1.status as status1,
        o2.status as status2,
        ABS(EXTRACT(EPOCH FROM (o2.order_date - o1.order_date))/60) as minutes_between
    FROM orders o1
    JOIN orders o2 ON o1.customer_id = o2.customer_id 
                   AND o1.order_id < o2.order_id
                   AND DATE(o1.order_date) = DATE(o2.order_date)
                   AND ABS(o1.total_amount - o2.total_amount) < 5.00
)
SELECT * FROM potential_duplicate_orders
WHERE minutes_between < 30
ORDER BY customer_id, order1_date;

-- 4. Normalize phone numbers
UPDATE customers 
SET phone = '+1' || REGEXP_REPLACE(phone, '[^0-9]', '', 'g')
WHERE phone ~ '^[^+]' AND LENGTH(REGEXP_REPLACE(phone, '[^0-9]', '', 'g')) = 10;

-- 5. Normalize email addresses (lowercase)
UPDATE customers 
SET email = LOWER(TRIM(email));
*/ 