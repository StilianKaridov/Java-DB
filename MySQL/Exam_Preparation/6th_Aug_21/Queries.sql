# 2. Insert
INSERT INTO games (name, rating, budget, team_id)
(SELECT
	LOWER(REVERSE(substr(t.name, 2))),
    t.id,
    t.leader_id * 1000,
    t.id
FROM teams t
WHERE t.id BETWEEN 1 AND 9);

# 3. Update
UPDATE employees e
        JOIN
    teams t ON e.id = t.leader_id 
SET 
    e.salary = e.salary + 1000
WHERE
    e.age < 40 AND e.salary < 5000;

# 4. Delete
DELETE g FROM games g
        LEFT JOIN
    games_categories gc ON g.id = gc.game_id 
WHERE
    g.release_date IS NULL
    AND gc.category_id IS NULL;

# 5. Employees
SELECT 
    first_name, last_name, age, salary, happiness_level
FROM
    employees
ORDER BY salary , id;

# 6. Addresses of the teams
SELECT 
    t.name `team_name`,
    a.name `address_name`,
    CHAR_LENGTH(a.name) `count_of_characters`
FROM
    teams t
        LEFT JOIN
    offices o ON t.office_id = o.id
        JOIN
    addresses a ON o.address_id = a.id
WHERE
    o.website IS NOT NULL
ORDER BY `team_name` , `address_name`;

# 7. Categories Info
SELECT 
    c.name,
    COUNT(gc.game_id) `games_count`,
    ROUND(AVG(g.budget), 2) `avg_budget`,
    MAX(g.rating) `max_rating`
FROM
    categories c
        JOIN
    games_categories gc ON c.id = gc.category_id
        JOIN
    games g ON gc.game_id = g.id
GROUP BY c.name
HAVING `max_rating` >= 9.5
ORDER BY `games_count` DESC , c.name;

# 8. Games of 2022
SELECT 
    g.name,
    g.release_date,
    CONCAT(LEFT(g.description, 10), '...') `summary`,
    (CASE
        WHEN MONTH(g.release_date) BETWEEN 1 AND 3 THEN 'Q1'
        WHEN MONTH(g.release_date) BETWEEN 4 AND 6 THEN 'Q2'
        WHEN MONTH(g.release_date) BETWEEN 7 AND 9 THEN 'Q3'
        ELSE 'Q4'
    END) `quarter`,
    t.name `team_name`
FROM
    games g
        JOIN
    teams t ON g.team_id = t.id
WHERE
    MONTH(g.release_date) % 2 = 0
        AND YEAR(g.release_date) = 2022
        AND RIGHT(g.name, 1) LIKE '2'
ORDER BY `quarter`;

# 9. Full info for games
SELECT 
    g.name,
    IF(g.budget < 50000,
        'Normal budget',
        'Insufficient budget') `budget_level`,
    t.name `team_name`,
    a.name `address_name`
FROM
    games g
        JOIN
    teams t ON g.team_id = t.id
        JOIN
    offices o ON t.office_id = o.id
        JOIN
    addresses a ON o.address_id = a.id
        LEFT JOIN
    games_categories gc ON g.id = gc.game_id
WHERE
    g.release_date IS NULL
        AND gc.category_id IS NULL
ORDER BY g.name;

# 10. Find all basic information for a game
DELIMITER $$

CREATE FUNCTION  udf_game_info_by_name (game_name VARCHAR (20))
RETURNS TEXT
DETERMINISTIC
BEGIN
	
    DECLARE game_id INT;
    DECLARE team_name VARCHAR(40);
    DECLARE address_text VARCHAR(50);
	
    SET game_id = (SELECT id FROM games 
					WHERE name LIKE game_name);
    
    SET team_name = (SELECT t.name FROM games g
					JOIN teams t ON g.team_id = t.id
                    WHERE g.id = game_id);
    
    SET address_text = (SELECT a.name FROM addresses a
						JOIN offices o ON a.id = o.address_id
                        JOIN teams t ON o.id = t.office_id
                        JOIN games g ON t.id = g.team_id
                        WHERE g.id = game_id);
    
	RETURN CONCAT_WS(' ', 'The', game_name, 'is developed by a', team_name, 'in an office with an address', address_text);
	
END$$

SELECT udf_game_info_by_name('Bitwolf') AS info$$

# 11. Update Budget of the Games
CREATE PROCEDURE udp_update_budget(min_game_rating FLOAT)
BEGIN

	UPDATE games g
    LEFT JOIN games_categories gc ON g.id = gc.game_id
    SET g.budget = g.budget + 100000,
		g.release_date = DATE_ADD(g.release_date, INTERVAL 1 YEAR)
	WHERE g.rating > min_game_rating 
			AND g.release_date IS NOT NULL
            AND gc.category_id IS NULL;
END$$

CALL udp_update_budget(8)$$