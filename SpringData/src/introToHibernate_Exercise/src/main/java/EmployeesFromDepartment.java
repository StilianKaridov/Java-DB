import entities.Employee;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EmployeesFromDepartment {

    private static final String GET_EMPLOYEES_BY_DEPARTMENT =
            "select e from Employee e where e.department.name LIKE :dn" +
                    " order by e.salary, e.id";

    private static final String PRINT_FORMAT = "%s %s from %s - $%.2f%n";
    private static final String DEPARTMENT_NAME = "Research and Development";

    public static void main(String[] args) {
        final EntityManagerFactory emf = Persistence.createEntityManagerFactory("soft_uni");
        final EntityManager entityManager = emf.createEntityManager();

        entityManager.createQuery(GET_EMPLOYEES_BY_DEPARTMENT, Employee.class)
                .setParameter("dn", DEPARTMENT_NAME)
                .getResultList()
                .forEach(e -> System.out.printf(PRINT_FORMAT,
                        e.getFirstName(),
                        e.getLastName(),
                        e.getDepartment().getName(),
                        e.getSalary()));

        entityManager.close();
    }
}