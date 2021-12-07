
package FONTS.src.domini.controladors;

import FONTS.src.domini.exceptions.*;
import FONTS.src.domini.model.*;
import FONTS.src.persistencia.ControladorPersistenciaItem;
import FONTS.src.persistencia.ControladorPersistenciaRatings;
import FONTS.src.persistencia.ControladorPersistenciaRecomendation;

import java.util.*;

public class ControladorDomini {

// Atributes

    private static ControladorDomini   dominiSingelton = null;

    private static User actualUser;
    private static Item selectedItem;
    private static Map <Integer, User> usersList;
    private static Conjunt_Items itemList;
    private static Map<String, TipusItem> itemTypeList;
    private static Map<Integer, ArrayList<User>> itemValoratedBy;
    private static ArrayList<myPair> lastRecomendation;

//CtrlDomini control= CtrlDomini.getInstance();
//control.getAllUsers();
//constructor
    public static ControladorDomini getInstance(){
        if (dominiSingelton == null)
        {
            dominiSingelton = new ControladorDomini() {};
        }
        return dominiSingelton;
    }

    private void iniCtrlDomini(){
        usersList = new HashMap<Integer, User>();
        actualUser = null;
        selectedItem = null;
        itemList = new Conjunt_Items();
        itemTypeList = new HashMap<String, TipusItem>();
        itemValoratedBy = new HashMap<Integer,ArrayList<User>>();
        lastRecomendation = new ArrayList<myPair>();
        User admin= new User(-1);
        admin.setRol(TipusRol.Administrador);
        usersList.put(-1, admin);
    }

    private ControladorDomini(){
        iniCtrlDomini();
    }

//Profile controller

