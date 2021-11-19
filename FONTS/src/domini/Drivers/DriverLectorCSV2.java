package FONTS.src.domini.drivers;
import FONTS.src.domini.model.LectorCSV2;

import java.util.ArrayList;
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

        String path_Val="Entradas_CSV/ratings.db.csv";
        String path_Items="Entradas_CSV/items.csv";

        LectorCSV2 reader = new LectorCSV2();

        //Leer valoraciones
        ArrayList<Vector<String>> readed_ratings = new ArrayList<Vector<String>>();
        readed_ratings = reader.Lector_Ratings(path_Val);

        for(int i=0;i<readed_ratings.size();++i) {
            //System.out.println(readed_ratings.get(i));
        }

        //Leer Items
        Vector<String> readed_Items = new Vector<String>();
        readed_Items = reader.Lector_Items(path_Items);

        for(int i=0;i<readed_Items.size();++i) {
            System.out.println(readed_Items.get(i)+"\n");
        }



    }
}
