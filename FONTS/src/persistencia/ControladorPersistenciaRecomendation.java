package FONTS.src.persistencia;

public class ControladorPersistenciaRecomendation {

    static ControladorPersistenciaRecomendation CtrRecomendation;

    private ControladorPersistenciaRecomendation(){
    }

    public static ControladorPersistenciaRecomendation getInstance(){
        if (CtrRecomendation==null){
            CtrRecomendation = new ControladorPersistenciaRecomendation();
        }
        return CtrRecomendation;
    }

}
