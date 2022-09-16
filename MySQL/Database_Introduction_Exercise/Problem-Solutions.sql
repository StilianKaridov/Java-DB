				#Creation of the Minions Database 
CREATE DATABASE `minions`;  

USE `minions`;

#Problem 1
CREATE TABLE `minions`(
	`id` INT NOT NULL,
    `name` VARCHAR(50) NOT NULL,
    `age` INT,
    
    PRIMARY KEY (id)
);

CREATE TABLE `towns`(
	`town_id` INT NOT NULL,
	`name` VARCHAR(50) NOT NULL,
    
    PRIMARY KEY (town_id)
);

#Problem 2
ALTER TABLE `towns`
CHANGE COLUMN `town_id` `id` INT NOT NULL AUTO_INCREMENT;

ALTER TABLE `minions`
ADD COLUMN `town_id` INT NOT NULL;

ALTER TABLE `minions`
ADD CONSTRAINT `fk_minions_towns`
FOREIGN KEY `minions`(`town_id`)
REFERENCES `towns`(`id`);

#Problem 3
INSERT INTO `towns` (`id`, `name`)
VALUES (1, 'Sofia'), (2, 'Plovdiv'), (3, 'Varna');

INSERT INTO `minions`(`id`, `name`, `age`, `town_id`)
VALUES (1, 'Kevin', 22, 1), (2, 'Bob', 15, 3), (3 ,'Steward', NULL, 2);

#Problem 4
DELETE FROM `minions` WHERE(`id` = 1);
DELETE FROM `minions` WHERE(`id` = 2);
DELETE FROM `minions` WHERE(`id` = 3);

#Problem 5
DROP TABLE `minions`;
DROP TABLE `towns`;

#Problem 6
CREATE TABLE `people`(
	`id` INT AUTO_INCREMENT PRIMARY KEY,
    `name` VARCHAR(200) NOT NULL,
    `picture` BLOB,
    `height` DOUBLE (10,2),
    `weight` DOUBLE (10,2),
    `gender` CHAR(1) NOT NULL,
    `birthdate` DATE NOT NULL,
    `biography` TEXT
);

INSERT INTO `people`(`name`, `picture`, `height`, `weight`, `gender`, `birthdate`, `biography`)
	VALUES
    ('Stilian', NULL, 1.80, 85.3, 'm', '2001-09-26', NULL),
    ('Todor', NULL, 1.80, 95.0, 'm', '1970-09-18', NULL),
    ('Dimitar', NULL, 1.83, 76.1, 'm', '2001-06-22', NULL),
    ('Magdalena', NULL, 1.76, 70.0, 'f', '1979-03-16', NULL),
    ('Vladimir', NULL, 1.75, 69.0, 'm', '2007-10-20', NULL);
    
#Problem 7
CREATE TABLE `users`(
	`id` INT UNIQUE PRIMARY KEY AUTO_INCREMENT,
    `username` VARCHAR(30) NOT NULL,
    `password` VARCHAR(26) NOT NULL,
    `profile_picture` BLOB,
    `last_login_time` TIME,
    `is_deleted` BOOLEAN
);

INSERT INTO `users`(`username`, `password`, `profile_picture`, `last_login_time`, `is_deleted`)
	VALUES
    ('stilian_01', '123456', NULL, TIME(NOW()), 0),
    ('to6i', '1234567', NULL, TIME(NOW()), 0),
    ('dimitar.01', '1234568', NULL, TIME(NOW()), 0),
    ('magito79', '1234569', NULL, TIME(NOW()), 0),
    ('vladimir07', '12345610', NULL, TIME(NOW()), 0);
    
#Problem 8
ALTER TABLE `users`
DROP PRIMARY KEY,
ADD CONSTRAINT `pk_users`
PRIMARY KEY `users`(`id`, `username`);

#Problem 9
ALTER TABLE `users`
CHANGE COLUMN `last_login_time` `last_login_time` DATETIME DEFAULT NOW();

#Problem 10
ALTER TABLE `users`
DROP PRIMARY KEY,
ADD CONSTRAINT `pk_users`
PRIMARY KEY `users`(`id`),
CHANGE COLUMN `username` `username` VARCHAR(30) UNIQUE;


				#Creation of the Movies Database 
#Problem 11
CREATE DATABASE `movies`;

USE `movies`;


CREATE TABLE `directors`(
	`id` INT AUTO_INCREMENT PRIMARY KEY,
    `director_name` VARCHAR(50) NOT NULL,
    `notes` TEXT
);

INSERT INTO `directors`(`director_name`, `notes`)
VALUES
('Stilian', 'Note1'),
('Ivan', 'Note3'),
('Dimitar', 'Note3'),
('Vladimir', 'Note4'),
('Todor', 'Note5');

