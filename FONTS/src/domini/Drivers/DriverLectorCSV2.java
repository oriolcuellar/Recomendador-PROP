package FONTS.src.domini.drivers;

import FONTS.src.domini.model.LectorCSV2;

import java.util.ArrayList;
import java.util.Vector;

public class DriverLectorCSV2 {


    public static void main(String[] args) {
        //Leer valoraciones
        String path="Entradas_CSV/ratings.db.csv";

        LectorCSV2 reader = new LectorCSV2();
        ArrayList<Vector<String>> readed_ratings = new ArrayList<Vector<String>>();
        readed_ratings = reader.Lector_Ratings(path);

        for(int i=0;i<readed_ratings.size();++i) {
            System.out.println(readed_ratings.get(i));
        }

    }
}