    public void register(String struserId, String password) throws Exception {
        //post: es crea un usuari i es posa d'usuari actiu.
        //err: Usuari actiu null, userId not exists, id o passw son strings buits
        try {
            int userId = Integer.valueOf(struserId);
            if (usersList.containsKey(userId)) throw new UserExistsException(struserId);
            else if ( actualUser != null) throw new ImpossibleStateException("register");
            else if (struserId.equals("") || password.equals("")) {
                throw new NotValidUserorPasswException(struserId +" "+ password);
            } else {
                TipusRol rol = TipusRol.Usuari;
                User nouU= new User(userId, password, rol);
                usersList.put(userId,nouU );
            }
        }
        catch (Exception e){
            throw e;
        }

    }
    public void login(String struserId, String password) throws Exception{
        //post: es crea un usuari i es posa d'usuari actiu.
        try {
            int userId = Integer.valueOf((struserId));
            if (actualUser != null) {
                throw new ImpossibleStateException("login");
            } else if (usersList.containsKey(userId)) {
                actualUser = usersList.get(userId);
                if (actualUser.getPassword().equals(password)) {//logged
                    System.out.println("Sessió iniciada");
                } else {
                    throw new WrongDataException("login" + struserId + " "+ password);
                }
            }
            else throw new UserNotExistsException(struserId);
        }
        catch (Exception e){
            System.out.println(e);
        }
    }
    public  void logout()throws Exception{
        if (actualUser==null) throw new ImpossibleStateException("logout");
        else{
            actualUser=null;
            System.out.println("Logged Out");
        }

    }
    public  void editProfile(String newPass) throws Exception{
        if (actualUser==null) throw new ImpossibleStateException("editProfile");
        else{
            actualUser.setPassword(newPass);
            System.out.println("Password changed Out");
        }
    }
    public void deleteProfile(String delete_me) throws Exception{
        try {
            if(actualUser == null) throw new ImpossibleStateException("deleteProfile");
            else if(!actualUser.getRol().equals(TipusRol.Administrador)) throw new NotAnAdministratorException(String.valueOf(actualUser.getUserID()));
            else if(delete_me.equals("-1")) throw new DeleteAdministratorException(delete_me);
            else if (usersList.containsKey(Integer.valueOf(delete_me))) {
                User u = usersList.get(delete_me);
                ArrayList<valoratedItem> lista_valorados = u.getValoratedItems();
                for(valoratedItem i: lista_valorados){
                    ArrayList <User> usuarios=itemValoratedBy.get(i.getItem().getID());
                    usuarios.remove(u);
                }
            }
            else throw new UserNotExistsException(delete_me);
        }
        catch (Exception e){
            throw e;
        }

    }
    public ArrayList<myPair> showRecommendedItemsSlope(int k, int maxValue) throws Exception{// to do------------------
        try {
            if (actualUser == null) throw new ImpossibleStateException("showRecommendedItemsSlope");
            else if (actualUser.getRol().equals(TipusRol.Administrador)) throw new NotAnUserException(String.valueOf(actualUser.getUserID()));
            else if (k<1 || k>usersList.size()) throw new WrongDataException("showRecommendedItemsSlope "+ k);
            else if (actualUser.getValoratedItems().size()==0)  throw new UserWithoutRatingsException("showRecommendedItemsSlope");
            //kmeans

            Kmeans kmeans = new Kmeans();
            kmeans.run(usersList,k);
            //kmeans.printAllClusters();

            //slope one
            SlopeOne slopeOne = new SlopeOne();
            //cambiar por actual user
            return slopeOne.getPredictions(actualUser, itemValoratedBy, maxValue);
            //slopeOne.printResults();
        }
        catch (Exception e){
            throw e;
        }

    }
    public ArrayList<Item> showRecommendedItemsKNN(int num_elem,String path ) throws Exception{//"Entradas_CSV/ratings.test.known.csv"
        //leer valoraciones know
        ArrayList <Item> it = new ArrayList<Item>();
        ArrayList <Double> va = new ArrayList<Double>();
        ArrayList<Vector<String>> readed_ratings = new ArrayList<Vector<String>>();
        try {
            ControladorPersistenciaRatings reader = new ControladorPersistenciaRatings();
            readed_ratings = reader.Lector_Ratings(path);
        }
        catch (Exception e){
            throw e;
        }

        for (Vector<String> vs : readed_ratings) {
            Item nou_it= new Item(Integer.valueOf(vs.get(1)));
            if (!it.contains(nou_it)) {//No existeix item
                it.add(nou_it);
                va.add(Double.valueOf(vs.get(2)));
            }
        }
        //k-neighbours
        K_Neareast_Neightbour knn = new K_Neareast_Neightbour();
        return knn.Algorithm(num_elem, itemList, it, va);
    }
    public float doRecomendation(int k_slope, int max_slope) throws Exception{
        try{
            ArrayList<myPair> slope = dominiSingelton.showRecommendedItemsSlope(k_slope, max_slope);
            lastRecomendation=slope;
            float a =2;
            return a;
        }
        catch (Exception e){
            throw e;
        }
    }
    public float evaluateRecomendation(String path) throws Exception{ //co
        if (actualUser==null) throw new ImpossibleStateException("avaluateRecomendation");
        else if(actualUser.getRol().equals(TipusRol.Administrador)) throw new NotAnUserException("avaluateRecomendation");
        else if (lastRecomendation.size()==0) throw new EmptyLastRecomendationException("evaluateRecomendation");
        try{
            ControladorPersistenciaRatings ctrRec = new ControladorPersistenciaRatings();
            ArrayList <Vector<String>> ratings_leidos = ctrRec.Lector_Ratings(path);
            ArrayList <myPair> ratingsOrdenados = new ArrayList<myPair>();
            for (Vector<String> v: ratings_leidos){//ordenar y eliminar valoraciones que no son mias
                if (String.valueOf(actualUser.getUserID()).equals(v.get(0))){
                    myPair m = new myPair(Integer.valueOf(v.get(1)),Float.valueOf(v.get(2)));
                    ratingsOrdenados.add(m);
                }
            }
            //Comparator<myPair> comparador = Collections.reverseOrder();
            Collections.sort(ratingsOrdenados, new Comparator<myPair>() {//ordenar las varolaciones en unknown
                public int compare(myPair p1, myPair p2) {
                    if (p1.getValoration()>p2.getValoration()) return 1;
                    else if(p1.getValoration()<p2.getValoration()) return -1;
                    return 0;
                }
            });
            Collections.reverse(ratingsOrdenados);//girar


            //for (myPair m4: ratingsOrdenados) System.out.println(m4.getItemID()+" "+m4.getValoration());
            //System.out.println(aux.size());
            //System.out.println(ratingsOrdenados.size());
            ArrayList<myPair> lastRecomendationAuxiliar = new ArrayList<myPair>();
            for (myPair m: lastRecomendation){//eliminar mis predicciones que no estan en unknown
                for (myPair m2: ratingsOrdenados){
                    if(m2.getItemID()==m.getItemID()){
                        lastRecomendationAuxiliar.add(m);
                    }
                }
            }
            System.out.println(" ");
            //for (myPair m: lastRecomendationAuxiliar) System.out.println(m.getItemID()+" "+m.getValoration());
            ArrayList <myPair> ratingsFiltrados = new ArrayList<myPair>();
            for (myPair m: lastRecomendationAuxiliar){//eliminar de known los que no haya prediccion
                for (myPair m2: ratingsOrdenados){
                    if (m.getItemID()==m2.getItemID()){
                        ratingsFiltrados.add(m);
                    }
                }
            }
            //System.out.println(ratingsFiltrados.size());
            //System.out.println(lastRecomendation.size());
            //System.out.println(lastRecomendationAuxiliar.size());
            try {
                RateRecomendation r = new RateRecomendation();
                return r.execute(lastRecomendationAuxiliar, ratingsFiltrados);
            }
            catch (Exception e){
                throw e;
            }
        }
        catch (Exception e){
            throw e;
        }
    }

