package introToHibernate_Exercise.introToHibernate_Exercise.src.main.java;

import introToHibernate_Exercise.introToHibernate_Exercise.src.main.java.entities.Address;
import introToHibernate_Exercise.introToHibernate_Exercise.src.main.java.entities.Employee;
import introToHibernate_Exercise.introToHibernate_Exercise.src.main.java.entities.Town;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Scanner;

public class RemoveTowns {

    private static final String GET_TOWN = "select t from Town t" +
            " where t.name like :tn";
    private static final String GET_ADDRESSES_BY_TOWN = "select a from Address a" +
            " where a.town.name like :tn";
    private static final String PRINT_FORMAT = "%d address in %s deleted";

    public static void main(String[] args) {
        final EntityManagerFactory emf = Persistence.createEntityManagerFactory("soft_uni");
        final EntityManager entityManager = emf.createEntityManager();

        final String townName = new Scanner(System.in).nextLine();

        entityManager.getTransaction().begin();

        entityManager.createQuery("select e from Employee e" +
                        " where e.address.town.name like :tn", Employee.class)
                .setParameter("tn", townName)
                .getResultList()
                .forEach(e -> e.setAddress(null));

        List<Address> addresses = entityManager.createQuery(GET_ADDRESSES_BY_TOWN, Address.class)
                .setParameter("tn", townName)
                .getResultList();

        final int countOfAddressesDeleted = addresses.size();

        for (Address a : addresses) {
            entityManager.remove(a);
        }

        Town town = entityManager.createQuery(GET_TOWN, Town.class)
                .setParameter("tn", townName)
                .getSingleResult();

        entityManager.remove(town);

        entityManager.getTransaction().commit();

        System.out.printf(PRINT_FORMAT, countOfAddressesDeleted, townName);

        entityManager.close();
    }
}