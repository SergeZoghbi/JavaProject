package DataAccess;

import java.sql.CallableStatement;
import java.sql.SQLException;

public class LogsRepository {

    private CallableStatement statement;
    private MySQLConnection mySQLConnection;

    public LogsRepository() throws SQLException, ClassNotFoundException {
        this.mySQLConnection = MySQLConnection.getMySQLConnection();
    }


    public void LogSearch(String UNI_ID, String SearchWord)  {
        String query = "{ call logSearch(?,?) }";
        try {
            statement = mySQLConnection.connection.prepareCall(query);
            statement.setObject(1, UNI_ID);
            statement.setObject(2, SearchWord);
            statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void LogCirculaireAdded(String UNI_ID) {
        String query = "{ call log_add_circulaire(?) }";
        try {
            statement = mySQLConnection.connection.prepareCall(query);
            statement.setObject(1, UNI_ID);
            statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void Log_auth(String UNI_ID)  {
        String query = "{ call log_auth(?,?,?) }";
        try {
            statement = mySQLConnection.connection.prepareCall(query);
            statement.setObject(1, "logout");
            statement.setObject(2, 1);
            statement.setObject(3, UNI_ID);
            statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
