package FONTS.src.domini.model;

import FONTS.src.domini.model.*;
import FONTS.src.domini.controladores.*;
import java.util.ArrayList;
import java.util.Arrays;


public class Main {
    public static void main(String[] args) {
//el administrador es el num -1, hay que logearse con el antes de hacer nada. id= -1 passw = -1
        CtrlDomini c= CtrlDomini.getInstance();
        //c.login("-1", "-1");
        c.register(null,null);
        //c.showAllUsers();
        c.loadRates("Entradas_CSV/ratings.db.csv");
        //c.loadItems();
        //c.showAllItems();
         c.showRecommendedItemsSlope(300,100);

    }
}