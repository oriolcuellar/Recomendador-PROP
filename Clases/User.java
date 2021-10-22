public class User { 
    //atributes
    private String userID;
    private String password;
    private TipusRol Rol;
    //getters
    public String getUserID() {
        return userID;
    }
    public String getPassword() {
        return password;
    }
    public TipusRol getRol() { return Rol; }


    //setters
    public void setUserID(String userID) {
        this.userID = userID;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setRol(TipusRol rol) { Rol = rol; }

    //constructores
    public User(String userID, String password, TipusRol Rol) {
        this.userID = userID;
        this.password = password;
        this.Rol = Rol;
    }
    public User() {}
}

