package DataAccess;

import java.sql.CallableStatement;
import java.sql.SQLException;

public class LogsRepository {

    private CallableStatement statement;
    private MySQLConnection mySQLConnection;

    public LogsRepository() throws SQLException, ClassNotFoundException {
        this.mySQLConnection = MySQLConnection.getMySQLConnection();
    }


    public void LogSearch(String UNI_ID, String SearchWord) throws SQLException {
        String query = "{ call logSearch(?,?) }";

        statement = mySQLConnection.connection.prepareCall(query);
        statement.setObject(1, UNI_ID);
        statement.setObject(2, SearchWord);
        statement.executeQuery();
    }

    public void LogCirculaireAdded(String UNI_ID) throws SQLException {
        String query = "{ call log_add_circulaire(?) }";

        statement = mySQLConnection.connection.prepareCall(query);
        statement.setObject(1, UNI_ID);
        statement.executeQuery();
    }

    public void Log_auth(String UNI_ID) throws SQLException {
        String query = "{ call log_auth(?,?,?) }";

        statement = mySQLConnection.connection.prepareCall(query);
        statement.setObject(1, "logout");
        statement.setObject(2, 1);
        statement.setObject(3, UNI_ID);
        statement.executeQuery();
    }
}
