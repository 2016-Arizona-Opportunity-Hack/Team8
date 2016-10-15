package mongohandler.constants;

/**
 * Created by nick on 10/15/16.
 */
public class AuthenticationConstants {
    // Names of the authentication fields
    public static final String GOOGLE_AUTH = "google";
    public static final String TWITTER_AUTH = "twitter";
    public static final String FACEBOOK_AUTH = "facebook";


    // Google authentication doc field names
    public static final String GOOGLE_ID = "id";
    public static final String GOOGLE_TOKEN = "token";
    public static final String GOOGLE_EMAIL = "email";
    public static final String GOOGLE_NAME = "name";

    // facebook authentication doc field names
    public static final String FACEBOOK_ID = "id";
    public static final String FACEBOOK_TOKEN = "token";
    public static final String FACEBOOK_EMAIL = "email";
    public static final String FACEBOOK_NAME = "name";

    // twitter authentication doc field names
    public static final String TWITTER_ID = "id";
    public static final String TWITTER_TOKEN = "token";
    public static final String TWITTER_DISPLAY_NAME = "displayName";
    public static final String TWITTER_USERNAME = "username";
}
