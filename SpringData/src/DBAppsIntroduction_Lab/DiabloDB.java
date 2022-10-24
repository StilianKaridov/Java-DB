package DBAppsIntroduction_Lab;

import utilsAndConstants.Constants;
import utilsAndConstants.Utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class DiabloDB {
    private static final String SELECT_USER_GAMES = "select u.user_name," +
            " u.first_name," +
            " u.last_name," +
            " count(ug.user_id) games_count" +
            " from  users u" +
            " join users_games ug on u.id = ug.user_id" +
            " where u.user_name LIKE ?" +
            " group by ug.user_id";

    private static final String NO_SUCH_USER_PRINT_FORMAT = "No such user exists";
    private static final String EXISTING_USER_PRINT_FORMAT = "User: %s%n" +
            "%s %s has played %d games";

    private static final String COLUMN_LABEL_USER_FIRSTNAME = "first_name";
    private static final String COLUMN_LABEL_USER_LASTNAME = "last_name";
    private static final String COLUMN_LABEL_USER_GAMES_COUNT = "games_count";

    public static void main(String[] args) throws SQLException {
        final Connection connection = Utils.getSQLConnection(Constants.DIABLO_DATABASE_URL);

        final Scanner scanner = new Scanner(System.in);

        final String userName = scanner.nextLine();

        final PreparedStatement userStatement = connection.prepareStatement(SELECT_USER_GAMES);
        userStatement.setString(1, userName);

        final ResultSet userGamesSet = userStatement.executeQuery();

        if(!userGamesSet.next()){
            System.out.println(NO_SUCH_USER_PRINT_FORMAT);

            connection.close();
            return;
        }

        final String firstName = userGamesSet.getString(COLUMN_LABEL_USER_FIRSTNAME);
        final String lastName = userGamesSet.getString(COLUMN_LABEL_USER_LASTNAME);
        final int gamesCount = userGamesSet.getInt(COLUMN_LABEL_USER_GAMES_COUNT);

        System.out.printf(EXISTING_USER_PRINT_FORMAT, userName, firstName, lastName, gamesCount);

        connection.close();
    }
}
