package src.domini.model;

import src.domini.model.*;
import java.util.ArrayList;

public class Item {
    //atributs
    private int ID;
    private TipusItem tipus;
    private ArrayList valors;

    public static int compare(Item a, Item b) {

        return a.ID - b.ID;
    }

    //getters
    public int getID() {
        return ID;
    }
    public TipusItem getTipus() {
        return tipus;
    }
    public ArrayList getValors() {
        return valors;
    }
    //setters
    public void setID(int ID) {
        this.ID = ID;
    }

    //constructores

    /* El indice del valor debe coincidir con el indice del atributo para funcionar*/
    public Item(int ID, TipusItem tipus, ArrayList valors) {
    //    if (!Conjunt_Items.existeix_item(ID)) {
            this.ID = ID;
            this.tipus = tipus;
            valors = valors;
     //   }
    }
    public Item(int ID) {this.ID = ID;}
    public Item(){}
}
