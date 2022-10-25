package DBAppsIntroduction_Exercise;

import utilsAndConstants.Constants;
import utilsAndConstants.Utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PrintAllMinionNames {

    private static final String SELECT_MINIONS_NAMES = "select name from minions";
    private static final String COLUMN_LABEL_MINION_NAME = "name";
    private static final String PRINT_FORMAT = "%s%n%s%n";

    public static void main(String[] args) throws SQLException {
        final Connection connection = Utils.getSQLConnection(Constants.MINIONS_DATABASE_URL);

        final PreparedStatement getMinionsNamesStatement = connection.prepareStatement(SELECT_MINIONS_NAMES);

        final ResultSet minionsNamesSet = getMinionsNamesStatement.executeQuery();

        List<String> names = new ArrayList<>();
        while(minionsNamesSet.next()){
            final String currentName = minionsNamesSet.getString(COLUMN_LABEL_MINION_NAME);
            names.add(currentName);
        }

        final int midIndex = names.size() / 2;

        for (int i = 0; i < midIndex; i++) {
            String firstToPrint = names.get(i);
            String secondToPrint = names.get(names.size() - i - 1);

            System.out.printf(PRINT_FORMAT, firstToPrint, secondToPrint);
        }

        connection.close();
    }
}
