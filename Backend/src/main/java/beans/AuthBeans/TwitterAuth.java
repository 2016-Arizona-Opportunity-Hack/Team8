package beans.AuthBeans;

/**
 * Created by nick on 10/9/16.
 */
public class TwitterAuth{

    public String id = "";
    public String token = "";
    public String username = "";
    public String displayName = "";

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean equals(Object obj) {
        if (obj instanceof TwitterAuth){
            TwitterAuth other = (TwitterAuth) obj;

            if ((this.id.equals(other.id))
                    && (this.username.equals(other.username))
                    && (this.displayName.equals(other.displayName))
                    && this.token.equals(other.token)){
                return true;
            }
        }
        return false;
    }

}
