package FONTS.src.domini.model;

import FONTS.src.domini.model.*;
public class myPair implements  Comparable<myPair> {
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

    @Override
    public int compareTo(myPair otherPair) {
        if(! (this.valoration > otherPair.getValoration())) return 1;
        return 0;
    }
}
