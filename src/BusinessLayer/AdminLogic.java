package BusinessLayer;

import Common.ResultSetToJSON;
import DataAccess.FacadeClass;
import Models.Faculty;
import Models.School;
import Models.University;
import Models.User;
import com.google.gson.Gson;
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


public class AdminLogic {

    private FacadeClass facadeClass = FacadeClass.getInstance();
    private ArrayList<Faculty> faculties = null;
    private ArrayList<School> schools = null;
    private ArrayList<University> universities = null;
    private ArrayList<User> userStore = null;

    public String AddUser(String fn, String ln, String type, String fac, String oldUni, String oldSchool) {


        String IdUni = facadeClass.AddUser(fn, ln, mapFacNameToId(fac), mapSchoolNameToId(oldSchool), mapTypeToInt(type), mapUniNameToId(oldUni));
        if (!IdUni.equals("-1")) {
            User userAdded = new User();
            userAdded.ID_UNI = IdUni;
            userAdded.FIRST_NAME = fn;
            userAdded.LAST_NAME = ln;
            userAdded.FACULTY_NAME = fac;
            userAdded.SCHOOL_NAME = oldSchool;
            userAdded.UNIVERSITY_NAME = oldUni;
            userAdded.TYPE = type;
            userStore.add(userAdded);
        }
        return IdUni;
    }

    public boolean DeleteUser(String id) {

        if(facadeClass.DeleteUser(id) == 1){
            userStore.removeIf(user -> user.ID_UNI.equals(id));
            return true;
        }
        return false;
    }

    public boolean EditUser(String id, String fn, String ln, String type, String fac, String oldUni, String oldSchool) {

        int resultat = facadeClass.UpdateUser(id, fn, ln, mapFacNameToId(fac), mapSchoolNameToId(oldSchool), mapTypeToInt(type), mapUniNameToId(oldUni));
        if (resultat == 1) {
            int index = 0;
            for (User user : userStore) {
                if (user.ID_UNI.equals(id)) {
                    User userEdited = new User();
                    userEdited.ID_UNI = user.ID_UNI;
                    userEdited.TYPE = type;
                    userEdited.UNIVERSITY_NAME = oldUni;
                    userEdited.SCHOOL_NAME = oldSchool;
                    userEdited.FIRST_NAME = fn;
                    userEdited.LAST_NAME = ln;
                    userEdited.FACULTY_NAME = fac;
                    userStore.set(index, userEdited);
                    return true;
                }
                index++;
            }
        }
        return false;
    }

    private int mapTypeToInt(String type) {
        return type.equals("Student") ? 2 : 3;
    }

    private int mapFacNameToId(String facName) {
        for (Faculty fac : faculties) {
            if (fac.FACULTY_NAME.equals(facName)) {
                return fac.ID;
            }
        }
        return -1;
    }

    public String[] getFaculties() {

        String[] allFaculties = new String[faculties.size()];
        for (int i = 0; i < faculties.size(); i++) {
            allFaculties[i] = faculties.get(i).FACULTY_NAME;
        }
        return allFaculties;
    }

    public void fillFacultiesArrayList(){
        if(faculties == null){
            faculties = new ArrayList<>();
            ResultSet rs = facadeClass.CallGetProcedures("getAllFaculties");
            JSONArray jsonArray = ResultSetToJSON.convertToJSON(rs);
            for (Object json : jsonArray) {
                Faculty faculty = new Gson().fromJson(json.toString(), Faculty.class);
                faculties.add(faculty);
            }
        }

    }

    private int mapSchoolNameToId(String schoolName) {
        for (School school : schools) {
            if (school.SCHOOL_NAME.equals(schoolName)) {
                return school.ID;
            }
        }
        return -1;
    }

    public String[] getSchools() {
        String[] allSchools = new String[schools.size()];
        for (int i = 0; i < schools.size(); i++) {
            allSchools[i] = schools.get(i).SCHOOL_NAME;
        }
        return allSchools;

    }

    public void fillSchoolArrayList(){
        if(schools == null){
            schools = new ArrayList<>();
            ResultSet rs = facadeClass.CallGetProcedures("getAllSchools");
            JSONArray jsonArray = ResultSetToJSON.convertToJSON(rs);
            for (Object json : jsonArray) {
                School school1 = new Gson().fromJson(json.toString(), School.class);
                schools.add(school1);
            }
        }
    }

    private int mapUniNameToId(String uniName) {
        for (University uni : universities) {
            if (uni.UNIVERSITY_NAME.equals(uniName)) {
                return uni.ID;
            }
        }
        return -1;
    }

    public String[] getUniversities() {

        String[] allUnis = new String[universities.size()];
        for (int i = 0; i < universities.size(); i++) {
            allUnis[i] = universities.get(i).UNIVERSITY_NAME;
        }
        return allUnis;
    }

    public void fillUniverstiesArrayList(){
        if(universities == null){
            universities = new ArrayList<>();
            ResultSet rs = facadeClass.CallGetProcedures("getAllUniversities");

            JSONArray jsonArray = ResultSetToJSON.convertToJSON(rs);

            for (Object json : jsonArray) {
                University university = new Gson().fromJson(json.toString(), University.class);
                universities.add(university);
            }
        }
    }

    public Object[][] getFilteredUsers(String u_id) {
        System.out.println("Filtring Data");
        ResultSet rs = this.facadeClass.AccountInfo(u_id);
        userStore.clear();
        JSONArray jsonArray = ResultSetToJSON.convertToJSON(rs);
        Object[][] listOfUsers = new Object[jsonArray.length()][];
        int i = 0;
        for (Object json : jsonArray) {
            User user = new Gson().fromJson(json.toString(), User.class);
            userStore.add(user);
            listOfUsers[i] = user.returnObject();
            i++;
        }
        return listOfUsers;
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
        return "Users Activity";
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

        if (userStore == null) {
            System.out.println("am jiboun men db");
            userStore = new ArrayList<>();
            ResultSet rs = this.facadeClass.CallGetProcedures("getLastTenUsers");
            JSONArray jsonArray = ResultSetToJSON.convertToJSON(rs);
            Object[][] listOfUsers = new Object[jsonArray.length()][];

            int i = 0;
            for (Object json : jsonArray) {
                User user = new Gson().fromJson(json.toString(), User.class);
                listOfUsers[i] = user.returnObject();
                userStore.add(user);
                i++;
            }
            return listOfUsers;
        } else {
            System.out.println("Array return");
            Object[][] listOfusers = new Object[userStore.size()][];
            int j = 0;
            for (User user : userStore) {
                listOfusers[j] = user.returnObject();
                j++;
            }
            return listOfusers;
        }
    }


    public void ResetPassword(String uniID) {
        facadeClass.ResetPassword(uniID);
    }


    public void Logout(String id) {
        facadeClass.Log_auth(id);
    }


}
