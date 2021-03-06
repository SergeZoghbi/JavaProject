package DataAccess;

import com.mongodb.DBCursor;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FacadeClass {

    private static FacadeClass facadeClass;
    private CirculaireRepository circulaireRepository;
    private DashboardDropDownDataRepository dashboardDropDownDataRepository;
    private LogsRepository logsRepository;
    private UserRepository userRepository;


    private FacadeClass() {

        this.circulaireRepository = new CirculaireRepository();
        this.dashboardDropDownDataRepository = new DashboardDropDownDataRepository();
        this.logsRepository = new LogsRepository();
        this.userRepository = new UserRepository();

    }

    public static synchronized FacadeClass getInstance() {
        if (facadeClass == null) {
            facadeClass = new FacadeClass();
        }
        return facadeClass;
    }


    public void AddCirculaire(String title, String faculty_name, String content) {
        this.circulaireRepository.AddCirculaire(title, faculty_name, content);
    }

    public DBCursor getCirculairesByDate(String date, String Fac_name) {
        return this.circulaireRepository.getCirculairesByDate(date, Fac_name);
    }

    public DBCursor getLastCirculaire(String facName) {
        return this.circulaireRepository.getLastCirculaire(facName);
    }

    public ResultSet AccountInfo(String UNI_ID) {
        return this.userRepository.AccountInfo(UNI_ID);
    }

    public ResultSet getFacultyName(String UNI_ID) throws SQLException {
        return this.userRepository.getFacultyName(UNI_ID);
    }


    public String AddUser(String FIRST_NAME, String LAST_NAME, Integer id_fac, Integer id_school, Integer id_type, Integer id_uni) {
        return this.userRepository.AddUser(FIRST_NAME, LAST_NAME, id_fac, id_school, id_type, id_uni);
    }

    public Integer UpdateUser(String ID_UNI, String FIRST_NAME, String LAST_NAME, Integer id_fac, Integer id_school, Integer id_type, Integer id_uni) {
        return this.userRepository.UpdateUser(ID_UNI, FIRST_NAME, LAST_NAME, id_fac, id_school, id_type, id_uni);
    }

    public Integer ChangePassword(String UNI_ID, String Old_Pass, String New_Pass) {
        return this.userRepository.ChangePassword(UNI_ID, Old_Pass, New_Pass);
    }

    public Integer DeleteUser(String UNI_ID) {
        return this.userRepository.DeleteUser(UNI_ID);
    }

    public Integer Login(String UNI_ID, String Password) {
        return this.userRepository.Login(UNI_ID, Password);
    }

    public Integer ResetPassword(String UNI_ID) {
        return this.userRepository.ResetPassword(UNI_ID);
    }

    public ResultSet CallGetProcedures(String procedureName) {
        return this.dashboardDropDownDataRepository.CallGetProcedures(procedureName);
    }

    public void LogSearch(String UNI_ID, String SearchWord) {
        this.logsRepository.LogSearch(UNI_ID, SearchWord);
    }

    public void LogCirculaireAdded(String UNI_ID) {
        this.logsRepository.LogCirculaireAdded(UNI_ID);
    }

    public void Log_auth(String UNI_ID) {
        this.logsRepository.Log_auth(UNI_ID);
    }
}
