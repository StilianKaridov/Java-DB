package DBAppsIntroduction_Exercise;

import utilsAndConstants.Constants;
import utilsAndConstants.Utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ChangeTownNamesCasing {

    private static final String SELECT_TOWNS_BY_COUNTRY = "select upper(t.name) as name from towns t where t.country LIKE ?";
    private static final String UPDATE_TOWNS_NAMES = "update towns set name = upper(name) where country LIKE ?";
    private static final String COLUMN_LABEL_TOWN_NAME = "name";
    private static final String NO_TOWNS_AFFECTED_FORMAT = "No town names were affected.";
    private static final String PRINT_FORMAT = "%d town names were affected.%n[%s]";

    public static void main(String[] args) throws SQLException {
        final Connection connection = Utils.getSQLConnection(Constants.MINIONS_DATABASE_URL);
        final Scanner scanner = new Scanner(System.in);

        final String country = scanner.nextLine();

        final PreparedStatement getTownsByCountryStatement = connection.prepareStatement(SELECT_TOWNS_BY_COUNTRY);
        getTownsByCountryStatement.setString(1, country);

        final ResultSet townsSet = getTownsByCountryStatement.executeQuery();

        List<String> towns = new ArrayList<>();

        if (!townsSet.next()) {
            System.out.println(NO_TOWNS_AFFECTED_FORMAT);
        } else {
            // Using do-while, so we did not lose the first town, if there is.
            // This is caused due to the above check in the IF statement
            do {
                String townName = townsSet.getString(COLUMN_LABEL_TOWN_NAME);
                towns.add(townName);
            } while (townsSet.next());

            final PreparedStatement updateTownsStatement = connection.prepareStatement(UPDATE_TOWNS_NAMES);
            updateTownsStatement.setString(1, country);
            updateTownsStatement.executeUpdate();

            System.out.printf(PRINT_FORMAT, towns.size(), String.join(", ", towns));
        }

        connection.close();
    }
}
