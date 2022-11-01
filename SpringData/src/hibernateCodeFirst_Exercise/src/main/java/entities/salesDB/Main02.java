package entities.salesDB;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main02 {
    public static void main(String[] args) {
        //Don't forget to change the name of the persistence unit
        //in the persistence.xml file
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("sales");
        EntityManager entityManager = emf.createEntityManager();

    }
}