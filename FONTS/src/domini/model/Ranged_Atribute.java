package FONTS.src.domini.model;
import FONTS.src.domini.model.*;

//@Author Jordi Olmo
public class Ranged_Atribute  extends Atribute{

    //Atributes
    private Double lower, upper;

    //Constructor
    public Ranged_Atribute(String name, String type, Double min, Double max) {
        super(name, type);
        lower = min;
        upper = max;
    }
    public Ranged_Atribute(Atribute a, double min, double max) {
        super(a);
        lower = min;
        upper = max;
    }

    //Getters
    public Double getLower() {
        return lower;
    }
    public Double getUpper() {
        return upper;
    }

    //Setters
    public void setLower(Double lower) {
        this.lower = lower;
    }
    public void setUpper(Double upper) {
        this.upper = upper;
    }
}
