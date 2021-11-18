package FONTS.src.domini.model;

import FONTS.src.domini.model.*;
import java.util.ArrayList;

/** \brief Clase que implementa RateRecomendation.
 *  @author Oriol Cuellar
 */
public class RateRecomendation {

    /** ArrayList de myPair.
     *  Almacena las valoraciones de un usuario a items.
     *  @see myPair
     */
    private static ArrayList<myPair> arr;

    /** Variable result de tipo float.
     *  Se utiliza para almacenar el resultado final de el algoritmo.
     */
    private static float result;

    /** Constructora de la clase.
     * @param arr2 items y sus valoraciones de parte de un usuario.
     */
    public  RateRecomendation(ArrayList<myPair> arr2 ){
        this.arr=arr2;
        result=-1;
    }
    /** Función que ejecuta el algoritmo.
     */
    public void execute(){
        float res=0;
        for (int i=0;i<arr.size();++i){
            double top=(Math.pow(2, arr.get(i).getValoration())-1);
            double down=(Math.log10(i+1+1) / Math.log10(2));
            top=top/down;
            res+=top;
        }
        result=res;
    }
    /** Función que devuelve el resultado.
     * @return result de la recomendacion.
     */
    public float getResult(){
        return result;
    }
}
