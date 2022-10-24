package DBAppsIntroduction_Lab;

import utilsAndConstants.Constants;
import utilsAndConstants.Utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class SoftUniDB {
    private static final String SELECT_EMPLOYEES_BY_SALARY = "select first_name, last_name" +
            " from employees" +
            " where salary > ?";

    private static final String EMPLOYEE_PRINT_FORMAT = "%s %s%n";
    private static final String COLUMN_LABEL_EMPLOYEE_FIRSTNAME = "first_name";
    private static final String COLUMN_LABEL_EMPLOYEE_LASTNAME = "last_name";


    public static void main(String[] args) throws SQLException {
        final Connection connection = Utils.getSQLConnection(Constants.SOFT_UNI_DATABASE_URL);

        final Scanner scanner = new Scanner(System.in);
        final double employeeSalary = Double.parseDouble(scanner.nextLine());

        final PreparedStatement employeesStatement = connection.prepareStatement(SELECT_EMPLOYEES_BY_SALARY);
        employeesStatement.setDouble(1, employeeSalary);

        final ResultSet employeesSet = employeesStatement.executeQuery();

        while (employeesSet.next()) {
            final String firstName = employeesSet.getString(COLUMN_LABEL_EMPLOYEE_FIRSTNAME);
            final String lastName = employeesSet.getString(COLUMN_LABEL_EMPLOYEE_LASTNAME);

            System.out.printf(EMPLOYEE_PRINT_FORMAT, firstName, lastName);
        }

        connection.close();
    }
}
