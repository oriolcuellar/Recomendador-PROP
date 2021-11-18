package FONTS.src.domini.drivers;

import FONTS.src.domini.model.Item;
import FONTS.src.domini.model.myPair;
import FONTS.src.domini.model.valoratedItem;

import java.util.Scanner;

/** \brief Driver de la clase myPair.
 *  @author Roberto Amat
 */
public class DriverMyPair {
    public static void main(String[] args) {
        System.out.println("Introduzca los valores necesarios para crear el par:");
        System.out.print("\n    - ID del item: ");
        Scanner S = new Scanner(System.in);
        int itemID =  S.nextInt();
        System.out.print("\n    - Valoración del item: ");
        int valoration = S.nextInt();
        myPair p = new myPair(itemID, valoration);

        System.out.println("\nValoración introducida: " + valoration + "\nValoración guardada en el par: " + p.getValoration());
        System.out.println("========================================");
        System.out.println("\nID introducido: " + itemID + "\nID guardado en el par: " + p.getItemID());
        System.out.println("========================================");
        if( valoration == p.getValoration() && itemID == p.getItemID()) System.out.println("\n FUNCIONA BIEN");
        else System.out.println("\n NO FUNCIONA BIEN");
    }
}
