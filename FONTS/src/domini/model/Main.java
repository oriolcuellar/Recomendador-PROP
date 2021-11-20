package FONTS.src.domini.model;

import FONTS.src.domini.model.*;
import FONTS.src.domini.controladores.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;



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

        boolean escogido=false;
        while (!escogido) {
            mostrar_opciones();
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

}