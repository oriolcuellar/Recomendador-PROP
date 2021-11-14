package src.domini.model;

import src.domini.model.*;
import java.util.ArrayList;
import java.util.Vector;

//@Author Jordi Olmo
public class K_Neareast_Neightbour {

    //Atributes
    private Conjunt_Items C_Items;
    private Double [][] Distances;

    //Constructora
    public K_Neareast_Neightbour(Conjunt_Items c_Items) {
        C_Items = c_Items;
        Distances = new Double[C_Items.n_Items()][C_Items.n_Items()];
        initzialitzar_matriu();
        omplir_matriu();
    }

    //Getters
    public ArrayList<Double> Distancies_i(int pos) {

        ArrayList<Double>  Distancias = new ArrayList<Double>();

        for (int i = 0; i < Distances.length; ++i)
            Distancias.add(Distances[pos][i]);
        return Distancias;
    }

    public Double Distance (Item a , Item b) {return  a.Distance(b);}

    //Operacions Auxiliars
    private void omplir_matriu() {

        for (int i = 0; i < Distances.length; ++i)
            for(int j = 0; j < Distances.length; ++j){

                if (i == j)
                Distances[i][j] =  0.0;
                else if(Distances[i][j] != -1.) {

                    Double aux = C_Items.getItems().get(i).Distance(C_Items.getItems().get(j));
                    Distances[i][j] =  aux;
                    Distances[j][i] = aux;
                }
            }
    }

    private void initzialitzar_matriu() {

        for (int i = 0; i < Distances.length; ++i)
            for(int j = 0; j < Distances.length; ++j)
                Distances[i][j] = -1.0;
    }

}