    public void selectItem(){
    }

    public void rateItem(){}

    public Vector<String> showAllItems(){
        Vector <String> totsItems = new Vector<String>();
        for(Item i: itemList.getItems()){
            System.out.println(i.getID());
            totsItems.add(String.valueOf(i.getID()));
        }
        return totsItems;
    }

    public Vector <Vector<String>> ShowRatedItems() throws Exception{//vector de vectores de strings de 3 posiciones user id, item id, valoracion
        if (actualUser==null) throw new ImpossibleStateException("ShowRatedItems");
        else if(usersList.get(actualUser).getValoratedItems().size()==0) throw new NoRatedItemsException(String.valueOf(actualUser.getUserID()));

        Vector <Vector<String>> valorations = new Vector<Vector<String>>();
        try {
            for (valoratedItem i : usersList.get(actualUser).getValoratedItems()) {
                System.out.println("\n" + actualUser.getUserID() + " " + i.getItem().getID() + " " + i.getValoracio() + "\n");
                Vector<String> aux = new Vector<String>();
                aux.add(String.valueOf(actualUser.getUserID()));
                aux.add(String.valueOf(i.getItem().getID()));
                aux.add(String.valueOf(i.getValoracio()));
                valorations.add(aux);
            }
        }
        catch (Exception e){
            throw e;
        }
        return valorations;
    }
    public void saveItems(String path) throws Exception{
        if (actualUser==null) throw new ImpossibleStateException("saveItems");
        else if (actualUser.getRol().equals(TipusRol.Administrador)) throw new NotAnUserException(String.valueOf(actualUser.getUserID()));
        try{
            ControladorPersistenciaItem ctrlItem= new ControladorPersistenciaItem();
            ctrlItem.Escritor_Items(path, itemList);
        }
        catch (Exception e){
            throw e;
        }
    }
    public void saveRatings(String path) throws Exception{
        if (actualUser==null) throw new ImpossibleStateException("saveRatings");
        else if (actualUser.getRol().equals(TipusRol.Administrador)) throw new NotAnUserException(String.valueOf(actualUser.getUserID()));
        try{
            ControladorPersistenciaRatings ctrlRating= new ControladorPersistenciaRatings();
            ctrlRating.Escritor_Ratings(path,usersList );
        }
        catch (Exception e){
            throw e;
        }
    }
    public void saveRecomendation(String path) throws Exception{
        if (actualUser==null) throw new ImpossibleStateException("saveRatings");
        else if (actualUser.getRol().equals(TipusRol.Administrador)) throw new NotAnUserException(String.valueOf(actualUser.getUserID()));
        try{
            ControladorPersistenciaRecomendation ctrlRecomendation= new ControladorPersistenciaRecomendation();
            ctrlRecomendation.Escritor_Recomendation(path );
        }
        catch (Exception e){
            throw e;
        }
    }

