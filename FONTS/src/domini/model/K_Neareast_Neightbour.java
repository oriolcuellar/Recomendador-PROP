package FONTS.src.domini.model;

import FONTS.src.domini.model.*;
import java.util.ArrayList;
import java.util.Vector;

/** \brief Clase que implementa el algoritmo K_Neareast_Neightbour y a la vez guarda una matriz con todas las distancias.
 *  @author Jordi Olmo
 */
public class K_Neareast_Neightbour {

    //Atributes

    /** El conjunto de todos los Items guardados en la estructura de datos Conjunt_Item
     * @see Conjunt_Items
     */
    private Conjunt_Items C_Items;

    /** Matriu de ArrayList de Double que contiene las distancias de un item a todos los otros items, teniendo en cuenta que las posicion coincide con
     * la que ocupan en Conjunt_Items tanto en filas, como en columnas. i.e. Si el item ocupa la posicion i en Conjunt_Items la fila i de la matriz,
     corresponde a ese Item y sus distancias. También en cada fila la columna i representa la distancia del item de esa fila al Item de posicion.
     * i. Por lo tanto la posicion[i][i] de la matriz tiene valor 0.0.
     */
    private ArrayList<ArrayList<Double> > Distances;

    //Constructora

    /** Constructora de la clase. Inicilaitza y calcula la matriz de Distancias respecto al Conjunt_Items de parametro.
     * @param c_Items Conjunt_Items de todos los items registrados en el sistema.
     */

    public K_Neareast_Neightbour(Conjunt_Items c_Items) {
        C_Items = c_Items;
        Distances = new ArrayList<ArrayList<Double> >();
        initzialitzar_matriu();
        omplir_matriu();
    }

    //Getters

    /** Devuelve la distancia entre dos items.
     * @param a  Primer Item.
     * @param b  Segundo Item.
     */

    public Double Distance (Item a , Item b) {

        Double aux = Distances.get(C_Items.get_posiion(a)).get(C_Items.get_posiion(b));
        if(aux != -1.0)
            return aux;
        else
            return  a.Distance(b);
    }

    //Algorisme

    /** Algoritmo de recomendación, devuelve una ArraList con los k items mas parecidos a itemsUsats, (itemsUsats quedan
     añadidos a C_Items si no lo estaban.
     * @param k  Número de items que se devuelven.
     * @param itemsUsats  ArrayList de los Items sobre los que se quiere la recomendación.
     * @param Valoracions  ArrayList con las valoraciones de los itemsUsat, la posicion de la valoración en la ArrayList,
    debe coincidir con la posicion del Item en itemsUsats.
     */

    public ArrayList<Item> Algorithm(int k, ArrayList<Item> itemsUsats, ArrayList<Double> Valoracions) {

        ArrayList<Double> Valors = new ArrayList<Double>();

        for(int i = 0; i < C_Items.n_Items(); ++i)
            Valors.add(i, 0.);

        for (int i = 0; i < itemsUsats.size(); ++i)
            C_Items.anyadir_item(itemsUsats.get(i));


        for(int i = 0; i < itemsUsats.size(); ++i){
            int pos1 = C_Items.get_posiion(itemsUsats.get(i));
            if(pos1 > Valoracions.size())
                Valors.add((Valoracions.size()-1), Valoracions.get(i));
            else
                Valors.add((pos1), Valoracions.get(i));
        }


        anyadir_elements(itemsUsats.size());
        omplir_matriu();

        ArrayList <ArrayList<Item>> M_de_Items = new ArrayList<ArrayList<Item>>();
        ArrayList <ArrayList<Double>> Valoracio = new ArrayList<ArrayList<Double>>();
        ArrayList <ArrayList<Double>> Dis = new ArrayList<ArrayList<Double>>();
        for (int j = 0; j < C_Items.n_Items(); ++j) {

            ArrayList<Item> Items_Aux = new ArrayList<Item>();
            ArrayList<Double> Val_Aux = new ArrayList<Double>();
            ArrayList<Double> Dis_Aux = new ArrayList<Double>();

            clonador_ArrayList(Items_Aux, C_Items.getItems());
            clonador_ArrayList(Val_Aux, Valors);
            clonador_ArrayList(Dis_Aux, Distances.get(j));

            M_de_Items.add(j, Items_Aux);
            Valoracio.add(j, Val_Aux);
            Dis.add(j, Dis_Aux);

            ordenar_Items(Dis.get(j), M_de_Items.get(j), Valoracio.get(j),  0, Dis.get(j).size()-1);

            /*//borra los que son mas que k
            for (int i = M_de_Items.get(j).size() -1; i >= k ; --i)
                M_de_Items.get(j).remove(i);*/
        }

        ArrayList<Item> Items_a_devolver = new ArrayList<>(k);
        comparar_conjunts(Valoracio,Items_a_devolver, Dis, M_de_Items, k);

        return Items_a_devolver;
    }

