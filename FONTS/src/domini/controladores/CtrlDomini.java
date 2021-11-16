
//@Author Oriol Cuellar

package FONTS.src.domini.controladores;

import FONTS.src.domini.model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class CtrlDomini {

// Atributes

    private static CtrlDomini   dominiSingelton = null;

    private static User actualUser;
    private static Item selectedItem;
    private static Map <Integer, User> usersList;
    private static ArrayList <ItemUsat> ratesList;
    private static Conjunt_Items itemList;
    private static Map <String, TipusItem> itemTypeList;
    private static Map<Integer,ArrayList<User>> itemValoratedBy;

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
        itemList = new Conjunt_Items();
        itemTypeList = new HashMap<String, TipusItem>();
        itemValoratedBy = new HashMap<Integer,ArrayList<User>>();
        User admin= new User(-1);
        admin.setRol(TipusRol.Administrador);
        usersList.put(-1, admin);
    }

    private CtrlDomini(){
        iniCtrlDomini();
    }

//Profile controller

    public void register(String struserId, String password){
        //pre: Los strings no son null
        //post: es crea un usuari i es posa d'usuari actiu.
        //err: Usuari actiu null, userId not exists, id o passw son strings buits
        int userId=Integer.valueOf(struserId);
        if (usersList.containsKey(userId) || actualUser!=null || struserId.equals("") || password.equals("")){

            System.out.println("\n error al registrar \n");
        }
        else{
            TipusRol rol=TipusRol.Usuari;
            actualUser= new User(userId, password, rol);
            usersList.put(userId, actualUser);
        }
    }
    public void login(String struserId, String password){
        //pre: Usuari actiu null, User amb userId i password existeix
        //post: es crea un usuari i es posa d'usuari actiu.
        int userId=Integer.valueOf((struserId));
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

        SlopeOne slopeOne = new SlopeOne(itemValoratedBy);
        //cambiar por actual user
        slopeOne.getPredictions(usersList.get(7));
        slopeOne.printResults();

        //k-neighbours


    }


    public void selectItem(){}
    public void rateItem(){}
    public void showAllItems(){
        for(Item i: itemList.getItems()){
            System.out.println(i.getID());
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
                for (int p=0;p<i.length();++p){
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
      //                      if (a.getUpper()<aux) a.setUpper(aux);
  //                          if (a.getLower()>aux) a.setLower(aux);
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
            readed_ratings = reader.Lector_Ratings("Entradas_CSV/ratings.db.csv");

            TipusRol t = TipusRol.Usuari;
            for (Vector<String> vs : readed_ratings) {
                if (usersList.containsKey(Integer.valueOf(vs.get(0)))) {//existeix
                    User usuari = usersList.get(Integer.valueOf(vs.get(0)));
                    if (usuari.searchUsedItem(Integer.valueOf(vs.get(1))) == null) {//no existe el item en sus valoraciones
                        usuari.addItemUsat(Integer.valueOf(vs.get(1)), Float.valueOf(vs.get(2)));

                    }
                } else {//no existeix, es crea, afegim valoracio a la seva llista, afegim valoracio allista itemUsatList
                    User usuari = new User(Integer.valueOf(vs.get(0)));
                    usuari.addItemUsat(Integer.valueOf(vs.get(1)), Float.valueOf(vs.get(2)));
                    usersList.put(Integer.valueOf(vs.get(0)), usuari);
                }
                //parte del item
                if (itemValoratedBy.containsKey(Integer.valueOf(vs.get(1)))){//existeix item al map
                    User usuari = usersList.get(Integer.valueOf(vs.get(0)));
                    itemValoratedBy.get(Integer.valueOf(vs.get(1))).add(usuari);
                }
                else{//NO existeix item al map
                    User usuari = usersList.get(Integer.valueOf(vs.get(0)));
                    ArrayList <User> au = new ArrayList<User>();
                    au.add(usuari);
                    itemValoratedBy.put( Integer.valueOf(vs.get(1)), au);
                }
            }
        }
        else{
            System.out.println("\n" + "usuari no es aministrador" + "\n");
        }
    }//to do------------------------------------
    public void deleteUser(String delete_me){
        //pre: actualUser admin
        if(actualUser!=null && actualUser.getRol().equals(TipusRol.Administrador) && !delete_me.equals("-1")){//no esborres l'admin


            /*

            for (ItemUsat i: ratesList){
                if ( delete_me.equals(StringValueOf(i.getUsuari().getUserID())) ratesList.delete(i);
            }
            usersList.delete(delete_me);
             */
        }
    }
    public void createUser( String create_me){
        if(actualUser.getRol().equals(TipusRol.Administrador) ){//no esborres l'admin
            if (!usersList.containsKey(create_me)){
              User nou = new User(Integer.valueOf(create_me));
                usersList.put(Integer.valueOf(create_me), nou);
            }

        }
    }
    public void showAllUsers( ){
        for (User u: usersList.values()){
            System.out.println(u.getUserID());
        }
    }

}
//driver stubs por cada clase
//juegos de prueba
//excepciones

