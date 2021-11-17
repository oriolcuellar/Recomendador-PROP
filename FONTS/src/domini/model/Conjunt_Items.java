package FONTS.src.domini.model;

import FONTS.src.domini.model.*;
import java.util.*;

//@Author Jordi Olmo
public class Conjunt_Items {

    //Atributes
    private ArrayList<Item> Items;

    //Creadora
    public Conjunt_Items(ArrayList<Item> items) {
        Items = items;
        Collections.sort(Items, new Comparator<Item> () {

            // Used for sorting in ascending order of Item.ID
            public int compare(Item a, Item b)
            {
                return a.getID() - b.getID();
            }
        });
    }
    public Conjunt_Items() {
        Items = new ArrayList<Item> ();
    }

    //Getters
    public  boolean existeix_item(int id) {

        return binarySearch(Items, 0, Items.size()-1, id);
    }
    public int get_posiion (Item a) {

        return binarySearchPosition(Items, 0, Items.size()-1, a.getID());
    }

    public int n_Items() { return Items.size();}
    public ArrayList<Item> getItems() {
        return Items;
    }

    //Setters

    public boolean setItems(ArrayList<Item> items) {
        Items = items;
        return true;
    }

    //Operacions

    public boolean anyadir_item(Item a)  {

        if (!existeix_item(a.getID())) {
            int i = BinaryInsertion(Items, 0, Items.size()-1, a.getID());
            Items.add(i+1, a);
            return true;
        }
        else return false;//falta exception creo
    }

    //Operacions Auxiliars

    private boolean binarySearch(ArrayList<Item> Items, int l, int r, int ID) {

        if (r >= l) {
            int mid = l + (r - l) / 2;
            //mirem se es en el mig
            if (Items.get(mid).getID() == ID)
                return true;
            //si no en es subvector esquerra
            if (Items.get(mid).getID() > ID)
                return binarySearch(Items, l, mid - 1, ID);
            //si no a la dreta
            return binarySearch(Items, mid + 1, r, ID);
        }
        else return false;
    }

    private int binarySearchPosition(ArrayList<Item> Items, int l, int r, int ID) {

        if (r >= l) {
            int mid = l + (r - l) / 2;
            //mirem se es en el mig
            if (ID == Items.get(mid).getID())
                return mid;
            //si no en es subvector esquerra
            if (Items.get(mid).getID() > ID)
                return binarySearchPosition(Items, l, mid - 1, ID);
            //si no a la dreta
            return binarySearchPosition(Items, mid + 1, r, ID);
        }
        else return -1; //en teoria exception
    }

    private int BinaryInsertion(ArrayList<Item> Items, int l, int r, int ID) {

        if (r > l) {
            int mid = l + (r - l) / 2;
            //mirem se es en el mig
            if (ID == Items.get(mid).getID())
                return mid;
            //si no en es subvector esquerra
            if (Items.get(mid).getID() > ID)
                return BinaryInsertion(Items, l, mid - 1, ID);
            //si no a la dreta
            return BinaryInsertion(Items, mid + 1, r, ID);
        }
        else return r; //en teoria exception
    }
}