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


    public int compareTo(myPair otherPair, myPair otherPair2) {
        if(otherPair2.getValoration() < otherPair.getValoration()) return 1;
        return 0;
    }
}
