package FONTS.src.domini.model;

import FONTS.src.domini.model.*;
import FONTS.src.domini.controladores.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Vector;


public class Main {
    public void loadFiles() {

    }
    public static void main(String[] args) {
        Scanner S = new Scanner(System.in);
        String s =  S.next();
        System.out.println(s);
        //leer valoraciones know
        ArrayList <Item> it = new ArrayList<Item>();
        ArrayList <Double> va = new ArrayList<Double>();
        ArrayList<Vector<String>> readed_ratings = new ArrayList<Vector<String>>();

        LectorCSV2 reader = new LectorCSV2();
        readed_ratings = reader.Lector_Ratings("122");

        for (Vector<String> vs : readed_ratings) {
            Item nou_it= new Item(Integer.valueOf(vs.get(1)));
            if (!it.contains(nou_it)) {//No existeix item
                it.add(nou_it);
                va.add(Double.valueOf(vs.get(2)));
            }
        }
        //k-neighbours

        //K_Neareast_Neightbour knn = new K_Neareast_Neightbour(itemList);
        //knn.Algorithm(num_elem,it, va );
    /*
    TO DO
        - Pedir que escriba el directorio de los ficheros que quiere leer
     */
    }
}