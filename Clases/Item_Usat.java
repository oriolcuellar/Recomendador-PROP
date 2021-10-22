import java.util.ArrayList;

public class Item_Usat {

    private int Valoracio;
    private Item ItemUsat;
    private User Usuari;

    public Item_Usat(Item  ItemUsat, User Usuari) {
        this.ItemUsat = ItemUsat;
        this.Usuari = Usuari;
        Valoracio = 0;
    }

    public Item_Usat() {
        Valoracio = 0;
    }

    public int getQuality() {
        return Valoracio;
    }

    public User getUsuari() {
        return Usuari;
    }
    public Item getItemUsat() {
        return ItemUsat;
    }
    public void setQuality(int Valor) {
        Valoracio = Valor;
    }

    public void setUsuari(User Usuari) {
        this.Usuari = Usuari;
    }
    public void setItemUsat(Item itemUsat) {
        ItemUsat = itemUsat;
    }
}
