package src.domini.model;

import src.domini.model.*;
import src.domini.controladores.*;
import java.util.ArrayList;


public class Main {
    public static void main(String[] args) {
       //DriverKmeans dr = new DriverKmeans();
       //dr.readRatingsExecuteKmeans(7);
//el administrador es el num -1, hay que logearse con el antes de hacer nada. id= -1 passw = -1
        CtrlDomini c= CtrlDomini.getInstance();
        c.login(-1, "-1");
       c.loadRates();
        //c.loadItems();
        //c.showAllItems();
        // c.showRecommendedItems(5);
       //c.loadItems();
        //c.ShowRatedItems();
    }
}