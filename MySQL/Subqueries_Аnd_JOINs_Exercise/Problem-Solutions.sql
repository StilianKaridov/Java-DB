# 1. Employee Address
SELECT 
    e.employee_id, e.job_title, e.address_id, a.address_text
FROM
    employees e
        JOIN
    addresses a ON e.address_id = a.address_id
ORDER BY address_id
LIMIT 5;

# 2. Addresses with Towns
SELECT 
    e.first_name, e.last_name, t.name 'town', a.address_text
FROM
    addresses a
        JOIN
    employees e ON a.address_id = e.address_id
        JOIN
    towns t ON a.town_id = t.town_id
ORDER BY e.first_name , e.last_name
LIMIT 5;

# 3. Sales Employee
SELECT 
    e.employee_id, e.first_name, e.last_name, d.name
FROM
    employees e
        JOIN
    departments d ON e.department_id = d.department_id
WHERE
    d.name = 'Sales'
ORDER BY employee_id DESC;

# 4. Employee Departments
SELECT 
    e.employee_id,
    e.first_name,
    e.salary 'salary',
    d.name 'department_name'
FROM
    employees e
        JOIN
    departments d ON e.department_id = d.department_id
WHERE
    e.salary > 15000
ORDER BY d.department_id DESC
LIMIT 5;

# 5. Employees Without Project
SELECT 
    e.employee_id, e.first_name
FROM
    employees e
WHERE
    e.employee_id NOT IN (SELECT 
            ep.employee_id
        FROM
            employees_projects ep)
ORDER BY e.employee_id DESC
LIMIT 3;

# 6. Employees Hired After
SELECT 
    e.first_name,
    e.last_name,
    e.hire_date,
    d.name 'department_name'
FROM
    employees e
        JOIN
    departments d ON e.department_id = d.department_id
WHERE
    hire_date > 1999 / 01 / 01
        AND d.name IN ('Sales' , 'Finance')
ORDER BY hire_date;

# 7. Employees with Project
SELECT 
    e.employee_id, e.first_name, p.name 'project_name'
FROM
    employees e
        JOIN
    employees_projects ep ON e.employee_id = ep.employee_id
        JOIN
    projects p ON ep.project_id = p.project_id
WHERE
    DATE(p.start_date) > '2002-08-13'
        AND p.end_date IS NULL
ORDER BY e.first_name , project_name
LIMIT 5;

# 8. Employee 24
SELECT 
    e.employee_id,
    e.first_name,
    IF(YEAR(p.start_date) >= 2005,
        NULL,
        p.name) 'project_name'
FROM
    employees e
        JOIN
    employees_projects ep ON e.employee_id = ep.employee_id
        JOIN
    projects p ON ep.project_id = p.project_id
WHERE
    ep.employee_id = 24
ORDER BY project_name;

# 9. Employee Manager
SELECT 
    e.employee_id,
    e.first_name,
    e.manager_id,
    e1.first_name 'manager_name'
FROM
    employees e
        JOIN
    employees e1 ON e.manager_id = e1.employee_id
WHERE
    e.manager_id IN (3 , 7)
ORDER BY e.first_name;

# 10. Employee Summary
SELECT 
    e.employee_id,
    CONCAT_WS(' ', e.first_name, e.last_name) 'employee_name',
    CONCAT_WS(' ', e1.first_name, e1.last_name) 'manager_name',
    d.name 'department_name'
FROM
    employees e
        JOIN
    employees e1 ON e.manager_id = e1.employee_id
        JOIN
    departments d ON e.department_id = d.department_id
ORDER BY e.employee_id
LIMIT 5;

# 11. Min Average Salary
SELECT AVG(salary) 'min_average_salary'
FROM employees
GROUP BY department_id
ORDER BY min_average_salary
LIMIT 1;

# 12. Highest Peaks in Bulgaria
SELECT 
    c.country_code, m.mountain_range, p.peak_name, p.elevation
FROM
    countries c
        JOIN
    mountains_countries mc ON c.country_code = mc.country_code
        JOIN
    mountains m ON mc.mountain_id = m.id
        JOIN
    peaks p ON m.id = p.mountain_id
WHERE
    p.elevation > 2835
        AND c.country_code LIKE 'BG'
ORDER BY p.elevation DESC;

# 13. Count Mountain Ranges
SELECT 
    c.country_code, COUNT(m.mountain_range) 'mountain_range'
FROM
    mountains m
        JOIN
    mountains_countries c ON m.id = c.mountain_id
WHERE
    c.country_code IN ('BG', 'RU', 'US')
GROUP BY c.country_code
ORDER BY mountain_range DESC;

# 14. Countries with Rivers


# 15. Continents and Currencies


# 16. Countries Without Any Mountains
SELECT 
    COUNT(country_code) - (SELECT 
            COUNT(DISTINCT country_code)
        FROM
            mountains_countries) 'country_count'
FROM
    countries;

# 17. Highest Peak and Longest River by Country
