package FONTS.src.persistencia;

import FONTS.src.domini.exceptions.EmptyFileException;
import FONTS.src.domini.exceptions.FileExistsException;
import FONTS.src.domini.exceptions.FileNotExistsException;
import FONTS.src.domini.model.*;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
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
        if (items.size()==0) throw new EmptyFileException("Lector_Items");
        return items;
    }

    public void Escritor_Items(String csvFile, Conjunt_Items list_items) throws Exception{
        //File fichero = new File(csvFile);

        //if (fichero.exists()) throw new FileExistsException(csvFile);
        //else {
            try{
               /* Map <String, ArrayList <String> > todo = new HashMap <String, ArrayList <String> > ();

                for (Item i: list_items.getItems()){
                    //String atr=i.getDadesInicials().get(0);
                    //String val=i.getDadesInicials().get(1);
                    if (todo.containsKey(atr)){//ya hay items de este tipo
                        todo.get(atr).add(val);
                    }
                    else{// este tipo de item es el primero del map
                        ArrayList <String> nou = new ArrayList<String>();
                        nou.add(val);
                        todo.put(atr, nou);
                    }


                }*/
                int n=0;
                File fichero = new File(csvFile);
                BufferedWriter bw = new BufferedWriter(new FileWriter(csvFile));
                for(Item i: list_items.getItems()){//para cada tipo de item
                    if(n==0) bw.write(i.getAtr());
                    n++;
                    bw.write(i.getString());
                    bw.close();
                }

            } catch (Exception e){
                throw e;
            }
        //}
    }

}
