# 2. Insert
INSERT INTO actors (first_name, last_name, birthdate, height, awards, country_id)
(
	SELECT 
		REVERSE(first_name),
		REVERSE(last_name),
		DATE_SUB(birthdate, INTERVAL 2 DAY),
		height + 10,
		country_id,
		3
	FROM
		actors
	WHERE
		id <= 10
);

# 3. Update
UPDATE movies_additional_info
SET runtime = runtime - 10
WHERE id >= 15 AND id <= 25;

# 4. Delete
DELETE c FROM countries c
LEFT JOIN movies m ON c.id = m.country_id
WHERE m.country_id IS NULL;

# 5. Countries
SELECT *
FROM countries c
ORDER BY c.currency DESC, c.id;

# 6. Old movies
SELECT m.id, m.title, mi.runtime, mi.budget, mi.release_date
FROM movies m
JOIN movies_additional_info mi ON m.movie_info_id = mi.id
WHERE YEAR(release_date) BETWEEN 1996 AND 1999
ORDER BY runtime, id
LIMIT 20;

# 7. Movie casting
SELECT
	CONCAT_WS(' ', a.first_name, a.last_name) `full_name`,
    CONCAT(REVERSE(a.last_name), CHAR_LENGTH(a.last_name), '@cast.com') `email`,
    (2022 - YEAR(a.birthdate)) `age`,
    a.height
FROM actors a
LEFT JOIN movies_actors ma ON a.id = ma.actor_id
WHERE ma.actor_id IS NULL
ORDER BY a.height;

# 8. International festival
SELECT 
	c.name, 
	COUNT(m.country_id) `movies_count`
FROM countries c
JOIN movies m ON c.id = m.country_id
GROUP BY c.name
HAVING `movies_count` >= 7
ORDER BY c.name DESC;

# 9. Rating system
SELECT
	m.title,
    CASE
		WHEN mi.rating <= 4 THEN 'poor'
        WHEN mi.rating <= 7 THEN 'good'
        ELSE 'excellent'
    END 'rating',
    IF(mi.has_subtitles IS TRUE, 'english', '-') 'subtitles',
    mi.budget
FROM movies m
JOIN movies_additional_info mi ON m.movie_info_id = mi.id
ORDER BY mi.budget DESC;

# 10. History movies
DELIMITER $$

CREATE FUNCTION udf_actor_history_movies_count(full_name VARCHAR(50))
RETURNS INT
NOT DETERMINISTIC
READS SQL DATA
BEGIN

	RETURN (SELECT COUNT(ma.actor_id)
			FROM actors a
			JOIN movies_actors ma ON a.id = ma.actor_id
			JOIN movies m ON ma.movie_id = m.id
			JOIN genres_movies gm ON m.id = gm.movie_id
			JOIN genres g ON gm.genre_id = g.id
			WHERE g.name = 'History' AND CONCAT_WS(' ', first_name, last_name) LIKE full_name
		);

END$$

SELECT udf_actor_history_movies_count('Stephan Lundberg') 
	AS	'history_movies'$$
    
# 11. Movie awards
CREATE PROCEDURE udp_award_movie(movie_title VARCHAR(50))
BEGIN 
    
    UPDATE actors a
	JOIN movies_actors ma ON a.id = ma.actor_id
    JOIN movies m ON ma.movie_id = m.id
	SET a.awards = a.awards + 1
    WHERE m.title = movie_title;

END$$

CALL udp_award_movie('Tea For Two')$$