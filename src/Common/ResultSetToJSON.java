package Common;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Date;

public class ResultSetToJSON {



public static JSONArray convertToJSON(ResultSet rs) throws SQLException, JSONException
{
    JSONArray jArray = new JSONArray();
    JSONObject jsonObject = null;
    ResultSetMetaData rsmd = rs.getMetaData();
    int columnCount = rsmd.getColumnCount();
    while (rs.next())
    {
        jsonObject = new JSONObject();
        for (int index = 1; index <= columnCount; index++)
        {
            String column = rsmd.getColumnName(index);
            Object value = rs.getObject(column);
            if (value == null)
            {
                jsonObject.put(column, "");
            } else if (value instanceof Integer) {
                jsonObject.put(column, (Integer) value);
            } else if (value instanceof String) {
                jsonObject.put(column, (String) value);
            } else if (value instanceof Boolean) {
                jsonObject.put(column, (Boolean) value);
            } else if (value instanceof Date) {
                jsonObject.put(column, ((Date) value).getTime());
            } else if (value instanceof Long) {
                jsonObject.put(column, (Long) value);
            } else if (value instanceof Double) {
                jsonObject.put(column, (Double) value);
            } else if (value instanceof Float) {
                jsonObject.put(column, (Float) value);
            } else if (value instanceof BigDecimal) {
                jsonObject.put(column, (BigDecimal) value);
            } else if (value instanceof Byte) {
                jsonObject.put(column, (Byte) value);
            }
            else if (value instanceof byte[]) {
                jsonObject.put(column, (byte[]) value);
            } else {
                throw new IllegalArgumentException("Unmappable object type: " + value.getClass());
            }
        }
        jArray.put(jsonObject);
    }
    return jArray;
}
}
