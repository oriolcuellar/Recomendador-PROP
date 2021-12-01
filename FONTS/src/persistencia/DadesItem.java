package FONTS.src.persistencia;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Vector;

public class DadesItem {

    /** Función que lee Items de un fichero CSV.
     * @param csvFile Path al fichero CSV.
     * @return Vector de Strings que contienen los items leidos.
     */
    public Vector <String> Lector_Items(String csvFile) throws Exception{
        //post: return un vector de les files del csv

        Vector <String> items = new Vector<String>();
        BufferedReader br = null;
        String line = "";
        try{
            br = new BufferedReader(new FileReader(csvFile));
        }

        try {

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
}
