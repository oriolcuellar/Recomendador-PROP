public class User {
    //atributes
    private String userID;
    private String password;
    private boolean admin;
    //getters
    public String getUserID() {
        return userID;
    }
    public String getPassword() {
        return password;
    }
    public boolean getAdmin() {
        return admin;
    }
    //setters
    public String setUserID(String userID) {
        this.userID = userID;
    }
    public String setPassword(String password) {
        this.password = password;
    }
    public boolean setAdmin(boolean admin) {
        this.admin = admin;
    }
    //constructores
    public User(String userID, String password, boolean admin) {
        this.userID = userID;
        this.password = password;
        this.admin = admin;
    }
    public User() {}
}

