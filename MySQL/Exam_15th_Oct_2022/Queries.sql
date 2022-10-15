# 2. Insert
INSERT INTO products(name, type, price)
(SELECT
	CONCAT_WS(' ', last_name, 'specialty'),
    'Cocktail',
    CEIL((1/100) * salary)
FROM waiters
WHERE id > 6);

# 3. Update
UPDATE orders 
SET 
    table_id = table_id - 1
WHERE
    id BETWEEN 12 AND 23;

# 4. Delete
DELETE w FROM waiters w
        LEFT JOIN
    orders o ON w.id = o.waiter_id 
WHERE
    o.waiter_id IS NULL;

# 5. Clients
SELECT 
    *
FROM
    clients
ORDER BY birthdate DESC , id DESC;

# 6. Birthdate
SELECT 
    first_name, last_name, birthdate, review
FROM
    clients
WHERE
    card IS NULL
        AND YEAR(birthdate) BETWEEN 1978 AND 1993
ORDER BY last_name DESC , id
LIMIT 5;

# 7. Accounts
SELECT 
    CONCAT(last_name,
            first_name,
            CHAR_LENGTH(first_name),
            'Restaurant') `username`,
    REVERSE(SUBSTR(email, 2, 12)) `password`
FROM
    waiters
WHERE
    salary IS NOT NULL
ORDER BY `password` DESC;

# 8. Top from menu
SELECT 
    p.id, p.name, COUNT(op.product_id) `count`
FROM
    products p
        JOIN
    orders_products op ON p.id = op.product_id
GROUP BY p.name
HAVING `count` >= 5
ORDER BY `count` DESC , p.name;

# 9. Availability
SELECT 
    o.table_id,
    t.capacity,
    COUNT(oc.client_id) `count_clients`,
    CASE
        WHEN t.capacity > COUNT(oc.client_id) THEN 'Free seats'
        WHEN t.capacity = COUNT(oc.client_id) THEN 'Full'
        ELSE 'Extra seats'
    END `availability`
FROM
    orders o
        JOIN
    tables t ON o.table_id = t.id
        JOIN
    orders_clients oc ON o.id = oc.order_id
WHERE
    floor = 1
GROUP BY o.table_id
ORDER BY o.table_id DESC;

# 10. Extract bill
DELIMITER $$

CREATE FUNCTION udf_client_bill(full_name VARCHAR(50))
RETURNS DECIMAL(19, 2)
NOT DETERMINISTIC
READS SQL DATA
BEGIN

	RETURN(SELECT 
				SUM(p.price) `bill`
			FROM
				clients c
					JOIN
				orders_clients oc ON c.id = oc.client_id
					JOIN
				orders o ON oc.order_id = o.id
					JOIN
				orders_products op ON o.id = op.order_id
					JOIN
				products p ON op.product_id = p.id
			WHERE
				CONCAT_WS(' ', c.first_name, c.last_name) LIKE full_name
			GROUP BY oc.client_id);
END$$

SELECT c.first_name,c.last_name, udf_client_bill('Silvio Blyth') as 'bill' FROM
clients c
WHERE c.first_name = 'Silvio' AND c.last_name= 'Blyth'$$

# 11. Happy hour
CREATE PROCEDURE udp_happy_hour(type VARCHAR(50))
BEGIN

	UPDATE products p
    SET p.price = p.price - (p.price * 0.2)
    WHERE p.type LIKE type AND p.price >= 10;

END$$

CALL udp_happy_hour ('Cognac')$$