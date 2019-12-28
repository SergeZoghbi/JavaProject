package DataAccess;

import BusinessLayer.LoginFrameLogic;
import Models.Circulaire;
import Models.User;
import com.mongodb.DBCursor;

import javax.swing.*;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class FacadeClass {

    private static FacadeClass facadeClass;

    private CirculaireRepository circulaireRepository;
    private DashboardDropDownDataRepository dashboardDropDownDataRepository;
    private LogsRepository logsRepository;
    private UserRepository userRepository;


    private FacadeClass() {
        try {
            this.circulaireRepository = new CirculaireRepository();
            this.dashboardDropDownDataRepository = new DashboardDropDownDataRepository();
            this.logsRepository = new LogsRepository();
            this.userRepository = new UserRepository();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public static synchronized FacadeClass getInstance() {
        if (facadeClass == null) {
            facadeClass = new FacadeClass();
        }
        return facadeClass;
    }


    public void AddCirculaire(String title , String faculty_name , String content) {
        this.circulaireRepository.AddCirculaire(title, faculty_name, content);
    }

    public DBCursor getCirculairesByDate(String date) {
        return this.circulaireRepository.getCirculairesByDate(date);
    }

    public DBCursor getLastCirculaire() {
        return this.circulaireRepository.getLastCirculaire();
    }

    public ResultSet AccountInfo(String UNI_ID) throws SQLException {
        return this.userRepository.AccountInfo(UNI_ID);
    }

    public ResultSet AccountInfo() throws SQLException {
        return this.userRepository.GetLastTenUsers();
    }

    public int AddUser(String FIRST_NAME, String LAST_NAME, Integer id_fac, Integer id_school, Integer id_type, Integer id_uni) throws SQLException {
        return this.userRepository.AddUser(FIRST_NAME, LAST_NAME, id_fac, id_school, id_type, id_uni);
    }

    public int UpdateUser(String ID_UNI, String FIRST_NAME, String LAST_NAME, Integer id_fac, Integer id_school, Integer id_type, Integer id_uni) throws SQLException {
        return this.userRepository.UpdateUser(ID_UNI,FIRST_NAME,LAST_NAME,id_fac,id_school,id_type,id_uni);
    }

    public int ChangePassword(String UNI_ID, String Old_Pass, String New_Pass) throws SQLException {
        return this.userRepository.ChangePassword(UNI_ID, Old_Pass, New_Pass);
    }

    public int DeleteUser(String UNI_ID) throws SQLException {
        return this.userRepository.DeleteUser(UNI_ID);
    }

    public int Login(String UNI_ID, String Password) throws SQLException {
        return this.userRepository.Login(UNI_ID, Password);
    }

    public int ResetPassword(String UNI_ID) throws SQLException {
        return this.userRepository.ResetPassword(UNI_ID);
    }

    public ResultSet CallGetProcedures(String procedureName) throws SQLException {
        return this.dashboardDropDownDataRepository.CallGetProcedures(procedureName);
    }

    public void LogSearch(String UNI_ID, String SearchWord) throws SQLException {
        this.logsRepository.LogSearch(UNI_ID, SearchWord);
    }

    public void LogCirculaireAdded(String UNI_ID) throws SQLException {
        this.logsRepository.LogCirculaireAdded(UNI_ID);
    }

    public void Log_auth(String UNI_ID) throws SQLException {
        this.logsRepository.Log_auth(UNI_ID);
    }
}
