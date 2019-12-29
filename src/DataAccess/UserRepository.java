package DataAccess;

import Models.User;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class UserRepository {

    private CallableStatement statement;
    private MySQLConnection mySQLConnection;
    private ResultSet resultSet;

    public UserRepository() throws SQLException, ClassNotFoundException {
        this.mySQLConnection = MySQLConnection.getMySQLConnection();
    }


    public ResultSet AccountInfo(String UNI_ID) throws SQLException {
        String query = "{ call AccountInfo(?) }";
        this.statement = this.mySQLConnection.connection.prepareCall(query);
        this.statement.setObject(1, UNI_ID);
        this.resultSet = this.statement.executeQuery();
        return this.resultSet;
    }

    public ResultSet getFacultyName(String UNI_ID) throws SQLException {
        String query = "{ call getFacultyName(?) }";
        this.statement = this.mySQLConnection.connection.prepareCall(query);
        this.statement.setObject(1, UNI_ID);
        this.resultSet = this.statement.executeQuery();
        return this.resultSet;
    }


    public ResultSet GetLastTenUsers() throws SQLException {
        String query = "{ call getLastTenUsers() }";
        this.statement = this.mySQLConnection.connection.prepareCall(query);
        this.resultSet = this.statement.executeQuery();
        return this.resultSet;
    }


    public int AddUser(String FIRST_NAME, String LAST_NAME, Integer id_fac, Integer id_school, Integer id_type, Integer id_uni) throws SQLException {
        String query = "{ ? = call addUser(?,?,?,?,?,?) }";
        this.statement = this.mySQLConnection.connection.prepareCall(query);
        this.statement.registerOutParameter(1, Types.JAVA_OBJECT);
        this.statement.setObject(2, FIRST_NAME);
        this.statement.setObject(3, LAST_NAME);
        this.statement.setObject(4, id_fac);
        this.statement.setObject(5, id_school);
        this.statement.setObject(6, id_type);
        this.statement.setObject(7, id_uni);
        this.resultSet = this.statement.executeQuery();
        this.resultSet.next();
        return Integer.parseInt(this.resultSet.getString(1));
    }

    public int UpdateUser(String ID_UNI, String FIRST_NAME, String LAST_NAME, Integer id_fac, Integer id_school, Integer id_type, Integer id_uni) throws SQLException {

        String query = "{ ? = call updateUser(?,?,?,?,?,?,?) }";

        this.statement = this.mySQLConnection.connection.prepareCall(query);
        this.statement.registerOutParameter(1, Types.JAVA_OBJECT);
        this.statement.setObject(2, ID_UNI);
        this.statement.setObject(3, FIRST_NAME);
        this.statement.setObject(4, LAST_NAME);
        this.statement.setObject(5, id_fac);
        this.statement.setObject(6, id_school);
        this.statement.setObject(7, id_type);
        this.statement.setObject(8, id_uni);
        this.resultSet = this.statement.executeQuery();
        this.resultSet.next();
        return Integer.parseInt(this.resultSet.getString(1));
    }


    public int ChangePassword(String UNI_ID, String Old_Pass, String New_Pass) throws SQLException {

        String query = "{ ? = call ChangePassword(?,?,?) }";

        this.statement = this.mySQLConnection.connection.prepareCall(query);
        this.statement.registerOutParameter(1, Types.JAVA_OBJECT);
        this.statement.setObject(2, UNI_ID);
        this.statement.setObject(3, Old_Pass);
        this.statement.setObject(4, New_Pass);
        this.resultSet = this.statement.executeQuery();
        this.resultSet.next();
        return Integer.parseInt(this.resultSet.getString(1));
    }

    public int DeleteUser(String UNI_ID) throws SQLException {

        String query = "{ ? = call deleteUser(?) }";

        this.statement = this.mySQLConnection.connection.prepareCall(query);
        this.statement.registerOutParameter(1, Types.JAVA_OBJECT);
        this.statement.setObject(2, UNI_ID);
        this.resultSet = this.statement.executeQuery();
        this.resultSet.next();
        return Integer.parseInt(this.resultSet.getString(1));
    }


    public int Login(String UNI_ID, String Password) throws SQLException {

        String query = "{ ? = call login(?,?) }";

        this.statement = this.mySQLConnection.connection.prepareCall(query);
        this.statement.registerOutParameter(1, Types.JAVA_OBJECT);
        this.statement.setObject(2, UNI_ID);
        this.statement.setObject(3, Password);
        this.resultSet = this.statement.executeQuery();
        this.resultSet.next();
        return Integer.parseInt(this.resultSet.getString(1));
    }


    public int ResetPassword(String UNI_ID) throws SQLException {

        String query = "{ ? = call resetPassword(?) }";

        this.statement = this.mySQLConnection.connection.prepareCall(query);
        this.statement.registerOutParameter(1, Types.JAVA_OBJECT);
        this.statement.setObject(2, UNI_ID);
        this.resultSet = this.statement.executeQuery();
        this.resultSet.next();
        return Integer.parseInt(this.resultSet.getString(1));
    }

}
