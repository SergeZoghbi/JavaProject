package BusinessLayer;

import Common.CharToString;
import Common.MD5;
import DataAccess.FacadeClass;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLException;

public class LoginFrameLogic {

    public int CheckAuthentication(String user_id, char[] password) {
        String passwd = CharArrayToString(password);
        try {
            FacadeClass facadeClass = FacadeClass.getInstance();
           return facadeClass.Login(user_id, MD5.getMd5(passwd));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
    public  String CharArrayToString(char[] arr){
        String str = "";
        for (int i = 0 ; i < arr.length ; i++) {
            str += arr[i];
        }
        return str;
    }


    public String readApi() {
        try {
            URL url = new URL("https://quotes.rest/qod");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            int responseCode = con.getResponseCode();

            String readLine;
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(con.getInputStream()));
                StringBuilder response = new StringBuilder();
                while ((readLine = in.readLine()) != null) {
                    response.append(readLine);
                }
                in.close();
                return response.toString().split(":")[5].split(",")[0];
            } else {
//                System.out.println("GET NOT WORKED");
                return "No more quotes left ... :(";
            }


        } catch (IOException e1) {
            System.out.println("IOException");
        }
        return null;
    }


}
