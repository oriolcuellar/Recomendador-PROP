package FONTS.src.domini.drivers;
import FONTS.src.domini.model.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

/** \brief Driver de la clase Kmeans.
 *  @author Roberto Amat
 */
public class DriverKmeans {

    /** Mapa que contiene como llave el ID de un usuario y com valor dicho usuario.
     *  Se utiliza para relizar las pruebas con los datos recibidos a través del fichero.
     */
    static Map<Integer, User> usersList = new HashMap<>();
    /** Mapa que permanecera vacio y se utiliza para tratar una excepción.
     */
    static Map<Integer, User> usersList2 = new HashMap<>();


    /** Función que lee el archivo que contiene los datos a probar.
     * @param csvFile Dirección del archivo que contiene los datos-
     * @return Vector que almacena los datos leídos.
     */
    public static ArrayList<Vector<String>> Lector_Ratings(String csvFile) {
        ArrayList<Vector<String>> ratings = new ArrayList<>();
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

    /** Función que se encarga de leer los datos y almacenarlos en su lugar correspondiente.
     */
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

    /** Main del driver.
     *  Prueba 4 casos:
     *      - Llamar al algoritmo con más clusters que usuarios.
     *      - Llamar al algoritmo pasandole 0 usuarios.
     *      - Llamar al algoritmo con k = 0 (0 clusters).
     *      - Llamar al algoritmo con un fichero de datos real.
     */
    public static void main(String[] args) {
        readData();
        Kmeans kmeans = new Kmeans(63423432, usersList);
        kmeans.printAllClusters();
        Kmeans kmeans2 = new Kmeans(6, usersList2);
        kmeans2.printAllClusters();
        Kmeans kmeans3 = new Kmeans(0, usersList);
        kmeans3.printAllClusters();
        Kmeans kmeans4 = new Kmeans(6, usersList);
        kmeans4.printAllClusters();
    }
}
