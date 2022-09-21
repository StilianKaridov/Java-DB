# 1. Find Book Titles
SELECT `title` FROM `books`
WHERE SUBSTRING(`title`, 1, 3) = 'The'
ORDER BY `id`;

# 2. Replace Titles
SELECT REPLACE(`title`, 'The', '***') AS 'Title'
FROM `books`
WHERE SUBSTRING(`title`, 1, 3) = 'The'
ORDER BY `id`;

# 3. Sum Cost of All Books
SELECT ROUND(SUM(`cost`), 2) AS 'Sum'
FROM `books`;

# 4. Days Lived
SELECT 
	CONCAT_WS(' ', `first_name`, `last_name`) AS 'Full Name',
    TIMESTAMPDIFF(DAY, `born`, `died`) AS 'Days Lived'
FROM `authors`;

# 5. Harry Potter Books
SELECT `title` FROM `books`
WHERE `title` LIKE 'Harry Potter%'
ORDER BY `id`;