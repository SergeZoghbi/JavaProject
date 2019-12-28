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

import static java.lang.Thread.sleep;


public class AdminLogic {

    private FacadeClass facadeClass = FacadeClass.getInstance();

    public int AddUser(String fn, String ln, String type, String fac, String oldUni, String oldSchool) {

        try {
            return facadeClass.AddUser(fn, ln, mapFacNameToId(fac), mapSchoolNameToId(oldSchool), mapTypeToInt(type), mapUniNameToId(oldUni));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
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
        try {
            int resultat = facadeClass.UpdateUser(id, fn, ln, mapFacNameToId(fac), mapSchoolNameToId(oldSchool), mapTypeToInt(type), mapUniNameToId(oldUni));
            return resultat == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private int mapTypeToInt(String type) {
        return type.equals("student") ? 2 : 3;
    }


    private ArrayList<Faculty> faculties = new ArrayList<>();

    private int mapFacNameToId(String facName) {
        for (Faculty fac : faculties) {
            if (fac.FACULTY_NAME.equals(facName)) {
                return fac.ID;
            }
        }
        return -1;
    }

    public String[] getFaculties(String fac_name) {
        try {

            ResultSet rs = facadeClass.CallGetProcedures("getAllFaculties");

            JSONArray jsonArray = ResultSetToJSON.convertToJSON(rs);

            for (Object json : jsonArray) {
                Faculty faculty = new Gson().fromJson(json.toString(), Faculty.class);
                faculties.add(faculty);
            }


            String[] allFaculties = new String[faculties.size() + 1];
            allFaculties[0] = fac_name;
            for (int i = 1; i < faculties.size(); i++) {
                allFaculties[i] = faculties.get(i).FACULTY_NAME;
            }
            return allFaculties;


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    private ArrayList<School> schools = new ArrayList<>();

    private int mapSchoolNameToId(String schoolName) {
        for (School school : schools) {
            if (school.SCHOOL_NAME.equals(schoolName)) {
                return school.ID;
            }
        }
        return -1;
    }

    public String[] getSchools(String school) {
        try {

            ResultSet rs = facadeClass.CallGetProcedures("getAllSchools");

            JSONArray jsonArray = ResultSetToJSON.convertToJSON(rs);

            for (Object json : jsonArray) {
                School school1 = new Gson().fromJson(json.toString(), School.class);
                schools.add(school1);
            }


            String[] allSchools = new String[schools.size() + 1];
            allSchools[0] = school;
            for (int i = 1; i < schools.size(); i++) {
                allSchools[i] = schools.get(i).SCHOOL_NAME;
            }
            return allSchools;


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    private ArrayList<University> universities = new ArrayList<>();

    private int mapUniNameToId(String uniName) {
        for (University uni : universities) {
            if (uni.UNIVERSITY_NAME.equals(uniName)) {
                return uni.ID;
            }
        }
        return -1;
    }

    public String[] getUniversities(String uni) {
        try {

            ResultSet rs = facadeClass.CallGetProcedures("getAllUniversities");

            JSONArray jsonArray = ResultSetToJSON.convertToJSON(rs);

            for (Object json : jsonArray) {
                University university = new Gson().fromJson(json.toString(), University.class);
                universities.add(university);
            }


            String[] allUnis = new String[universities.size() + 1];
            allUnis[0] = uni;
            for (int i = 1; i < universities.size(); i++) {
                allUnis[i] = universities.get(i).UNIVERSITY_NAME;
            }
            return allUnis;


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }


    public Object[][] getFilteredUsers(String u_id) {
        ResultSet rs = null;
        JSONArray jsonArray = null;
        try {
            rs = this.facadeClass.AccountInfo(u_id);
            jsonArray = ResultSetToJSON.convertToJSON(rs);
            Object[][] listOfUsers = new Object[jsonArray.length()][];
            int i = 0;
            for (Object json : jsonArray) {
                User user = new Gson().fromJson(json.toString(), User.class);
                listOfUsers[i] = user.returnObject();
                i++;
            }
            return listOfUsers;


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
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
        try {
            ResultSet rs = this.facadeClass.CallGetProcedures("getLastTenUsers");
            Object[][] listOfUsers = new Object[10][];
            JSONArray jsonArray = ResultSetToJSON.convertToJSON(rs);
            int i = 0;
            for (Object json : jsonArray) {
                User user = new Gson().fromJson(json.toString(), User.class);
                listOfUsers[i] = user.returnObject();
                i++;
            }

            return listOfUsers;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public void ResetPassword(String uniID) {
        try {
            facadeClass.ResetPassword(uniID);
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
