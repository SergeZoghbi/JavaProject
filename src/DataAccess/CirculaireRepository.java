package DataAccess;

import Models.Circulaire;
import com.mongodb.*;
import org.bson.types.ObjectId;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CirculaireRepository {

    private DBCollection collection;


    public CirculaireRepository() {
        MongoDB mongoDB = MongoDB.getMongoDB();
        DB database = mongoDB.mongoClient.getDB("JavaProject2020DB");
        this.collection = database.getCollection("Circulaire");
    }


    public void AddCirculaire(String title, String faculty_name, String content) {
        DateFormat dateFormat = new SimpleDateFormat("dd/M/yyyy");
        String date = dateFormat.format(new Date());
        String id = new ObjectId().toString();
        DBObject circulaireToAdd = new BasicDBObject("_id",id)
                .append("title", title)
                .append("content", content)
                .append("faculty_name", faculty_name)
                .append("date", date);

        collection.insert(circulaireToAdd);
    }

    public DBCursor getCirculairesByDate(String date, String facName) {
        BasicDBObject whereQuery = new BasicDBObject("date",date).append("faculty_name",facName);
        return collection.find(whereQuery);
    }

    public DBCursor getLastCirculaire(String Fac_name) {
        DBObject sortingQuery = new BasicDBObject("$natural", -1);
        return collection.find(new BasicDBObject("faculty_name", Fac_name)).sort(sortingQuery).limit(10);
    }


}
