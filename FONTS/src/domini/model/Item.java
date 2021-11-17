package FONTS.src.domini.model;

import FONTS.src.domini.model.*;
import java.util.ArrayList;
import java.util.Vector;

//@Author Jordi Olmo

/*
    Para leer items, tienes que leer la primera fila, devolver un vector de string con cada atribute (que estan separados por ,), el id sera ese vector.toString()
    i cada posicion del vector sera el nombre del atributo. Para mirar el tipo y el rango tendras que esperar a los valores. Si es rellevant o no ns hacerlo automaticamente.
    Luego cada fila sera un item , pasas es string de la fila a vectores i la primera posicion serà el id y el resto los valores. Para el rang del atributo, tendras que recorer
    todos los items que tengan ese atributo i mirar la posicion de ese atributo en el vector de valores y conseguir el màximo y el minimo de todos esos.

 */
public class Item {

    //Atributes
    private int ID;
    private TipusItem tipus;
    private ArrayList <String> valors;

    //Constructors

    /* El indice del valor debe coincidir con el indice del atributo para funcionar*/
    public Item(int ID, TipusItem tipus, ArrayList <String> valors) {
        //    if (!Conjunt_Items.existeix_item(ID)) {
        this.ID = ID;
        this.tipus = tipus;
        this.valors = valors;
        //   }
    }

    public Item(int ID) {this.ID = ID;}

    //Getters

    public int getID() {
        return ID;
    }
    public TipusItem getTipus() {
        return tipus;
    }
    public ArrayList getValors() {
        return valors;
    }

    //Setters
    public void setID(int ID) {
        this.ID = ID;
    }

    //Operacions

    //mirar desacoplament de Atribute
    public Double Distance (Item b) {

        ArrayList<Atribute>  V_A = new ArrayList<Atribute>();
        ArrayList<Atribute>  V_B = new ArrayList<Atribute>();

        ArrayList <String> Valors_A = new ArrayList<String>();
        ArrayList <String> Valors_B = new ArrayList<String>();

        clonador_ArrayList(V_A, tipus.Atributs_rellevants());
        clonador_ArrayList(V_B, b.getTipus().Atributs_rellevants());
        clonador_ArrayList(Valors_A, valors);
        clonador_ArrayList(Valors_B, b.getValors());

        if (!tipus.equals(b.getTipus())) {

            /*Transformar 2 Atributes de dos tipus diferents en dos del mateix tipus*/
            for (int i = 0; i < V_A.size();) {
                for (int j = 0; j < V_B.size();) {

                    if (!V_A.get(i).equals(V_B.get(j))) {
                        if (V_A.get(i).getName().compareTo(V_B.get(j).getName()) < 0) {

                            V_A.remove(i);
                            Valors_A.remove(i);
                        } else {

                            V_B.remove(j);
                            Valors_B.remove(j);
                        }
                    } else {

                        ++i;
                        ++j;
                    }
                }
                ++i;
            }
        }
        int n_dimensions = V_A.size();
        if(V_A.size() > 0 && V_B.size() > 0) {

            Double Distancia  = comparador_tipus_iguals(V_A, V_B, Valors_A, Valors_B, n_dimensions);
            return  Distancia;
        }
        return 0.0;
    }

    //Operacions Auxiliars

    /* Es presoposa que que totels les ArrayList son de la mateixa mida = n_d, ja que tan sols funciona si els dos items tenen els mateixos atributs*/
    private Double comparador_tipus_iguals (ArrayList<Atribute>  V_A, ArrayList<Atribute>  V_B, ArrayList <String> Valors_A, ArrayList <String> Valors_B, int n_d) {

        Double distance = 0.0;
        for (int i = 0; i < n_d; ++i){

            if(!(Valors_A.get(i).equals("")) && !(Valors_B.get(i).equals(""))) {

                if (V_A.get(i) == V_B.get(i)) {

                    if (V_A.get(i).getType().equals("Boolean") || V_A.get(i).getType().equals("String")) {

                        if (Valors_A.get(i).equals(Valors_B.get(i)))
                            distance += 1.0;
                        else
                            distance += 0.0;
                    }
                    else if (V_A.get(i).getType().equals("Vector de String")) {

                        Vector<String> S_A = Construc_Vector(Valors_A.get(i), ';');
                        Vector<String> S_B = Construc_Vector(Valors_B.get(i), ';');

                        int n_string = Math.max(S_A.size(), S_B.size());
                        Double coeficient = 1 / (double) n_string;
                        Double Sum = 0.0;

                        for (int j = 0; j < S_A.size(); j++) {
                            for (int k = 0; k < S_B.size(); k++) {

                                if (S_A.get(j).equals(S_B.get(k)))
                                    Sum += coeficient;

                            }
                        }
                        Sum /= 2.0;
                        distance += Math.pow(Sum, 2);

                    }

                    //es considerarà que a partir de 50 anys de diferencia la distancia es 0
                    // es considera que any pelicula > 1900
                    else if (V_A.get(i).getType().equals("Data")) {

                        String s_a = Valors_A.get(i);
                        String s_b = Valors_B.get(i);

                        Vector<String> D_A = Construc_Vector(s_a, '-');
                        Vector<String> D_B = Construc_Vector(s_b, '-');

                        int dia_a, dia_b, mes_a, mes_b, any_a, any_b;

                        dia_a = Integer.valueOf(D_A.get(2));
                        mes_a = Integer.valueOf(D_A.get(1));
                        any_a = Integer.valueOf(D_A.get(0)) - 1900;

                        dia_b = Integer.valueOf(D_B.get(2));
                        mes_b = Integer.valueOf(D_B.get(1));
                        any_b = Integer.valueOf(D_B.get(0)) - 1900;

                        int total_a = dia_a + (mes_a / 2 * 31 + any_a * 4 * 30) + (mes_a / 2 * 30 + any_a * 7 * 31) + (any_a * 28);
                        int total_b = dia_b + (mes_b / 2 * 31 + any_b * 4 * 30) + (mes_b / 2 * 30 + any_b * 7 * 31) + (any_b * 28);
                        Double max = Double.valueOf((50 * 4 * 30) + (50 * 7 * 31) + (50 * 28));

                        Double coeficient = 1.0 - (((Math.max(total_a, total_b)) - Math.min(total_a, total_b)) / max);
                        distance += Math.pow(coeficient, 2);

                    } else if (V_A.get(i).getType().equals("Rang")) {

                        Double valor_a = Double.valueOf(Valors_A.get(i));
                        Double valor_b = Double.valueOf(Valors_B.get(i));

                        Double max = V_A.get(i).getUpper();
                        Double min = V_A.get(i).getLower();
                        Double coeficient = 1.0 - ((Math.max(valor_a, valor_b) - Math.min(valor_a, valor_b)) / (max - min));
                        distance += Math.pow(coeficient, 2);

                    }
                }

            }
        }
        return Math.sqrt(distance);
    }

    //retorna el string amb la data YYYY-MM-DD , en un vector de string (3), on [0] == YYYY, [1] = MM, [2] = DD
    public Vector<String> Construc_Vector(String s, char c) {

        Vector<String> v = new Vector<String>(0);
        for (int i = 0;i < s.length(); i++ ){
            if (s.charAt(i) == c) {

                v.add(s.substring(0, i));
                s = s.substring(i+1, s.length());
                i = 0;
            }
        }

        v.add(s);
        return v;
    }

    //copia la Arraylist B en A (han de ser els dos del mateix tipus
    private void clonador_ArrayList (ArrayList  A, ArrayList  B) {

            for(int i = 0; i< B.size(); ++i)
                A.add(i, B.get(i));
    }

}
