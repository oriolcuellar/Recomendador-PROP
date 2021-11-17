package FONTS.src.domini.Drivers;
import FONTS.src.domini.model.*;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class DriverRateRecomendation {


    public static void main(String[] args) {
        Map<Integer, ArrayList<User>> item_valorated_by= new HashMap<Integer, ArrayList<User>>();
//crear
        Map<Integer, User> usersList= new HashMap<Integer, User>();

        ArrayList<Vector<String>> readed_ratings = new ArrayList<Vector<String>>();
        LectorCSV2 reader = new LectorCSV2();
        readed_ratings = reader.Lector_Ratings("Entradas_CSV/ratings.test.known.csv");

        TipusRol t = TipusRol.Usuari;
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
            //parte del item
            if (item_valorated_by.containsKey(Integer.valueOf(vs.get(1)))){//existeix item al map
                User usuari = usersList.get(Integer.valueOf(vs.get(0)));
                item_valorated_by.get(Integer.valueOf(vs.get(1))).add(usuari);
            }
            else{//NO existeix item al map
                User usuari = usersList.get(Integer.valueOf(vs.get(0)));
                ArrayList <User> au = new ArrayList<User>();
                au.add(usuari);
                item_valorated_by.put( Integer.valueOf(vs.get(1)), au);
            }
        }

        //kmeans
        Kmeans kmeans = new Kmeans(6, usersList);
        ArrayList <Cluster> ac=kmeans.getClusters();
        //slope one
        SlopeOne So = new SlopeOne(item_valorated_by,5);
        ArrayList<myPair> predictions= So.getPredictions(usersList.get(35368));

        So.printResults();

        RateRecomendation recomendation = new RateRecomendation(predictions);
        recomendation.execute();

    }

}
