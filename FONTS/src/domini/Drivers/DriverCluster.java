package FONTS.src.domini.drivers;

import FONTS.src.domini.model.Cluster;
import FONTS.src.domini.model.LectorCSV2;
import FONTS.src.domini.model.User;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/** \brief Driver de la clase Cluster.
 *  @author Roberto Amat
 */
public class DriverCluster {


    static Cluster c;
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

    static void testFunction(int f) {
        switch(f)
        {
            case 1 :
                {
                    System.out.println("=========================================");
                    System.out.println("Devuelve el centroide actual del cluster.");
                    System.out.println(c.getcentroid().getUserID());
                    System.out.println("=========================================");
                    break;
                }

            case 2 :
                {
                    System.out.println("=======================================================================");
                    System.out.println("Imprime el ID de todos los usuarios que forman parte del cluster: ");
                    c.printCluster();
                    System.out.println("=======================================================================");
                    break;
                }

            case 3 :

                {
                    Scanner s = new Scanner(System.in);
                    System.out.println("==================================================================================");
                    System.out.print("Cambia el centroide del cluster por el objecto on el ID introducido: ");
                    User u = new User(s.nextInt());
                    c.setCentroid(u);
                    System.out.println("==================================================================================");
                    break;
                }

            case 4 :

                {
                    System.out.println("=======================================================");
                    Scanner s = new Scanner(System.in);
                    System.out.print("Inserta en cluster el usuario con el ID introducido: ");
                    User u = new User(s.nextInt());
                    c.addUser(u);
                    System.out.println("=======================================================");
                    break;
                }

            case 5:
                {
                    System.out.println("=============================================================================");
                    c = new Cluster();
                    Scanner s = new Scanner(System.in);
                    System.out.print("Introduce el numero de usuarios que formaran parte del nuevo cluster: ");
                    int n = s.nextInt();
                    int ID;
                    System.out.println("Introduce el ID de cada usuario (el primero introducido sera el centroide: ");
                    for (int i = 0; i < n; ++i) {
                        User u = new User(s.nextInt());
                        c.addUser(u);
                    }
                    System.out.println("=============================================================================");
                    break;
                }

            default :
        }
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

    /** Main del driver.
     *  Prueba 5 casos
     *      - Borrar un usuario que no existe en el cluster
     *      - Añadir usuarios leidos al cluster
     *      - Añadir usuario que ya existe
     *      - Establecer como centroide un usuario que no forma parte del cluster
     *      - Imprimir correctamente el cluster
     */
    public static void main(String[] args) {
        printInfo();
        Scanner s = new Scanner(System.in);
        testFunction(5);
        int f;
        do{
            System.out.println("\nSelecciona funcion a probar: ");
            f = s.nextInt();
            testFunction(f);

        }while(f != 6);
    }

}
