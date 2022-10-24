package DBAppsIntroduction_Exercise;

import utilsAndConstants.Constants;
import utilsAndConstants.Utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class GetMinionNames {

    private static final String GET_VILLAIN_NAME = "select name" +
            " from villains" +
            " where id = ?";

    private static final String GET_MINIONS_NAME_AGE = "select name, age" +
            " from minions m" +
            " join minions_villains mv on m.id = mv.minion_id" +
            " where mv.villain_id = ?";

    private static final String COLUMN_LABEL_NAME = "name";
    private static final String COLUMN_LABEL_MINION_AGE = "age";
    private static final String NO_SUCH_VILLAIN_FORMAT = "No villain with ID %d exists in the database.";
    private static final String VILLAIN_PRINT_FORMAT = "Villain: %s%n";
    private static final String MINIONS_PRINT_FORMAT = "%d. %s %d%n";


    public static void main(String[] args) throws SQLException {
        final Connection connection = Utils.getSQLConnection(Constants.MINIONS_DATABASE_URL);

        final Scanner scanner = new Scanner(System.in);

        final int villainID = Integer.parseInt(scanner.nextLine());

        final PreparedStatement getVillainNameStatement = connection.prepareStatement(GET_VILLAIN_NAME);
        getVillainNameStatement.setInt(1, villainID);

        final ResultSet villainNameSet = getVillainNameStatement.executeQuery();

        if(!villainNameSet.next()) {
            System.out.printf(NO_SUCH_VILLAIN_FORMAT, villainID);

            connection.close();
            return;
        }

        final String villainName = villainNameSet.getString(COLUMN_LABEL_NAME);
        System.out.printf(VILLAIN_PRINT_FORMAT, villainName);

        final PreparedStatement getMinionsStatement = connection.prepareStatement(GET_MINIONS_NAME_AGE);
        getMinionsStatement.setInt(1, villainID);

        final ResultSet minionsSet = getMinionsStatement.executeQuery();

        for (int i = 1; minionsSet.next() ; i++) {
            final String minionName = minionsSet.getString(COLUMN_LABEL_NAME);
            final int minionAge = minionsSet.getInt(COLUMN_LABEL_MINION_AGE);

            System.out.printf(MINIONS_PRINT_FORMAT, i, minionName, minionAge);
        }

        connection.close();
    }
}
