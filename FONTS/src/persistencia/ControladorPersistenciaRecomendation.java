package FONTS.src.persistencia;

import FONTS.src.domini.exceptions.FileExistsException;
import FONTS.src.domini.model.myPair;
import FONTS.src.domini.model.valoratedItem;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

public class ControladorPersistenciaRecomendation {


    public ControladorPersistenciaRecomendation(){
    }

    public void Escritor_Recomendation(String csvFile, ArrayList <myPair> lista)throws Exception{
        File fichero = new File(csvFile);

        if (fichero.exists()) throw new FileExistsException(csvFile);
        else {
            try{
                BufferedWriter bw = new BufferedWriter(new FileWriter(csvFile));
                for (myPair i: lista){
                        String linea="";
                        linea+= i.getItemID()+ ", ";
                        linea+= i.getValoration()+"\n";
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
