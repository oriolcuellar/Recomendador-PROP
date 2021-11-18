package FONTS.src.domini.model;

import FONTS.src.domini.model.*;
import java.util.*;

//@Author Jordi Olmo
public class TipusItem {

    //atributes
    private String ID;
    private ArrayList<Atribute> atributes;

    //contructores
    public TipusItem( ArrayList<Atribute> atributes) {

        Vector <String> v = new Vector<String> (atributes.size());

        for (int i = 0; i < v.size(); ++i) {

            v.set(i, atributes.get(i).getName());
        }

        Collections.sort(atributes, new Comparator<Atribute>() {
            @Override
            public int compare(Atribute a, Atribute b) {
                return a.getName().compareTo(b.getName());
            }

        });

        this.ID = v.toString();
        this.atributes = atributes;
    }

    public TipusItem(){}

    //getters
    public String getID() {
        return ID;
    }
    public ArrayList<Atribute> getAtributes() {
        return atributes;
    }

    public int num_atributs() {return atributes.size(); }
    public int num_atributs_rellevants() {

        int n = 0;
        for (int i = 0;i < atributes.size(); i++){
            if(atributes.get(i).isRellevant()) ++n;
        }
        return n;
    }

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