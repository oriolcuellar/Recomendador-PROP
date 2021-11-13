package src.domini.model;

import src.domini.model.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Map;

// @Author Marc Camarillas, Roberto Amat
public class LectorCSV2 {

    //private Usuarios u;
    private static Map<Integer, User> usersList;
    private static ArrayList<Item> itemList;
    private static ArrayList<ItemUsat> itemUsatList;

    private void readRatings(int userID, int itemID, float valoration) {
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
        }

    }

    public LectorCSV2(Map <Integer, User> u, ArrayList<Item> i, ArrayList<ItemUsat> iu){
        this.usersList = u;
        this.itemList = i;
        this.itemUsatList = iu;
    }

    public void Lector(String csvFile, String dataType) {
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
                if(dataType.equals("Ratings") && !first) {
                    readRatings(Integer.parseInt(datos[0]), Integer.parseInt(datos[1]), Float.parseFloat(datos[2]));
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
    }
}