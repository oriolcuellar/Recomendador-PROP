package FONTS.src.persistencia;

public class DadesRecomendation {

    static DadesRecomendation CtrRecomendation;

    private DadesRecomendation(){
    }

    public static DadesRecomendation getInstance(){
        if (CtrRecomendation==null){
            CtrRecomendation = new DadesRecomendation();
        }
        return CtrRecomendation;
    }

}
