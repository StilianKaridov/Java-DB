package DBAppsIntroduction_Exercise;

import utilsAndConstants.Constants;
import utilsAndConstants.Utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GetVillainsNames {

    private static final String VILLAINS_MINIONS = "select v.name, count(distinct mv.minion_id) minions_count" +
            " from villains v" +
            " join minions_villains mv on v.id = mv.villain_id" +
            " group by mv.villain_id" +
            " having minions_count > 15" +
            " order by minions_count desc";

    private static final String COLUMN_LABEL_VILLAIN_NAME = "name";
    private static final String COLUMN_LABEL_MINIONS_COUNT = "minions_count";
    private static final String PRINT_FORMAT = "%s %d";

    public static void main(String[] args) throws SQLException {
        final Connection connection = Utils.getSQLConnection(Constants.MINIONS_DATABASE_URL);

        final PreparedStatement getVillainsStatement = connection.prepareStatement(VILLAINS_MINIONS);

        final ResultSet villainsSet = getVillainsStatement.executeQuery();

        while(villainsSet.next()) {
            final String villainName = villainsSet.getString(COLUMN_LABEL_VILLAIN_NAME);
            final int minionsCount = villainsSet.getInt(COLUMN_LABEL_MINIONS_COUNT);

            System.out.printf(PRINT_FORMAT, villainName, minionsCount);
        }

        connection.close();
    }
}
