package FONTS.src.domini.model;

import FONTS.src.domini.model.*;
import java.util.ArrayList;
import java.util.Collections;

//@Author Jordi Olmo
public class KND {

    //Atributes
    private Conjunt_Items C_Items;
    private K_Neareast_Neightbour K_NN;

    //Constructura Creo que al anyadir los itemsusats se quedan en el conjunt preguntar si hace falta quitarlos
    public KND(Conjunt_Items C) {
        C_Items = C;
        K_NN = new K_Neareast_Neightbour(C_Items);
    }
}

    //Operacions


