package DataAccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnection {

    public Connection connection;
    private static MySQLConnection mySQLConnection = null;

    private MySQLConnection() throws ClassNotFoundException, SQLException {
        String driver = "org.gjt.mm.mysql.Driver";
        String url = "jdbc:mysql://localhost:3306/JavaProjectDB?autoReconnect=true&useSSL=false";
        String userName = "root";
        String password = "123456";
        Class.forName(driver);
        this.connection = DriverManager.getConnection(url, userName, password);
    }

    public static synchronized MySQLConnection getMySQLConnection() throws SQLException, ClassNotFoundException {
        if (mySQLConnection == null) {
            mySQLConnection = new MySQLConnection();
        }
        return mySQLConnection;
    }


}
