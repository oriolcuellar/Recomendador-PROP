package FONTS.src.domini.drivers;
import FONTS.src.domini.model.*;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

/** \brief Driver de la clase DriverRateRecomendation.
 *  @author Oriol Cuellar
 */

public class DriverRateRecomendation {
    /** Mapa que contiene como llave el ID de un @si Item y com valor los usuarios que lo han valorado.
     *  Se utiliza como parametro para el algoritmo Slope-One
     *  @see Item
     *  @see User
     *  @see SlopeOne
     */
    static Map<Integer, ArrayList<User>> item_valorated_by= new HashMap<Integer, ArrayList<User>>();

    /** Mapa que contiene como llave el ID de un User y com valor el objeto User.
     *  Se utiliza como parametro para el algoritmo K-Means y Slope-One
     *  @see User
     *  @see Kmeans
     *  @see SlopeOne
     */
    static Map<Integer, User> usersList= new HashMap<Integer, User>();

    /** Main del Driver.
     *  Lee recomendaciones de un fichero CSV. Crea en los maps correspondientes los usuarios i los items que ha leido.
     */
    public static void main(String[] args) {

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
        Kmeans kmeans = new Kmeans(usersList);
        kmeans.run(6);
        ArrayList <Cluster> ac=kmeans.getClusters();
        //slope one
        SlopeOne So = new SlopeOne(item_valorated_by, usersList,10);
        ArrayList<myPair> predictions= So.getPredictions(usersList.get(1575));
        //Rate Recomendation algorithm
        RateRecomendation recomendation = new RateRecomendation(predictions);
        recomendation.execute();
        System.out.println(recomendation.getResult());

    }

}
