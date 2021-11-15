package src.domini.model;

import src.domini.model.*;
import java.util.ArrayList;
import java.util.Collections;

//@Author Jordi Olmo
public class KND {

    //Atributes
    private int k;
    private ArrayList<Item> itemsUsats;
    private ArrayList<Double> Valoracions;
    private Conjunt_Items C_Items;
    private K_Neareast_Neightbour K_NN;

    //Constructura
    public KND(int k, Conjunt_Items C, ArrayList<Item> itemsUsats, ArrayList<Double> Valoracions) {
        this.k = k;
        this.Valoracions = Valoracions;
        this.itemsUsats = itemsUsats;
        for(int i = 0; i < itemsUsats.size(); ++i)
            Valoracions.add(i, 0.);
        C_Items = C;
        for (int i = 0; i < itemsUsats.size(); ++i)
            C_Items.anyadir_item(itemsUsats.get(i));
        K_NN = new K_Neareast_Neightbour(C_Items);
    }

    //Operacions

    public ArrayList<Item> Algorithm() {

        ArrayList <ArrayList<Item>> M_de_Items = new ArrayList<ArrayList<Item>>();
        ArrayList<ArrayList<Double>> Distances = new ArrayList<ArrayList<Double>>();
        ArrayList <ArrayList<Double>> Valoracio = new ArrayList<ArrayList<Double>>();
        for (int j = 0; j < C_Items.n_Items(); ++j) {

            Distances.add(j, K_NN.Distancies_i(j));
            M_de_Items.add(j, C_Items.getItems());
            Valoracio.add(j, Valoracions);
            ordenar_Items(Distances.get(j), M_de_Items.get(j), Valoracio.get(j),  0, Distances.get(j).size()-1);

            /*//borra los que son mas que k
            for (int i = M_de_Items.get(j).size() -1; i >= k ; --i)
                M_de_Items.get(j).remove(i);*/
        }

        ArrayList<Item> Items_a_devolver = new ArrayList<>(k);
        comparar_conjunts(Valoracio,Items_a_devolver, Distances, M_de_Items, k);

        return Items_a_devolver;
    }

    //Operacions Auxiliars

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

    private void merge(ArrayList <Double> distancies,ArrayList<Item> Items, ArrayList<Double> Val, int l, int m, int r)
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

        for (int i = k; i < I_Finals.size(); ++i)
            I_Finals.remove(i);
    }

}