    //Operacions Auxiliars del algorisme

    /** Dadas las matrizes de los parametros, devuelve una ArrayList con los items recomendados de medida k, siguiendo
     la siguiente fórmula: Siendo las matrices en orden de los parámetros Y, Z, X -> por unos Item i, j cualquier as.
     I_Finals = Sumatorio(Zi*Yi) | Xi == Xj.
     * @param Val Matriz de ArrayList con las valoraciones de todos los Item, si este no pertenecía a itemsUsats, su
     valor en la matriz será 0.0. La posicion de los items coincidira con M_de_Items.
     * @param I_Finals  ArrayList de los Items que se devolverán.
     * @param Distances Matriz de ArrayList con las distancias de todos los item. La posicion de los items coincidira
     con M_de_Items.
     * @param M_de_Items  Matriz con todos los Items ordenados con los que tengan mayor valor de distancia antes en las
       columnas. Esta ordenación se debe respetar en las otras dos matrices de los parametros.
     * @param k Número de items que se devuelven.
     */

    //Sent les matrius en ordre del parametres Y, Z, X -> per uns Item i, j qualsevols I_Finals = Sumatori(Zi*Yi) | Xi == Xj
    private void comparar_conjunts (ArrayList<ArrayList<Double>> Val, ArrayList<Item> I_Finals, ArrayList<ArrayList<Double>> Distances,
                                    ArrayList <ArrayList<Item>> M_de_Items, int k) {

        ArrayList <Double> Valors = new ArrayList<Double>();
        for (int i = 0; i < M_de_Items.size(); ++i)
            for (int j = 0; j < M_de_Items.get(i).size(); ++j) {

                if (!I_Finals.contains(M_de_Items.get(i).get(j))) {

                    I_Finals.add(i, M_de_Items.get(i).get(j));
                    Double multiplicacio =  Distances.get(i).get(j) * Val.get(i).get(j);
                    Valors.add(i, multiplicacio);
                }
                else {

                    int p = I_Finals.indexOf(M_de_Items.get(i).get(j));
                    Double multiplicacio =  Distances.get(i).get(j) * Val.get(i).get(j);
                    Double nou = Valors.get(p) + multiplicacio;
                    Valors.set(p, nou);
                }
            }

        ordenar_simplificado(I_Finals, Valors, 0, I_Finals.size()-1);

        for (int i = I_Finals.size()-1; i >= k ; --i)
            I_Finals.remove(i);
    }

    /** Rellena la matriz Distances con las respectivas distancias de los items. La matriz ya debe ser del tamaño del
     conjunt d'Items.
     */

    //la matriz ya debe ser del tamaño del conjunt d'items
    private void omplir_matriu() {

        for (int i = 0; i < C_Items.n_Items(); ++i)
            for(int j = 0; j < C_Items.n_Items(); ++j){

                if (i == j)
                    Distances.get(i).set(j, 0.0);

                else if((Distances.get(i).get(j) == -1.) && (Distances.get(i).get(j) == -1.)) {

                    Double aux = C_Items.getItems().get(i).Distance(C_Items.getItems().get(j));
                    Distances.get(i).set(j, aux);
                    Distances.get(j).set(i, aux);
                }
            }
    }

