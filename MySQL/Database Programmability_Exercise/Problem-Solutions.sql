DELIMITER $$

# 1. Employees with Salary Above 35000
CREATE PROCEDURE usp_get_employees_salary_above_35000()
BEGIN

	SELECT first_name, last_name 
    FROM employees
    WHERE salary > 35000
    ORDER BY first_name, last_name, employee_id;
	
END$$

CALL usp_get_employees_salary_above_35000$$

# 2. Employees with Salary Above Number
CREATE PROCEDURE usp_get_employees_salary_above(searchAbove DECIMAL(19, 4))
BEGIN

	SELECT first_name, last_name 
    FROM employees
    WHERE salary >= searchAbove
    ORDER BY first_name, last_name, employee_id;
    
END$$

CALL usp_get_employees_salary_above(45000)$$

# 3. Town Names Starting With
CREATE PROCEDURE usp_get_towns_starting_with(town_name_starting VARCHAR(50))
BEGIN

	SELECT name
    FROM towns t
    WHERE t.name LIKE CONCAT(town_name_starting, '%')
    ORDER BY t.name;
    
END$$

CALL usp_get_towns_starting_with('b')$$

# 4. Employees from Town
CREATE PROCEDURE usp_get_employees_from_town(town_name VARCHAR(50))
BEGIN

	SELECT e.first_name, e.last_name
    FROM employees e
    JOIN addresses a ON e.address_id = a.address_id
    JOIN towns t ON a.town_id = t.town_id
    WHERE t.name = town_name
    ORDER BY e.first_name, e.last_name, e.employee_id;
    
END$$

CALL usp_get_employees_from_town('Sofia')$$

# 5. Salary Level Function
CREATE FUNCTION ufn_get_salary_level(employee_salary DECIMAL(19,4))
RETURNS VARCHAR(7)
NOT DETERMINISTIC
READS SQL DATA
BEGIN
	DECLARE salary_level VARCHAR(7);
    
    SET salary_level = CASE 
							WHEN employee_salary < 30000 THEN 'Low'
							WHEN employee_salary <= 50000 THEN 'Average'
							ELSE 'High'
						END; 
    
    RETURN salary_level; 
END$$

SELECT ufn_get_salary_level(29000)$$
SELECT ufn_get_salary_level(35000)$$
SELECT ufn_get_salary_level(55000)$$

# 6. Employees by Salary Level
CREATE PROCEDURE usp_get_employees_by_salary_level(salary_level VARCHAR(7))
BEGIN
	
    SELECT first_name, last_name 
	FROM employees
	WHERE ufn_get_salary_level(salary) LIKE salary_level
	ORDER BY first_name DESC, last_name DESC;
    
END$$

CALL usp_get_employees_by_salary_level('high')$$
CALL usp_get_employees_by_salary_level('average')$$
CALL usp_get_employees_by_salary_level('low')$$

# 7. Define Function
CREATE FUNCTION ufn_is_word_comprised(set_of_letters varchar(50), word varchar(50)) 
RETURNS INT
DETERMINISTIC
BEGIN
	DECLARE pattern VARCHAR(60);    

    SET pattern = CONCAT('\\b','[',set_of_letters,']+\\b');
	
    RETURN (SELECT word REGEXP pattern);
END$$

SELECT ufn_is_word_comprised('oistmiahf', 'Sofia')$$
SELECT ufn_is_word_comprised('oistmiahf', 'halves')$$
SELECT ufn_is_word_comprised('bobr', 'Rob')$$
SELECT ufn_is_word_comprised('pppp', 'Guy')$$

# 8. Find Full Name
CREATE PROCEDURE usp_get_holders_full_name()
BEGIN
	
	SELECT CONCAT_WS(' ', first_name, last_name) AS 'full_name'
    FROM account_holders
    ORDER BY `full_name`, id;

END$$

CALL usp_get_holders_full_name()$$

# 9. People with Balance Higher Than
CREATE PROCEDURE usp_get_holders_with_balance_higher_than(supplied_number DECIMAL(19, 4))
BEGIN

	SELECT ah.first_name, ah.last_name
	FROM account_holders ah
	GROUP BY ah.id
	HAVING (
		SELECT SUM(balance)
		FROM accounts a
		WHERE ah.id = a.account_holder_id
	) > supplied_number;

END$$

CALL usp_get_holders_with_balance_higher_than(7000)$$

# 10. Future Value Function
CREATE FUNCTION ufn_calculate_future_value(initial_sum DECIMAL(19, 4), yearly_interest_rate DOUBLE, number_of_years INT)
RETURNS DECIMAL(19, 4)
NOT DETERMINISTIC
READS SQL DATA
BEGIN
	
    DECLARE future_value DECIMAL(19, 4);
    
    SET future_value = initial_sum * (POW((1 + yearly_interest_rate), number_of_years));
    
	RETURN future_value;

END$$

SELECT ufn_calculate_future_value(1000, 0.5, 5)$$

# 11. Calculating Interest












