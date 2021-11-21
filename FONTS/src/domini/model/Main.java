package FONTS.src.domini.model;

import FONTS.src.domini.model.*;
import FONTS.src.domini.controladores.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;


public class Main {

    /** Mapa que contiene como llave el ID de un Item y com valor los usuarios que lo han valorado.
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

    public static void main(String[] args) {
        printInfo();
        Scanner s = new Scanner(System.in);
        boolean escogido=false;
        String op="";
        //escoger algoritmo
        while (!escogido) {
            mostrar_opciones();
            op=s.next();
            if (op.equals("1") || op.equals("2") || op.equals("3")){
                escogido=true;
            }
            else{
                System.out.print("Porfavor introduce un valor correcto\n ");
            }
        }

        //ejecutar algoritmo
        if (op.equals("1")){
            execute_kmeans();
        }
        if (op.equals("2")){
            execute_Slope_One();
        }
        if (op.equals("3")){
            execute_RateRecomendation();
        }
    }

    static void printInfo() {
        System.out.println("\nClase MAIN\n");
        System.out.println("    Permite probar los algoritmos KMeans, Slope-One y \n    dar una valoración a la recomendación de items dada por los algoritmos");
    }
    static void mostrar_opciones() {
        System.out.println("=====================================================================================");
        System.out.println("\nQUE DESEAS EJECUTAR\n");
        System.out.println("    - 1 ");
        System.out.println("         KMeans");
        System.out.println("    - 2 ");
        System.out.println("         Slope-One");
        System.out.println("    - 3 ");
        System.out.println("         Valorar una recomendacion\n\n");

    }
    static void execute_kmeans(){
        System.out.println("=====================================================================================");
        System.out.println("EJECUTANDO K MEANS");

        //leer datos
        ArrayList<Vector<String>> readed_ratings = new ArrayList<Vector<String>>();
        readed_ratings=leer_datos(readed_ratings);
        rellenar_listas(readed_ratings);

        Scanner s = new Scanner(System.in);
        boolean escogido=false;
        int k;
        //escoger valor K
        while (!escogido) {
            System.out.print("Introduce el valor de la K (numero de clusters del algoritmo)");
            k=s.nextInt();
            if (k>0 && k<50){//cual es el maximo?---------------------------------------------------------------------------------------------------------------
                escogido=true;
            }
            else{
                System.out.print("Porfavor introduce un valor correcto\n ");
            }
        }
    }
    static void execute_Slope_One(){//para este necesito kmeans?
        System.out.println("=====================================================================================");
        System.out.println("EJECUTANDO SLOPE-ONE");
    }
    static void execute_RateRecomendation(){//para este necesito los dos?

    }

    /** Función que rellena una lista de usuarios y una lista de items con los usuarios que lo han valorado.
     *  @param readed_ratings .
     */
    static void rellenar_listas(ArrayList<Vector<String>> readed_ratings){
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
    }

    /** Función que lee valoraciones.
     *  @param readed_ratings .
     *  @return ArrayList de vectores de Strings.Cada vector contiene 2 posiciones, UserId, ItemId, rating
     */
    static ArrayList<Vector<String>> leer_datos(ArrayList<Vector<String>> readed_ratings){
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
        return readed_ratings;
    }

}