    /** Inicializa la matriz Distances con -1.0 en todos los items. La matriz ya debe ser del tamaño del
     conjunt d'Items.
     */

    private void initzialitzar_matriu() {

        for (int i = 0; i < C_Items.n_Items(); ++i) {

            ArrayList<Double> Aux = new ArrayList<Double>();
            for (int j = 0; j < C_Items.n_Items(); ++j)
                Aux.add(-1.0);
            Distances.add(Aux);
        }

    }

    /** Añades los itemsUsats a Distance con -1.0, si estos no pertenecían a Conjunt_Items.
     * @param n numeros de items en itemsUsats.
     */

    private void anyadir_elements(int n) {

        for (int i = 0; i < n; ++i) {

            ArrayList<Double> Aux = new ArrayList<Double>();
            for (int j = 0; j < C_Items.n_Items(); ++j)
                Aux.add(-1.0);
            Distances.add(Aux);
        }

        for (int i = 0; i < n; ++i)
            for (int j = 0; j < n; ++j )
                Distances.get(i).add(-1.0);

    }

    /** Ordena toda la ArrayList de los parametros, de forma creciente para los items con mayor valor en el parametro,
     distancies. El algoritmo es un merge_sort modificado para ordenar las tres ArrayList.
     * @param l extremo izquierda de las ArrayList a ordenar.
     * @param r extremo derecho de las ArrayList a ordenar.
     * @param Val ArrayList de Double con las valoraciones de los items de itemUsats.
     * @param Items ArrayList de Item.
     * @param distancies ArrayList de Double con distancies.
     */

    private void ordenar_Items(ArrayList <Double> distancies, ArrayList<Item> Items, ArrayList<Double> Val, int l, int r) {

        if (l < r) {
            // Troba el punt del mig
            int m = l + (r - l) / 2;

            // ordena l'esquerra i la dreta
            ordenar_Items(distancies, Items, Val,  l, m);
            ordenar_Items(distancies,Items, Val,  m + 1, r);

            // Combina els dos vectors
            merge(distancies, Items, Val,  l, m, r);
        }
    }

    //Operacions auxiliars per ordenar

    /** Merge de la funcion Ordenar_Items para combinar las ArrayList separadas con el orden especificado en esa funcion.
     * @param l extremo izquierda de las ArrayList a ordenar.
     * @param r extremo derecho de las ArrayList a ordenar.
     * @param Val ArrayList de Double con las valoraciones de los items de itemUsats.
     * @param Items ArrayList de Item.
     * @param distancies ArrayList de Double con distancies.
     */

    private void merge (ArrayList <Double> distancies,ArrayList<Item> Items, ArrayList<Double> Val, int l, int m, int r)
    {
        // Find sizes of two subarrays to be merged
        int n1 = m - l + 1;
        int n2 = r - m;

        /* Create temp arrays */
        Double L[] = new Double[n1];
        Double R[] = new Double[n2];

        Item LI[] = new Item[n1];
        Item RI[] = new Item[n2];

        Double LV[] = new Double[n1];
        Double RV[] = new Double[n2];

        /*Copy data to temp arrays*/
        for (int i = 0; i < n1; ++i) {

            L[i] = distancies.get(l+i);
            LI[i] = Items.get(l+i);
            LV[i] = Val.get(l+i);
        }

        for (int j = 0; j < n2; ++j) {

            R[j] = distancies.get(m + 1 + j);
            RI[j] = Items.get(m + 1 + j);
            RV[j] = Val.get(m + 1 + j);
        }

        /* Merge the temp arrays */

        // Initial indexes of first and second subarrays
        int i = 0, j = 0;

        // Initial index of merged subarray array
        int k = l;
        while (i < n1 && j < n2) {
            if (L[i] >= R[j]) {
                distancies.set(k, L[i]);
                Items.set(k, LI[i]);
                Val.set(k, LV[i]);
                i++;
            }
            else {
                distancies.set(k, R[j]);
                Items.set(k, RI[j]);
                Val.set(k, RV[i]);
                j++;
            }
            k++;
        }

        /* Copy remaining elements of L[] if any */
        while (i < n1) {
            distancies.set(k, L[i]);
            Items.set(k, LI[i]);
            Val.set(k, LV[i]);
            i++;
            k++;
        }

        /* Copy remaining elements of R[] if any */
        while (j < n2) {
            distancies.set(k, R[j]);
            Items.set(k, RI[j]);
            Val.set(k, RV[j]);
            j++;
            k++;
        }
    }

