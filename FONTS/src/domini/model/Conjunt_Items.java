package FONTS.src.domini.model;

import FONTS.src.domini.model.*;
import java.util.*;

/** \brief Estructura de datos para guardar Item de forma ordenada por ID.
 *  @author Jordi Olmo
 */
public class Conjunt_Items {

    //Atributes
    /** ArrayList que contiene todos los Items.
     * @see Item
     */
    private ArrayList<Item> Items;

    //Creadora

    /** Creadora de la classe. Crea un Conjunt_Item con la Array ordenada crecientemente por ID de Item.
     * @see Item
     * @param items ArrayList de Items con todos los Item del programa.
     */

    public Conjunt_Items(ArrayList<Item> items) {
        Items = items;
        Collections.sort(Items, new Comparator<Item> () {

            // Used for sorting in ascending order of Item.ID
            public int compare(Item a, Item b)
            {
                return a.getID() - b.getID();
            }
        });
    }

    /** Creadora de la classe con una ArraList vacía.
     * @see Item
     */

    public Conjunt_Items() {
        Items = new ArrayList<Item> ();
    }

    //Getters

    /** Devuelve si el Item con este id esta en el Conjunt o no.
     * @see Item
     * @param id ID del item a buscar.
     */

    public  boolean existeix_item(int id) {

        return binarySearch(Items, 0, Items.size()-1, id);
    }

    /** Devuelve la posicion del Item en la ArrayList del conjunt.
     * @see Item
     * @param a Item del que se quiere saber la posicion.
     */

    public int get_posiion (Item a) {

        return binarySearchPosition(Items, 0, Items.size()-1, a.getID());
    }

    /** Devuelve el numero de Item de la conjunt.
     */

    public int n_Items() { return Items.size();}

    /** Devuelve una ArrayList con los Item del Conjunt (Ordenada).
     * @see Item
     */

    public ArrayList<Item> getItems() {
        return Items;
    }

    //Setters

    /** Define una ArrayList como los Item del Conjunt y la ordena.
     * @see Item
     */

    public boolean setItems(ArrayList<Item> items) {
        Items = items;
        Collections.sort(Items, new Comparator<Item> () {

            // Used for sorting in ascending order of Item.ID
            public int compare(Item a, Item b)
            {
                return a.getID() - b.getID();
            }
        });
        return true;
    }

    //Operacions

    /** Añade el item del parametro al Conjunt.
     * @see Item
     * @param a Item a añadir en el conjunt.
     */

    public boolean anyadir_item(Item a)  {

        if (!existeix_item(a.getID())) {
            int i = BinaryInsertionPos(Items, 0, Items.size()-1, a.getID());
            Items.add(i+1, a);
            return true;
        }
        else return false;//falta exception creo
    }

    //Operacions Auxiliars

    /** Funcion derivada del algoritmo margeSort que sirve para buscar un Item en una ArrayList ordenada crecientemente
     por ID de Item.
     * @see Item
     * @param Items ArrayList de Item en la que buscar.
     * @param l Extremo izquierdo de la ArrayList en la que buscar.
     * @param r Extremo derecho de la ArrayList en la que buscar.
     * @param ID ID del Item a buscar en el conjunt.
     */

    private boolean binarySearch(ArrayList<Item> Items, int l, int r, int ID) {

        if (r >= l) {
            int mid = l + (r - l) / 2;
            //mirem se es en el mig
            if (Items.get(mid).getID() == ID)
                return true;
            //si no en es subvector esquerra
            if (Items.get(mid).getID() > ID)
                return binarySearch(Items, l, mid - 1, ID);
            //si no a la dreta
            return binarySearch(Items, mid + 1, r, ID);
        }
        else return false;
    }

    /** Funcion derivada del algoritmo margeSort que sirve para buscar la posicion de un Item en una ArrayList ordenada crecientemente
     por ID de Item.
     * @see Item
     * @param Items ArrayList de Item en la que buscar la posicion.
     * @param l Extremo izquierdo de la ArrayList en la que buscar.
     * @param r Extremo derecho de la ArrayList en la que buscar.
     * @param ID ID del Item a buscar la posicion en el conjunt.
     */

    private int binarySearchPosition(ArrayList<Item> Items, int l, int r, int ID) {

        if (r >= l) {
            int mid = l + (r - l) / 2;
            //mirem se es en el mig
            if (ID == Items.get(mid).getID())
                return mid;
            //si no en es subvector esquerra
            if (Items.get(mid).getID() > ID)
                return binarySearchPosition(Items, l, mid - 1, ID);
            //si no a la dreta
            return binarySearchPosition(Items, mid + 1, r, ID);
        }
        else return -1; //en teoria exception
    }

    /** Funcion derivada del algoritmo margeSort que sirve para buscar la posicion en la que se insertaria un item,
     devuelve la posicion-1.
     * @see Item
     * @param Items ArrayList de Item en la que buscar.
     * @param l Extremo izquierdo de la ArrayList en la que buscar.
     * @param r Extremo derecho de la ArrayList en la que buscar.
     * @param ID ID del Item a buscar donde insertar en el conjunt.
     */

    private int BinaryInsertionPos(ArrayList<Item> Items, int l, int r, int ID) {

        if (r > l) {
            int mid = l + (r - l) / 2;
            //mirem se es en el mig
            if (ID == Items.get(mid).getID())
                return mid;
            //si no en es subvector esquerra
            if (Items.get(mid).getID() > ID)
                return BinaryInsertionPos(Items, l, mid - 1, ID);
            //si no a la dreta
            return BinaryInsertionPos(Items, mid + 1, r, ID);
        }
        else  if (r >= 0 && Items.get(r).getID() > ID)
            return r-1; //en teoria exception
        else return r;
    }
}