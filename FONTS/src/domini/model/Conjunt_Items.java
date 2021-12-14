package FONTS.src.domini.model;

import FONTS.src.domini.model.*;
import java.util.*;

/** \brief Estructura de datos para guardar Item de forma ordenada por ID y a la vez guarda una matriz con todas las distancias.
 *  @author Jordi Olmo
 */
public class Conjunt_Items {

    //Atributes
    /** ArrayList que contiene todos los Items.
     * @see Item
     */
    private ArrayList<Item> Items;

    /** Matriz de ArrayList de Double que contiene las distancias de un item a todos los otros items, teniendo en cuenta que las posicion coincide con
     la que ocupan en Conjunt_Items tanto en filas, como en columnas. i.e. Si el item ocupa la posicion i en Conjunt_Items la fila i de la matriz,
     corresponde a ese Item y sus distancias. También en cada fila la columna i representa la distancia del item de esa fila al Item de posicion.
     i. Por lo tanto la posicion[i][i] de la matriz tiene valor 0.0.
     */
    private ArrayList<ArrayList<Double> > Distances;

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
        Distances = new ArrayList<ArrayList<Double>>();
        initzialitzar_matriu();
    }

    /** Creadora de la classe con una ArraList vacía.
     * @see Item
     */

    public Conjunt_Items() {

        Items = new ArrayList<Item> ();
        Distances = new ArrayList<ArrayList<Double> >();
    }

    //Getters

    /** Devuelve si el Item con este ID está en el Conjunt o no.
     * @see Item
     * @param id ID del Item a buscar.
     */

    public  boolean existeix_item(int id) {

        return binarySearch(Items, 0, Items.size()-1, id);
    }

    /** Devuelve la posicion del Item en la ArrayList del conjunt.
     * @see Item
     * @param a Item del que se quiere saber la posicion.
     */

    public int get_position(Item a) {

        return binarySearchPosition(Items, 0, Items.size()-1, a.getID());
    }

    /** Devuelve el número de Item de la conjunt.
     */

    public int n_Items() { return Items.size();}

    /** Devuelve una ArrayList con los Item del Conjunt (Ordenada).
     * @see Item
     */

    public ArrayList<Item> getItems() {
        return Items;
    }

    /** Devuelve la matriz de distancias de todos los items del conjunt
     */

    public ArrayList<ArrayList<Double>> getDistances() { return Distances; }

    /** Devuelve el vector con las distancias del item a
     * * @see Item
     * * @param a Item del que se quieren saber sus distancias.
     */

    public ArrayList<Double> getDistancesofItem(Item a) {

        int pos = get_position(a);
        return Distances.get(pos);
    }

    /** Devuelve la distancia entre dos items. Al finalizar si esta no estaba en la matriz se añade.
     * @param a  Primer Item.
     * @param b  Segundo Item.
     */

    public Double Distance (Item a , Item b) {

        int pos_a = get_position(a);
        int pos_b = get_position(b);
        if (a.getID() != b.getID()) {

            if (existeix_item(a.getID()) && existeix_item(b.getID())){

                double d = a.Distance(b);
                if (pos_a <= Distances.size() - 1 && pos_b <= Distances.size() - 1) {

                    Distances.get(pos_a).set(pos_b, d);
                    Distances.get(pos_b).set(pos_a, d);
                }
                return d;
            }
            else return -1.0;
        }

        else {

            if (pos_a <= Distances.size() - 1 && pos_b <= Distances.size() - 1)
                Distances.get(pos_a).set(pos_b, 0.0);
            return 0.0;
        }
    }

    //Setters

    /** Define una ArrayList como los Item del Conjunt y la ordena. También calcula la matriz de distancias.
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
        initzialitzar_matriu();
        return true;
    }

    //Operacions
/*
    /** Rellena la matriz Distances con las respectivas distancias de los items. La matriz ya debe ser del tamaño del
     conjunt d'Items.
     */
/*
    public void omplir_matriu() {

        for (int i = 0; i < n_Items(); ++i)
            for(int j = 0; j < n_Items(); ++j){

                if (i == j)
                    Distances.get(i).set(j, 0.0);

                else if((Distances.get(i).get(j) == -1.) && (Distances.get(i).get(j) == -1.)) {

                    Double aux = Items.get(i).Distance(Items.get(j));
                    Distances.get(i).set(j, aux);
                    Distances.get(j).set(i, aux);
                }
            }
    }*/

    /** Añade el item del parametro al Conjunt. También añade las filas y columnas correspondientes a la matriz de distancias.
     * @see Item
     * @param a Item a añadir en el conjunt.
     */

    public boolean anyadir_item(Item a)  {

        if (!existeix_item(a.getID())) {
            int i = BinaryInsertionPos(Items, 0, Items.size()-1, a.getID());
            Items.add(i+1, a);
            anyadir_elements( i+1, a);
            return true;
        }
        else return false;//falta exception creo
    }

    /** Elimina el item del parametro al Conjunt.
     * @see Item
     * @param a Item a eliminar en el conjunt.
     */

    public boolean eliminar_item(Item a)  {

        if (existeix_item(a.getID())) {
            int i = binarySearchPosition(Items, 0 ,n_Items()-1, a.getID());
            Items.remove(a);
            eliminar_elements(i);
            return true;
        }
        else return false;
    }

    //Operacions Auxiliars

    /** Inicializa la matriz Distances con sus respectivas distancias en todos los items.
     */

    private void initzialitzar_matriu() {

        for (int i = 0; i < n_Items(); ++i) {

            ArrayList<Double> Aux = new ArrayList<Double>();
            for (int j = 0; j < n_Items(); ++j){

                if (i == j) Aux.add(0.0);
                else {

                    Double d;
                    if (i <= j) d = Distance(Items.get(i), Items.get(j));
                    else d = Distances.get(j).get(i);
                    Aux.add(d);
                }
            }

            Distances.add(Aux);
        }
    }



    /** Añades el Item a Distance con -1.0, si este no pertenecía a Conjunt_Items.
     * @param i posision donde se insertó el Item
     */

    private void anyadir_elements(int i, Item a) {

        ArrayList<Double> Aux = new ArrayList<Double>();
        if (n_Items() > 0) {

            for (int j = 0; j < n_Items(); ++j){

                Item b = Items.get(j);
                Double d = Distance(a,b);
                Aux.add(d);
            }

            Distances.add(i, Aux);

            for (int j = 0; j < n_Items(); ++j) {

                Double d = Distances.get(i).get(j);
                if (i != j) Distances.get(j).add(i, d);
            }
        }
        else {

            ArrayList<ArrayList<Double>> Dis_Aux = new ArrayList<ArrayList<Double>>();
            Aux.add(0.0);
            Dis_Aux.add(Aux);
            Distances = Dis_Aux;
        }
    }

    /** Añades el Item a Distance con -1.0, si este no pertenecía a Conjunt_Items.
     * @param i pos-1 donde se insertó el Item
     */

    private void eliminar_elements(int i) {

        if (n_Items() > 1) {

            for (int j = 0; j < n_Items(); ++j)
                Distances.get(j).remove(i);
        }
        else {

            ArrayList<ArrayList<Double>> Dis_Aux = new ArrayList<ArrayList<Double>>();
            Distances = Dis_Aux;
        }
    }

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