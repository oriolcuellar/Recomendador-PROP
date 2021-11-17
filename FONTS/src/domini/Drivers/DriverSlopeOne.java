package FONTS.src.domini.drivers;

import FONTS.src.domini.model.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class DriverSlopeOne{
    static Map<Integer,User> usersList = new HashMap<Integer,User> ();
    static Map<Integer,ArrayList<User>> itemValoratedBy = new HashMap<Integer,ArrayList<User>>();
    static ArrayList <Vector<String>> ratings;

    public static ArrayList<Vector<String>> Lector_SlopeOne_Test(String csvFile) {
        ratings = new ArrayList<Vector<String>>();
        BufferedReader br = null;
        String line = "";
        //Se define separador ","
        String cvsSplitBy = ",";
        try {
            br = new BufferedReader(new FileReader(csvFile));
            boolean first = true;
            while ((line = br.readLine()) != null) {
                String[] datos = line.split(cvsSplitBy);
                //Imprime datos.
                for(int i = 0; i < datos.length; ++i) {
                    //System.out.print(datos[i] + ", ");
                }
                System.out.println();
                if(!first) {
                    Vector<String> dentro = new Vector<String>();
                    dentro.add(datos[0]);
                    dentro.add(datos[1]);
                    dentro.add(datos[2]);
                    dentro.add(datos[3]);
                    ratings.add(dentro);
                }
                first = false;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
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
        ArrayList<Vector<String>> readed_ratings = Lector_SlopeOne_Test("Entradas_CSV/SlopeOneTest.csv");

        TipusRol t = TipusRol.Usuari;
        for (Vector<String> vs : readed_ratings) {
            if (usersList.containsKey(Integer.valueOf(vs.get(0)))) {//existeix
                User usuari = usersList.get(Integer.valueOf(vs.get(0)));
                if (usuari.searchUsedItem(Integer.parseInt(vs.get(1))) == null) {//no existe el item en sus valoraciones
                    usuari.addItemUsat(Integer.parseInt(vs.get(1)), Float.parseFloat(vs.get(2)));

                }
            } else {//no existeix, es crea, afegim valoracio a la seva llista, afegim valoracio allista itemUsatList
                User usuari = new User(Integer.parseInt(vs.get(0)));
                usuari.setNumCluster(Integer.parseInt(vs.get(3)));
                usuari.addItemUsat(Integer.parseInt(vs.get(1)), Float.parseFloat(vs.get(2)));
                usersList.put(Integer.valueOf(vs.get(0)), usuari);
            }
            //parte del item
            if (itemValoratedBy.containsKey(Integer.valueOf(vs.get(1)))){//existeix item al map
                User usuari = usersList.get(Integer.valueOf(vs.get(0)));
                itemValoratedBy.get(Integer.valueOf(vs.get(1))).add(usuari);
            }
            else{//NO existeix item al map
                User usuari = usersList.get(Integer.valueOf(vs.get(0)));
                ArrayList <User> au = new ArrayList<User>();
                au.add(usuari);
                itemValoratedBy.put( Integer.valueOf(vs.get(1)), au);
            }
        }
    }

   public static void main(String[] args) {
        readData();
       SlopeOne So = new SlopeOne(itemValoratedBy, usersList,5);
       System.out.println("USER: " + usersList.get(3).getUserID());
       So.getPredictions(usersList.get(3));
       System.out.println();
       usersList.get(3).printUsedItems();
       System.out.println();
       So.printResults();
       System.out.println();
    }
}
