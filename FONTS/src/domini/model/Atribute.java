package FONTS.src.domini.model;

import java.util.Vector;

/** \brief Clase que representa un Atributo.
 *  @author Jordi Olmo
 */
public class Atribute {

    //atributes

    /** Nombre del Atributo.
     */
    private String Nom;

    /** Tipo del Atribute wue deberia ser uno de los siguientes para usarse en el algoritmo
     {Boolean, String, Vector de String, Data, Rang}.
     */
    private String Tipus;

    /** Booleano que representa si el atributo es relevante o no, es decir si se debe considerar para comparar o no.
     */
    private boolean Rellevant;

    //Constructors

    /** Creadora de la classe, con el nomnbre y tipo de los parametros, rellevant por defecto es true.
     * @param name Nombre del atributo.
     * @param type Tipo del Atributo.
     */

    public Atribute(String name, String type) {

        Nom = name;
        Tipus = type;
        Rellevant = true;
    }
    /** Creadora de la classe, con el nomnbre de parametro y tipus vacio, rellevant por defecto es true.
     * @param name Nombre del atributo.
     */

    public Atribute(String name) {

        Nom = name;
        Tipus = "";
        Rellevant = true;
    }

    /** Creadora de la classe, con el nombre y tipo vacíos, rellevant por defecto es true.
     */

    public Atribute() {

        Nom = "";
        Tipus = "";
        Rellevant = true;
    }

    /** Creadora de la classe, con un atributo de parametro, rellevant por defecto es true.
     * @param a Atributo al que se igualaran los valores de sus atributos en este.
     */

    public Atribute(Atribute a) {

        Nom = a.Nom;
        Tipus = a.Tipus;
        Rellevant = a.Rellevant;
    }

    //Getters

    /** Devuelve si el Atribute es rellevant.
     */

    public boolean isRellevant() {
        return Rellevant;
    }

    /** Devuelve el nombre del Atribute.
     */

    public String getName() {
        return Nom;
    }

    /** Devuelve el tipus del Atribute.
     */

    public String getType() {
        return Tipus;
    }

    /** Funcion para definir en Ranged_Atribute.
     */

    public Double getLower() {
        return 0.0;
    }

    /** Funcion para definir en Ranged_Atribute.
     */

    public Double getUpper() {
        return 0.0;
    }

    //Setters

    /** Define el type del Atribute.
     */

    public void setTipus(String tipus) {
        this.Tipus = tipus;
    }

    /** Define el name del Atribute.
     */

    public void setNom(String nom) {
        this.Nom = nom;
    }

    /** Define si el Atribute es Rellevant.
     */

    public void setRellevant(boolean rellevant) {
        this.Rellevant = rellevant;
    }

    /** Funcion para definir en Ranged_Atribute.
     */

    public void setLower(double low) {    }

    /** Funcion para definir en Ranged_Atribute.
     */

    public void setUpper(double up) {   }

    //Operacions

    public Vector<String> Construc_vector(String s) {

        Vector<String> v = new Vector<String>(0);
        for (int i = 0;i < s.length(); i++ ){

            if (s.charAt(i) == ';') {

                v.add(s.substring(0, i));
                s = s.substring(i+1, s.length());
                i = 0;
            }

        }
        v.add(s);
        return v;
    }
}
