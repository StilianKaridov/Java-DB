# 2. Insert
INSERT INTO clients (full_name, phone_number)
(SELECT
	CONCAT_WS(' ', d.first_name, d.last_name),
    CONCAT('(0', 88, ')', ' ',9999, d.id * 2)
FROM drivers d
WHERE d.id BETWEEN 10 AND 20
);

# 3. Update
UPDATE cars 
SET 
    `condition` = 'C'
WHERE
    (mileage >= 800000 OR mileage IS NULL)
        AND year <= 2010
        AND make != 'Mercedes-Benz';
        
# 4. Delete
DELETE c FROM clients c
        LEFT JOIN
    courses cs ON c.id = cs.client_id 
WHERE
    cs.client_id IS NULL
    AND CHAR_LENGTH(c.full_name) > 3;

# 5. Cars
SELECT
	make,
    model,
    `condition`
FROM cars
ORDER BY id;

# 6. Drivers and Cars
SELECT 
    d.first_name, d.last_name, c.make, c.model, c.mileage
FROM
    drivers d
        JOIN
    cars_drivers cd ON d.id = cd.driver_id
        JOIN
    cars c ON cd.car_id = c.id
WHERE
    c.mileage IS NOT NULL
ORDER BY c.mileage DESC , d.first_name;

# 7. Number of courses
SELECT 
    c.id `car_id`,
    c.make,
    c.mileage,
    COUNT(cs.car_id) `count_of_courses`,
    ROUND(AVG(cs.bill), 2) `avg_bill`
FROM
    cars c
        LEFT JOIN
    courses cs ON c.id = cs.car_id
GROUP BY c.id
HAVING `count_of_courses` != 2
ORDER BY `count_of_courses` DESC , c.id;

# 8. Regular clients
SELECT 
    c.full_name,
    COUNT(cs.car_id) `count_of_cars`,
    SUM(cs.bill) `total_sum`
FROM
    clients c
        JOIN
    courses cs ON c.id = cs.client_id
        JOIN
    cars cr ON cs.car_id = cr.id
GROUP BY cs.client_id
HAVING `count_of_cars` > 1
    AND c.full_name LIKE '_a%'
ORDER BY c.full_name;

# 9. Full info for courses
SELECT 
    a.name,
    IF(HOUR(cs.start) BETWEEN 6 AND 20,
        'Day',
        'Night') `day_time`,
    cs.bill,
    c.full_name,
    cr.make,
    cr.model,
    ct.name `category_name`
FROM
    addresses a
        JOIN
    courses cs ON a.id = cs.from_address_id
        JOIN
    clients c ON cs.client_id = c.id
        JOIN
    cars cr ON cs.car_id = cr.id
        JOIN
    categories ct ON cr.category_id = ct.id
ORDER BY cs.id;

# 10. Find all courses by client’s phone number
DELIMITER $$

CREATE FUNCTION udf_courses_by_client (phone_num VARCHAR (20))
RETURNS INT
NOT DETERMINISTIC
READS SQL DATA
BEGIN

	RETURN (SELECT COUNT(cs.client_id)
			FROM clients c
            JOIN courses cs ON c.id = cs.client_id
            WHERE c.phone_number LIKe phone_num
            GROUP BY cs.client_id
            );

END$$

SELECT udf_courses_by_client ('(803) 6386812') as `count`$$

# 11. Full info for address
CREATE PROCEDURE udp_courses_by_address(address_name VARCHAR(100))
BEGIN

	SELECT
		a.name,
        c.full_name,
        CASE
			WHEN cs.bill <= 20 THEN 'Low'
            WHEN cs.bill <= 30 THEN 'Medium'
            ELSE 'High'
        END `level_of_bill`,
        cr.make,
        cr.`condition`,
        ct.name `cat_name`
	FROM addresses a
    JOIN courses cs ON a.id = cs.from_address_id
    JOIN clients c ON cs.client_id = c.id
    JOIN cars cr ON cs.car_id = cr.id
    JOIN categories ct ON cr.category_id = ct.id
    WHERE a.name LIKE address_name
    ORDER BY cr.make, c.full_name;
END$$

CALL udp_courses_by_address('700 Monterey Avenue')$$