package BusinessLayer;

import Common.MD5;
import DataAccess.FacadeClass;
import Models.Circulaire;
import Presentation.UserUIMain;
import com.google.gson.Gson;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class UserLogic {


    private FacadeClass facadeClass = FacadeClass.getInstance();
    private ArrayList<Circulaire> CirculaireStore = null;

    public void AddNewCirculaire(String uid, String titre, String faculte, String contenu) {
        facadeClass.AddCirculaire(titre, faculte, contenu);
        DateFormat dateFormat = new SimpleDateFormat("dd/M/yyyy");
        String date = dateFormat.format(new Date());
        if (UserUIMain.filteredDate.contains(date)) {
            Circulaire circulaire = new Circulaire();
            circulaire.title = titre;
            circulaire.faculty_name = faculte;
            circulaire.content = contenu;
            circulaire.date = date;
            CirculaireStore.add(circulaire);
        }
        facadeClass.LogCirculaireAdded(uid);
    }

    public String[] getColumnsName() {
        return new String[]{"Title", "Date", "Content", "View"};
    }

    public Object[][] getTableData(String fac_Name) {
        if (CirculaireStore == null) {
            CirculaireStore = new ArrayList<>();
            DBCursor results = facadeClass.getLastCirculaire(fac_Name);
            Object[][] listofCir = new Object[results.length()][];
            int i = 0;
            for (DBObject result : results) {
                Circulaire circulaire = new Gson().fromJson(result.toString(), Circulaire.class);
                CirculaireStore.add(circulaire);
                listofCir[i] = circulaire.returnObject();
                i++;
            }
            return listofCir;
        } else {
            Object[][] listofCir = new Object[CirculaireStore.size()][];
            int j = 0;
            for (Circulaire Cir : CirculaireStore) {
                listofCir[j] = Cir.returnObject();
                j++;
            }
            return listofCir;
        }
    }


    public Object[][] filterData(String uid, String date, String fac_name) {
        facadeClass.LogSearch(uid, date);
        CirculaireStore.clear();
        DBCursor results = facadeClass.getCirculairesByDate(date, fac_name);
        Object[][] listofFac = new Object[results.length()][];
        int i = 0;
        for (DBObject result : results) {
            Circulaire circulaire = new Gson().fromJson(result.toString(), Circulaire.class);
            CirculaireStore.add(circulaire);
            listofFac[i] = circulaire.returnObject();
            i++;
        }
        return listofFac;
    }


    public String CharArrayToString(char[] arr) {
        String str = "";
        for (int i = 0; i < arr.length; i++) {
            str += arr[i];
        }
        return str;
    }


    public void resetPassword(String uniID, char[] oldPassword, char[] newPassword) {
        String oldPasswd = CharArrayToString(oldPassword);
        String newPasswd = CharArrayToString(newPassword);
        facadeClass.ChangePassword(uniID, MD5.getMd5(oldPasswd), MD5.getMd5(newPasswd));
    }

    public String getFacName(String uid) {
        try {
            ResultSet rs = facadeClass.getFacultyName(uid);
            rs.next();
            return rs.getString("FACULTY_NAME");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }


    public void Logout(String id) {
        facadeClass.Log_auth(id);
    }


}
