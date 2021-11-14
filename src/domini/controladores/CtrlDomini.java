
//@Author Oriol Cuellar

package src.domini.controladores;

import src.domini.model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class CtrlDomini {

// Atributes

    private static CtrlDomini   dominiSingelton = null;

    private User actualUser;
    private Item selectedItem;
    private static Map <Integer, User> usersList;
    private static ArrayList <ItemUsat> ratesList;
    private static ArrayList<Item> itemList;

    private static Map <String, TipusItem> itemTypeList;
//CtrlDomini control= CtrlDomini.getInstance();
//control.getAllUsers();
//constructor
    public static CtrlDomini getInstance(){
        if (dominiSingelton == null)
        {
            dominiSingelton = new CtrlDomini() {};
        }
        return dominiSingelton;
    }

    private void iniCtrlDomini(){
        usersList = new HashMap<Integer, User>();
        actualUser = null;
        selectedItem = null;
        ratesList = new ArrayList<ItemUsat>();
        itemList = new ArrayList<Item>();
        itemTypeList = new HashMap<String, TipusItem>();
        User admin= new User(-1);
        admin.setRol(TipusRol.Administrador);
        usersList.put(-1, admin);
    }

    private CtrlDomini(){
        iniCtrlDomini();
    }

//Profile controller

    public void register(Integer userId, String password){
        //pre: Usuari actiu null, userId not exists, userId and password not null
        //post: es crea un usuari i es posa d'usuari actiu.
        if (usersList.containsKey(userId) || actualUser!=null || userId==null || password==null || password.equals("")){

            System.out.println("\n error al registrar \n");
        }
        else{
            TipusRol rol=TipusRol.Usuari;
            actualUser= new User(userId, password, rol);
            usersList.put(userId, actualUser);
        }
    }
    public void login(int userId, String password){
        //pre: Usuari actiu null, User amb userId i password existeix
        //post: es crea un usuari i es posa d'usuari actiu.
        if (actualUser!=null) {
            System.out.println("\n tanca sessio primer \n");
        }
        else if (usersList.containsKey(userId)) {
            actualUser=usersList.get(userId);
            if (actualUser.getPassword().equals(password)) {//logged
                System.out.println("\n Sessió iniciada \n");
            }
            else {
                System.out.println("\n error usuari o contrasenya \n");
            }
        }
    }
    public  void logout(){
        //pre: usuari actiu es null
        if (actualUser==null) System.out.println("\n No hi ha usuari loggejat \n");
        else{
            actualUser=null;
            System.out.println("\n Logged Out \n");
        }

    }
    public static void editProfile(){}
    public static void deleteProfile(){}
    public static void showRecommendedItems(int k){// to do------------------
        //kmeans

        Kmeans kmeans = new Kmeans(k, usersList);
        kmeans.printAllClusters();

        //slope one

            //map id item, users que lo tienen valorado
        //k-neighbours


    }
    public void rateRecommendation(){}//to do----------------------
    public void selectItem(){}
    public void rateItem(){}
    public void showAllItems(){
        for(Item i: itemList){
            System.out.println("\n" + i.getID() + "\n");
        }
    }
    public void ShowRatedItems(){

        //System.out.println(ratesList.size());
       /* for(ItemUsat i: usersList.get(actualUser).getItemsUsats()){
            System.out.println("\n" + i.getUsuari()+ " "+ i.getItem()+" "+ i.getValoracio() + "\n");
        }*/

    }
    public void save(){}
    public void exit(){}
    public void createItem(String atributs, String valors){



        //cremos vector atributos

        String[] datos = atributs.split(",");
        ArrayList <Atribute> va = new ArrayList<Atribute>();
        ArrayList <String> vsa = new ArrayList<String>();//solo para definir el tipo de item
        int pos_id=0;
        for (int i = 0; i <datos.length; ++i) {
            if(datos[i]=="id") pos_id=i;
            else{
                Atribute aux = new Atribute(datos[i]);
                va.add(aux);
                vsa.add(datos[i]);
            }
        }
        //creamos tipus item
        String ID_ti=vsa.toString();
        TipusItem ti = new TipusItem(va);

        //DEFINIR TIPO ATRIBUTO
            //string de valores to vector
        String[] datos2 = valors.split(",");
        ArrayList <String> vsv= new ArrayList<String>();
        for (int i = 0; i <vsa.size(); ++i) {
            if(i!=pos_id){
                Atribute aux = new Atribute(datos[i]);
                vsv.add(datos[i]);
            }
        }
        ArrayList<Atribute> va = new ArrayList<>();
        for (String i : vsv) {
            Boolean ranged = true;
            Atribute a = vsa.get();
            a.setNom(i);
            if (i.equals("False") || i.equals("True")) a.setTipus("Boolean");
            else if(i.contains(";")){
                a.setTipus("Vector de String");
            }
            else if(i.length()==10 && i.charAt(0)<='9' && i.charAt(0)>='0' && i.charAt(1)<='9' && i.charAt(1)>='0' && i.charAt(2)<='9' && i.charAt(2)>='0'
                    && i.charAt(3)<='9' && i.charAt(3)>='0' && i.charAt(4)=='-' && i.charAt(5)<='9' && i.charAt(5)>='0' && i.charAt(6)<='9' && i.charAt(6)>='0'
                    && i.charAt(7)=='-' && i.charAt(8)>='0' && i.charAt(8)<='9' && i.charAt(9)<='9' && i.charAt(9)>='0' ){
                a.setTipus("Data");
            }

            else {
                for (int p=0;p<i.length();++p){
                    if (!((i.charAt(p)>='0' && i.charAt(p)>='0') || i.charAt(p)=='.')) ranged = false;
                }
                if(ranged){
                    Ranged_Atribute ra = new Ranged_Atribute();
                    ra.setNom(i);
                    ra.setTipus("Rang");
                    va.add(ra);

                }
                else a.setTipus("String");
            }
            if(!(ranged)) va.add(a);
        }

        //creamos tipus item

        String key=va.toString();
        if (!(itemTypeList.containsKey(key))){
            itemTypeList.put(key, ti);
        }

        //creamos item



    }
    public void deleteItem(){}
    public void modifyItem(){}
    public void loadItems(){
        //pre: actualUser es admin
        if (actualUser!=null && usersList.get(actualUser.getUserID()).getRol().equals((TipusRol.Administrador))) {
            Vector<String> mat_items = new Vector<String>();
            LectorCSV2 reader = new LectorCSV2();
            mat_items = reader.Lector_Items("Entradas_CSV/items.csv");

            for(int i=1;i<mat_items.size();++i) {
                dominiSingelton.createItem(mat_items.get(0), mat_items.get(i));
            }

        }

    }
    public void loadRates(){//falta añadir item usado a la lista de items usados
        //pre: actualUser es admin
        if (actualUser!=null && usersList.get(actualUser.getUserID()).getRol().equals((TipusRol.Administrador))){

            ArrayList<Vector<String>> readed_ratings = new ArrayList<Vector<String>>();

            LectorCSV2 reader = new LectorCSV2();
            readed_ratings = reader.Lector_Ratings("Entradas_CSV/ratings.db.csv", "Ratings");

            TipusRol t = TipusRol.Usuari;
            for (Vector<String> vs : readed_ratings) {
                if (usersList.containsKey(Integer.valueOf(vs.get(0)))) {//existeix
                    User usuari = usersList.get(Integer.valueOf(vs.get(0)));
                    if (usuari.searchUsedItem(Integer.valueOf(vs.get(1))) == null) {//no existe el item en sus valoraciones
                        usuari.addItemUsat(Integer.valueOf(vs.get(1)), Float.valueOf(vs.get(0)));

                    }
                } else {//no existeix, es crea, afegim valoracio a la seva llista, afegim valoracio allista itemUsatList
                    User usuari = new User(Integer.valueOf(vs.get(0)));
                    usuari.addItemUsat(Integer.valueOf(vs.get(1)), Float.valueOf(vs.get(2)));
                    usersList.put(Integer.valueOf(vs.get(0)), usuari);
                }
            }
        }
        else{
            System.out.println("\n" + "usuari no es aministrador" + "\n");
        }
    }//to do------------------------------------
    public void deleteUser(){
        //pre: actualUser admin
        if(actualUser.getRol().equals(TipusRol.Administrador)){

        }
    }
    public void createUser(){}

}
//driver stubs por cada clase
//juegos de prueba
//excepciones

