# SQL Test Task Repository

This repository contains a test task for candidates who will work with SQL. The task includes working with a PostgreSQL database and executing various SQL queries.

## 🚀 Quick Start

### Prerequisites
- Docker and Docker Compose installed on your system
- SQL client (DBeaver, TablePlus, PgAdmin, DataGrip, or other)

### Database Setup

1. Clone the repository:
```bash
git clone <repository-url>
cd sql-test-task
```

2. Start the PostgreSQL Docker container:
```bash
docker-compose up -d
```

3. Verify that the container is running:
```bash
docker ps
```

## 🔌 Connection Details

- **Host:** localhost
- **Port:** 5432
- **Database:** test_db
- **Username:** test_user
- **Password:** test_pass

## 📊 Table Structure

The database contains 4 tables with corresponding relationships:

### `customers` - Customers
- `customer_id` (SERIAL PRIMARY KEY) - unique customer identifier
- `first_name` (VARCHAR(50)) - first name
- `last_name` (VARCHAR(50)) - last name  
- `email` (VARCHAR(100) UNIQUE) - email address
- `phone` (VARCHAR(20)) - phone number
- `registration_date` (DATE) - registration date

### `products` - Products
- `product_id` (SERIAL PRIMARY KEY) - unique product identifier
- `product_name` (VARCHAR(100)) - product name
- `category` (VARCHAR(50)) - category
- `price` (DECIMAL(10,2)) - price
- `stock_quantity` (INTEGER) - quantity in stock

### `orders` - Orders
- `order_id` (SERIAL PRIMARY KEY) - unique order identifier
- `customer_id` (INTEGER) - customer identifier (FK → customers)
- `order_date` (TIMESTAMP) - order date and time
- `total_amount` (DECIMAL(10,2)) - total order amount
- `status` (VARCHAR(20)) - order status

### `order_items` - Order Items
- `order_item_id` (SERIAL PRIMARY KEY) - unique item identifier
- `order_id` (INTEGER) - order identifier (FK → orders)
- `product_id` (INTEGER) - product identifier (FK → products)
- `quantity` (INTEGER) - quantity
- `unit_price` (DECIMAL(10,2)) - price per unit

## 🎯 Interview Tasks

Execute the following SQL queries:

### 1. Basic Queries
- Get a list of all customers with their contact information
- Find all products with a price greater than $500
- Show all orders from the last month

### 2. Table Joins (JOIN)
- Get a list of orders with customer names
- Show order details with product names
- Find customers who have not made any orders yet

### 3. Aggregate Functions
- Count the total number of orders for each customer
- Find the most expensive product in each category
- Calculate the average order amount

### 4. Complex Queries
- Find the top-3 most popular products by sales quantity
- Get customers with total order amount greater than $2000
- Show products that have never been ordered

### 5. Data Modification
- Add a new customer
- Update order status
- Increase the price of all products in the "Electronics" category by 10%

## 🛑 Stopping the Container

To stop the container, execute:
```bash
docker-compose down
```

To completely remove (including data):
```bash
docker-compose down -v
```

## 📝 Notes

- All test data is created automatically when the container starts
- The database remains accessible while the container is running
- Data changes are stored in Docker volume and are not lost on restart