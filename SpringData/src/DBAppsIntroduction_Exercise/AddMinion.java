package DBAppsIntroduction_Exercise;

import utilsAndConstants.Constants;
import utilsAndConstants.Utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class AddMinion {

    private static final String GET_TOWN_ID = "select t.id" +
            " from towns t" +
            " where t.name LIKE ?";
    private static final String GET_VILLAIN_ID = "select v.id" +
            " from villains v" +
            " where v.name LIKE ?";
    private static final String GET_MINION_ID = "select m.id" +
            " from minions m" +
            " where m.name LIKE ?";

    private static final String INSERT_TOWN = "insert into towns(name) values (?)";
    private static final String INSERT_MINION = "insert into minions(name , age, town_id) values(?, ?, ?)";
    private static final String INSERT_VILLAIN = "insert into villains(name, evilness_factor) values(?, 'evil')";
    private static final String INSERT_MINIONS_VILLAINS = "insert into minions_villains values (?, ?)";

    private static final String SUCCESSFULLY_ADDED_TOWN_FORMAT = "Town %s was added to the database.%n";
    private static final String SUCCESSFULLY_ADDED_VILLAIN_FORMAT = "Villain %s was added to the database.%n";
    private static final String SUCCESSFULLY_INSERT_MAPPING_TABLE_FORMAT = "Successfully added %s to be minion of %s.%n";

    private static final String COLUMN_LABEL_ID = "id";

    public static void main(String[] args) throws SQLException {
        final Connection connection = Utils.getSQLConnection(Constants.MINIONS_DATABASE_URL);

        final Scanner scanner = new Scanner(System.in);

        final String[] minionInfo = scanner.nextLine().split(" ");

        // Get minion info
        final String minionName = minionInfo[1];
        final int minionAge = Integer.parseInt(minionInfo[2]);
        final String townName = minionInfo[3];

        // Town statement
        ResultSet townIDSet = statement(connection, townName, GET_TOWN_ID, INSERT_TOWN, SUCCESSFULLY_ADDED_TOWN_FORMAT);

        // Insert into minions table
        final PreparedStatement insertMinionStatement = connection.prepareStatement(INSERT_MINION);
        insertMinionStatement.setString(1, minionName);
        insertMinionStatement.setInt(2, minionAge);

        final int townID = townIDSet.getInt(COLUMN_LABEL_ID);
        insertMinionStatement.setInt(3, townID);
        insertMinionStatement.executeUpdate();

        // If the villain doesn't exist, insert in villains table
        final String villainName = scanner.nextLine().split(" ")[1];

        ResultSet villainIDSet = statement(connection, villainName, GET_VILLAIN_ID, INSERT_VILLAIN, SUCCESSFULLY_ADDED_VILLAIN_FORMAT);

        // Insert in mapping table
        final PreparedStatement getMinionIDStatement = connection.prepareStatement(GET_MINION_ID);
        getMinionIDStatement.setString(1, minionName);

        final ResultSet minionIDSet = getMinionIDStatement.executeQuery();

        minionIDSet.next();
        final int minionID = minionIDSet.getInt(COLUMN_LABEL_ID);
        final int villainID = villainIDSet.getInt(COLUMN_LABEL_ID);

        final PreparedStatement insertMinionsVillainsStatement = connection.prepareStatement(INSERT_MINIONS_VILLAINS);

        insertMinionsVillainsStatement.setInt(1, minionID);
        insertMinionsVillainsStatement.setInt(2, villainID);
        insertMinionsVillainsStatement.executeUpdate();

        System.out.printf(SUCCESSFULLY_INSERT_MAPPING_TABLE_FORMAT, minionName, villainName);
        connection.close();
    }

    private static ResultSet statement(Connection connection,
                                       String argument,
                                       String getIDQuery,
                                       String insertQuery,
                                       String printFormat
                                       ) throws SQLException {
        // Argument statement
        final PreparedStatement getTownIDStatement = connection.prepareStatement(getIDQuery);
        getTownIDStatement.setString(1, argument);

        ResultSet townIDSet = getTownIDStatement.executeQuery();

        // If this ID doesn't exist, insert with insertQuery
        if (!townIDSet.next()) {
            final PreparedStatement insertTownStatement = connection.prepareStatement(insertQuery);
            insertTownStatement.setString(1, argument);
            insertTownStatement.executeUpdate();

            townIDSet = getTownIDStatement.executeQuery();
            townIDSet.next();
            System.out.printf(printFormat, argument);
        }

        return townIDSet;
    }
}