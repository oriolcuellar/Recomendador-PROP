package src.domini.model;

public class myPair {
    private int itemID;
    private float valoration;

    public myPair(int itemID, float valoration) {
        this.itemID = itemID;
        this.valoration = valoration;
    }

    public float getValoration() {
        return valoration;
    }

    public int getItemID() {
        return itemID;
    }
}
