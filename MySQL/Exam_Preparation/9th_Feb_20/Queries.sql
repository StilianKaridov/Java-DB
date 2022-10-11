# 2. Insert
INSERT INTO coaches(first_name, last_name, salary, coach_level)
(
	SELECT
		first_name,
        last_name,
        salary,
        CHAR_LENGTH(first_name) 'coach_level'
	FROM players p
    WHERE p.age >= 45
);

# 3. Update - Needs more work
UPDATE coaches c
SET c.coach_level = c.coach_level + 1
WHERE c.first_name LIKE 'A%' 
		AND
	(SELECT COUNT(coach_id) FROM players_coaches pc
	WHERE pc.coach_id = c.id) >= 1;
    
# 4. Delete
DELETE FROM players
WHERE age >= 45;

# 5. Players
SELECT 
    first_name, age, salary
FROM
    players
ORDER BY salary DESC;

# 6. Young offense players without contract
SELECT 
    p.id,
    CONCAT_WS(' ', p.first_name, p.last_name) 'full_name',
    p.age,
    p.position,
    p.hire_date
FROM
    players p
WHERE
    p.age < 23 AND p.hire_date IS NULL
        AND p.position = 'A'
        AND (SELECT 
            sd.strength
        FROM
            skills_data sd
        WHERE
            p.skills_data_id = sd.id) > 50
ORDER BY p.salary , p.age;

# 7. Detail info for all teams
SELECT
	t.name 'team_name',
    t.established,
    t.fan_base,
	(SELECT COUNT(*) FROM players p
	WHERE t.id = p.team_id) `count_of_players`
FROM teams t
ORDER BY `count_of_players` DESC, t.fan_base DESC;

# 8. The fastest player by towns
SELECT
	MAX(sd.speed) `max_speed`,
    tw.name
FROM players p
	RIGHT JOIN skills_data sd ON p.skills_data_id = sd.id
    RIGHT JOIN teams t ON p.team_id = t.id
    RIGHT JOIN stadiums s ON t.stadium_id = s.id
    RIGHT JOIN towns tw ON s.town_id = tw.id
WHERE t.name != 'Devify'
GROUP BY tw.name
ORDER BY `max_speed` DESC, tw.name;

# 9. Total salaries and players by country
SELECT 
    c.name,
    COUNT(p.id) `total_count_of_players`,
    SUM(p.salary) `total_sum_of_salaries`
FROM
    players p
        RIGHT JOIN
    teams t ON p.team_id = t.id
        RIGHT JOIN
    stadiums s ON t.stadium_id = s.id
        RIGHT JOIN
    towns tw ON s.town_id = tw.id
        RIGHT JOIN
    countries c ON tw.country_id = c.id
GROUP BY c.name
ORDER BY `total_count_of_players` DESC , c.name;

# 10. Find all players that play on stadium
DELIMITER $$

CREATE FUNCTION udf_stadium_players_count(stadium_name VARCHAR(30))
RETURNS INT
DETERMINISTIC
BEGIN

	RETURN (SELECT COUNT(*) 
			FROM teams t
			JOIN stadiums s ON t.stadium_id = s.id
			JOIN players p ON t.id = p.team_id
            WHERE s.name = stadium_name
			);
    
END$$

SELECT udf_stadium_players_count ('Jaxworks') as `count`$$

# 11. Find good playmaker by teams
CREATE PROCEDURE udp_find_playmaker(min_dribble_points INT, team_name VARCHAR(45))
BEGIN

SELECT 
    CONCAT_WS(' ', p.first_name, p.last_name) 'full_name',
    p.age,
    p.salary,
    sd.dribbling,
	sd.speed,
	t.name
FROM players p
JOIN skills_data sd ON p.skills_data_id = sd.id
JOIN teams t ON p.team_id = t.id
WHERE sd.dribbling > min_dribble_points
	AND t.name = team_name
	AND sd.speed > (SELECT AVG(sk.speed) FROM skills_data sk)
ORDER BY sd.speed DESC
LIMIT 1;

END$$

CALL udp_find_playmaker(20, 'Skyble')$$
