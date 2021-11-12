import java.util.ArrayList;
import java.util.Vector;

//Author Jordi Olmo
public class K_Neareast_Neightbour {

        private Conjunt_Items C_Items;
        private Double [][] Distances;

    public K_Neareast_Neightbour(Conjunt_Items c_Items) {
        C_Items = c_Items;
        Distances = new Double[C_Items.n_Items()][C_Items.n_Items()];
        initzialitzar_matriu();
        omplir_matriu();
    }

    public ArrayList<Double> Distancies_i(int pos) {

        ArrayList<Double>  Distancias = new ArrayList<Double>();

        for (int i = 0; i < Distances.length; ++i)
            Distancias.add(Distances[pos][i]);
        return Distancias;
    }

    public void omplir_matriu() {

        for (int i = 0; i < Distances.length; ++i)
            for(int j = 0; j < Distances.length; ++j){

                if (i == j)
                Distances[i][j] =  0.0;
                else if(Distances[i][j] != -1.) {

                    Double aux = Distance(C_Items.getItems().get(i),C_Items.getItems().get(j));
                    Distances[i][j] =  aux;
                    Distances[j][i] = aux;
                }
            }
    }

    public void initzialitzar_matriu() {

        for (int i = 0; i < Distances.length; ++i)
            for(int j = 0; j < Distances.length; ++j)
                Distances[i][j] = -1.0;
    }


    public Double Distance (Item a , Item b) {

        ArrayList<Atribute>  V_A = a.getTipus().Atributs_rellevants();
        ArrayList<Atribute>  V_B = b.getTipus().Atributs_rellevants();

        ArrayList Valors_A = a.getValors();
        ArrayList Valors_B = b.getValors();

         if (!a.getTipus().equals(b.getTipus())) {

            /*Transformar 2 Atributes de dos tipus diferents en dos del mateix tipus*/
            for (int i = 0; i < V_A.size();)
                for (int j = 0; j < V_B.size();) {

                    if (!V_A.get(i).equals(V_B.get(j))) {
                        if (V_A.get(i).getName().compareTo(V_B.get(j).getName()) < 0) {

                                V_A.remove(i);
                                Valors_A.remove(i);
                        }
                        else {

                                V_B.remove(j);
                                Valors_B.remove(j);
                        }
                    }
                    else {

                        ++i;
                        ++j;
                    }
                }
        }
        int n_dimensions = V_A.size();
        Double Distancia  = comparador_tipus_iguals(V_A, V_B, Valors_A, Valors_B, n_dimensions);
        return Distancia;
    }

    /* Es presoposa que que totels les ArrayList son de la mateixa mida = n_d, ja que tan sols funciona si els dos items tenen els mateixos atributs*/
    private Double comparador_tipus_iguals (ArrayList<Atribute>  V_A, ArrayList<Atribute>  V_B, ArrayList Valors_A, ArrayList Valors_B, int n_d) {

        Double distance = 0.0;
        for (int i = 0; i < n_d; ++i){

            if(V_A.get(i) == V_B.get(i)) {

                if (V_A.get(i).getType().equals("Boolean") || V_A.get(i).getType().equals("String")) {

                    if (Valors_A.get(i).equals(Valors_B.get(i)))
                        distance += 1.0;
                    else
                        distance += 0.0;
                }

                else if(V_A.get(i).getType().equals("Vector de String")) {

                    Vector<String> S_A = (Vector<String>) Valors_A.get(i);
                    Vector<String> S_B = (Vector<String>) Valors_B.get(i);

                    int n_string = Math.max(S_A.size(), S_B.size());
                    Double coeficient = 1/(double)n_string;
                    Double Sum = 0.0;

                    for(int j = 0; j < S_A.size(); i++ ) {
                        for (int k = 0; k < S_B.size(); i++) {

                            if (S_A.get(j).equals(S_B.get(k)))
                                Sum += coeficient;

                        }
                    }
                    distance += Math.pow(Sum, 2);

                }

                //es considerarà que a partir de 50 anys de diferencia la distncia es 0
                // es considera que any pelicula > 1900
                else if(V_A.get(i).getType().equals("Data")) {

                        String s_a = (String) Valors_A.get(i);
                        String s_b = (String) Valors_B.get(i);

                        Vector <String> D_A = Construc_data(s_a);
                        Vector <String> D_B = Construc_data(s_b);

                        int dia_a, dia_b, mes_a, mes_b, any_a, any_b;

                        dia_a = Integer.valueOf(D_A.get(2));
                        mes_a = Integer.valueOf(D_A.get(1));
                        any_a = Integer.valueOf(D_A.get(0)) - 1900;

                        dia_b = Integer.valueOf(D_B.get(2));
                        mes_b = Integer.valueOf(D_B.get(1));
                        any_b = Integer.valueOf(D_B.get(0)) - 1900;

                        int total_a = dia_a + (mes_a/2 * 31 + any_a * 6 * 30) + (mes_a/2 * 30 + any_a * 6 * 31);
                        int total_b = dia_b + (mes_b/2 * 31 + any_b * 6 * 30) + (mes_b/2 * 30 + any_b * 6 * 31);
                        Double max = Double.valueOf((50 * 6 * 30) + (50 * 6 * 31));

                        Double coeficient = ((Math.max(total_a, total_b))- Math.min(total_a, total_b)) / max;
                        distance += Math.pow(coeficient, 2);
                }

                else if(V_A.get(i).getType().equals("Rang")) {

                        Double valor_a = (Double) Valors_A.get(i);
                        Double valor_b = (Double) Valors_B.get(i);

                        Ranged_Atribute R_a =(Ranged_Atribute) (V_A.get(i));
                        Double max = R_a.getUpper();
                        Double min = R_a.getLower();
                        Double coeficient = (valor_a - valor_b)/(max-min);
                        distance += Math.pow(coeficient, 2);

                }
            }
        }
        return Math.sqrt(distance);
    }

        //retorna el string amb la data YYYY-MM-DD , en un vector de string (3), on [0] == YYYY, [1] = MM, [2] = DD
    public Vector<String> Construc_data(String s) {

        Vector<String> v = new Vector<String>(0);
        for (int i = 0;i < s.length(); i++ ){
            if (s.charAt(i) == '-') {

                v.add(s.substring(0, i));
                s = s.substring(i+1, s.length());
                i = 0;
            }
        }

        v.add(s);
        return v;
    }
}
