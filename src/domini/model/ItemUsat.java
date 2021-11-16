package src.domini.model;

import src.domini.model.*;
import java.util.ArrayList;

//@Author Marc y Roberto

public class ItemUsat {

    private float valoracio;
    private Item item;
    private User usuari;

    //Creadores

    public ItemUsat(Item  item, User usuari, float valoracio) {
        this.item = item;
        this.usuari = usuari;
        this.valoracio = valoracio;
    }

    public ItemUsat(Item item, float valoracio) {
        this.item = item;
        this.valoracio = valoracio;
    }

    public ItemUsat() {
        valoracio = -1;
    }

    //Getters

    public float getValoracio() {
        return valoracio;
    }
    public User getUsuari() {
        return usuari;
    }
    public Item getItem() {
        return item;
    }

    //Setters

    public void setValoracio(float valoracio) {
        this.valoracio = valoracio;
    }
    public void setUsuari(User Usuari) {
        this.usuari = Usuari;
    }
    public void setItemUsat(Item item) {
        this.item = item;
    }
}