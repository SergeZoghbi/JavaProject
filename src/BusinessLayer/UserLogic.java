package BusinessLayer;

import Common.CharToString;
import Common.MD5;
import DataAccess.FacadeClass;
import Models.Circulaire;
import com.google.gson.Gson;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static java.lang.Thread.sleep;

public class UserLogic {

    private FacadeClass facadeClass = FacadeClass.getInstance();

    public void AddNewCirculaire(String uid , String titre , String faculte , String contenu) {

        facadeClass.AddCirculaire(titre,faculte,contenu);
        try {
            facadeClass.LogCirculaireAdded(uid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
//        return new Object[]
//                {titre , faculte , contenu , new SimpleDateFormat("dd/MM/YYYY").format(new Date()) , null};
    }


    public String[] getColumnsName() {
        return new String[]{"Title", "Date","Content",  "View"};
    }

    public Object[][] getTableData() {
        DBCursor results = facadeClass.getLastCirculaire();

        Object[][] listofFac = new Object[10][];
        int i =0;
        for (DBObject result : results) {
            Circulaire circulaire = new Gson().fromJson(result.toString(), Circulaire.class);
            listofFac[i] = circulaire.returnObject();
            i++;
        }
        return listofFac;
    }


    public Object[][] filterData(String uid ,String date){
        try {
            facadeClass.LogSearch(uid,date);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        DBCursor results = facadeClass.getCirculairesByDate(date);

        Object[][] listofFac = new Object[results.length()][];
        int i =0;
        for (DBObject result : results) {
            Circulaire circulaire = new Gson().fromJson(result.toString(), Circulaire.class);
            listofFac[i] = circulaire.returnObject();
            i++;
        }
        return listofFac;
    }


    public  String CharArrayToString(char[] arr){
        String str = "";
        for (int i = 0 ; i < arr.length ; i++) {
            str += arr[i];
        }
        return str;
    }

//    public String[] getUserInfo(String uniID){
//        return new String[]{ uniID , "Michael" , "Ghosn" , "12345678" , "Engineering" , null , "Antonine fathers school" };
//    }


    public void resetPassword(String uniID , char[] oldPassword , char[] newPassword){
       String oldPasswd = CharArrayToString(oldPassword);
       String newPasswd = CharArrayToString(newPassword);

        try {
            facadeClass.ChangePassword(uniID, MD5.getMd5(oldPasswd),MD5.getMd5(newPasswd));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }




    public void Logout(String id) {
        try {
            facadeClass.Log_auth(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
