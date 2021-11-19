package FONTS.src.domini.drivers;

import FONTS.src.domini.model.Cluster;
import FONTS.src.domini.model.User;
import java.util.*;

/** \brief Driver de la clase Cluster.
 *  @author Roberto Amat
 */
public class DriverCluster {


    /** Añade todos los usuarios leidos por fichero al cluster
     * @param c Cluster donde se insertan los usuarios
     */
    static Cluster c;


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

            default : System.out.print("Porfavor introduce un valor correcto\n ");
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