    public void createItem(String atributs, String valors) throws Exception{
        if (actualUser==null) throw new ImpossibleStateException("ShowRatedItems");
        try {
            createItemPath(atributs, valors, itemList, itemTypeList);
        }
        catch (Exception e){
            throw e;
        }
    }
    private void createItemKNN(String atributs, String valors, Conjunt_Items ListaItems, Map <String, TipusItem> ListaTiposItems) throws Exception{
        if (actualUser==null) throw new ImpossibleStateException("ShowRatedItems");
        try {
            createItemPath(atributs, valors,ListaItems,ListaTiposItems);
        }
        catch (Exception e){
            throw e;
        }
    }
    private void createItemPath(String atributs, String valors, Conjunt_Items ListaItems, Map <String, TipusItem> ListaTiposItems){
        //dado una lista de items lo mete ahi si no existe

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
                Atribute aux = new Atribute(datos[i], "");
                va.add(aux);
                vsa.add(datos[i]);

            }
        }

        //miramos si no existe item

        int comp = Integer.valueOf(datos_valors.get(pos_id));
        if (ListaItems.existeix_item(comp)){
            System.out.println("ja existeix id");
            //acabar la funcion-----------------------------------------------------------------------------
        }
        //creamos tipus item si NO EXISTE
        boolean new_type_item=false;
        String ID_ti=vsa.toString();
        TipusItem ti;
        if (ListaTiposItems.containsKey(ID_ti)){//existe
            ti=ListaTiposItems.get(ID_ti);
            va=ti.getAtributes();
        }
        else{//no existe
            ti = new TipusItem(va);
            ListaTiposItems.put(ID_ti, ti);
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
                        Ranged_Atribute ra = new Ranged_Atribute(a.getName(), a.getType(),min, max );
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
                            Ranged_Atribute ra = new Ranged_Atribute(a.getName(), a.getType(),min, max );
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
        if (!(ListaItems.existeix_item(id))){
            ListaItems.anyadir_item(i);
        }


    }
    public void deleteItem(String deleteme) throws Exception{
        if (!actualUser.getRol().equals(TipusRol.Administrador)) throw new NotAnAdministratorException(String.valueOf(actualUser.getUserID()));
        else if (itemList.existeix_item(Integer.valueOf(deleteme))) throw new ItemNotExistsException(deleteme);
        Item it = itemList.getItems().get(Integer.valueOf(deleteme));

        //borrar de cada usuario el item si lo ha valorado
        for (int i: usersList.keySet()){
            User u = usersList.get(i);
            ArrayList <valoratedItem> valorated = u.getValoratedItems();
            valorated.remove(it);
        }
        //borrar de el vector de itemValoratedBy
        itemValoratedBy.remove(it.getID());
        //borrar de itemList
        itemList.eliminar_item(it);
        //si es item selected==null
        if (selectedItem==it) selectedItem=null;
    }
    //necesario?----------------------------------------------------------------------------------------
    public void modifyItem(){

    }
    //--------------------------

    public void loadItems(String path) throws Exception{//"Entradas_CSV/items.csv"
        //pre: actualUser es admin
        try {
            if (actualUser == null) throw new ImpossibleStateException("loadItems");
            else if (actualUser.getRol().equals(TipusRol.Administrador)) {
                Vector<String> mat_items = new Vector<String>();
                ControladorPersistenciaItem reader = new ControladorPersistenciaItem();
                mat_items = reader.Lector_Items(path);

                for (int i = 1; i < mat_items.size(); ++i) {
                    dominiSingelton.createItem(mat_items.get(0), mat_items.get(i));
                }

            } else throw new NotAnAdministratorException("loadItems");
        }
        catch (Exception e){
            throw e;
        }

    }
    //"Entradas_CSV/ratings.db.csv" = path
    public void loadRates(String path) throws Exception{//falta añadir item usado a la lista de items usados
        //pre: actualUser es admin
        try {
            if (actualUser==null) throw new ImpossibleStateException("loadRates");
            else if(!usersList.get(actualUser.getUserID()).getRol().equals((TipusRol.Administrador)))
                throw new NotAnAdministratorException((String.valueOf(actualUser.getUserID())));
            else{

                ArrayList<Vector<String>> readed_ratings = new ArrayList<Vector<String>>();

                ControladorPersistenciaRatings reader = new ControladorPersistenciaRatings();
                readed_ratings = reader.Lector_Ratings(path);

                TipusRol t = TipusRol.Usuari;
                for (Vector<String> vs : readed_ratings) {
                    if (usersList.containsKey(Integer.valueOf(vs.get(0)))) {//existeix
                        User usuari = usersList.get(Integer.valueOf(vs.get(0)));
                        if (usuari.searchUsedItem(Integer.valueOf(vs.get(1))) == null) {//no existe el item en sus valoraciones
                            usuari.addvaloratedItem(Integer.valueOf(vs.get(1)), Float.valueOf(vs.get(2)));

                        }
                    } else {//no existeix, es crea, afegim valoracio a la seva llista, afegim valoracio allista itemUsatList
                        User usuari = new User(Integer.valueOf(vs.get(0)));
                        usuari.addvaloratedItem(Integer.valueOf(vs.get(1)), Float.valueOf(vs.get(2)));
                        usersList.put(Integer.valueOf(vs.get(0)), usuari);
                    }
                    //parte del item
                    if (itemValoratedBy.containsKey(Integer.valueOf(vs.get(1)))) {//existeix item al map
                        User usuari = usersList.get(Integer.valueOf(vs.get(0)));
                        itemValoratedBy.get(Integer.valueOf(vs.get(1))).add(usuari);
                    } else {//NO existeix item al map
                        User usuari = usersList.get(Integer.valueOf(vs.get(0)));
                        ArrayList<User> au = new ArrayList<User>();
                        au.add(usuari);
                        itemValoratedBy.put(Integer.valueOf(vs.get(1)), au);
                    }
                }
            }
        }
        catch (Exception e){
            throw e;
        }
    }

    //Necesario?--------------------------------------------------
    /*
    public void deleteUser(String delete_me){
        //pre: actualUser admin
        if(actualUser!=null && actualUser.getRol().equals(TipusRol.Administrador) && !delete_me.equals("-1")){//no esborres l'admin




            for (ItemUsat i: ratesList){
                if ( delete_me.equals(StringValueOf(i.getUsuari().getUserID())) ratesList.delete(i);
            }
            usersList.delete(delete_me);

        }
    }
    -----------------------------------------------------------------------
*/

    public void createUser( String create_me) throws Exception{
        if(actualUser.getRol().equals(TipusRol.Administrador) ){//no esborres l'admin
            if (!usersList.containsKey(create_me)){
              User nou = new User(Integer.valueOf(create_me));
                usersList.put(Integer.valueOf(create_me), nou);
            }

        }
        else throw new NotAnAdministratorException("createUser");
    }
    public Vector <String> showAllUsers( ){
        Vector <String> vs = new Vector<String>();
        for (User u: usersList.values()){
            System.out.println(u.getUserID());
            vs.add(String.valueOf(u.getUserID()));
        }
        return vs;
    }



}
