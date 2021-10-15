import java.util.ArrayList;

public class TipusItem {
    //atributes
    private int ID;
    private ArrayList<Atribute> atributes;
    //getters
    public int getID() {
        return ID;
    }
    public ArrayList<Atribute> getAtributes() {
        return atributes;
    }
    //contructores
    public TipusItem(int ID, ArrayList<Atribute> atributes) {
        this.ID = ID;
        this.atributes = atributes;
    }
    public TipusItem(){}
}
