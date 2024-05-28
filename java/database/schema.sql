BEGIN TRANSACTION;

-- Drop tables if they exist
DROP TABLE IF EXISTS items;
DROP TABLE IF EXISTS categories;
DROP TABLE IF EXISTS claims;
DROP TABLE IF EXISTS notifications;
DROP TABLE IF EXISTS item_images;
DROP TABLE IF EXISTS users;

-- Create users table
CREATE TABLE users (
     user_id SERIAL,
     username VARCHAR(50) NOT NULL UNIQUE,
     password_hash VARCHAR(200) NOT NULL,
     role VARCHAR(50) NOT NULL,
     CONSTRAINT PK_user PRIMARY KEY (user_id)
);

-- Create categories table
CREATE TABLE categories (
     category_id SERIAL,
     category_name VARCHAR(100) NOT NULL,
     CONSTRAINT PK_category PRIMARY KEY (category_id)
);

-- Create items table
CREATE TABLE items (
     item_id SERIAL,
     name VARCHAR(100) NOT NULL,
     description TEXT NOT NULL,
     date_lost DATE NOT NULL,
     is_claimed BOOLEAN DEFAULT FALSE,
     reported_by INT NOT NULL,
     category_id INT NOT NULL,
     CONSTRAINT PK_item PRIMARY KEY (item_id),
     CONSTRAINT FK_item_user FOREIGN KEY (reported_by) REFERENCES users(user_id),
     CONSTRAINT FK_item_category FOREIGN KEY (category_id) REFERENCES categories(category_id)
);

-- Create claims table
CREATE TABLE claims (
     claim_id SERIAL,
     item_id INT NOT NULL,
     claimed_by INT NOT NULL,
     date_claimed DATE NOT NULL,
     CONSTRAINT PK_claim PRIMARY KEY (claim_id),
     CONSTRAINT FK_claim_item FOREIGN KEY (item_id) REFERENCES items(item_id),
     CONSTRAINT FK_claim_user FOREIGN KEY (claimed_by) REFERENCES users(user_id)
);

-- Create notifications table
CREATE TABLE notifications (
     notification_id SERIAL,
     message TEXT NOT NULL,
     is_read BOOLEAN DEFAULT FALSE,
     user_id INT NOT NULL,
     created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
     CONSTRAINT PK_notification PRIMARY KEY (notification_id),
     CONSTRAINT FK_notification_user FOREIGN KEY (user_id) REFERENCES users(user_id)
);

-- Create item_images table
CREATE TABLE item_images (
     image_id SERIAL,
     item_id INT NOT NULL,
     image_url VARCHAR(255) NOT NULL,
     CONSTRAINT PK_image PRIMARY KEY (image_id),
     CONSTRAINT FK_image_item FOREIGN KEY (item_id) REFERENCES items(item_id)
);

COMMIT TRANSACTION;
