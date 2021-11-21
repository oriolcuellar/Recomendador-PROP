package FONTS.src.domini.model;

import java.util.ArrayList;

/** \brief Clase que implementa el algoritmo K_Neareast_Neightbour y a la vez guarda una matriz con todas las distancias.
 *  @author Jordi Olmo
 */
public class K_Neareast_Neightbour {

    //Atributes

    /** El conjunto de todos los Items guardados en la estructura de datos Conjunt_Item
     * @see Conjunt_Items
     */
    private Conjunt_Items C_Items;

    //Constructora

    /** Constructora de la clase.
     */

    public K_Neareast_Neightbour() {
    }

    //Getters

    /** Devuelve la distancia entre dos items.
     * @param a  Primer Item.
     * @param b  Segundo Item.
     */

    //Algorisme

    /** Algoritmo de recomendación, devuelve una ArraList con los k items mas parecidos a itemsUsats, (itemsUsats quedan
     añadidos a C_Items si no lo estaban.
     * @param k  Número de items que se devuelven.
     * @param itemsUsats  ArrayList de los Items sobre los que se quiere la recomendación.
     * @param Valoracions  ArrayList con las valoraciones de los itemsUsat, la posicion de la valoración en la ArrayList,
     * @param C_Items Conjunt_Items de todos los items registrados en el sistema.
    debe coincidir con la posicion del Item en itemsUsats.
     */

    public ArrayList<Item> Algorithm(int k,Conjunt_Items C_Items, ArrayList<Item> itemsUsats, ArrayList<Double> Valoracions) {

        ArrayList<Double> Valors = new ArrayList<Double>();

        for(int i = 0; i < C_Items.n_Items(); ++i)
            Valors.add(i, 0.0);

        for (int i = 0; i < itemsUsats.size(); ++i) {
            C_Items.anyadir_item(itemsUsats.get(i));
            Valors.add(0.0);
        }

        for (int i = 0; i < itemsUsats.size(); ++i) {

            int pos1 = C_Items.get_position(itemsUsats.get(i));
            Valors.set((pos1), Valoracions.get(i));
        }

        C_Items.omplir_matriu();
        ArrayList <ArrayList<Double>> M_Dis = C_Items.getDistances();

        ArrayList <ArrayList<Item>> M_de_Items = new ArrayList<ArrayList<Item>>();
        ArrayList <ArrayList<Double>> Dis = new ArrayList<ArrayList<Double>>();


        for (int j = 0; j < C_Items.n_Items(); ++j) {

            ArrayList<Item> Items_Aux = new ArrayList<Item>();
            ArrayList<Double> Dis_Aux = new ArrayList<Double>();

            clonador_ArrayList(Items_Aux, C_Items.getItems());
            clonador_ArrayList(Dis_Aux, M_Dis.get(j));

            M_de_Items.add(j, Items_Aux);
            Dis.add(j, Dis_Aux);

            ordenar_Items(Dis.get(j), M_de_Items.get(j),0, Dis.get(j).size()-1);

        }

        ArrayList<Item> Items_a_devolver = new ArrayList<>(k);
        comparar_conjunts(Valors,Items_a_devolver, Dis, M_de_Items, k);

        return Items_a_devolver;
    }

    //Operacions Auxiliars del algorisme

    /** Dadas las matrizes de los parametros, devuelve una ArrayList con los items recomendados de medida k, siguiendo
     la siguiente fórmula: Siendo las matrices en orden de los parámetros X,Y y el vector V -> por unos Item i, j cualquiera.
     Valors_i = Sumatorio(Xij * Vi)
     * @param Val ArrayList con las valoraciones de todos los Item de itemsUsats.
     * @param I_Finals  ArrayList de los Items que se devolverán.
     * @param Distances Matriz de ArrayList con las distancias de todos los item. La posicion de los items coincidira
     con M_de_Items.
     * @param M_de_Items  Matriz con todos los Items ordenados con los que tengan mayor valor de distancia antes en las
       columnas. Esta ordenación se debe respetar en las otras dos matrices de los parametros.
     * @param k Número de items que se devuelven.
     */

