package src.domini.model;

import src.domini.model.*;
import java.util.Vector;

//Author Jordi Olmo
public class Atribute {
    //atributes
    private String nom;
    private String tipus; //Boolean, String, Vector de String, Data, Rang
    private boolean rellevant;

    //constructors
    public Atribute(String name, String type) {
        nom = name;
        tipus = type;
        rellevant=true;
    }
    public Atribute(String name) {
        nom = name;
        rellevant=true;
        tipus="";
    }
    public Atribute() {
        rellevant=true;
        tipus="";
        nom="";
    }
    public Atribute(Atribute a) {
        rellevant=a.rellevant;
        tipus=a.tipus;
        nom=a.nom;
    }

    //getters
    public boolean isRellevant() {
        return rellevant;
    }
    public String getName() {
        return nom;
    }
    public String getType() {
        return tipus;
    }

    //setters
    public void setTipus(String tipus) {
        this.tipus = tipus;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public void setRellevant(boolean rellevant) {
        this.rellevant = rellevant;
    }

    //operaciones
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
