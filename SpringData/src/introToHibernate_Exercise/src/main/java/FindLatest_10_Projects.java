import entities.Project;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;

public class FindLatest_10_Projects {

    private static final String GET_PROJECTS = "select p from Project p" +
            " order by p.startDate desc";
    private static final String PRINT_FORMAT = "Project name: %s%n" +
            "   Project Description: %s%n" +
            "   Project Start Date: %s%n" +
            "   Project End Date: %s%n";

    public static void main(String[] args) {
        final EntityManagerFactory emf = Persistence.createEntityManagerFactory("soft_uni");
        final EntityManager entityManager = emf.createEntityManager();

        entityManager.createQuery(GET_PROJECTS, Project.class)
                .setMaxResults(10)
                .getResultList()
                .stream()
                .sorted(Comparator.comparing(Project::getName))
                .forEach(p -> System.out.printf(PRINT_FORMAT,
                        p.getName(),
                        p.getDescription(),
                        p.getStartDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.s")),
                        p.getEndDate()));

        entityManager.close();
    }
}