package entities.gringottsDB;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main {
    public static void main(String[] args) {
        //Don't forget to change the name of the persistence unit
        //in the persistence.xml file
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("gringotts");
        EntityManager entityManager = emf.createEntityManager();

    }
}