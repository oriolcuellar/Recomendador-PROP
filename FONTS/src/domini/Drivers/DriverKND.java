package FONTS.src.domini.drivers;

import FONTS.src.domini.model.*;

import java.util.ArrayList;
import java.util.Arrays;

//@Author Jordi Olmo
public class DriverKND {
    public static void main(String[] args) {
        //DriverKmeans dr = new DriverKmeans();
        //dr.readRatingsExecuteKmeans(7);
        Ranged_Atribute AT = new Ranged_Atribute("Peso", "Rang", 32.4, 75.8);
        Atribute AT2 = new Atribute("Color", "String");
        Atribute AT3 = new Atribute("Fecha", "Data");
        Atribute AT4 = new Atribute("Disponible", "Boolean");
        Atribute AT5 = new Atribute("KeyWords", "Vector de String");

        Ranged_Atribute AU = new Ranged_Atribute("Duracion", "Rang", 60.0, 240.0);
        Atribute AU2 = new Atribute("Idioma", "String");
        Atribute AU4 = new Atribute("Disponible", "Boolean");
        Atribute AU3 = new Atribute("Estreno", "Data");

        ArrayList<Atribute> Tipus1 = new ArrayList<Atribute>(Arrays.asList(AT, AT2, AT3, AT4, AT5));
        ArrayList<Atribute> Tipus2 = new ArrayList<Atribute>(Arrays.asList(AU, AU2, AU4, AU3));

        TipusItem T1 = new TipusItem(Tipus1);
        TipusItem T2 = new TipusItem(Tipus2);

        ArrayList<String> Valors2 = new ArrayList<String>(Arrays.asList("Verde", "true", "1992-04-12", "hola;que;tal", "32.4"));
        ArrayList<String> Valors3 = new ArrayList<String>(Arrays.asList("Verde", "false", "1960-05-03", "Burnos;dias;siñorina", "75.8"));
        ArrayList<String> Valors4 = new ArrayList<String>(Arrays.asList("Rojo", "false", "2000-12-04", "Habia;una;vez;un;varquito;chuiquito;dias", "32.4"));
        ArrayList<String> Valors5 = new ArrayList<String>(Arrays.asList("Azul", "", "1999-00-00", "hola;que;tal", "46.8"));

        ArrayList<String> Valors6 = new ArrayList<String>(Arrays.asList("true", "120", "1992-04-12", "Ingles"));
        ArrayList<String> Valors7 = new ArrayList<String>(Arrays.asList("false", "712", "1960-05-03", "Españolo"));
        ArrayList<String> Valors8 = new ArrayList<String>(Arrays.asList("", "240", "2000-12-04", "Ingles"));
        ArrayList<String> Valors9 = new ArrayList<String>(Arrays.asList("true", "60", "1999-00-00", "Espa"));

        Item a2 = new Item(2, T1, Valors2);
        Item a3 = new Item(3, T1, Valors3);
        Item a4 = new Item(4, T1, Valors4);
        Item a5 = new Item(5, T1, Valors5);

        Item a6 = new Item(6, T2, Valors6);
        Item a7 = new Item(7, T2, Valors7);
        Item a8 = new Item(8, T2, Valors8);
        Item a9 = new Item(9, T2, Valors9);

        ArrayList<Item> Items = new ArrayList<Item>(Arrays.asList(a2, a3, a4, a5));

        Conjunt_Items Ct = new Conjunt_Items(Items);
        ArrayList<Item> ItemsUsats = new ArrayList<Item>(Arrays.asList(a6, a7, a8, a9));
        ArrayList<Double> Valoracions = new ArrayList<Double>(Arrays.asList(3.2, 4.1, 1.0, 2.1));

        K_Neareast_Neightbour prueba = new K_Neareast_Neightbour( Ct);
        ArrayList<Item> Resultado = prueba.Algorithm(3, ItemsUsats, Valoracions);

        for (int i = 0; i < Resultado.size(); ++i)
            System.out.println(Resultado.get(i).getID());

    }
}


