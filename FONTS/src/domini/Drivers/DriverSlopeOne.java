package FONTS.src.domini.drivers;

import FONTS.src.domini.model.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/** \brief Driver de la clase SlopeOne.
 *  @author Marc Camarillas
 */
public class DriverSlopeOne{

    /** Mapa que contiene como llave el ID de un usuario y com valor dicho usuario.
     *  Se utiliza para relizar las pruebas con los datos recibidos a través del fichero.
     */
    static Map<Integer,User> usersList = new HashMap<Integer,User> ();
    /** Mapa que contiene como llave el ID de un item y com valor la lista de usuarios que lo han valorado.
     *  Se utiliza para relizar las pruebas con los datos recibidos a través del fichero.
     */
    static Map<Integer,ArrayList<User>> itemValoratedBy = new HashMap<Integer,ArrayList<User>>();

    /** Función que lee el archivo que contiene los datos a probar.
     * @param csvFile Dirección del archivo que contiene los datos-
     * @return Vector que almacena los datos leídos.
     */
    public static ArrayList<Vector<String>> Lector_SlopeOne_Test(String csvFile) {
        ArrayList<Vector<String>> ratings = new ArrayList<Vector<String>>();
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
    /** Función que se encarga de leer los datos y almacenarlos en su lugar correspondiente.
     */
    public static void readData() {
        ArrayList<Vector<String>> readed_ratings = Lector_SlopeOne_Test("Entradas_CSV/SlopeOneTest.csv");

        TipusRol t = TipusRol.Usuari;
        for (Vector<String> vs : readed_ratings) {
            if (usersList.containsKey(Integer.valueOf(vs.get(0)))) {//existeix
                User usuari = usersList.get(Integer.valueOf(vs.get(0)));
                if (usuari.searchUsedItem(Integer.parseInt(vs.get(1))) == null) {//no existe el item en sus valoraciones
                    usuari.addvaloratedItem(Integer.parseInt(vs.get(1)), Float.parseFloat(vs.get(2)));

                }
            } else {//no existeix, es crea, afegim valoracio a la seva llista, afegim valoracio allista itemUsatList
                User usuari = new User(Integer.parseInt(vs.get(0)));
                usuari.setNumCluster(Integer.parseInt(vs.get(3)));
                usuari.addvaloratedItem(Integer.parseInt(vs.get(1)), Float.parseFloat(vs.get(2)));
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


    /** Main del driver.
     *  Prueba 5 casos:
     *      - Llamar al algoritmo con itemBaloratedBy vacio.
     *      - Llamar al algoritmo con un user que no existe.
     *      - Llamar al algoritmo con usersList vacia.
     *      - Llamar al algoritmo con maxValue mayor que valor máximo.
     *      - Llamar al algoritmo con un fichero de datos real.
     */
    public static void main(String[] args) {
        readData();

        System.out.println("CASO DONDE EL MAP ITEMVALORATEDBY ESTÁ VACIO");
        Map<Integer,ArrayList<User>> itemValoratedBy1 = new HashMap<Integer,ArrayList<User>>();
        SlopeOne So = new SlopeOne(itemValoratedBy1, usersList,5);
        So.getPredictions(usersList.get(3));
        So.printResults();
        System.out.println();

        System.out.println("CASO DONDE EL USUARIO AL QUE QUEREMOS PREDECIR NO EXISTE");
        SlopeOne So1 = new SlopeOne(itemValoratedBy, usersList,5);
        So1.getPredictions(usersList.get(1000));
        So1.printResults();
        System.out.println();

        System.out.println("CASO DONDE EL MAP USERSLIST ESTÁ VACIO");
        Map<Integer,User> usersList1 = new HashMap<Integer,User> ();
        SlopeOne So2 = new SlopeOne(itemValoratedBy, usersList1,5);
        So2.getPredictions(usersList1.get(3));
        So2.printResults();
        System.out.println();

        System.out.println("CASO DONDE EL MAXVALUE ES MAYOR AL VALOR MÁXIMO");
        SlopeOne So3 = new SlopeOne(itemValoratedBy, usersList,10);
        So3.getPredictions(usersList.get(3));
        So3.printResults();
        System.out.println();

        System.out.println("CASO DONDE USAMOS TODO DEL FICHERO DE DATOS QUE LEEMOS");
        SlopeOne So4 = new SlopeOne(itemValoratedBy, usersList,5);
        So4.getPredictions(usersList.get(3));
        So4.printResults();
        System.out.println();
    }
}
