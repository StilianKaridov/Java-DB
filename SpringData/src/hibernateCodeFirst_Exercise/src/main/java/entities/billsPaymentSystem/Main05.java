package entities.billsPaymentSystem;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public class Main05 {
    public static void main(String[] args) {
        //Don't forget to change the name of the persistence unit
        //in the persistence.xml file
        EntityManager entityManager = Persistence
                .createEntityManagerFactory("bills")
                .createEntityManager();
    }
}
