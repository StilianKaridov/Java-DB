# 1. Records’ Count
SELECT COUNT(`id`) AS `count`
FROM `wizzard_deposits`;

# 2. Longest Magic Wand
SELECT MAX(`magic_wand_size`) AS `longest_magic_wand`
FROM `wizzard_deposits`;

# 3. Longest Magic Wand per Deposit Groups
SELECT `deposit_group`, MAX(`magic_wand_size`) AS `longest_magic_wand`
FROM `wizzard_deposits`
GROUP BY `deposit_group`
ORDER BY `longest_magic_wand`, `deposit_group`;

# 4. Smallest Deposit Group per Magic Wand Size
SELECT `deposit_group`
FROM `wizzard_deposits`
GROUP BY `deposit_group`
ORDER BY AVG(`magic_wand_size`)
LIMIT 1;

# 5. Deposits Sum
SELECT `deposit_group`, SUM(`deposit_amount`) AS `total_sum`
FROM `wizzard_deposits`
GROUP BY `deposit_group`
ORDER BY `total_sum`;

# 6. Deposits Sum for Ollivander Family
SELECT `deposit_group`, SUM(`deposit_amount`) AS `total_sum`
FROM `wizzard_deposits`
WHERE `magic_wand_creator` = 'Ollivander family'
GROUP BY `deposit_group`
ORDER BY `deposit_group`;

# 7. Deposits Filter
SELECT `deposit_group`, SUM(`deposit_amount`) AS `total_sum`
FROM `wizzard_deposits`
WHERE `magic_wand_creator` = 'Ollivander family'
GROUP BY `deposit_group`
HAVING `total_sum` < 150000
ORDER BY `total_sum` DESC;

# 8. Deposit Charge
SELECT `deposit_group`, `magic_wand_creator`,
		MIN(`deposit_charge`) AS `min_deposit_charge`
FROM `wizzard_deposits`
GROUP BY `deposit_group`, `magic_wand_creator`
ORDER BY `magic_wand_creator`, `deposit_group`;

# 9. Age Groups

