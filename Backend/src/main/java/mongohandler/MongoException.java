package mongohandler;

/**
 * Created by nick on 10/1/16.
 */
public class MongoException extends Exception {
    public MongoException(String message){
        super(message);
    }

    public MongoException(){
        super("An error occured while accessing the DB");
    }

}
