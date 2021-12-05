package FONTS.src.persistencia;

import FONTS.src.domini.exceptions.FileExistsException;
import FONTS.src.domini.exceptions.FileNotExistsException;
import FONTS.src.domini.model.Conjunt_Items;
import FONTS.src.domini.model.Item;
import FONTS.src.domini.model.User;
import FONTS.src.domini.model.valoratedItem;

import java.io.*;
import java.util.ArrayList;
import java.util.Map;
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

    public void Escritor_Items(String csvFile, Conjunt_Items list_items) throws Exception{
        File fichero = new File(csvFile);

        if (fichero.exists()) throw new FileExistsException(csvFile);
        else {
            try{
                BufferedWriter bw = new BufferedWriter(new FileWriter(csvFile));
                for (Item i: list_items.getItems()){
                    String linea="";
                    linea+=i.getOriginalID();
                    bw.write(linea);
                }

                // Hay que cerrar el fichero
                bw.close();
            } catch (Exception e){
                throw e;
            }
        }
    }

}
