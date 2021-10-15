import java.util.ArrayList;

public class Item {
    //atributs
    private int ID;
    private TipusItem tipus;
    private ArrayList valors;
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
    //constructores
    public Item(int ID, TipusItem tipus, ArrayList valors) {
        this.ID = ID;
        this.tipus= tipus;
        this.valors = valors;
    }
    public Item(){}
}

