package introToHibernate_Exercise.src.main.java;

import javax.persistence.*;

public class ChangeCasing {

    private static final String UPDATE_TOWNS_WITH_NAME_LENGTH_BELOW_OR_EQUAL_TO_5 =
            "update Town t set t.name = upper(t.name) where length(t.name) <= 5";

    public static void main(String[] args) {
        final EntityManagerFactory emf = Persistence.createEntityManagerFactory("soft_uni");
        final EntityManager entityManager = emf.createEntityManager();

        entityManager.getTransaction().begin();

        entityManager.createQuery(UPDATE_TOWNS_WITH_NAME_LENGTH_BELOW_OR_EQUAL_TO_5).executeUpdate();

        entityManager.getTransaction().commit();
        entityManager.close();
    }
}