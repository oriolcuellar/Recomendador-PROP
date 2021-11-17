package FONTS.src.domini.model;

import FONTS.src.domini.model.*;
import java.util.Vector;

//Author Jordi Olmo
public class Atribute {

    //atributes

    private String Nom;
    private String Tipus; //Boolean, String, Vector de String, Data, Rang
    private boolean Rellevant;

    //Constructors

    public Atribute(String name, String type) {

        Nom = name;
        Tipus = type;
        Rellevant = true;
    }
    public Atribute(String name) {

        Nom = name;
        Tipus = "";
        Rellevant = true;
    }
    public Atribute() {

        Nom = "";
        Tipus = "";
        Rellevant = true;
    }
    public Atribute(Atribute a) {

        Nom = a.Nom;
        Tipus = a.Tipus;
        Rellevant = a.Rellevant;
    }

    //Getters

    public boolean isRellevant() {
        return Rellevant;
    }
    public String getName() {
        return Nom;
    }
    public String getType() {
        return Tipus;
    }
    public Double getLower() {
        return 0.0;
    }
    public Double getUpper() {
        return 0.0;
    }

    //Setters

    public void setTipus(String tipus) {
        this.Tipus = tipus;
    }
    public void setNom(String nom) {
        this.Nom = nom;
    }
    public void setRellevant(boolean rellevant) {
        this.Rellevant = rellevant;
    }
    public void setLower(double low) {    }
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