CREATE TABLE `genres`(
	`id` INT AUTO_INCREMENT PRIMARY KEY,
    `genre_name` VARCHAR(50) NOT NULL,
    `notes` TEXT
);

INSERT INTO `genres`(`genre_name`, `notes`)
VALUES
('Horror', 'genre1'),
('Comedy', 'genre2'),
('Adventure', 'genre3'),
('Action', 'genre4'),
('Drama', 'genre5');

CREATE TABLE `categories`(
	`id` INT AUTO_INCREMENT PRIMARY KEY,
    `category_name` VARCHAR(50) NOT NULL,
    `notes` TEXT
);

INSERT INTO `categories`(`category_name`, `notes`)
VALUES
('First', 'cat1'),
('Second', 'cat2'),
('Third', 'cat3'),
('Fourth', 'cat4'),
('Fifth', 'cat5');

CREATE TABLE `movies`(
	`id` INT AUTO_INCREMENT PRIMARY KEY,
    `title` VARCHAR(100) NOT NULL,
    `director_id` INT,
    `copyright_year` INT,
    `length` DOUBLE(4, 2),
    `genre_id` INT,
    `category_id` INT,
    `rating` DOUBLE(4, 2),
    `notes` TEXT
);

INSERT INTO `movies`(`title`)
VALUES
('The Lord Of the Rings'),
('Hobbit'),
('Fast and Furious'),
('Top Gun'),
('Iron Man');

ALTER TABLE `movies`
ADD CONSTRAINT `fk_movies_directors`
FOREIGN KEY `movies`(`director_id`)
REFERENCES `directors`(`id`),
ADD CONSTRAINT `fk_movies_genres`
FOREIGN KEY `movies`(`genre_id`)
REFERENCES `genres`(`id`),
ADD CONSTRAINT `fk_movies_categories`
FOREIGN KEY `movies`(`category_id`)
REFERENCES `categories`(`id`);

#Problem 12
		#Creation of car_rental Database
CREATE DATABASE car_rental;

USE car_rental;

CREATE TABLE `categories`(
	`id` INT PRIMARY KEY AUTO_INCREMENT,
    `category` VARCHAR(50) NOT NULL,
    `daily_rate` INT,
    `weekly_rate` INT,
    `monthly_rate` INT,
    `weekend_rate` INT
);

INSERT INTO `categories`(`category`) 
VALUES
('Sedan'),
('Combi'),
('Hetchback');

CREATE TABLE `cars`(
	`id` INT PRIMARY KEY AUTO_INCREMENT,
    `plate_number` INT NOT NULL,
    `make` VARCHAR(50) NOT NULL,
    `model` VARCHAR(50) NOT NULL,
    `car_year` INT,
    `category_id` INT,
    `doors` INT,
    `picture` BLOB,
    `car_condition` VARCHAR(100),
    `available` BOOLEAN
);

INSERT INTO `cars`(`plate_number`, `make`, `model`) 
VALUES
(0000, 'BMW', 'M5'),
(1111, 'Mercedes', 'CLS'),
(2222, 'Audi', 'A4');

ALTER TABLE `cars`
ADD CONSTRAINT `fk_cars_categories`
FOREIGN KEY `cars`(`category_id`)
REFERENCES `categories`(`id`); 

CREATE TABLE `employees`(
	`id` INT PRIMARY KEY AUTO_INCREMENT,
    `first_name` VARCHAR(50) NOT NULL,
    `last_name` VARCHAR(50) NOT NULL,
    `title` VARCHAR(50),
    `notes` TEXT
);

INSERT INTO `employees`(`first_name`, `last_name`) 
VALUES
('Ivan', 'Ivanov'),
('Pesho', 'Peshov'),
('Mitko', 'Mitkov');

CREATE TABLE `customers`(
	`id` INT PRIMARY KEY AUTO_INCREMENT,
    `driver_license_number` VARCHAR(50) NOT NULL,
    `full_name` VARCHAR(100) NOT NULL,
    `address` VARCHAR(50),
    `city` VARCHAR(50),
    `zip_code` CHAR(4),
    `notes` TEXT
);

INSERT INTO `customers`(`driver_license_number`, `full_name`) 
VALUES
('1234', 'Stilian Karidov'),
('12345', 'Todor Karidov'),
('123456', 'Vladimir Karidov');

