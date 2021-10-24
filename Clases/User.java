import java.util.ArrayList;

public class User {
    //atributes
    private String userID;
    private String password;
    private TipusRol Rol;
    private ArrayList<Item_Usat> itemsUsats;
    //getters
    public String getUserID() {
        return userID;
    }
    public String getPassword() {
        return password;
    }
    public TipusRol getRol() { return Rol; }
    public ArrayList<Item_Usat> getItemsUsats() { return itemsUsats; }

    //setters
    public void setUserID(String userID) {
        this.userID = userID;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setRol(TipusRol rol) { Rol = rol; }
    public void setItemsUsats(ArrayList<Item_Usat> usats) { itemsUsats = usats; }

    //modifiers
    public void addItemUsat(Item_Usat usat) { itemsUsats.add(usat); }

    //constructores
    public User(String userID, String password, TipusRol Rol) {
        this.userID = userID;
        this.password = password;
        this.Rol = Rol;
    }
    public User() {}
}

