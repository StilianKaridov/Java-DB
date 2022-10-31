package introToHibernate_Exercise.src.main.java;

import introToHibernate_Exercise.src.main.java.entities.Employee;
import introToHibernate_Exercise.src.main.java.entities.Project;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Comparator;
import java.util.Scanner;

public class GetEmployeeWithProject {

    private static final String GET_EMPLOYEE_BY_ID = "select e from Employee e" +
            " where e.id = :ed";
    private static final String EMPLOYEE_PRINT_FORMAT = "%s %s - %s%n";
    private static final String PROJECTS_PRINT_FORMAT = "   %s%n";

    public static void main(String[] args) {
        final EntityManagerFactory emf = Persistence.createEntityManagerFactory("soft_uni");
        final EntityManager entityManager = emf.createEntityManager();

        final int employeeID = Integer.parseInt(new Scanner(System.in).nextLine());

        Employee employee = entityManager.createQuery(GET_EMPLOYEE_BY_ID, Employee.class)
                .setParameter("ed", employeeID)
                .getSingleResult();

        System.out.printf(EMPLOYEE_PRINT_FORMAT,
                employee.getFirstName(),
                employee.getLastName(),
                employee.getJobTitle());

        employee.getProjects()
                .stream()
                .sorted(Comparator.comparing(Project::getName))
                .forEach(p -> System.out.printf(PROJECTS_PRINT_FORMAT, p.getName()));

        entityManager.close();
    }
}