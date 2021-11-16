package FONTS.src.domini.model;

import FONTS.src.domini.model.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Vector;

// @Author Marc Camarillas, Roberto Amat
public class LectorCSV2 {

    //private Usuarios u;

    private ArrayList <Vector<String>> ratings;
    private Vector <String> items;

        /*
        TipusRol t = TipusRol.Usuari;
        if (usersList.containsKey(userID)){//existeix
            User usuari = usersList.get(userID);
            if (usuari.searchUsedItem(itemID) == null){//no existe el item en sus valoraciones
                usuari.addItemUsat(itemID, valoration);
            }
        }
        else{//no existeix, es crea, afegim valoracio a la seva llista, afegim valoracio allista itemUsatList
            User usuari = new User(userID);
            usuari.addItemUsat(itemID,valoration );
            usersList.put(userID,usuari );
        }*/


    public LectorCSV2(){
    }
    public Vector <String> Lector_Items(String csvFile){
        //post: return un vector de les files del csv

        items=new Vector<String>();
        BufferedReader br = null;
        String line = "";
        try {
            br = new BufferedReader(new FileReader(csvFile));
            boolean first = true;
            int num_atributes=0;
            while ((line = br.readLine()) != null) {
                /*String[] datos = line.split(cvsSplitBy);
                //Imprime datos.
                Vector <String> v = new Vector<>();
                for (int i = 0; i <datos.length; ++i) {
                    //System.out.print(datos[i] + ", ");
                    v.add(datos[i]);
                }*/
                items.add(line);
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
        return items;
    }

    public ArrayList<Vector<String>> Lector_Ratings(String csvFile ) {
        ratings=new ArrayList<Vector<String>>();
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
}