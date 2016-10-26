package mongohandler.constants;

/**
 * Created by nick on 10/1/16.
 */
public class MongoConstants {
    public static final String PROFILE_COLLECTION_NAME = "Profile";
    public static final String TEAM_COLLECTION_NAME = "Team";
    public static final String ACTIVITY_COLLECTION_NAME = "Acctivities";
    public static final String DATABASE_NAME = "GALENT_FEW_MARATHON";

    public static final String DEFAULT_HOSTNAME = "localhost";
    public static final int DEFAULT_PORT = 27017;

    // Account Bean fields
    public static final String ACCOUNT_ID_FIELD = "AccountID";
    public static final String FIRST_NAME_FIELD = "FirstName";
    public static final String LAST_NAME_FIELD = "LastName";
    public static final String TOTAL_MILES_FIELD = "TotalMiles";

    // Team Bean fields
    public static final String TEAM_ID_FIELD = "TeamID";
    public static final String TEAM_MEMBERS_FIELD = "TeamMembers";

    // LoggedMiles Bean Fields
    public static final String ACTIVITY_FIELD = "Activity";
    public static final String MILES_FIELD = "Miles";
    public static final String DATE_FIELD = "Date";


}
