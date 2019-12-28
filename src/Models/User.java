package Models;

import java.util.Objects;

public class User {
    public String ID_UNI;
    public String FIRST_NAME;
    public String LAST_NAME;
    public String UNIVERSITY_NAME;
    public String SCHOOL_NAME;
    public String FACULTY_NAME;
    public String TYPE;
//    public Faculty faculty;
//    public School school;
//    public Type type;
//    public University university;

    public User(String ID_UNI, String FIRST_NAME, String LAST_NAME, String UNIVERSITY_NAME, String SCHOOL_NAME, String FACULTY_NAME, String TYPE) {
        this.ID_UNI = ID_UNI;
        this.FACULTY_NAME = FACULTY_NAME;
        this.FIRST_NAME = FIRST_NAME;
        this.LAST_NAME = LAST_NAME;
        this.SCHOOL_NAME = Objects.requireNonNullElse(SCHOOL_NAME, "None");


        this.TYPE = TYPE;

        this.UNIVERSITY_NAME = Objects.requireNonNullElse(UNIVERSITY_NAME, "None");
    }

    public Object[] returnObject() {
        return new Object[]
                {ID_UNI, FIRST_NAME, LAST_NAME, TYPE, FACULTY_NAME, UNIVERSITY_NAME, SCHOOL_NAME, null};
    }

}
