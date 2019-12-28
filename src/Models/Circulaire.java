package Models;

public class Circulaire {
    public String date;
    public String title;
    public String content;
    public String faculty_name;

    public Object[] returnObject(){
        return new Object[]
                { title , faculty_name , content , date , null };
    }

}
