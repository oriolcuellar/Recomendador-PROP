package FONTS.src.persistencia;

import FONTS.src.domini.exceptions.FileNotExistsException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Vector;

public class ControladorPersistenciaItem {


    public ControladorPersistenciaItem(){
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
        try{
            br = new BufferedReader(new FileReader(csvFile));
        }
        catch (Exception e){
            throw new FileNotExistsException(csvFile);
        }
        try {
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
