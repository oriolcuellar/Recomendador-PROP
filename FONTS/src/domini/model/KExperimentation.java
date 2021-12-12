package FONTS.src.domini.model;

import FONTS.src.domini.controladors.ControladorDomini;

// @Author Marc Camarillas
public class KExperimentation {



    private static void calculateOptimK() throws Exception{
        ControladorDomini Ctrl = ControladorDomini.getInstance();
        try {
            Ctrl.login("-1", "-1");
            Ctrl.loadRates("EXE/Entradas_CSV/ratings.db.csv");
        } catch (Exception e) {
            throw e;
        }
        Kmeans k = new Kmeans();
        for(int i = 1; i <= 10; ++i) {
            k.run(Ctrl.getUsersList(),9);
            System.out.println("HOLA");
            System.out.println(i + " " + k.obtainWCSS());
        }
    }

    public static void main(String[] args) throws Exception {
        calculateOptimK();
    }
}
