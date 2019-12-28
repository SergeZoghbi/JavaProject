package Common;

public class CharToString {

    public static String CharArrayToString(char[] arr){
        String str = "";
        for (int i = 0 ; i < arr.length ; i++) {
            str += arr[i];
        }
        return str;
    }
}
