package BusinessLayer;

import Common.ResultSetToJSON;
import DataAccess.FacadeClass;
import Models.User;
import com.google.gson.Gson;
import org.jfree.chart.util.ArrayUtils;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.json.JSONArray;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormatSymbols;
import java.util.ArrayList;

import static java.lang.Thread.sleep;


public class AdminLogic {

    private FacadeClass facadeClass = FacadeClass.getInstance();

    public int AddUser(String fn, String ln, String type, String fac, String oldUni, String oldSchool) {

        System.out.println(fn + ln + type + fac + oldUni + oldSchool);

        return 2019;
    }

    public boolean DeleteUser(String id) {

        try {
            return facadeClass.DeleteUser(id) == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean EditUser(String id, String fn, String ln, String type, String fac, String oldUni, String oldSchool) {

        System.out.println(id + fn + ln + type + fac + oldUni + oldSchool);

        return true;
    }


    private ResultSet faculties = null;
    public String[] getFaculties(String fac){
        try {
            ArrayList<String> list = new ArrayList<>();
            list.add(fac);
            ResultSet rs = facadeClass.CallGetProcedures("getAllFaculties");
            faculties = rs;

            while(rs.next()){
                list.add(rs.getString("FACULTY_NAME"));
            }

            String[] allFac = new String[list.size()];
            for(int i = 0 ; i < list.size() ; i ++ ){
                allFac[i] = list.get(i);
            }
            return allFac;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }

    private ResultSet schools = null;
    public String[] getSchools(String schl){
       try {
           ArrayList<String> list = new ArrayList<>();
           list.add(schl);
           ResultSet rs = facadeClass.CallGetProcedures("getAllSchools");
           schools = rs;

           while (rs.next()){
               list.add(rs.getString("SCHOOL_NAME"));
           }

           String[] allSchools = new String[list.size()];
           for(int i = 0 ; i < list.size() ; i ++ ){
               allSchools[i] = list.get(i);
           }
           return allSchools;


       }catch (SQLException e){
           e.printStackTrace();
       }

        return null;
    }


    private ResultSet universities = null;
    public String[] getUniversities(String uni){
        try {
            ArrayList<String> list = new ArrayList<>();
            list.add(uni);
            ResultSet rs = facadeClass.CallGetProcedures("getAllUniversities");
            universities = rs;

            while (rs.next()){
                list.add(rs.getString("UNIVERSITY_NAME"));
            }

            String[] allUnis = new String[list.size()];
            for(int i = 0 ; i < list.size() ; i ++ ){
                allUnis[i] = list.get(i);
            }
            return allUnis;


        }catch (SQLException e){
            e.printStackTrace();
        }

        return null;
    }


    public Object[][] getFilteredUsers(String u_id) {
        try {
            sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new Object[][]{
//                new User("201710198" , "Michael" , "Ghosn" ,  "Etudiant" , "Engineering" , "UA" , "Antonine fathers school").returnObject(),
//                new User("201710251" , "Serge"  , "Zoghbi" ,  "Etudiant" , "Engineering" , "UA" , "SCAin Najem").returnObject() ,
        };
    }


    public String getPieChartName() {
        return "Student Repartition between Faculties";
    }

    public DefaultPieDataset createPieChartDataset() {

        DefaultPieDataset pieDataset = new DefaultPieDataset();
        try {
            ResultSet rs = this.facadeClass.CallGetProcedures("getStdRepartitionPerFac");
            while (rs.next()) {
                pieDataset.setValue(rs.getString("FACULTY_NAME"), Double.parseDouble(rs.getString("NB_OF_STUDENT")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pieDataset;
    }


    public String getBarChartName() {
        return "Circulaire Per Month";
    }

    public DefaultCategoryDataset createBarChartDataset() {

        final String month = "Month";

        DefaultCategoryDataset barChartCategoryDataset = new DefaultCategoryDataset();
        try {

            ResultSet resultSet = this.facadeClass.CallGetProcedures("getTotalCirculairePerMonth");
            while (resultSet.next()) {
                barChartCategoryDataset.addValue(Double.parseDouble(resultSet.getString("NB_OF_CIRCULAIRE")), month, new DateFormatSymbols().getMonths()[Integer.parseInt(resultSet.getString("MONTH")) - 1]);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return barChartCategoryDataset;
    }


    public String getLineChartName() {
        return "Line Chart";
    }

    public XYDataset createLineChartDataset() {
        final XYSeries series1 = new XYSeries("Student");
        try {
            ResultSet rs = this.facadeClass.CallGetProcedures("getActivityStudent");
            while (rs.next()) {
                series1.add(Double.parseDouble(rs.getString("DAY")), Double.parseDouble(rs.getString("NB_OF_STUDENT")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        final XYSeries series2 = new XYSeries("Conseiller");
        try {
            ResultSet rs = this.facadeClass.CallGetProcedures("getActivityConsiller");
            while (rs.next()) {
                series2.add(Double.parseDouble(rs.getString("DAY")), Double.parseDouble(rs.getString("NB_OF_CONSILLER")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        final XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series1);
        dataset.addSeries(series2);


        return dataset;
    }


    public String[] getColumnNames() {
        return new String[]{"University ID", "First Name", "Last Name", "Type", "Faculty", "Old University", "School Name", "View"};
    }

    public Object[][] getTableData() {
        try {
            ResultSet rs = this.facadeClass.CallGetProcedures("getLastTenUsers");
            Object[][] listOfUsers = new Object[20][];
            int i = 0;
            while (rs.next()){
                listOfUsers[i] = new User(rs.getString("ID_UNI"),rs.getString("FIRST_NAME"),rs.getString("LAST_NAME"),rs.getString("UNIVERSITY_NAME"),
                        rs.getString("SCHOOL_NAME"),rs.getString("FACULTY_NAME"),rs.getString("TYPE")).returnObject();
                i++;
            }


            Object[][] finalList = new Object[i][];
            finalList = listOfUsers;
                return finalList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public void ResetPassword(String uniID) {
        System.out.println(uniID);
    }


}
