# 2. Insert
INSERT INTO reviews (content, picture_url, published_at, rating)
(SELECT 
	LEFT(p.description, 15),
    REVERSE(p.name),
    '2010-10-10',
    p.price / 8
FROM products p
WHERE p.id >= 5);

# 3. Update
UPDATE products
SET quantity_in_stock = quantity_in_stock - 5
WHERE quantity_in_stock BETWEEN 60 AND 70;

# 4. Delete
DELETE c FROM customers c
LEFT JOIN orders o ON c.id = o.customer_id
WHERE o.customer_id IS NULL;

# 5. Categories
SELECT * FROM categories
ORDER BY name DESC;

# 6. Quantity
SELECT p.id, p.brand_id, p.name, p.quantity_in_stock
FROM products p
WHERE p.price > 1000 AND p.quantity_in_stock < 30
ORDER BY p.quantity_in_stock, p.id;

# 7. Review
SELECT * FROM reviews
WHERE content LIKE 'My%' AND CHAR_LENGTH(content) > 61
ORDER BY rating DESC;

# 8. First customers
SELECT 
	CONCAT_WS(' ', c.first_name, c.last_name) `full_name`,
    c.address,
    o.order_datetime `order_date`
FROM customers c
JOIN orders o ON c.id = o.customer_id
WHERE YEAR(o.order_datetime) <= 2018
ORDER BY `full_name` DESC;

# 9. Best categories
SELECT
	COUNT(p.id) `items_count`,
    c.name,
    SUM(p.quantity_in_stock) `total_quantity`
FROM categories c
JOIN products p ON c.id = p.category_id
GROUP BY c.name
ORDER BY `items_count` DESC, `total_quantity`
LIMIT 5;

# 10. Extract client cards count
DELIMITER $$

CREATE FUNCTION udf_customer_products_count(name VARCHAR(30))
RETURNS INT
DETERMINISTIC
BEGIN

	RETURN (SELECT COUNT(op.order_id) 
			FROM orders_products op
            JOIN orders o ON op.order_id = o.id
            JOIN customers c ON o.customer_id = c.id
            WHERE c.first_name LIKE name);

END$$

SELECT 
	c.first_name, 
	c.last_name, 
    udf_customer_products_count('Shirley') `total_products`
FROM customers c
WHERE c.first_name = 'Shirley'$$

# 11. Reduce price
CREATE PROCEDURE udp_reduce_price(category_name VARCHAR(50))
BEGIN

	UPDATE products p
    JOIN categories c ON p.category_id = c.id
    JOIN reviews r ON p.review_id = r.id
    SET p.price = p.price - (p.price * 0.3)
    WHERE c.name LIKE category_name 
			AND 
        r.rating < 4;

END$$

CALL udp_reduce_price('Phones and tablets')$$