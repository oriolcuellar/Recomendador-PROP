package FONTS.src.domini.model;

import FONTS.src.domini.model.*;
import FONTS.src.domini.controladores.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;


public class Main {
    public static void main(String[] args) {

        ArrayList<Vector<String>> readed_ratings = new ArrayList<Vector<String>>();
        LectorCSV2 reader = new LectorCSV2();
        try {
            readed_ratings = reader.Lector_Ratings("Entradas_CSV/ratings.test.known.csv");
        }
        catch (Exception e){
            System.out.println("mal");
        }

/*
        //kmeans

        Kmeans kmeans = new Kmeans(usersList);
        kmeans.run(k);
        //kmeans.printAllClusters();

        //slope one

        SlopeOne slopeOne = new SlopeOne(itemValoratedBy, usersList, maxValue);
        //cambiar por actual user
        slopeOne.getPredictions(actualUser);
        slopeOne.printResults();*/

    }
}