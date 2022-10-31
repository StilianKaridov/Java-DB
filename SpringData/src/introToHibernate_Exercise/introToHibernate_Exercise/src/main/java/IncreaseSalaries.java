package introToHibernate_Exercise.introToHibernate_Exercise.src.main.java;

import introToHibernate_Exercise.introToHibernate_Exercise.src.main.java.entities.Employee;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class IncreaseSalaries {

    private static final String PRINT_FORMAT = "%s %s ($%.2f)%n";
    private static final String UPDATE_EMPLOYEES_SALARY_BY_DEPARTMENT = "update Employee e" +
            " set e.salary = e.salary * 1.12" +
            " where e.department.id IN (1, 2, 4, 11)";

    private static final String GET_EMPLOYEES_WITH_UPDATED_SALARY = "select e from Employee e" +
            " where e.department.id IN (1, 2, 4, 11)";

    public static void main(String[] args) {
        final EntityManagerFactory emf = Persistence.createEntityManagerFactory("soft_uni");
        final EntityManager entityManager = emf.createEntityManager();

        entityManager.getTransaction().begin();

        entityManager.createQuery(UPDATE_EMPLOYEES_SALARY_BY_DEPARTMENT)
                .executeUpdate();

        entityManager.getTransaction().commit();

        entityManager.createQuery(GET_EMPLOYEES_WITH_UPDATED_SALARY, Employee.class)
                .getResultList()
                .forEach(e -> System.out.printf(PRINT_FORMAT,
                        e.getFirstName(),
                        e.getLastName(),
                        e.getSalary()));

        entityManager.close();
    }
}