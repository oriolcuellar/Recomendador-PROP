package FONTS.src.domini.model;

import java.util.ArrayList;

public class HybridApproach {

    public HybridApproach() {
    }
    
    public ArrayList<ArrayList> Algoritmo (ArrayList<myPair> Slope, ArrayList<ArrayList> Knn, int n_items, int n_valorats){

        ArrayList<myPair> S = new ArrayList<myPair>();
        ArrayList<ArrayList> K = new ArrayList<ArrayList>();

        int pos_positiu = binarySearchPosition_Slope(Slope, 0, Slope.size());
        S = new ArrayList<myPair>(Slope.subList(0, pos_positiu));
        clonador_ArrayList_Knn(K, Knn);

        Float max_Slope = S.get(0).getValoration();
        Double max_Knn = (Double) K.get(1).get(0);
        normalizar_val_Slope(S, max_Slope);
        normalizar_val_Knn(K, max_Knn);

        Double percentatge_valorats = n_valorats / Double.valueOf(n_items);
        Double percentatge_recomanats = S.size()/ Double.valueOf(n_items);

        Double percentantge_Knn = 0.30;

        if (percentatge_recomanats < 0.70 && percentatge_recomanats + percentantge_Knn < 0.5 )
            percentantge_Knn +=  percentatge_valorats;

        if (percentatge_valorats > percentantge_Knn)
            percentantge_Knn = percentatge_recomanats;

        Double percentarge_Slope = 1 - percentantge_Knn;

        ArrayList<Item> i_aux = K.get(0);
        ArrayList<Double> v_aux = K.get(1);

        for (int i = 0; i < K.get(0).size(); ++i){

            Double val_Slope = valoracion_item(S, i_aux.get(i).getID());
            Double val_Knn = v_aux.get(i);
            Double val_final = val_Knn * percentantge_Knn + val_Slope * percentarge_Slope;
            K.get(1).set(i, val_final);
        }

        ordenar_simplificado(K.get(0), K.get(1), 0, K.get(0).size()-1);

        return K;
    }

    private void clonador_ArrayList_Knn (ArrayList<ArrayList>  A, ArrayList<ArrayList>  B) {

        ArrayList<Item> item_aux = new ArrayList<Item>();
        ArrayList<Double> valoration_aux = new ArrayList<Double>();
        int tamanyo = B.get(0).size();

        for (int j = 0; j < tamanyo; ++j){

            item_aux.add((Item) B.get(0).get(j));
            valoration_aux.add((Double) B.get(1).get(j));
        }

        A.add(item_aux);
        A.add(valoration_aux);
    }

    /** Funcion derivada del algoritmo margeSort que sirve para buscar la posicion de un Item en una ArrayList ordenada crecientemente
     por ID de Item.
     * @see Item
     * @param Slope ArrayList de Item en la que buscar la posicion.
     * @param l Extremo izquierdo de la ArrayList en la que buscar.
     * @param r Extremo derecho de la ArrayList en la que buscar.
     */

    private int binarySearchPosition_Slope(ArrayList<myPair> Slope, int l, int r) {

        if (r >= l) {
            int mid = l + (r - l) / 2;
            //mirem se es en el mig
            if (Slope.get(mid+1).getValoration() < 0 && Slope.get(mid).getValoration() >= 0)
                return mid;
            //si no en es subvector esquerra
            if (Slope.get(mid).getValoration() < 0)
                return binarySearchPosition_Slope(Slope, l, mid - 1);
            //si no a la dreta
            return binarySearchPosition_Slope(Slope, mid + 1, r);
        }
        else return -1; //en teoria exception
    }
    private Double valoracion_item(ArrayList <myPair> Slope, int ID){

        Double valoracio = -1.0;
        for(int i = 0; i < Slope.size(); ++i)
            if (Slope.get(i).getItemID() == ID) valoracio = Double.valueOf(Slope.get(i).getValoration());

        return  valoracio;
    }

    private void normalizar_val_Slope (ArrayList<myPair> Slope, Float max_val) {

        if (max_val > 0) {
            for (int i = 0; i < Slope.size(); ++i) {

                myPair aux = Slope.get(i);
                int id_aux = aux.getItemID();
                Float val_aux = aux.getValoration() / max_val;
                Slope.set(i, new myPair(id_aux, val_aux));
            }
        }
    }

    private void normalizar_val_Knn (ArrayList<ArrayList> Knn, Double max_val) {

        if (max_val > 0) {

            ArrayList<Double> valorations_aux = Knn.get(1);

            for (int i = 0; i < valorations_aux.size(); ++i) {

                Double val_aux = valorations_aux.get(i) / max_val;
                valorations_aux.set(i, val_aux);
            }
        }
    }

    /** Version de ordenar_conjuntos simplificada para usar solo 2 Arraylist y ordenar crecientemente según
     el valor de Val (la valoracion)
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

}
