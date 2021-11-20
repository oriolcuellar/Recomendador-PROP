package FONTS.src.domini.drivers;
import FONTS.src.domini.model.*;


import java.util.*;

/** \brief Driver de la clase RateRecomendation.
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
     *      - Lee valoraciones de un fichero CSV dado el path.
     *      - Crea en los maps correspondientes, los usuarios i los items que ha leido.
     *      - Llama al algoritmo K-means y Slope-One
     *      - Evalua el resultado con el algortimo
     * @see Kmeans
     * @see SlopeOne
     * @see  RateRecomendation
     */
    public static void main(String[] args) {
        printInfo();
        //se leen valoraciones
        ArrayList<Vector<String>> readed_ratings = new ArrayList<Vector<String>>();
        boolean leido= false;
        while (!leido) {
            try {

                System.out.println("\n- INTRODUCE el path al CSV de valoraciones que desea leer: ");
                Scanner s = new Scanner(System.in);
                String path;
                path = s.next();
                LectorCSV2 reader = new LectorCSV2();
                readed_ratings = reader.Lector_Ratings(path);
                leido=true;
            } catch (Exception e) {
                System.out.println("\n\n- ERROR");
                System.out.println(e);
                System.out.println("\n- Prueba con una entrada como \n   ratings.test.known.csv \n   o \n   nombre_carpeta/ratings.test.known.csv (si estubiera en carpeta)");
            }
        }

        //se rellenan userslist y item_valorated_by
        TipusRol t = TipusRol.Usuari;
        for (Vector<String> vs : readed_ratings) {
            //parte del Usuario
            if (usersList.containsKey(Integer.valueOf(vs.get(0)))) {//Usuario ya existe
                User usuari = usersList.get(Integer.valueOf(vs.get(0)));
                if (usuari.searchUsedItem(Integer.parseInt(vs.get(1))) == null) {//No existe el item en sus valoraciones
                    usuari.addvaloratedItem(Integer.parseInt(vs.get(1)), Float.parseFloat(vs.get(2)));

                }
            } else {//Usuari no existe, se crea, añadimos valoracion a su lista, añadimos valoracion itemUsatList
                User usuari = new User(Integer.parseInt(vs.get(0)));
                usuari.addvaloratedItem(Integer.parseInt(vs.get(1)), Float.parseFloat(vs.get(2)));
                usersList.put(Integer.valueOf(vs.get(0)), usuari);
            }
            //parte del item
            if (item_valorated_by.containsKey(Integer.valueOf(vs.get(1)))){//Existe item al map
                User usuari = usersList.get(Integer.valueOf(vs.get(0)));
                item_valorated_by.get(Integer.valueOf(vs.get(1))).add(usuari);
            }
            else{//NO existe item al map
                User usuari = usersList.get(Integer.valueOf(vs.get(0)));
                ArrayList <User> au = new ArrayList<User>();
                au.add(usuari);
                item_valorated_by.put( Integer.valueOf(vs.get(1)), au);
            }
        }

        //kmeans
        Kmeans kmeans = new Kmeans();
        kmeans.run(usersList,30);
        ArrayList <Cluster> ac=kmeans.getClusters();

        //slope one
        SlopeOne So = new SlopeOne();
        ArrayList<myPair> predictions = So.getPredictions(usersList.get(1663),item_valorated_by,10);
        //So.printResults();

        //Algortimo de valorar recomendaciones
        RateRecomendation recomendation = new RateRecomendation();
        float result=recomendation.execute(predictions);
        System.out.println("\n\n El resultado es: " + result);

    }

    static void printInfo() {
        System.out.println("\nDRIVER DE LA CLASE RATE RECOMENDATION\n");
        System.out.println("    La clase Rate Recomendation evalua la salida de los algoritmos K-Means y Slope-One.");
        System.out.println("    Esta salida la redirige a la funcion execute de la clase Rate Recomendation");

    }

}

