package FONTS.src.domini.model;

import FONTS.src.domini.model.*;
import java.util.ArrayList;

/** \brief Clase que representa un item valorado
 *  @author Marc Camarillas
 */
public class valoratedItem {

    private float valoracio;
    private Item item;
    private User usuari;

    //Creadores

    public valoratedItem(Item  item, User usuari, float valoracio) {
        this.item = item;
        this.usuari = usuari;
        this.valoracio = valoracio;
    }

    public valoratedItem(Item item, float valoracio) {
        this.item = item;
        this.valoracio = valoracio;
    }

    public valoratedItem() {
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
    public void setvaloratedItem(Item item) {
        this.item = item;
    }
}