package DBAppsIntroduction_Exercise;

import utilsAndConstants.Constants;
import utilsAndConstants.Utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class IncreaseMinionsAge {

    private static final String SELECT_MINIONS = "select name, age from minions";

    private static String UPDATE_MINIONS_AGE_NAMES = "update minions" +
            " set age = age + 1, name = lower(name)" +
            " where id IN (?)";

    private static final String COLUMN_LABEL_MINION_NAME = "name";
    private static final String COLUMN_LABEL_MINION_AGE = "age";

    private static final String PRINT_FORMAT = "%s %d%n";

    public static void main(String[] args) throws SQLException {
        final Connection connection = Utils.getSQLConnection(Constants.MINIONS_DATABASE_URL);
        final Scanner scanner = new Scanner(System.in);

        final List<String> minionsIDs = Arrays.stream(scanner.nextLine().split("\\s+"))
                .map(String::valueOf)
                .collect(Collectors.toList());

        UPDATE_MINIONS_AGE_NAMES = UPDATE_MINIONS_AGE_NAMES.replace("?", String.join(", ", minionsIDs));
        final PreparedStatement updateMinionsStatement = connection.prepareStatement(UPDATE_MINIONS_AGE_NAMES);
        updateMinionsStatement.executeUpdate();

        final PreparedStatement getMinionsStatement = connection.prepareStatement(SELECT_MINIONS);
        final ResultSet minionsSet = getMinionsStatement.executeQuery();

        while (minionsSet.next()) {
            String name = minionsSet.getString(COLUMN_LABEL_MINION_NAME);
            int age = minionsSet.getInt(COLUMN_LABEL_MINION_AGE);

            System.out.printf(PRINT_FORMAT, name, age);
        }

        connection.close();
    }
}
