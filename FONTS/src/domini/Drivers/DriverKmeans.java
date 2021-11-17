package FONTS.src.domini.drivers;

import FONTS.src.domini.model.*;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class DriverKmeans {
    static Map<Integer, User> usersList = new HashMap<>();
    static Map<Integer, User> usersList2 = new HashMap<>();
    static ArrayList<Vector<String>> ratings;

    public static ArrayList<Vector<String>> Lector_Ratings(String csvFile) {
        ratings= new ArrayList<>();
        BufferedReader br = null;
        String line = "";
        //Se define separador ","
        String cvsSplitBy = ",";
        try {
            br = new BufferedReader(new FileReader(csvFile));
            boolean first = true;
            while ((line = br.readLine()) != null) {
                String[] datos = line.split(cvsSplitBy);
                if(!first) {
                    Vector<String> dentro = new Vector<>();
                    dentro.add(datos[0]);
                    dentro.add(datos[1]);
                    dentro.add(datos[2]);
                    ratings.add(dentro);
                }
                first = false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return ratings;

    }

    public static void readData() {

        LectorCSV2 l = new LectorCSV2();
        ArrayList<Vector<String>> readed_ratings = Lector_Ratings("Entradas_CSV/ratings.db.csv");

        for (Vector<String> vs : readed_ratings) {
            if (usersList.containsKey(Integer.valueOf(vs.get(0)))) {//existeix
                User usuari = usersList.get(Integer.valueOf(vs.get(0)));
                if (usuari.searchUsedItem(Integer.parseInt(vs.get(1))) == null) {//no existe el item en sus valoraciones
                    usuari.addItemUsat(Integer.parseInt(vs.get(1)), Float.parseFloat(vs.get(2)));

                }
            } else {//no existeix, es crea, afegim valoracio a la seva llista, afegim valoracio allista itemUsatList
                User usuari = new User(Integer.parseInt(vs.get(0)));
                usuari.addItemUsat(Integer.parseInt(vs.get(1)), Float.parseFloat(vs.get(2)));
                usersList.put(Integer.valueOf(vs.get(0)), usuari);
            }
        }
    }


    public static void main(String[] args) {
        readData();
        User u = new User(23213);
        usersList2.put(23213,u);
        Kmeans kmeans = new Kmeans(6, usersList2);
        kmeans.printAllClusters();
    }
}