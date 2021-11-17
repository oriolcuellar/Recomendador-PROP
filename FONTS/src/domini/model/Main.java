package FONTS.src.domini.model;

import FONTS.src.domini.model.*;
import FONTS.src.domini.controladores.*;
import java.util.ArrayList;
import java.util.Arrays;


public class Main {
    public static void main(String[] args) {
       //DriverKmeans dr = new DriverKmeans();
       //dr.readRatingsExecuteKmeans(7);
        /*Atribute AT = new Atribute("Peso", "Rang");
        Atribute AT2 = new Atribute("Color", "String");
        Atribute AT3 = new Atribute("Fecha", "Data");
        Atribute AT4 = new Atribute("Disponible", "Boolean");
        Atribute AT5 = new Atribute("KeyWords", "Vector de String");

        Atribute AU = new Atribute("Duracion", "Rang");
        Atribute AU2 = new Atribute("Idioma", "String");
        Atribute AU4 = new Atribute("Disponible", "Boolean");
        Atribute AU3 = new Atribute("Estreno", "Data");

        ArrayList<Atribute> Tipus1 = new ArrayList<Atribute>(Arrays.asList(AT, AT2, AT3, AT4, AT5));
        ArrayList<Atribute> Tipus2 = new ArrayList<Atribute>(Arrays.asList(AU, AU2, AU4, AU3));

        TipusItem T1 = new TipusItem (Tipus1);
        TipusItem T2 = new TipusItem (Tipus2);

        ArrayList<String> Valors2 =  new ArrayList<String>(Arrays.asList("32.4", "Verde", "1992-04-12","true", "hola;que;tal"));
        ArrayList<String> Valors3 =  new ArrayList<String>(Arrays.asList("75.8", "Verde", "1960-05-03","false", "Burnos;dias;siñorina"));
        ArrayList<String> Valors4 =  new ArrayList<String>(Arrays.asList("32.4", "Rojo", "2000-12-04","false", "Habia;una;vex;una;varquito;chuiquito;dias"));
        ArrayList<String> Valors5 =  new ArrayList<String>(Arrays.asList("32.4", "Azul", "1999-00-00","", "hola;que;tal"));

        ArrayList<String> Valors6 =  new ArrayList<String>(Arrays.asList("120", "Ingles", "1992-04-12","true"));
        ArrayList<String> Valors7 =  new ArrayList<String>(Arrays.asList("712", "Españolo", "1960-05-03","false"));
        ArrayList<String> Valors8 =  new ArrayList<String>(Arrays.asList("240", "Ingles", "2000-12-04",""));
        ArrayList<String> Valors9 =  new ArrayList<String>(Arrays.asList("60", "Espa", "1999-00-00","true"));

        Item a2 = new Item(2, T1, Valors2);
        Item a3 = new Item(3, T1, Valors3);
        Item a4 = new Item(4, T1, Valors4);
        Item a5 = new Item(5, T1, Valors5);

        Item a6 = new Item(6, T2, Valors6);
        Item a7 = new Item(7, T2, Valors7);
        Item a8 = new Item(8, T2, Valors8);
        Item a9 = new Item(9, T2, Valors9);

        ArrayList<Item> Items = new ArrayList<Item>(Arrays.asList(a2, a3,a4, a5));

        Conjunt_Items Ct = new Conjunt_Items(Items);
        ArrayList<Item> ItemsUsats = new ArrayList<Item>(Arrays.asList(a6, a7,a8, a9));
        ArrayList<Double> Valoracions = new ArrayList<Double>(Arrays.asList(3.2, 4.1,1.0, 2.1));

        KND prueba =new KND(3 ,Ct, ItemsUsats, Valoracions);
        ArrayList<Item> Resultado= prueba.Algorithm();

        for (int i = 0; i < Resultado.size(); ++i){

            System.out.println(Resultado.get(i).getID());
        }
*/
//el administrador es el num -1, hay que logearse con el antes de hacer nada. id= -1 passw = -1
        CtrlDomini c= CtrlDomini.getInstance();
        c.login("-1", "-1");
        //c.showAllUsers();
        c.loadRates();
        //c.loadItems();
        //c.showAllItems();
         c.showRecommendedItems(300,100);

    }
}