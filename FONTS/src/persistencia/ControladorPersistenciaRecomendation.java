package FONTS.src.persistencia;

import FONTS.src.domini.exceptions.EmptyFileException;
import FONTS.src.domini.exceptions.FileExistsException;
import FONTS.src.domini.exceptions.FileNotExistsException;
import FONTS.src.domini.model.myPair;
import FONTS.src.domini.model.valoratedItem;

import java.io.*;
import java.util.ArrayList;
import java.util.Vector;

public class ControladorPersistenciaRecomendation {


    public ControladorPersistenciaRecomendation(){
    }

    public ArrayList <String> Lector_Recomendation(String csvFile) throws Exception{
        ArrayList <String> recomendations=new ArrayList<String>();
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
                recomendations.add(line);
            }
        }
        catch (Exception e){
            throw e;
        }
        if (recomendations.size()==0) throw new EmptyFileException("Lector_Recomendation");
        return recomendations;
    }

    public void Escritor_Recomendation(String csvFile, ArrayList <myPair> lista)throws Exception{
        File fichero = new File(csvFile);

        //if (fichero.exists()) throw new FileExistsException(csvFile);
        //else {
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
        //}
    }


}
