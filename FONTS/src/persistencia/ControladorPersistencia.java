package FONTS.src.persistencia;

import java.io.*;
import java.util.ArrayList;
import java.util.Vector;

/** \brief Clase que implementa ControladorPersistencia.
 *  @author Oriol Cuellar
 */
public class ControladorPersistencia {
    static ControladorPersistencia CtrlPersistencia;
    static DadesItem CtrlItem;
    static DadesRatings CtrlRatings;
    static DadesRecomendation CtrlRecomendation;


    //----------------------------------------------------------------------------
    /** Constructora de la clase.
     */
     private ControladorPersistencia(){
         CtrlItem = DadesItem.getInstance();
         CtrlRatings = DadesRatings.getInstance();
         CtrlRecomendation = DadesRecomendation.getInstance();
    }
    public static ControladorPersistencia getInstance(){
        if (CtrlPersistencia==null){
            CtrlPersistencia = new ControladorPersistencia();
        }
        return CtrlPersistencia;
    }

    //----------------------------------------------------------------------------

    /** Función que lee Items de un fichero CSV.
     * @param csvFile Path al fichero CSV.
     * @return Vector de Strings que contienen los items leidos.
     */
    public Vector <String> Lector_Items(String csvFile) throws Exception{
        return CtrlItem.Lector_Items(csvFile);
    }
    /** Función que lee valoraciones de un fichero CSV.
     * @param csvFile Path al fichero CSV.
     * @return ArrayList de Vectores de Strings que contienen las valoraciones leidas.
     */
    public ArrayList<Vector<String>> Lector_Ratings(String csvFile ) throws Exception{
        return CtrlRatings.Lector_Ratings(csvFile);
    }

}