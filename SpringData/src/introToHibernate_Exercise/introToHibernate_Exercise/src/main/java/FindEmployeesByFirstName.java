package introToHibernate_Exercise.introToHibernate_Exercise.src.main.java;

import introToHibernate_Exercise.introToHibernate_Exercise.src.main.java.entities.Employee;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Scanner;

public class FindEmployeesByFirstName {

    private static final String GET_EMPLOYEES_FIRST_NAME_STARTING_WITH = "select e from Employee e" +
            " where e.firstName LIKE concat(:fn,'%')";
    private static final String PRINT_FORMAT = "%s %s - %s - ($%.2f)%n";

    public static void main(String[] args) {
        final EntityManagerFactory emf = Persistence.createEntityManagerFactory("soft_uni");
        final EntityManager entityManager = emf.createEntityManager();

        final String firstNameStartingLetters = new Scanner(System.in).nextLine();

        entityManager.createQuery(GET_EMPLOYEES_FIRST_NAME_STARTING_WITH, Employee.class)
                .setParameter("fn", firstNameStartingLetters)
                .getResultList()
                .forEach(e -> System.out.printf(PRINT_FORMAT,
                        e.getFirstName(),
                        e.getLastName(),
                        e.getJobTitle(),
                        e.getSalary()));

        entityManager.close();
    }
}