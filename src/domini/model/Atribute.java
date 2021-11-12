import java.util.Vector;

public class Atribute {
    //atributes
    private String nom;
    private String tipus;
    private boolean rellevant;

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

    //constructores
    public Atribute(String name, String type) {
        nom = name;
        tipus = type;
    }
    public Atribute() {}

    //operaciones varias
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
