package introToHibernate_Exercise.introToHibernate_Exercise.src.main.java;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Scanner;

public class ContainsEmployee {

    private static final String GET_COUNT_OF_EMPLOYEE_WITH_NAME_LIKE = "select count(e) from Employee e " +
            " where e.firstName like :fn and e.lastName like :ln";

    public static void main(String[] args) {
        final EntityManagerFactory emf = Persistence.createEntityManagerFactory("soft_uni");
        final EntityManager entityManager = emf.createEntityManager();

        final Scanner scanner = new Scanner(System.in);

        final String[] fullName = scanner.nextLine().split(" ");

        final String firstName = fullName[0];
        final String lastName = fullName[1];

        final Long countOfEmployee = entityManager.createQuery(GET_COUNT_OF_EMPLOYEE_WITH_NAME_LIKE, Long.class)
                .setParameter("fn", firstName)
                .setParameter("ln", lastName)
                .getSingleResult();

        if (countOfEmployee == 0) {
            System.out.println("No");
        } else {
            System.out.println("Yes");
        }

        entityManager.close();
    }
}