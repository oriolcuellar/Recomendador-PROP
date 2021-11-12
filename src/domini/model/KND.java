package src.domini.model;

import src.domini.model.*;
import java.util.ArrayList;
import java.util.Collections;

//Author Jordi Olmo
public class KND {

    private int k;
    private int pos;
    private Item item;
    private ArrayList<Item> itemsUsats;
    private Conjunt_Items C_Items;
    private K_Neareast_Neightbour K_NN;

    public KND(int k, Item a, ArrayList<Item> itemsUsats) {
        this.k = k;
        this.itemsUsats = itemsUsats;
        item = a;
        itemsUsats.add(a);
        C_Items = new Conjunt_Items(itemsUsats);
        pos = C_Items.get_posiion(a);
        K_NN = new K_Neareast_Neightbour(C_Items);
    }

    public ArrayList<Item> Algorithm() {

        ArrayList <Double> distancies = K_NN.Distancies_i(pos);
        ArrayList<Item> I_de_a = C_Items.getItems();
        ordenar_Items(distancies, I_de_a, 0, distancies.size());

        for (int i = k; i < I_de_a.size();++i)
            I_de_a.remove(i);

        return I_de_a;
    }

    private void ordenar_Items(ArrayList <Double> distancies, ArrayList<Item> Items, int l, int r) {

        if (l < r) {
            // Troba el punt del mig
            int m = l + (r - l) / 2;

            // ordena l'esquerra i la dreta
            ordenar_Items(distancies, Items, l, m);
            ordenar_Items(distancies,Items, m + 1, r);

            // Combina els dos vectors
            merge(distancies, Items, l, m, r);
        }
    }

    void merge(ArrayList <Double> distancies,ArrayList<Item> Items, int l, int m, int r)
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

            L[i] = distancies.get(l+1);
            LI[i] = Items.get(l+1);
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
                distancies.add(k, L[i]);
                Items.add(k, LI[i]);
                i++;
            }
            else {
                distancies.add(k, R[j]);
                Items.add(k, RI[j]);
                j++;
            }
            k++;
        }

        /* Copy remaining elements of L[] if any */
        while (i < n1) {
            distancies.add(k, L[i]);
            Items.add(k, LI[i]);
            i++;
            k++;
        }

        /* Copy remaining elements of R[] if any */
        while (j < n2) {
            distancies.add(k, R[j]);
            Items.add(k, RI[j]);
            j++;
            k++;
        }
    }
}
