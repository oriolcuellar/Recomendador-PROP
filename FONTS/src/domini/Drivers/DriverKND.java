package FONTS.src.domini.drivers;
import FONTS.src.domini.model.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

//@Author Jordi Olmo
public class DriverKND {

    private static Vector <String> items = new Vector<String>();
    private static Conjunt_Items itemList = new Conjunt_Items();
    private static Map<String, TipusItem> itemTypeList = new HashMap<String, TipusItem>();

    public static Vector<String> Lector_Items(String csvFile) {
        //post: return un vector de les files del csv

        items = new Vector<String>();
        BufferedReader br = null;
        String line = "";
        try {
            br = new BufferedReader(new FileReader(csvFile));
            boolean first = true;
            int num_atributes=0;
            while ((line = br.readLine()) != null) {
                items.add(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return items;
    }

    public static void loadItems(){

            items = Lector_Items("Entradas_CSV/items.csv");

            for(int i = 1; i < items.size();++i)
                createItem(items.get(0), items.get(i));

    }

    public static void createItem(String atributs, String valors){

        //string to arraylist de valors

        ArrayList<String> datos_valors = new ArrayList<String>();
        String pal="";
        boolean frase=false;
        for (int iterat=0;iterat<valors.length();++iterat){
            if(valors.charAt(iterat)=='"'){
                frase=!frase;
            }
            if(valors.charAt(iterat)!=',' || frase){
                pal+=valors.charAt(iterat);
            }
            if(valors.charAt(iterat)==',' && !frase){
                if (pal.length()==0) pal="";
                datos_valors.add(pal);
                //System.out.println(pal);
                pal="";

            }
        }
        if (valors.charAt(valors.length()-1)==','){
            datos_valors.add("");
        }
        else datos_valors.add(pal);

        //cremos vector atributos

        String[] datos = atributs.split(",");
        //System.out.println(datos.length);
        ArrayList <Atribute> va = new ArrayList<Atribute>();
        ArrayList <String> vsa = new ArrayList<String>();//solo para definir el tipo de item
        int pos_id=0;
        for (int i = 0; i <datos.length; ++i) {
            if(datos[i].equals("id")) pos_id=i;
            else{
                Atribute aux = new Atribute(datos[i]);
                va.add(aux);
                vsa.add(datos[i]);

            }
        }

        //miramos si no existe item

        int comp = Integer.valueOf(datos_valors.get(pos_id));
        if (itemList.existeix_item(comp)){
            System.out.println("ja existeix id");
            //acabar la funcion-----------------------------------------------------------------------------
        }
        //creamos tipus item si NO EXISTE
        boolean new_type_item=false;
        String ID_ti=vsa.toString();
        TipusItem ti;
        if (itemTypeList.containsKey(ID_ti)){//existe
            ti=itemTypeList.get(ID_ti);
            va=ti.getAtributes();
        }
        else{//no existe
            ti = new TipusItem(va);
            itemTypeList.put(ID_ti, ti);
            new_type_item=true;
        }

        //DEFINIR TIPO ATRIBUTO
        //string de valores to vector

        //if (valors[valors.length()].equals(","))
        //System.out.println(vsa.size());
        //System.out.println(datos_valors.size());
        ArrayList <String> vsv= new ArrayList<String>();
        for (int i = 0; i <vsa.size()+1; ++i) {
            //System.out.println(datos_valors.get(i));
            if(i!=pos_id){
                //System.out.println(vsa.get(i));

                vsv.add(datos_valors.get(i));
            }
        }
        for (int pos=0;pos<vsv.size();pos++) {
            String i = vsv.get(pos);
            Boolean ranged = true;
            Atribute a = va.get(pos);
            if (i.equals("False") || i.equals("True")){
                a.setTipus("Boolean");
                a.setRellevant(true);
            }
            else if(i.contains(";")){
                a.setTipus("Vector de String");
                a.setRellevant(true);
            }
            else if(i.length()==10 && i.charAt(0)<='9' && i.charAt(0)>='0' && i.charAt(1)<='9' && i.charAt(1)>='0' && i.charAt(2)<='9' && i.charAt(2)>='0'
                    && i.charAt(3)<='9' && i.charAt(3)>='0' && i.charAt(4)=='-' && i.charAt(5)<='9' && i.charAt(5)>='0' && i.charAt(6)<='9' && i.charAt(6)>='0'
                    && i.charAt(7)=='-' && i.charAt(8)>='0' && i.charAt(8)<='9' && i.charAt(9)<='9' && i.charAt(9)>='0' ){
                a.setTipus("Data");
                a.setRellevant(true);
            }
            else if(i.equals("")){
                if (a.getType().equals("")){
                    a.setTipus("Buit");
                    a.setRellevant(false);
                }
            }
            else {
                for (int p = 0; p < i.length() && ranged; ++p){
                    if (!((i.charAt(p)>='0' && i.charAt(p)<='9') || i.charAt(p)=='.')) ranged = false;
                }
                if(ranged){
                    if (new_type_item) {
                        double min=Double.valueOf(vsv.get(pos));
                        double max=Double.valueOf(vsv.get(pos));
                        a.setTipus("Rang");
                        a.setRellevant(true);
                        Ranged_Atribute ra = new Ranged_Atribute(a,min, max );
                        va.set(pos, ra);
                    }
                    else{
                        if (a.getType().equals("Rang")){
                            double aux = Double.valueOf(vsv.get(pos));
                            if (a.getUpper()<aux) a.setUpper(aux);
                            if (a.getLower()>aux) a.setLower(aux);
                        }
                        else{
                            double min=Double.valueOf(vsv.get(pos));
                            double max=Double.valueOf(vsv.get(pos));
                            a.setTipus("Rang");
                            a.setRellevant(true);
                            Ranged_Atribute ra = new Ranged_Atribute(a,min, max );
                            va.set(pos, ra);
                        }
                    }

                }
                else {//si la estaba creado y no tenia valor de string
                    a.setTipus("String");
                    a.setRellevant(true);
                }
            }
        }


        //creamos item
        int id = Integer.valueOf(datos_valors.get(pos_id));
        Item i =new Item(id, ti, vsv);
        if (!(itemList.existeix_item(id))) itemList.anyadir_item(i);


    }

    public static void main(String[] args) {

        loadItems();

        Ranged_Atribute AT = new Ranged_Atribute("Peso", "Rang", 32.4, 75.8);
        Atribute AT2 = new Atribute("Color", "String");
        Atribute AT3 = new Atribute("Fecha", "Data");
        Atribute AT4 = new Atribute("Disponible", "Boolean");
        Atribute AT5 = new Atribute("KeyWords", "Vector de String");

        Ranged_Atribute AU = new Ranged_Atribute("Duracion", "Rang", 60.0, 240.0);
        Atribute AU2 = new Atribute("Idioma", "String");
        Atribute AU4 = new Atribute("Disponible", "Boolean");
        Atribute AU3 = new Atribute("Estreno", "Data");

        ArrayList<Atribute> Tipus1 = new ArrayList<Atribute>(Arrays.asList(AT, AT2, AT3, AT4, AT5));
        ArrayList<Atribute> Tipus2 = new ArrayList<Atribute>(Arrays.asList(AU, AU2, AU4, AU3));

        TipusItem T1 = new TipusItem(Tipus1);
        TipusItem T2 = new TipusItem(Tipus2);

        ArrayList<String> Valors2 = new ArrayList<String>(Arrays.asList("Verde", "true", "1992-04-12", "hola;que;tal", "32.4"));
        ArrayList<String> Valors3 = new ArrayList<String>(Arrays.asList("Verde", "false", "1960-05-03", "Burnos;dias;siñorina", "75.8"));
        ArrayList<String> Valors4 = new ArrayList<String>(Arrays.asList("Rojo", "false", "2000-12-04", "Habia;una;vez;un;varquito;chuiquito;dias", "32.4"));
        ArrayList<String> Valors5 = new ArrayList<String>(Arrays.asList("Azul", "", "1999-00-00", "hola;que;tal", "46.8"));

        ArrayList<String> Valors6 = new ArrayList<String>(Arrays.asList("true", "120", "1992-04-12", "Ingles"));
        ArrayList<String> Valors7 = new ArrayList<String>(Arrays.asList("false", "712", "1960-05-03", "Españolo"));
        ArrayList<String> Valors8 = new ArrayList<String>(Arrays.asList("", "240", "2000-12-04", "Ingles"));
        ArrayList<String> Valors9 = new ArrayList<String>(Arrays.asList("true", "60", "1999-00-00", "Espa"));

        Item a2 = new Item(2, T1, Valors2);
        Item a3 = new Item(3, T1, Valors3);
        Item a4 = new Item(4, T1, Valors4);
        Item a5 = new Item(5, T1, Valors5);

        Item a6 = new Item(10, T2, Valors6);
        Item a7 = new Item(11, T2, Valors7);
        Item a8 = new Item(12, T2, Valors8);
        Item a9 = new Item(13, T2, Valors9);

        ArrayList<Item> Items = new ArrayList<Item>(Arrays.asList(a2, a3, a4, a5));

        Conjunt_Items Ct = new Conjunt_Items(Items);
        ArrayList<Item> ItemsUsats = new ArrayList<Item>(Arrays.asList(a9, a7, a8, a6));
        ArrayList<Double> Valoracions = new ArrayList<Double>(Arrays.asList(2.1, 4.1, 1.0, 3.2));

        K_Neareast_Neightbour prueba = new K_Neareast_Neightbour (itemList);
        ArrayList<Item> Resultado = prueba.Algorithm(3, ItemsUsats, Valoracions);

        for (int i = 0; i < Resultado.size(); ++i)
            System.out.println(Resultado.get(i).getID());

    }
}


