package introToHibernate_Exercise.introToHibernate_Exercise.src.main.java;

import introToHibernate_Exercise.introToHibernate_Exercise.src.main.java.entities.Address;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class AddressesWithEmployeeCount {

    private static final String GET_ADDRESSES = "select a from Address a" +
            " order by a.employees.size desc";
    private static final String PRINT_FORMAT = "%s, %s - %d employees%n";

    public static void main(String[] args) {
        final EntityManagerFactory emf = Persistence.createEntityManagerFactory("soft_uni");
        final EntityManager entityManager = emf.createEntityManager();

        entityManager.createQuery(GET_ADDRESSES, Address.class)
                .setMaxResults(10)
                .getResultList()
                .forEach(e -> System.out.printf(PRINT_FORMAT,
                        e.getText(),
                        e.getTown().getName(),
                        e.getEmployees().size()));

        entityManager.close();
    }
}