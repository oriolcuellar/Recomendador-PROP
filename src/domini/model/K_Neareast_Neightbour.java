package src.domini.model;

import src.domini.model.*;
import java.util.ArrayList;
import java.util.Vector;

//@Author Jordi Olmo
public class K_Neareast_Neightbour {

        private Conjunt_Items C_Items;
        private Double [][] Distances;

    public K_Neareast_Neightbour(Conjunt_Items c_Items) {
        C_Items = c_Items;
        Distances = new Double[C_Items.n_Items()][C_Items.n_Items()];
        initzialitzar_matriu();
        omplir_matriu();
    }

    public ArrayList<Double> Distancies_i(int pos) {

        ArrayList<Double>  Distancias = new ArrayList<Double>();

        for (int i = 0; i < Distances[pos].length; ++i)
            Distancias.add(Distances[pos][i]);
        return Distancias;
    }

    public void omplir_matriu() {

        for (int i = 0; i < Distances.length; ++i)
            for(int j = 0; j < Distances.length; ++j){

                if (i == j)
                Distances[i][j] =  0.0;
                else if((Distances[i][j] == -1.) && (Distances[j][i] == -1.)) {

                    Double aux = C_Items.getItems().get(i).Distance(C_Items.getItems().get(j));
                    Distances[i][j] =  aux;
                    Distances[j][i] = aux;
                }
            }
    }

    public void initzialitzar_matriu() {

        for (int i = 0; i < Distances.length; ++i)
            for(int j = 0; j < Distances.length; ++j)
                Distances[i][j] = -1.0;
    }


    public Double Distance (Item a , Item b) {

        return  a.Distance(b);
    }
}
