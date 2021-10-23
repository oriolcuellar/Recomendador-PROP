import java.util.ArrayList;

public class ItemRecomanat {
    //atributes
    private int qualitat;
    private ArrayList<Item> itemsRecomanats;
    private User usuari;
    //getters
    public int getQuality() {
        return qualitat;
    }
    public ArrayList<Item> getItemsRecomanats() {
        return itemsRecomanats;
    }
    public User getUsuari() {
        return usuari;
    }
    //setters
    public void setQualitat(int quality) {
        this.qualitat = quality;
    }
    public void setItemsRecomanats(ArrayList<Item> itemsRecomanats) {
        this.itemsRecomanats = itemsRecomanats;
    }
    public void setUsuari(User usuari) {
        this.usuari = usuari;
    }

    //constructores
    public ItemRecomanat(int qualitat, ArrayList<Item> itemsRecomanats, User usuari) {
        this.qualitat = qualitat;
        this.itemsRecomanats = itemsRecomanats;
        this.usuari = usuari;
    }
    public ItemRecomanat() {}
}