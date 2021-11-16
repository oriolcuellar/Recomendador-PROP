package FONTS.src.domini.model;

import FONTS.src.domini.model.*;
public class myPair {
    private int itemID;
    private float valoration;

    public myPair(int itemID, float valoration) {
        this.itemID = itemID;
        this.valoration = valoration;
    }

    public myPair() {

    }

    public float getValoration() {
        return valoration;
    }

    public int getItemID() {
        return itemID;
    }

    public class CustomComparator {
        public boolean compare(myPair object1, myPair object2) {
            return object1.getValoration() > (object2.getValoration());
        }
    }

}
