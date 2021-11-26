package FONTS.src.domini.model;

import FONTS.src.domini.model.*;
import java.util.ArrayList;

/** \brief Clase que implementa RateRecomendation.
 *  @author Oriol Cuellar
 */
public class RateRecomendation {


    /** Constructora de la clase.
     */
    public  RateRecomendation(){

    }
    /** Función que ejecuta el algoritmo.
     *  @param arr de myPair (items y sus valoraciones) de parte de un usuario.
     *  @return result de la recomendacion.
     * Nos dara la precision del conjunto de algoritmos recomendadores, dado un usuario al cual se le recomiendan items.
     *      - Sumatorio desde 1 hasta la posicion final del vector de
     *          -la division de
     *              - 2 elevado a la valoracion (de la posicion del vector) menos 1
     *              - Logaritmo base 2 de (la posicion - 1)
     * @see myPair
     */
    public float execute(ArrayList<myPair> arr, ArrayList<myPair> u ){
        //IDCG
        float res1=0;
        for (int i=0;i<arr.size();++i){
            double top=(Math.pow(2, u.get(i).getValoration())-1);
            double down=(Math.log10(i+1+1) / Math.log10(2));
            top=top/down;
            res1+=top;
        }
        //DCG
        float res=0;
        for (int i=0;i<arr.size();++i){
            double top=(Math.pow(2, arr.get(i).getValoration())-1);
            double down=(Math.log10(i+1+1) / Math.log10(2));
            top=top/down;
            res+=top;
        }
        //nDCG (normalized)
        return res/res1;
    }

}
