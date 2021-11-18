package FONTS.src.domini.drivers;

import FONTS.src.domini.model.Cluster;
import FONTS.src.domini.model.LectorCSV2;
import FONTS.src.domini.model.User;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

/** \brief Driver de la clase Cluster.
 *  @author Roberto Amat
 */
public class DriverCluster {

    /** Mapa que contiene como llave el ID de un usuario y com valor dicho usuario.
     *  Se utiliza para relizar las pruebas con los datos recibidos a través del fichero.
     */
    static private Map <Integer, User> usersList = new HashMap<>();
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

        ArrayList<Vector<String>> readed_ratings = Lector_Ratings("Entradas_CSV/ratings.db.csv");

        for (Vector<String> vs : readed_ratings) {
            if (usersList.containsKey(Integer.valueOf(vs.get(0)))) {//existeix
                User usuari = usersList.get(Integer.valueOf(vs.get(0)));
                if (usuari.searchUsedItem(Integer.parseInt(vs.get(1))) == null) {//no existe el item en sus valoraciones
                    usuari.addvaloratedItem(Integer.parseInt(vs.get(1)), Float.parseFloat(vs.get(2)));

                }

            } else {
                //no existeix, es crea, afegim valoracio a la seva llista, afegim valoracio allista itemUsatList
                User usuari = new User(Integer.parseInt(vs.get(0)));
                usuari.addvaloratedItem(Integer.parseInt(vs.get(1)), Float.parseFloat(vs.get(2)));
                usersList.put(Integer.valueOf(vs.get(0)), usuari);
            }
        }
    }

    /** Añade todos los usuarios leidos por fichero al cluster
     * @param c Cluster donde se insertan los usuarios
     */
    private static void addUsers(Cluster c) {
        boolean first = true;
        for(Map.Entry<Integer, User> entry : usersList.entrySet()) {
            c.addUser(entry.getValue());
            if(first) {
                first = false;
                c.setCentroid(entry.getValue());
            }
        }
    }

    /** Main del driver.
     *  Prueba 5 casos
     *      - Borrar un usuario que no existe en el cluster
     *      - Añadir usuarios leidos al cluster
     *      - Añadir usuario que ya existe
     *      - Establecer como centroide un usuario que no forma parte del cluster
     *      - Imprimir correctamente el cluster
     */
    public static void main(String[] args) {
        Cluster c = new Cluster();
        User u1 = new User(0);
        c.deleteUser(u1);
        c.setCentroid(u1);
        readData();
        addUsers(c);
        c.addUser(c.getcentroid());
        System.out.println();
        c.printCluster();

    }

}
