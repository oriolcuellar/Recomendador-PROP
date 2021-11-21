package FONTS.src.domini.drivers;
import java.util.*;
import static org.junit.Assert.*;

import FONTS.src.domini.model.Atribute;
import FONTS.src.domini.model.Item;
import FONTS.src.domini.model.Ranged_Atribute;
import FONTS.src.domini.model.TipusItem;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(value=Parameterized.class)



public class Suite_Distance_Item {

    private Double Distancia;
    private Item a, b;

    public Suite_Distance_Item(Item a, Item b) {
        this.a = a;
        this.b = b;
    }

    @Parameters
    public static Collection<Object[]> getTestParameters () {
        Object[][] Ret = new Object[5][3];

        Ranged_Atribute AT = new Ranged_Atribute("Peso", "Rang", 32.4, 75.8);
        Atribute AT2 = new Atribute("Color", "String");
        Atribute AT3 = new Atribute("Fecha", "Data");
        Atribute AT4 = new Atribute("Disponible", "Boolean");
        Atribute AT5 = new Atribute("KeyWords", "Vector de String");

        ArrayList<Atribute> Tipus1 = new ArrayList<Atribute>(Arrays.asList(AT));
        ArrayList<Atribute> Tipus2 = new ArrayList<Atribute>(Arrays.asList(AT2));
        ArrayList<Atribute> Tipus3 = new ArrayList<Atribute>(Arrays.asList(AT3));
        ArrayList<Atribute> Tipus4 = new ArrayList<Atribute>(Arrays.asList(AT4));
        ArrayList<Atribute> Tipus5 = new ArrayList<Atribute>(Arrays.asList(AT5));

        TipusItem RANG = new TipusItem(Tipus1);
        TipusItem STRING = new TipusItem(Tipus2);
        TipusItem DATA = new TipusItem(Tipus3);
        TipusItem BOOLEAN = new TipusItem(Tipus4);
        TipusItem VECTOR_DE_STRING = new TipusItem(Tipus5);

        ArrayList<String> Valors2 = new ArrayList<String>(Arrays.asList("32.4"));
        ArrayList<String> Valors22 = new ArrayList<String>(Arrays.asList("32.4"));

        ArrayList<String> Valors3 = new ArrayList<String>(Arrays.asList("Verde"));
        ArrayList<String> Valors32 = new ArrayList<String>(Arrays.asList("Verde"));

        ArrayList<String> Valors4 = new ArrayList<String>(Arrays.asList("2000-12-04"));
        ArrayList<String> Valors42 = new ArrayList<String>(Arrays.asList("2000-12-04"));

        ArrayList<String> Valors5 = new ArrayList<String>(Arrays.asList(""));
        ArrayList<String> Valors52 = new ArrayList<String>(Arrays.asList( ""));

        Item a2 = new Item(2, RANG, Valors2);
        Item a22 = new Item(22, RANG, Valors22);

        Item a3 = new Item(3, STRING, Valors3);
        Item a32 = new Item(32, STRING, Valors32);

        Item a4 = new Item(4, DATA, Valors4);
        Item a42 = new Item(42, DATA, Valors42);

        Item a5 = new Item(5, DATA, Valors5);
        Item a52 = new Item(52, DATA, Valors52);

        ArrayList <String> valors;

        Ret[0][0] = 1.0; Ret[0][1] = a2; Ret[0][2] = a22;
        Ret[1][0] = 1.0; Ret[2][1] = a3; Ret[2][2] = a32;
        Ret[2][0] = 1.0; Ret[2][1] = a4; Ret[2][2] = a42;
        Ret[3][0] = 1.0; Ret[3][1] = a5; Ret[3][2] = a52;

        return Arrays.asList(Ret);
    }
    @Test
    public void add() {
        assertEquals( Distancia, a.Distance(b), 0 );
    }
}
