package src.domini.model;

import src.domini.model.*;
import java.util.ArrayList;
import java.util.Collections;

//@Author Jordi Olmo
public class KND {

    private int k;
    private ArrayList<Integer> pos;
    private ArrayList<Item> items;
    private ArrayList<ItemUsat> itemsUsats;
    private Conjunt_Items C_Items;
    private K_Neareast_Neightbour K_NN;

    public KND(int k, ArrayList<Item> a, ArrayList<ItemUsat> itemsUsats) {
        this.k = k;
        items = a;
        for (int i = 0; i < a.size(); ++i)
            a.add(i, itemsUsats.get(i).getItem());
        C_Items = new Conjunt_Items(a);
        for (int i = 0; i < a.size(); ++i)
            pos.add(i, C_Items.get_posiion(a.get(i)));
        K_NN = new K_Neareast_Neightbour(C_Items);
    }

    public ArrayList<Item> Algorithm() {

        ArrayList <ArrayList<Item>> M_de_Items = new ArrayList<ArrayList<Item>>();
        ArrayList<ArrayList<Double>> Distances = new ArrayList<ArrayList<Double>>();
        ArrayList <ArrayList<Float>> Valoracio = new ArrayList<ArrayList<Float>>();
        for (int j = 0; j < pos.size(); ++j) {

            Distances.add(j, K_NN.Distancies_i(pos.get(j)));
            M_de_Items.add(j, C_Items.getItems());
            passar_valoraciones(itemsUsats, Valoracio.get(j));
            ordenar_Items(Distances.get(j), M_de_Items.get(j), Valoracio.get(j),  0, Distances.get(j).size());

            //borra los que son mas que k
            for (int i = k; i < M_de_Items.get(j).size(); ++i)
                M_de_Items.get(j).remove(i);
        }

        ArrayList<Item> Items_a_devolver = new ArrayList<>(k);
        comparar_conjunts(Valoracio,Items_a_devolver, Distances, M_de_Items, k);

        return Items_a_devolver;
    }

    private void ordenar_Items(ArrayList <Double> distancies, ArrayList<Item> Items, ArrayList<Float> Val, int l, int r) {

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

    private void merge(ArrayList <Double> distancies,ArrayList<Item> Items, ArrayList<Float> Val, int l, int m, int r)
    {
        // Find sizes of two subarrays to be merged
        int n1 = m - l + 1;
        int n2 = r - m;

        /* Create temp arrays */
        Double L[] = new Double[n1];
        Double R[] = new Double[n2];

        Item LI[] = new Item[n1];
        Item RI[] = new Item[n2];

        Float LV[] = new Float[n1];
        Float RV[] = new Float[n2];

        /*Copy data to temp arrays*/
        for (int i = 0; i < n1; ++i) {

            L[i] = distancies.get(l+1);
            LI[i] = Items.get(l+1);
            LV[i] = Val.get(l+1);
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
                distancies.add(k, L[i]);
                Items.add(k, LI[i]);
                Val.add(k, LV[i]);
                i++;
            }
            else {
                distancies.add(k, R[j]);
                Items.add(k, RI[j]);
                Val.add(k, RV[i]);
                j++;
            }
            k++;
        }

        /* Copy remaining elements of L[] if any */
        while (i < n1) {
            distancies.add(k, L[i]);
            Items.add(k, LI[i]);
            Val.add(k, LV[i]);
            i++;
            k++;
        }

        /* Copy remaining elements of R[] if any */
        while (j < n2) {
            distancies.add(k, R[j]);
            Items.add(k, RI[j]);
            Val.add(k, RV[i]);
            j++;
            k++;
        }
    }
    //Sent les matrius en ordre del parametres Y, Z, X -> per uns Item i, j qualsevols I_Finals = Sumatori(Zi*Yi) | Xi == Xj

    private void comparar_conjunts (ArrayList<ArrayList<Float>> Val, ArrayList<Item> I_Finals, ArrayList<ArrayList<Double>> Distances,
                                    ArrayList <ArrayList<Item>> M_de_Items, int k) {

        ArrayList <Double> Valors = new ArrayList<Double>();
        for (int i = 0; i < M_de_Items.size(); ++i)
            for (int j = 0; j < M_de_Items.get(i).size(); ++j) {

                if (!I_Finals.contains(M_de_Items.get(i).get(j))) {

                    I_Finals.add(i, M_de_Items.get(i).get(j));
                    Double multiplicacio =  Distances.get(i).get(j) * Val.get(i).get(j);
                    Valors.set(i, multiplicacio);
                }
                else {

                    int p = I_Finals.indexOf(M_de_Items.get(i).get(j));
                    Double multiplicacio =  Distances.get(i).get(j) * Val.get(i).get(j);
                    Double nou = Valors.get(p) + multiplicacio;
                    Valors.set(p, nou);
                }
            }

        ordenar_simplificado(I_Finals, Valors, 0, I_Finals.size());

        for (int i = k; i < I_Finals.size(); ++i)
            I_Finals.remove(i);
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

            L[i] = Val.get(l+1);
            LI[i] = Items.get(l+1);
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
                Val.add(k, L[i]);
                Items.add(k, LI[i]);
                i++;
            }
            else {
                Val.add(k, R[j]);
                Items.add(k, RI[j]);
                j++;
            }
            k++;
        }

        /* Copy remaining elements of L[] if any */
        while (i < n1) {
            Val.add(k, L[i]);
            Items.add(k, LI[i]);
            i++;
            k++;
        }

        /* Copy remaining elements of R[] if any */
        while (j < n2) {
            Val.add(k, R[j]);
            Items.add(k, RI[j]);
            j++;
            k++;
        }
    }

    private void passar_valoraciones(ArrayList <ItemUsat> M_de_Item, ArrayList <Float> Val) {

            for (int j = 0; j <M_de_Item.size(); ++j )
                Val.set(j, M_de_Item.get(j).getValoracio());
    }
}
