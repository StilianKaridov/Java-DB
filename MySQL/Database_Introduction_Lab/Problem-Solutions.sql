#Problem 1
CREATE DATABASE `gamebar`;

USE `gamebar`;

CREATE TABLE `employees`(
	id INT AUTO_INCREMENT,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    PRIMARY KEY(id)
);

CREATE TABLE `categories`(
	id INT AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    PRIMARY KEY(id)
);

CREATE TABLE `products`(
	id INT AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    category_id INT NOT NULL,
    PRIMARY KEY(id)
);

#Problem 2
INSERT INTO `employees` (`first_name`, `last_name`) 
	VALUES ('Vladimir', 'Karidov'), ('Stilian', 'Karidov'), ('Todor', 'Karidov');

#Problem 3
ALTER TABLE `employees` 
ADD COLUMN `middle_name` VARCHAR(50) AFTER `first_name`;

#Problem 4
ALTER TABLE `products` 
ADD CONSTRAINT `fk_products_categories`
FOREIGN KEY `products`(`category_id`)
REFERENCES `categories`(`id`);

#Problem 5
ALTER TABLE `employees`
CHANGE COLUMN `middle_name` `middle_name` VARCHAR(100);