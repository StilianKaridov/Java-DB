package introToHibernate_Exercise.src.main.java;

import introToHibernate_Exercise.src.main.java.entities.Employee;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EmployeesWithSalaryOver50000 {

    private static final String GET_EMPLOYEE_WITH_SALARY_HIGHER_THAN_50000 =
            "select e from Employee e " +
                    "where e.salary > 50000";

    public static void main(String[] args) {
        final EntityManagerFactory emf = Persistence.createEntityManagerFactory("soft_uni");
        final EntityManager entityManager = emf.createEntityManager();

        entityManager.createQuery(GET_EMPLOYEE_WITH_SALARY_HIGHER_THAN_50000, Employee.class)
                .getResultList()
                .forEach(e -> System.out.println(e.getFirstName()));

        entityManager.close();
    }
}