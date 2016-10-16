package beans.AuthBeans;

/**
 * Created by nick on 10/9/16.
 */
public class FacebookAuth {
    public String id;
    public String email;
    public String name;
    public String token;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean equals(Object obj) {
        if (obj instanceof FacebookAuth){
            FacebookAuth other = (FacebookAuth) obj;

            if ((this.id.equals(other.id))
                    && (this.email.equals(other.email))
                    && (this.name.equals(other.name))
                    && this.token.equals(other.token)){
                return true;
            }
        }
        return false;
    }
}
