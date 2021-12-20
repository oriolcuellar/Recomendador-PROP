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

        int x = posicion_item(K.get(0), S.get(0).getItemID());

        Double percentatge_valorats = n_valorats / Double.valueOf(n_items);
        Double max_Slope = Double.valueOf(S.get(0).getValoration());
        Double max_Knn = (Double) Knn.get(1).get(0);


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
    private int posicion_item(ArrayList<Item> Items_Knn, int ID){

        int posicion = -1;
        for(int i = 0; i < Items_Knn.size(); ++i)
            if (Items_Knn.get(i).getID() == ID) posicion = i;

        return  posicion;
    }
}
