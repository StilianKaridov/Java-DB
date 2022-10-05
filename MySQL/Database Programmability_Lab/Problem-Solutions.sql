DELIMITER $$

# 1. Count Employees by Town
CREATE FUNCTION ufn_count_employees_by_town(town_name VARCHAR(50))
RETURNS INT
DETERMINISTIC
BEGIN
	DECLARE count_employees INT;
    
    SET count_employees := (
			SELECT COUNT(employee_id) 'count' FROM employees e
			JOIN addresses a ON e.address_id = a.address_id
            JOIN towns t ON a.town_id = t.town_id
            WHERE t.name = town_name
	);
    
    RETURN count_employees;
END$$

SELECT ufn_count_employees_by_town('Sofia')$$

# 2. Employees Promotion
CREATE PROCEDURE usp_raise_salaries(department_name VARCHAR(50))
BEGIN

	UPDATE employees e SET e.salary = e.salary * 1.05
	WHERE e.department_id = (
				SELECT department_id FROM departments d
                WHERE d.name = department_name
    );
    
END$$

CALL usp_raise_salaries('Finance')$$

# 3. Employees Promotion by ID
CREATE PROCEDURE usp_raise_salary_by_id(id INT)
BEGIN

	UPDATE employees e
	SET e.salary = e.salary * 1.05
    WHERE e.employee_id = id;

END$$

CALL usp_raise_salary_by_id(17)$$

# 4. Triggered
CREATE TABLE `deleted_employees`(
	employee_id INT PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    middle_name VARCHAR(50),
    job_title VARCHAR(50),
    department_id INT,
    salary DECIMAL(19, 4)
)$$

CREATE TRIGGER tr_deleted_employees
AFTER DELETE
ON employees
FOR EACH ROW
BEGIN

	INSERT INTO deleted_employees(first_name, last_name, middle_name, job_title, department_id, salary)
    VALUES (OLD.first_name, OLD.last_name, OLD.middle_name, OLD.job_title, OLD.department_id, OLD.salary);

END$$