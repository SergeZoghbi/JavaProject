package DataAccess;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DashboardDropDownDataRepository {

    private MySQLConnection mySQLConnection;

    public DashboardDropDownDataRepository() throws SQLException, ClassNotFoundException {
        this.mySQLConnection = MySQLConnection.getMySQLConnection();
    }


    public ResultSet CallGetProcedures(String procedureName) throws SQLException {
        String query = "{ call " + procedureName + "() }";
        CallableStatement statement = this.mySQLConnection.connection.prepareCall(query);
        return statement.executeQuery();
    }
}
