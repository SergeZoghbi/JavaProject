package Models;



public class User {
    public String ID_UNI;
    public String FIRST_NAME;
    public String LAST_NAME;
    public String UNIVERSITY_NAME;
    public String SCHOOL_NAME;
    public String FACULTY_NAME;
    public String TYPE;


    public Object[] returnObject() {
        return new Object[]
                {ID_UNI, FIRST_NAME, LAST_NAME, TYPE, FACULTY_NAME, UNIVERSITY_NAME, SCHOOL_NAME, null};
    }

}
