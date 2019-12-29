package DataAccess;

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


    public Integer AddUser(String FIRST_NAME, String LAST_NAME, Integer id_fac, Integer id_school, Integer id_type, Integer id_uni) {
        String query = "{ ? = call addUser(?,?,?,?,?,?) }";
        try {
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Integer UpdateUser(String ID_UNI, String FIRST_NAME, String LAST_NAME, Integer id_fac, Integer id_school, Integer id_type, Integer id_uni){

        String query = "{ ? = call updateUser(?,?,?,?,?,?,?) }";

        try {
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
    return null;
    }


    public Integer ChangePassword(String UNI_ID, String Old_Pass, String New_Pass) {

        String query = "{ ? = call ChangePassword(?,?,?) }";

        try {
            this.statement = this.mySQLConnection.connection.prepareCall(query);
            this.statement.registerOutParameter(1, Types.JAVA_OBJECT);
            this.statement.setObject(2, UNI_ID);
            this.statement.setObject(3, Old_Pass);
            this.statement.setObject(4, New_Pass);
            this.resultSet = this.statement.executeQuery();
            this.resultSet.next();
            return Integer.parseInt(this.resultSet.getString(1));
        } catch (SQLException e) {
            e.printStackTrace();
        }
      return null;
    }

    public Integer DeleteUser(String UNI_ID) {

        String query = "{ ? = call deleteUser(?) }";

        try {
            this.statement = this.mySQLConnection.connection.prepareCall(query);
            this.statement.registerOutParameter(1, Types.JAVA_OBJECT);
            this.statement.setObject(2, UNI_ID);
            this.resultSet = this.statement.executeQuery();
            this.resultSet.next();
            return Integer.parseInt(this.resultSet.getString(1));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public Integer Login(String UNI_ID, String Password){
        String query = "{ ? = call login(?,?) }";

        try {
            this.statement = this.mySQLConnection.connection.prepareCall(query);
            this.statement.registerOutParameter(1, Types.JAVA_OBJECT);
            this.statement.setObject(2, UNI_ID);
            this.statement.setObject(3, Password);
            this.resultSet = this.statement.executeQuery();
            this.resultSet.next();
            return Integer.parseInt(this.resultSet.getString(1));
        } catch (SQLException e) {
            e.printStackTrace();
        }
       return null;
    }


    public Integer ResetPassword(String UNI_ID){

        String query = "{ ? = call resetPassword(?) }";

        try {
            this.statement = this.mySQLConnection.connection.prepareCall(query);
            this.statement.registerOutParameter(1, Types.JAVA_OBJECT);
            this.statement.setObject(2, UNI_ID);
            this.resultSet = this.statement.executeQuery();
            this.resultSet.next();
            return Integer.parseInt(this.resultSet.getString(1));
        } catch (SQLException e) {
            e.printStackTrace();
        }
       return null;
    }

}
