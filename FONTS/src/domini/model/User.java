package FONTS.src.domini.model;

import FONTS.src.domini.model.*;
import java.util.ArrayList;

import static java.lang.Math.sqrt;

// @Marc y Roberto

public class User {
    //atributes
    private int userID;
    private String password;
    private TipusRol Rol;
    private ArrayList<valoratedItem> itemsUsats;
    private int numCluster;

    //getters
    public int getUserID() {
        return userID;
    }
    public String getPassword() {
        return password;
    }
    public TipusRol getRol() { return Rol; }
    public ArrayList<valoratedItem> getItemsUsats() { return itemsUsats; }
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
    public void setItemsUsats(ArrayList<valoratedItem> usats) { itemsUsats = usats; }
    public void setNumCluster(int numCluster) {
        this.numCluster = numCluster;
    }

    //modifiers
    public void addvaloratedItem(valoratedItem usat) { itemsUsats.add(usat); }

    //constructores
    public User() {
        this.itemsUsats = new ArrayList<valoratedItem>();
        this.numCluster = -1;
    }

    public User(int userID, String password, TipusRol Rol) {
        this.userID = userID;
        this.password = password;
        this.Rol = Rol;
        this.itemsUsats = new ArrayList<valoratedItem>();
        this.numCluster = -1;
    }
    public User(int userID, String password, TipusRol Rol, int numCluster) {
        this.userID = userID;
        this.password = password;
        this.Rol = Rol;
        this.itemsUsats = new ArrayList<valoratedItem>();
        this.numCluster = numCluster;
    }
    public User(int userID) {
        this.userID = userID;
        this.password = String.valueOf(userID);
        this.Rol = TipusRol.Usuari;
        this.itemsUsats = new ArrayList<valoratedItem>();
        this.numCluster = -1;
    }

    // Functions

    public void addvaloratedItem(int itemID, float valoration) {
        Item i = new Item(itemID);
        valoratedItem usedItem = new valoratedItem(i, valoration);
        //falta comprobar que no se repitan
        itemsUsats.add(usedItem);
    }

    public float calculateDistances(User user2) {
        ArrayList<valoratedItem> itemsUsats2 = user2.getItemsUsats();
        ArrayList<Integer> intersectionItems = intersection(itemsUsats,itemsUsats2);
        float sumProdValorations = 0;

        for(int i = 0; i < intersectionItems.size(); ++i) {
            int itemID = intersectionItems.get(i);
            float valoration1 = searchUsedItem(itemID).getValoracio();
            float valoration2 = user2.searchUsedItem(itemID).getValoracio();
            sumProdValorations += valoration1*valoration2;
        }
        float sumSquareValoration1 = 0;
        float sumSquareValoration2 = 0;
        for(int i = 0; i < itemsUsats.size(); ++i) {
            float valoration1 = itemsUsats.get(i).getValoracio();
            sumSquareValoration1 += valoration1*valoration1;
        }
        for(int i = 0; i < itemsUsats2.size(); ++i) {
            float valoration2 = itemsUsats2.get(i).getValoracio();
            sumSquareValoration2 += valoration2*valoration2;
        }
        return sumProdValorations/(float)(sqrt(sumSquareValoration1)*sqrt(sumSquareValoration2));
    }

    public float calculateDistances2(User usuario2) {
        ArrayList<valoratedItem> valoracionesUsuario2 = usuario2.getItemsUsats();
        float sumaTotal = 0;
        int count = 0;
        for(int i = 0; i < itemsUsats.size(); ++i) {
            int id1 = itemsUsats.get(i).getItem().getID();
            for (int j = 0; j < valoracionesUsuario2.size(); ++j) {
                int id2 = valoracionesUsuario2.get(j).getItem().getID();
                if(id1 == id2) {
                    ++count;
                    valoratedItem item1 = itemsUsats.get(i);
                    valoratedItem item2 = valoracionesUsuario2.get(j);
                    sumaTotal += (item1.getValoracio() - item2.getValoracio()) * (item1.getValoracio() - item2.getValoracio());
                    break;
                }
            }
        }
        if(count == 0) return Float.POSITIVE_INFINITY;
        return (float) sqrt(sumaTotal);
    }
    public valoratedItem searchUsedItem(int itemID) {
        for(int i = 0; i < itemsUsats.size(); ++i) {
            if (itemsUsats.get(i).getItem().getID() == itemID) return itemsUsats.get(i);
        }
        return null;
    }

    // Prints

    public void printUsedItems() {
        for(int i = 0; i < itemsUsats.size(); ++i) {
            System.out.println("Item " + itemsUsats.get(i).getItem().getID() + " valorated with " + itemsUsats.get(i).getValoracio());
        }
    }

    private ArrayList<Integer> intersection(ArrayList<valoratedItem> l1, ArrayList<valoratedItem> l2) {
        ArrayList<Integer> l3 = new ArrayList<Integer>();
        for (valoratedItem valoratedItem1 : l1) {
            for (valoratedItem valoratedItem2 : l2) {
                if (valoratedItem1.getItem().getID() == valoratedItem2.getItem().getID()) {
                    l3.add(valoratedItem1.getItem().getID());
                }
            }
        }
        return l3;
    }

}
