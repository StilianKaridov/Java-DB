package DBAppsIntroduction_Exercise;

import utilsAndConstants.Constants;
import utilsAndConstants.Utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class RemoveVillain {

    private static final String SELECT_VILLAIN_BY_ID = "select v.name, count(mv.villain_id) minions_count" +
            " from villains v" +
            " join minions_villains mv on v.id = mv.villain_id" +
            " where v.id = ?" +
            " group by mv.villain_id;";

    private static final String DELETE_VILLAIN = "delete from villains" +
            " where id = ?";

    private static final String DELETE_VILLAINS_MINIONS = "delete from minions_villains" +
            " where villain_id = ?";


    private static final String COLUMN_LABEL_VILLAIN_NAME = "name";
    private static final String COLUMN_LABEL_MINIONS_COUNT = "minions_count";
    private static final String PRINT_FORMAT = "%s was deleted%n" +
            "%d minions released";

    public static void main(String[] args) throws SQLException {
        final Connection connection = Utils.getSQLConnection(Constants.MINIONS_DATABASE_URL);
        final Scanner scanner = new Scanner(System.in);

        final int villainID = Integer.parseInt(scanner.nextLine());

        final PreparedStatement getVillainByIDStatement = connection.prepareStatement(SELECT_VILLAIN_BY_ID);
        getVillainByIDStatement.setInt(1, villainID);

        final ResultSet villainSet = getVillainByIDStatement.executeQuery();

        if(!villainSet.next()){
            System.out.println("No such villain was found");

            connection.close();
            return;
        }

        final String villainName = villainSet.getString(COLUMN_LABEL_VILLAIN_NAME);
        final int minionsCount = villainSet.getInt(COLUMN_LABEL_MINIONS_COUNT);

        connection.setAutoCommit(false);
        try {
            final PreparedStatement deleteVillainsMinionsStatement = connection.prepareStatement(DELETE_VILLAINS_MINIONS);
            deleteVillainsMinionsStatement.setInt(1, villainID);
            deleteVillainsMinionsStatement.executeUpdate();

            final PreparedStatement deleteVillainStatement = connection.prepareStatement(DELETE_VILLAIN);
            deleteVillainStatement.setInt(1, villainID);
            deleteVillainStatement.executeUpdate();

            connection.commit();
            System.out.printf(PRINT_FORMAT, villainName, minionsCount);
        } catch (SQLException e) {
            connection.rollback();
        }

        connection.close();
    }
}
