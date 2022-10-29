import entities.Address;
import entities.Employee;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Scanner;

public class AddingANewAddressAndUpdatingEmployee {

    private static final String GET_EMPLOYEE_BY_LAST_NAME = "select e from Employee e" +
            " where e.lastName like :ln";
    private static final String NEW_ADDRESS_TEXT = "Vitoshka 15";

    public static void main(String[] args) {
        final EntityManagerFactory emf = Persistence.createEntityManagerFactory("soft_uni");
        final EntityManager entityManager = emf.createEntityManager();

        final String employeeLastName = new Scanner(System.in).nextLine();

        entityManager.getTransaction().begin();

        Address newAddress = new Address();
        newAddress.setText(NEW_ADDRESS_TEXT);
        entityManager.persist(newAddress);

        Employee employee = entityManager.createQuery(GET_EMPLOYEE_BY_LAST_NAME, Employee.class)
                .setParameter("ln", employeeLastName)
                .getSingleResult();

        employee.setAddress(newAddress);

        entityManager.getTransaction().commit();
        entityManager.close();
    }
}