package introToHibernate_Exercise.introToHibernate_Exercise.src.main.java;

import introToHibernate_Exercise.introToHibernate_Exercise.src.main.java.entities.Department;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EmployeesMaximumSalaries {

    private static final String GET_MAX_SALARY_FOR_DEPARTMENTS = "select d" +
            " from Department d" +
            " group by d.id" +
            " having max(d.manager.salary) not between 30000 and 70000";

    private static final String PRINT_FORMAT = "%s %.2f%n";

    public static void main(String[] args) {
        final EntityManagerFactory emf = Persistence.createEntityManagerFactory("soft_uni");
        final EntityManager entityManager = emf.createEntityManager();

        entityManager.createQuery(GET_MAX_SALARY_FOR_DEPARTMENTS, Department.class)
                .getResultList()
                .forEach(d -> System.out.printf(PRINT_FORMAT, d.getName(), d.getManager().getSalary()));

        entityManager.close();
    }
}