package FONTS.src.domini.drivers;
import FONTS.src.domini.model.Atribute;
import FONTS.src.domini.model.Item;
import FONTS.src.domini.model.TipusItem;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses; import org.junit.runners.Suite.SuiteClasses;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

/** \brief Driver de la classe item. Suite de distancias
 *  @author Jordi Olmo
 */
public class Suite_Resto_Item {

    private  Atribute AT;
    private  ArrayList<Atribute> Tipus1;
    private  TipusItem STRING;
    private  ArrayList<String> Valors;

    @Before
    public  void antesdeclase(){

        AT = new Atribute("Color", "String");
        Tipus1 = new ArrayList<Atribute>(Arrays.asList(AT));
        STRING = new TipusItem(Tipus1);
        Valors = new ArrayList<String>(Arrays.asList("Verde"));
    }

    @Test
    public void getID(){

        Item I = new Item(332);
        assertEquals(I.getID(),332);
    }

    @Test
    public void getTipus(){

        Item I = new Item(332, STRING , Valors);
        assertEquals(I.getTipus(),STRING);
    }

    @Test
    public void getValors(){

        Item I = new Item(332, STRING , Valors);
        assertEquals(I.getValors(),Valors);
    }

    @Test
    public void setID(){

        Item I = new Item(332, STRING , Valors);
        I.setID(58);
        assertEquals(I.getID(),58);
    }


}
