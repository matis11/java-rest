package models;

import com.mongodb.MongoClient;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

public class DatastoreHandlerUtil {
    private static DatastoreHandlerUtil Instance = new DatastoreHandlerUtil();
    private Datastore datastore;

    public static DatastoreHandlerUtil getInstance() {
        return Instance;
    }

    private DatastoreHandlerUtil() {
        final Morphia morphia = new Morphia();
        datastore = morphia.createDatastore(new MongoClient("localhost", 27017), "database");
        morphia.mapPackage("models");
        datastore.ensureIndexes();
    }

    public Datastore getDatastore() {
        return datastore;
    }
}
