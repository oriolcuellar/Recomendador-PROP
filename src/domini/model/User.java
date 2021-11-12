package src.domini.model;

import src.domini.model.*;
import java.util.ArrayList;

import static java.lang.Math.sqrt;

public class User {
    //atributes
    private int userID;
    private String password;
    private TipusRol Rol;
    private ArrayList<ItemUsat> itemsUsats;
    private int numCluster;

    //getters
    public int getUserID() {
        return userID;
    }
    public String getPassword() {
        return password;
    }
    public TipusRol getRol() { return Rol; }
    public ArrayList<ItemUsat> getItemsUsats() { return itemsUsats; }
    public int getNumCluster() {
        return numCluster;
    }

    //setters
    public void setUserID(int userID) {
        this.userID = userID;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setRol(TipusRol rol) { Rol = rol; }
    public void setItemsUsats(ArrayList<ItemUsat> usats) { itemsUsats = usats; }
    public void setNumCluster(int numCluster) {
        this.numCluster = numCluster;
    }

    //modifiers
    public void addItemUsat(ItemUsat usat) { itemsUsats.add(usat); }

    //constructores
    public User(int userID, String password, TipusRol Rol) {
        this.userID = userID;
        this.password = password;
        this.Rol = Rol;
        this.itemsUsats = new ArrayList<ItemUsat>();
        this.numCluster = -1;
    }
    public User() {
        this.itemsUsats = new ArrayList<ItemUsat>();
        this.numCluster = -1;
    }

    // Functions

    public void addItemUsat(int itemID, float valoration) {
        Item i = new Item(itemID);
        ItemUsat usedItem = new ItemUsat(i, valoration);
        //falta comprobar que no se repitan
        itemsUsats.add(usedItem);
    }

    public float calculateDistances(User usuario2) {
        ArrayList<ItemUsat> valoracionesUsuario2 = usuario2.getItemsUsats();
        float sumaTotal = 0;

        for(int i = 0; i < itemsUsats.size(); ++i) {
            int id1 = itemsUsats.get(i).getItem().getID();
            for (int j = 0; j < valoracionesUsuario2.size(); ++j) {
                int id2 = valoracionesUsuario2.get(j).getItem().getID();
                if(id1 == id2) {
                    ItemUsat item1 = itemsUsats.get(i);
                    ItemUsat item2 = valoracionesUsuario2.get(j);
                    sumaTotal += (item1.getValoracio() - item2.getValoracio()) * (item1.getValoracio() - item2.getValoracio());
                    break;
                }
            }
        }
        return (float) sqrt(sumaTotal);
    }
    public ItemUsat searchUsedItem(int itemID) {
        for(int i = 0; i < itemsUsats.size(); ++i) {
            if (itemsUsats.get(i).getItem().getID() == itemID) return itemsUsats.get(i);
        }
        return null;
    }
}
