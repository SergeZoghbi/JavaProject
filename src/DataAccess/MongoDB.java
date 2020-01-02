package DataAccess;


import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Arrays;

public class MongoDB {

    public MongoClient mongoClient;
    private static MongoDB mongoDB = null;

    private MongoDB(){
        char[] password = {'1','2','3','4','5','6'};
        Logger mongoLogger = Logger.getLogger( "org.mongodb.driver" );
        mongoLogger.setLevel(Level.SEVERE);

        MongoCredential credential = MongoCredential.createCredential("root", "JavaProject2020DB", password);
        this.mongoClient = new MongoClient(new ServerAddress("localhost", 27017),
                Arrays.asList(credential));
    }

    public static synchronized MongoDB getMongoDB()  {
        if (mongoDB == null) {
            mongoDB = new MongoDB();
        }
        return mongoDB;
    }
}