    private void comparar_conjunts (ArrayList<Double> Val, ArrayList<Item> I_Finals, ArrayList<ArrayList<Double>> Distances,
                                    ArrayList <ArrayList<Item>> M_de_Items, int k) {

        ArrayList <Double> Valors = new ArrayList<Double>();
        for (int i = 0; i < M_de_Items.size(); ++i)
            for (int j = 0; j < M_de_Items.get(i).size(); ++j) {

                if (!I_Finals.contains(M_de_Items.get(i).get(j))) {

                    I_Finals.add(i, M_de_Items.get(i).get(j));
                    Double multiplicacio =  Distances.get(i).get(j) * Val.get(i);
                    Valors.add(i, multiplicacio);
                }
                else {

                    int p = I_Finals.indexOf(M_de_Items.get(i).get(j));
                    double V = Val.get(i);
                    Double multiplicacio =  Distances.get(i).get(j) * V;
                    Double nou = Valors.get(p) + multiplicacio;
                    Valors.set(p, nou);
                }
            }

        ordenar_simplificado(I_Finals, Valors, 0, I_Finals.size()-1);

        for (int i = I_Finals.size()-1; i >= k ; --i)
            I_Finals.remove(i);
    }

    /** Ordena toda la ArrayList de los parametros, de forma creciente para los items con mayor valor en el parametro,
     distancies. El algoritmo es un merge_sort modificado para ordenar las tres ArrayList.
     * @param l extremo izquierda de las ArrayList a ordenar.
     * @param r extremo derecho de las ArrayList a ordenar.
     * @param Items ArrayList de Item.
     * @param distancies ArrayList de Double con distancies.
     */

    private void ordenar_Items(ArrayList <Double> distancies, ArrayList<Item> Items, int l, int r) {

        if (l < r) {
            // Troba el punt del mig
            int m = l + (r - l) / 2;

            // ordena l'esquerra i la dreta
            ordenar_Items(distancies, Items, l, m);
            ordenar_Items(distancies,Items,m + 1, r);

            // Combina els dos vectors
            merge(distancies, Items,  l, m, r);
        }
    }

    //Operacions auxiliars per ordenar

    /** Merge de la funcion Ordenar_Items para combinar las ArrayList separadas con el orden especificado en esa funcion.
     * @param l extremo izquierda de las ArrayList a ordenar.
     * @param r extremo derecho de las ArrayList a ordenar.
     * @param Items ArrayList de Item.
     * @param distancies ArrayList de Double con distancies.
     */

    private void merge (ArrayList <Double> distancies,ArrayList<Item> Items, int l, int m, int r)
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

            L[i] = distancies.get(l+i);
            LI[i] = Items.get(l+i);
        }

        for (int j = 0; j < n2; ++j) {

            R[j] = distancies.get(m + 1 + j);
            RI[j] = Items.get(m + 1 + j);
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
                i++;
            }
            else {
                distancies.set(k, R[j]);
                Items.set(k, RI[j]);
                j++;
            }
            k++;
        }

        /* Copy remaining elements of L[] if any */
        while (i < n1) {
            distancies.set(k, L[i]);
            Items.set(k, LI[i]);
            i++;
            k++;
        }

        /* Copy remaining elements of R[] if any */
        while (j < n2) {
            distancies.set(k, R[j]);
            Items.set(k, RI[j]);
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

    /** Copia todos los valores de la segunda ArrayList y los añade en la primera. Las ArrayList tienen que ser de los mismos tipos.
     * @param A ArrayList donde se copiara B, para que queden iguales debe ser vacía.
     * @param B ArrayList de origen que ser clonada en A.
     */

    private void clonador_ArrayList (ArrayList  A, ArrayList  B) {

        for(int i = 0; i< B.size(); ++i)
            A.add(i, B.get(i));
    }

}
