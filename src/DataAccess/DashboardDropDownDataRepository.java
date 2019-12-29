package DataAccess;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DashboardDropDownDataRepository {

    private MySQLConnection mySQLConnection;

    public DashboardDropDownDataRepository() throws SQLException, ClassNotFoundException {
        this.mySQLConnection = MySQLConnection.getMySQLConnection();
    }


    public ResultSet CallGetProcedures(String procedureName) {
        String query = "{ call " + procedureName + "() }";
        try {
            CallableStatement statement = this.mySQLConnection.connection.prepareCall(query);
            return statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
