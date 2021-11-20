package FONTS.src.domini.model;

import java.io.*;
import java.util.ArrayList;
import java.util.Vector;

/** \brief Clase que implementa LectorCSV2.
 *  @author Oriol Cuellar
 */
public class LectorCSV2 {
    /** Constructora de la clase.
     */
    public LectorCSV2(){
    }
    /** Función que lee Items de un fichero CSV.
     * @param csvFile Path al fichero CSV.
     * @return Vector de Strings que contienen los items leidos.
     */
    public Vector <String> Lector_Items(String csvFile) throws Exception{
        //post: return un vector de les files del csv

        Vector <String> items = new Vector<String>();
        BufferedReader br = null;
        String line = "";
        try {
            br = new BufferedReader(new FileReader(csvFile));
            boolean first = true;
            int num_atributes = 0;
            while ((line = br.readLine()) != null) {
                items.add(line);
            }
        }
        catch (Exception e){
                throw e;
            }
        return items;
    }
    /** Función que lee valoraciones de un fichero CSV.
     * @param csvFile Path al fichero CSV.
     * @return ArrayList de Vectores de Strings que contienen las valoraciones leidas.
     */
    public ArrayList<Vector<String>> Lector_Ratings(String csvFile ) throws Exception{
            ArrayList <Vector<String>> ratings=new ArrayList<Vector<String>>();
        BufferedReader br = null;
        String line = "";
        //Se define separador ","
        String cvsSplitBy = ",";
        int user=0;
        int item=1;
        int rating=2;
        try {
            br = new BufferedReader(new FileReader(csvFile));
            boolean first = true;
            while ((line = br.readLine()) != null) {
                String[] datos = line.split(cvsSplitBy);
                if (datos.length!=3) throw  new Exception("Not a ratings file: " + csvFile);
                if (first){//colamos orden. User, item, valoracion
                    if (datos[0].equals("userId")) user=0;
                    if (datos[1].equals("userId")) user=1;
                    if (datos[2].equals("userId")) user=2;
                    if (datos[0].equals("itemId")) item=0;
                    if (datos[1].equals("itemId")) item=1;
                    if (datos[2].equals("itemId")) item=2;
                    if (datos[0].equals("rating")) rating=0;
                    if (datos[1].equals("rating")) rating=1;
                    if (datos[2].equals("rating")) rating=2;
                }
                if(!first) {
                    Vector<String> dentro = new Vector<String>();
                    dentro.add(datos[user]);
                    dentro.add(datos[item]);
                    dentro.add(datos[rating]);
                    ratings.add(dentro);
                }
                first = false;
            }
        }
        catch (Exception e){
            throw e;
        }
        return ratings;

    }

}