CREATE TABLE `rental_orders`(
	`id` INT PRIMARY KEY AUTO_INCREMENT,
    `employee_id` INT NOT NULL,
    `customer_id` INT NOT NULL,
    `car_id` INT NOT NULL,
    `car_condition` VARCHAR(100),
    `tank_level` INT,
    `kilometrage_start` DOUBLE(10, 1),
    `kilometrage_end` DOUBLE(10, 1),
    `total_kilometrage` DOUBLE(10, 1),
    `start_date` DATE,
    `end_date` DATE,
    `total_days` INT,
    `rate_applied` INT,
    `tax_rate` INT,
    `order_status` BOOLEAN,
    `notes` TEXT
);

INSERT INTO `rental_orders`(`employee_id`, `customer_id`, `car_id`) 
VALUES
(1, 2, 3),
(2 , 1, 3),
(3, 2, 1);

ALTER TABLE `rental_orders`
ADD CONSTRAINT `fk_rentalorders_employees`
FOREIGN KEY `rental_orders`(`employee_id`)
REFERENCES `employees`(`id`),
ADD CONSTRAINT `fk_rentalorders_customers`
FOREIGN KEY `rental_orders`(`customer_id`)
REFERENCES `customers`(`id`),
ADD CONSTRAINT `fk_rentalorders_cars`
FOREIGN KEY `rental_orders`(`car_id`)
REFERENCES `cars`(`id`);

#Problem 13
				#Create of soft_uni Database
CREATE DATABASE `soft_uni`;

USE `soft_uni`;

CREATE TABLE `towns`(
	`id` INT PRIMARY KEY AUTO_INCREMENT,
    `name` VARCHAR(100) NOT NULL
);

CREATE TABLE `addresses`(
	`id` INT PRIMARY KEY AUTO_INCREMENT,
    `address_text` VARCHAR(100) NOT NULL,
    `town_id` INT NOT NULL
);

ALTER TABLE `addresses`
ADD CONSTRAINT `fk_addresses_towns`
FOREIGN KEY `addresses`(`town_id`)
REFERENCES `towns`(`id`);

CREATE TABLE `departments`(
	`id` INT PRIMARY KEY AUTO_INCREMENT,
    `name` VARCHAR(100) NOT NULL
);

CREATE TABLE `employees`(
	`id` INT PRIMARY KEY AUTO_INCREMENT,
    `first_name` VARCHAR(50) NOT NULL,
    `middle_name` VARCHAR(50) NOT NULL,
    `last_name` VARCHAR(50) NOT NULL,
    `job_title` VARCHAR(50) NOT NULL,
    `department_id` INT NOT NULL,
    `hire_date` DATE NOT NULL,
    `salary` DOUBLE NOT NULL,
    `address_id` INT
);

ALTER TABLE `employees`
ADD CONSTRAINT `fk_employees_departments`
FOREIGN KEY `employees`(`department_id`)
REFERENCES `departments`(`id`),
ADD CONSTRAINT `fk_employees_addresses`
FOREIGN KEY `employees`(`address_id`)
REFERENCES `addresses`(`id`);

INSERT INTO `towns`(`name`)
VALUES
('Sofia'),
('Plovdiv'),
('Varna'),
('Burgas');

INSERT INTO `departments`(`name`)
VALUES
('Engineering'),
('Sales'),
('Marketing'),
('Software Development'),
('Quality Assurance');

INSERT INTO `employees`(`first_name`, `middle_name`, `last_name`,
`job_title`, `department_id`, `hire_date`, `salary`)
VALUES
('Ivan', 'Ivanov', 'Ivanov', '.NET Developer', 4, '2013-02-01', 3500.00),
('Petar', 'Petrov', 'Petrov', 'Senior Engineer', 1, '2004-03-02', 4000.00),
('Maria', 'Petrova', 'Ivanova', 'Intern', 5, '2016-08-28', 525.25),
('Georgi', 'Terziev', 'Ivanov', 'CEO', 2, '2007-12-09', 3000.00),
('Peter', 'Pan', 'Pan', 'Intern', 3, '2016-08-28', 599.88);

#Problem 14
SELECT * FROM `towns`;
SELECT * FROM `departments`;
SELECT * FROM `employees`;

#Problem 15
SELECT * FROM `towns`
ORDER BY `name`;

SELECT * FROM `departments`
ORDER BY `name`;

SELECT * FROM `employees`
ORDER BY `salary` DESC;

#Problem 16
SELECT `name` FROM `towns`
ORDER BY `name`;

SELECT `name` FROM `departments`
ORDER BY `name`;

SELECT `first_name`, `last_name`, `job_title`, `salary` FROM `employees`
ORDER BY `salary` DESC;

#Problem 17
UPDATE `employees`
SET `salary` = `salary` * 1.10; 

SELECT `salary` FROM `employees`;