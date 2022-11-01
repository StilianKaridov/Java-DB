package entities.hospitalDB;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public class Main04 {
    public static void main(String[] args) {
        //Don't forget to change the name of the persistence unit
        //in the persistence.xml file
        EntityManager entityManager = Persistence
                .createEntityManagerFactory("hospital")
                .createEntityManager();
    }
}