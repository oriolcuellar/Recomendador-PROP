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
            printInfo();
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

    static void printInfo() {
        System.out.println("\nDRIVER DE LA CLASE CLUSTER\n");
        System.out.println("La clase cluster representa un conjunto de usuarios con gustos similares.");
        System.out.println("Contiene el conjunto dichos usuarios y su centroide, siendo este el usuario\n" +
                "mas cercano en cuanto a gustos con todos los demas.");
        System.out.println("\nFunciones de la clase disponibles para probar:\n");
        System.out.println("    1. getCentroid()\n    2. getCluster()\n    3. setCentroid(usuario)\n" +
                "    4. addUser(usuario)\n    5. CAMBIAR CLUSTER ACTUAL.\n    6. FINALIZAR PRUEBA.\n");
    }
}