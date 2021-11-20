package FONTS.src.domini.drivers;

import FONTS.src.domini.model.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/** \brief Driver de la clase TipusItem.
 *  @author Jordi Olmo
 */
public class DriverTipusItem {

    private static void escribir_ArrayList(ArrayList<Atribute> A) {

        for (int i = 0; i < A.size(); ++i)
            System.out.print(' ' + A.get(i).getName());
    }

    static void testFunction(int f) {
        switch(f)
        {
            case 1 :

            {
                System.out.println("================================================================================================");
                System.out.println("Introduce una serie de parametros de Atributes de la siguiente forma: ID Tipus" );
                System.out.println("Teniendo en cuenta que: ID y tipus son String, y los nombres deben ser diferentes");
                Scanner s = new Scanner(System.in);
                ArrayList<Atribute> Aux = new ArrayList<Atribute>();
                while (s.hasNext()){

                    Atribute a = new Atribute(s.next(), s.next());
                    Aux.add(a);
                }
                TipusItem nou_tipus = new TipusItem(Aux);
                System.out.println("El TipusItem se ha creado correctamente y estos son el ID y los Atributos que lo forman: "
                        + nou_tipus.getID());
                escribir_ArrayList(Aux);
                System.out.println("=================================================================================================");
                break;
            }

            case 2 :

            {
                System.out.println("================================================================================================");
                System.out.println("Introduce una serie de parametros de Atributes de la siguiente forma: ID Tipus" );
                System.out.println("Teniendo en cuenta que: ID y tipus son String, y los nombres deben ser diferentes");
                Scanner s = new Scanner(System.in);
                ArrayList<Atribute> Aux = new ArrayList<Atribute>();
                while (s.hasNext()){

                    Atribute a = new Atribute(s.next(), s.next());
                    Aux.add(a);
                }
                TipusItem nou_tipus = new TipusItem(Aux);
                System.out.println("El TipusItem se ha creado correctamente y este és su ID: "
                        + nou_tipus.getID());
                System.out.println("=================================================================================================");
                break;
            }

            case 3 :

            {
                System.out.println("================================================================================================");
                System.out.println("Introduce una serie de parametros de Atributes de la siguiente forma: ID Tipus" );
                System.out.println("Teniendo en cuenta que: ID y tipus son String, y los nombres deben ser diferentes");
                Scanner s = new Scanner(System.in);
                ArrayList<Atribute> Aux = new ArrayList<Atribute>();
                while (s.hasNext()){

                    Atribute a = new Atribute(s.next(), s.next());
                    Aux.add(a);
                }
                TipusItem nou_tipus = new TipusItem(Aux);
                System.out.println("El TipusItem se ha creado correctamente y estos son los Atributos que lo forman: ");
                Aux = nou_tipus.getAtributes();
                escribir_ArrayList(Aux);
                System.out.println("=================================================================================================");
                break;
            }

            case 4 :

            {
                System.out.println("================================================================================================");
                System.out.println("Introduce una serie de parametros de Atributes de la siguiente forma: ID Tipus" );
                System.out.println("Teniendo en cuenta que: ID y tipus son String, y los nombres deben ser diferentes");
                Scanner s = new Scanner(System.in);
                ArrayList<Atribute> Aux = new ArrayList<Atribute>();
                while (s.hasNext()){

                    Atribute a = new Atribute(s.next(), s.next());
                    Aux.add(a);
                }
                TipusItem nou_tipus = new TipusItem(Aux);
                System.out.println("El TipusItem se ha creado correctamente y este és el número de Atributos que lo forman: "
                        + nou_tipus.num_atributs());
                System.out.println("=================================================================================================");
                break;
            }

            case 5:

            {
                System.out.println("=========================================================================================================");
                System.out.println("Introduce una serie de parametros de Atributes de la siguiente forma: ID Tipus Rellevant" );
                System.out.println("Teniendo en cuenta que: ID y tipus son String, Rellevent és un Boolean y los nombres deben ser diferentes");
                Scanner s = new Scanner(System.in);
                ArrayList<Atribute> Aux = new ArrayList<Atribute>();
                while (s.hasNext()){

                    Atribute a = new Atribute(s.next(), s.next(), s.nextBoolean());
                    Aux.add(a);
                }
                TipusItem nou_tipus = new TipusItem(Aux);
                System.out.println("El TipusItem se ha creado correctamente y este és el número de Atributos relevantes que lo forman: "
                        + nou_tipus.Atributs_rellevants());
                System.out.println("=========================================================================================================");
                break;
            }

            case 6:

            {
                System.out.println("=========================================================================================================");
                System.out.println("Introduce una serie de parametros de Atributes de la siguiente forma: ID Tipus Rellevant" );
                System.out.println("Teniendo en cuenta que: ID y tipus son String, Rellevent és un Boolean y los nombres deben ser diferentes");
                Scanner s = new Scanner(System.in);
                ArrayList<Atribute> Aux = new ArrayList<Atribute>();
                while (s.hasNext()){

                    Atribute a = new Atribute(s.next(), s.next(), s.nextBoolean());
                    Aux.add(a);
                }
                TipusItem nou_tipus = new TipusItem(Aux);
                System.out.println("El TipusItem se ha creado correctamente y estos son los Atributos relevantes que lo forman: ");
                Aux = nou_tipus.Atributs_rellevants();
                escribir_ArrayList(Aux);
                System.out.println("=========================================================================================================");
                break;
            }

            case 7:break;

            default : System.out.println("No has introducido un número entre 1 y 7");
        }
    }

    static void printInfo() {

        System.out.println("\nDRIVER DE LA CLASE TipusItem\n");
        System.out.println("\nFunciones de la clase disponibles para probar:\n");
        System.out.println(
                "    1: Crear TipusItem\n       2: getID\n                          3: getAtributes()\n" +
                "    4: num_atributs()\n        5: num_atributs_rellevants()\n      6: Atributs_rellevants()\n" +
                "    7: FINALIZAR PRUEBA.");
    }

    public static void main(String[] args) {

        printInfo();
        Scanner s = new Scanner(System.in);
        int f;
        do{
            System.out.println("\nSelecciona funcion a probar: ");
            f = s.nextInt();
            testFunction(f);

        }while(f != 7);
    }
}
