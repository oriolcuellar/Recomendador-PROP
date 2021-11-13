package src.domini.model;

import src.domini.model.*;
import src.domini.controladores.*;
import java.util.ArrayList;


public class Main {
    public static void main(String[] args) {
       //DriverKmeans dr = new DriverKmeans();
       //dr.readRatingsExecuteKmeans(7);

        CtrlDomini c= CtrlDomini.getInstance();
       c.loadRates();
       c.showRecommendedItems(1);
    }
}