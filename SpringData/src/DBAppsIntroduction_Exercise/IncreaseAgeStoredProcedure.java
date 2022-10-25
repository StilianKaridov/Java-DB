package DBAppsIntroduction_Exercise;

import utilsAndConstants.Constants;
import utilsAndConstants.Utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class IncreaseAgeStoredProcedure {

    private static final String SELECT_MINION_BY_ID = "select name, age from minions where id = ?";
    private static final String CALL_STORED_PROCEDURE = "call usp_get_older(?)";

    private static final String NO_SUCH_MINION_FORMAT = "There is no minion with the given ID!";
    private static final String PRINT_FORMAT = "%s %d";

    private static final String COLUMN_LABEL_MINION_NAME = "name";
    private static final String COLUMN_LABEL_MINION_AGE = "age";

    public static void main(String[] args) throws SQLException {
        final Connection connection = Utils.getSQLConnection(Constants.MINIONS_DATABASE_URL);
        final Scanner scanner = new Scanner(System.in);

        final int minionID = Integer.parseInt(scanner.nextLine());

        final PreparedStatement updateMinionAgeStatement = connection.prepareCall(CALL_STORED_PROCEDURE);
        updateMinionAgeStatement.setInt(1, minionID);
        updateMinionAgeStatement.executeUpdate();

        final PreparedStatement getMinionByIDStatement = connection.prepareStatement(SELECT_MINION_BY_ID);
        getMinionByIDStatement.setInt(1, minionID);
        final ResultSet minionSet = getMinionByIDStatement.executeQuery();

        if (!minionSet.next()) {
            System.out.println(NO_SUCH_MINION_FORMAT);
        }

        final String name = minionSet.getString(COLUMN_LABEL_MINION_NAME);
        final int age = minionSet.getInt(COLUMN_LABEL_MINION_AGE);

        System.out.printf(PRINT_FORMAT, name, age);

        connection.close();
    }
}
