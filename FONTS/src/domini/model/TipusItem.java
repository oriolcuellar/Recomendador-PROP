package FONTS.src.domini.model;

import java.util.*;

/** \brief Clase que representa un tipo de item, definido por su conjunto de atributos.
 *  @author Jordi Olmo
 */
public class TipusItem {

    //atributes
    /** Identificador del tipes d'item, correspont a un String amb el nom de tots els seus atributs seguits
     */
    private String ID;

    /** ArrayList que contiene todos atributos que pertenecen a este tipo.
     * @see Atribute
     */
    private ArrayList<Atribute> atributes;

    //contructores

    /** Constructora de la clase. Copia la ArrayList de atributes y la ordena crecientemente por nombre de atributo,
     para el id coje todos los nonmbres de atributs y los combina en un String.
     * @param atributes ArrayList de los atributos que definen este tipo.
     */
    public TipusItem( ArrayList<Atribute> atributes) {

        ArrayList <String> v = new ArrayList<String> ();

        Collections.sort(atributes, new Comparator<Atribute>() {
            @Override
            public int compare(Atribute a, Atribute b) {
                return a.getName().compareTo(b.getName());
            }

        });

        for (int i = 0; i < atributes.size(); ++i) {

            v.add(i, atributes.get(i).getName());
        }

        this.ID = v.toString();
        this.atributes = atributes;
    }

    //getters

    /** Devueve el ID del tipus.
     */

    public String getID() {
        return ID;
    }

    /** Devueve el ArrayList de Atributes.
     * @see Atribute
     */

    public ArrayList<Atribute> getAtributes() {
        return atributes;
    }

    /** Devueve el numero de Atributes de este tipo.
     */

    public int num_atributs() {return atributes.size(); }

    /** Devueve el numero de Atributes rellevants de este tipo.
     * @see Atribute
     */

    public int num_atributs_rellevants() {

        int n = 0;
        for (int i = 0;i < atributes.size(); i++){
            if(atributes.get(i).isRellevant()) ++n;
        }
        return n;
    }

    /** Devueve el ArrayList de Atributes que son rellevants.
     * @see Atribute
     */

    public ArrayList<Atribute> Atributs_rellevants(){

        ArrayList <Atribute> Rellevants = new ArrayList<>();

        for (int i = 0;i < atributes.size(); i++){
            if(atributes.get(i).isRellevant())
                Rellevants.add(atributes.get(i));
        }
        return Rellevants;
    }

    //operacions
    public boolean afegir_atribut( Atribute a) {
        binary_insertion(atributes, a, 0 , atributes.size());
        return true;
    }


    //operacions auxiliars

    /** Funcion auxiliar que inserta un Atribute en la ArrayList de Atribute en la posicion idonea,
      para que siga estando ordenado per Name de Atribute. Usa una variacion del algoritmo merge-sort.
     * @see Atribute
      * @param atributes ArrayList de los atributos, previamente ordenada crecientemente por Name de Atribute.
     *  @param a Atribute a insertar en la ArrayList atributes.
     *  @param r Extremo derecho de la ArrayList donde insertar.
     *  @param l Extremo izquierdo de la ArrayList donde insertar.
     */

    //vigilar porque el de conjunt de item me daba mal sin tocarlo
    private void binary_insertion(ArrayList<Atribute> atributes, Atribute a, int l, int r){

        String nombre = a.getName();
        if (r >= l) {
            int mid = l + (r - l) / 2;
            //mirem se es en el mig
            boolean derecha = atributes.get(mid).getName().compareTo(nombre) > 0;
            boolean izquierda = atributes.get(mid-1).getName().compareTo(nombre) < 0;
            if (izquierda  &&  derecha)
                atributes.add( mid-1, a);
            //si no en es subvector esquerra
            if (izquierda)
                 binary_insertion(atributes, a, l, mid - 1);
            //si no derecha
            if (derecha)
                 binary_insertion(atributes, a, mid + 1, r);
            //si no es que son iguales AÑADIR EXCEPTION

        }
    }

}