    /** Version de ordenar_conjuntos simplificada para usar solo 2 Arraylist y ordenar crecientemente según
     el valor de Val
     * @param l extremo izquierda de las ArrayList a ordenar.
     * @param r extremo derecho de las ArrayList a ordenar.
     * @param Val ArrayList de Double con los valores resultado de aplicar comparar_conjunts, coincidiendo posiciones
     con el primer parametro.
     * @param Items ArrayList de Item, de tamaño igual a k.
     */

    private void ordenar_simplificado(ArrayList<Item> Items, ArrayList<Double> Val, int l, int r) {

        if (l < r) {
            // Troba el punt del mig
            int m = l + (r - l) / 2;

            // ordena l'esquerra i la dreta
            ordenar_simplificado(Items, Val,  l, m);
            ordenar_simplificado(Items, Val,  m + 1, r);

            // Combina els dos vectors
            merge_simplificado(Items, Val,  l, m, r);
        }
    }

    /** Merge simplificado de merge para que funcione con ordenar_simplificacion.
     * @param l extremo izquierda de las ArrayList a ordenar.
     * @param r extremo derecho de las ArrayList a ordenar.
     * @param Val ArrayList de Double con los valores resultado de aplicar comparar_conjunts, coincidiendo posiciones
    con el primer parametro.
     * @param Items ArrayList de Item, de tamaño igual a k.
     */

    private void merge_simplificado(ArrayList<Item> Items, ArrayList<Double> Val, int l, int m, int r)
    {
        // Find sizes of two subarrays to be merged
        int n1 = m - l + 1;
        int n2 = r - m;

        /* Create temp arrays */
        Double L[] = new Double[n1];
        Double R[] = new Double[n2];

        Item LI[] = new Item[n1];
        Item RI[] = new Item[n2];


        /*Copy data to temp arrays*/
        for (int i = 0; i < n1; ++i) {

            L[i] = Val.get(l+i);
            LI[i] = Items.get(l+i);
        }

        for (int j = 0; j < n2; ++j) {

            R[j] = Val.get(m + 1 + j);
            RI[j] = Items.get(m + 1 + j);
        }

        /* Merge the temp arrays */

        // Initial indexes of first and second subarrays
        int i = 0, j = 0;

        // Initial index of merged subarray array
        int k = l;
        while (i < n1 && j < n2) {
            if (L[i] >= R[j]) {
                Val.set(k, L[i]);
                Items.set(k, LI[i]);
                i++;
            }
            else {
                Val.set(k, R[j]);
                Items.set(k, RI[j]);
                j++;
            }
            k++;
        }

        /* Copy remaining elements of L[] if any */
        while (i < n1) {
            Val.set(k, L[i]);
            Items.set(k, LI[i]);
            i++;
            k++;
        }

        /* Copy remaining elements of R[] if any */
        while (j < n2) {
            Val.set(k, R[j]);
            Items.set(k, RI[j]);
            j++;
            k++;
        }
    }

    //Operacions Auxiliars varies

    /** Copia todos los valores de la segunda ArrayList y los añade en la primera.
     * @param A ArrayList donde se copiara B, para que queden iguales debe ser vacía.
     * @param B ArrayList de origen que ser clonada en A.
     */

    private void clonador_ArrayList (ArrayList  A, ArrayList  B) {

        for(int i = 0; i< B.size(); ++i)
            A.add(i, B.get(i));
    }

}
