package BusinessLayer;

import java.text.SimpleDateFormat;
import java.util.Date;

import static java.lang.Thread.sleep;

public class UserLogic {


    public Object[] AddNewCirculaire(String titre , String faculte , String contenu) {
        return new Object[]
                {titre , faculte , contenu , new SimpleDateFormat("dd/MM/YYYY").format(new Date()) , null};
    }


    public String[] getColumnsName() {
        return new String[]{"Title", "Faculty", "Content", "Date", "View"};
    }

    public Object[][] getTableData() {
        return  new Object[][] {
//                new Circulaire("PFE" , "Engineering" ,  "Michael's data aaaaaaaaaaaaaaaaaaaaaajjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiooooooooooooooooooooooooooooooooooooooooooooooooooooolllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkaaaaaaaaaaaaaaaaaaaaaaa" , "01/01/2020" ).returnObject() ,
//                new Circulaire("Rattrappage" , "Engineering"  ,  "Serge's data" , "02/01/2020" ).returnObject(),
//                new Circulaire("PFE" , "Engineering" ,  "Michael's data aaaaaaaaaaaaaaaaaaaaaajjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiooooooooooooooooooooooooooooooooooooooooooooooooooooolllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkaaaaaaaaaaaaaaaaaaaaaaa" , "01/01/2020" ).returnObject() ,
//                new Circulaire("Rattrappage" , "Engineering"  ,  "Serge's data" , "02/01/2020" ).returnObject(),
                };

    }


    public Object[][] filterData(String date){
        try {
            sleep(2000);
        } catch (Exception e1){}
        return new Object[][] {
//                new Circulaire("PFE" , "Engineering" ,  "Michael's data aaaaaaaaaaaaaaaaaaaaaajjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiooooooooooooooooooooooooooooooooooooooooooooooooooooolllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkaaaaaaaaaaaaaaaaaaaaaaa" , "01/01/2020" ).returnObject() ,
//                new Circulaire("Rattrappage" , "Engineering"  ,  "Serge's data" , "02/01/2020" ).returnObject(),
        };
    }



//    public String[] getUserInfo(String uniID){
//        return new String[]{ uniID , "Michael" , "Ghosn" , "12345678" , "Engineering" , null , "Antonine fathers school" };
//    }


    public void resetPassword(String uniID , char[] oldPassword , char[] newPassword){
       String oldPasswd = CharArrayToString(oldPassword);
       String newPasswd = CharArrayToString(newPassword);
    }


    public String CharArrayToString(char[] arr){
        String str = "";
        for (int i = 0 ; i < arr.length ; i++) {
            str += arr[i];
        }
        return str;
    }


}
