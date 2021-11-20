package FONTS.src.domini.drivers;
import FONTS.src.domini.model.LectorCSV2;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Vector;

/** \brief Driver de la clase LectorCSV2.
 *  @author Oriol Cuellar
 */
public class DriverLectorCSV2 {

    /** Main del driver.
     *  Prueba 2 casos:
     *      - Lee valoraciones de un path dado y los imprime por pantalla.
     *      - Lee items de un path dado y los imprime por pantalla.
     * @see LectorCSV2
     */
    public static void main(String[] args) {
        printInfo();

        Scanner s = new Scanner(System.in);
        int cual=-1;
        while(cual!=1 && cual!= 0) {
            System.out.println("\n- INTRODUCE \n  - 0 para leer valoraciones \n  - 1 para leer items \n");
            cual = s.nextInt();
        }
        boolean leido=false;
        LectorCSV2 reader = new LectorCSV2();
        ArrayList<Vector<String>> readed_ratings = new ArrayList<Vector<String>>();
        while (!leido){
            try {
                if (cual == 0) {//leer valoraciones
                    String path;
                    System.out.println("\n- INTRODUCE el path al CSV de valoraciones que desea leer: ");
                    path = s.next();
                    readed_ratings = reader.Lector_Ratings(path);
                    leido=true;
                }
            }
            catch (Exception e){
                System.out.println("\n\n- ERROR");
                System.out.println(e);
                System.out.println("\n- Prueba con una entrada como \n   - ratings.db.csv \n   - nombre_carpeta/ratings.db.csv) " +
                        "\n   - items.csv \n   - nombre_carpeta/items.csv");
            }
        }

        if (cual==0){
            System.out.println("UserId ItemId Rating");
            for (Vector<String> v: readed_ratings){
                String pal="";
                for(String st: v){
                    pal+=st+" ";
                }
                //System.out.println(pal);
            }
        }


/*
        //Leer Items
        Vector<String> readed_Items = new Vector<String>();
        try {
            readed_Items = reader.Lector_Items(path_Items);
        }
        catch (Exception e){
            System.out.println(e);
        }

        for(int i=0;i<readed_Items.size();++i) {
            System.out.println(readed_Items.get(i)+"\n");
        }

*/
    }
    static void printInfo() {
        System.out.println("\nDRIVER DE LA CLASE LectorCSV2\n");
        System.out.println("    La clase LectorCSV2 lee ficheros CSV de los paths introducidos por el usuario.");
        System.out.println("    Esta salida la redirige a la funcion execute de la clase Rate Recomendation");

    }
}

