# 1. Managers
SELECT 
    e.employee_id,
    CONCAT_WS(' ', e.first_name, e.last_name) 'full_name',
    d.department_id,
    d.name 'department_name'
FROM
    departments d
        JOIN
    employees e ON d.manager_id = e.employee_id
ORDER BY employee_id
LIMIT 5;

# 2. Town Addresses
SELECT 
    t.town_id, t.name 'town_name', a.address_text
FROM
    addresses a
        JOIN
    towns t ON a.town_id = t.town_id
WHERE
    t.name IN ('San Francisco' , 'Sofia', 'Carnation')
ORDER BY a.town_id , a.address_id;

# 3. Employees Without Managers
SELECT 
    e.employee_id,
    e.first_name,
    e.last_name,
    e.department_id,
    e.salary
FROM
    employees e
WHERE
    manager_id IS NULL;

# 4. High salary
SELECT 
    COUNT(*) 'count'
FROM
    employees
WHERE
    salary > (SELECT 
            AVG(salary)
        FROM
            employees);