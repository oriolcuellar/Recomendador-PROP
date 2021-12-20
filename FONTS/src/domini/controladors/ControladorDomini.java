package FONTS.src.domini.controladors;

import FONTS.src.domini.exceptions.*;
import FONTS.src.domini.model.*;
import FONTS.src.persistencia.ControladorPersistenciaItem;
import FONTS.src.persistencia.ControladorPersistenciaRatings;
import FONTS.src.persistencia.ControladorPersistenciaRecomendation;

import javax.swing.*;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/** \brief Clase que implementa el controlador de dominio.
 *  @author  Oriol Cuellar
 */
public class ControladorDomini {

// Atributes
    /**
     * Instancia singelton del controlador
     */
    private static ControladorDomini dominiSingelton = null;
    /**
     * Usuario logeado en el sistema
     * @see User
     */
    private static User actualUser;
    /**
     * Usuario administrador del sistema
     * @see User
     */
    private static User admin;
    /**
     * Item seleccionado del sistema
     * @see Item
     */
    private static Item selectedItem;
    /**
     * Map con todos los usuarios cargados en el sistema
     * @see User
     */
    private static Map <Integer, User> usersList;
    /**
     * Conjunt_Items con todos los items cargados en el sistema
     * @see Conjunt_Items
     */
    private static Conjunt_Items itemList;
    /**
     * Map con todos los tipos de items cargados en el sistema
     * @see TipusItem
     */
    private static Map<String, TipusItem> itemTypeList;
    /**
     * Map con todos los items y un vector con los usuarios que lo han valorado
     * @see User
     */
    private static Map<Integer, ArrayList<User>> itemValoratedBy;
    /**
     * ArrayList con todos los items de la ultima valoración del Hibryd
     * @see myPair
     */
    private static ArrayList<ArrayList> lastRecomendation;
    /**
     * ArrayList con todos los items de la ultima valoración
     * @see myPair
     */
    private static ArrayList<myPair> lastRecomendationSlope;
    /**
     * ArrayList con todos los items de la ultima valoración
     * @see myPair
     */
    private static ArrayList<ArrayList> lastRecomendationKNN;
    /**
     * Booleano que indica si se tiene que volver a calcular una recomendación.
     */
    private static boolean recomendationChanged;
    /**
     * Booleano que indica si se tiene que volver a calcular una recomendación con el algortimo SLOPE.
     */
    private static boolean recomendationChangedSlope;
    /**
     * Booleano que indica si se tiene que volver a calcular una recomendación con el algortimo KNN.
     */
    private static boolean recomendationChangedKNN;
    /**
     * String que indica el path al fichero de ratings para su posterior utilización eln algoritmos
     */
    private static String ratingPath;

    private static ArrayList <Vector<String>> UnKnown;

    //CtrlDomini control= CtrlDomini.getInstance();
//control.getAllUsers();
//constructor
    /**
     * Devuelve la instancia singelton del controlador
     */
    public static ControladorDomini getInstance(){
        if (dominiSingelton == null)
        {
            dominiSingelton = new ControladorDomini() {};
        }
        return dominiSingelton;
    }
    /**
     * Inicializa el controlador de dominio
     */
    private void iniCtrlDomini(){
        usersList = new HashMap<Integer, User>();
        actualUser = null;
        selectedItem = null;
        itemList = new Conjunt_Items();
        itemTypeList = new HashMap<String, TipusItem>();
        itemValoratedBy = new HashMap<Integer,ArrayList<User>>();
        lastRecomendation = new ArrayList<ArrayList>();
        lastRecomendationSlope = new ArrayList<myPair>();
        lastRecomendationKNN = new ArrayList<ArrayList>();
        UnKnown = new ArrayList<>();
        recomendationChanged=true;
        recomendationChangedSlope=true;
        recomendationChangedKNN=true;
        admin= new User(-1);
        admin.setRol(TipusRol.Administrador);
    }
    /**
     * Constructora del controlador de dominio
     */
    private ControladorDomini(){
        iniCtrlDomini();
    }

//Profile controller
    /**
     * Registra un nuevo usuario en el sistema
     * @param struserId  struserId es el nombre se usuario
     * @param password  password es la contraseña
     */
    public void register(String struserId, String password) throws Exception {
        //post: es crea un usuari i es posa d'usuari actiu.
        //err: Usuari actiu null, userId not exists, id o passw son strings buits
        try {
            int userId = Integer.valueOf(struserId);
            if (usersList.containsKey(userId) || String.valueOf(admin.getUserID()).equals(struserId)) throw new UserExistsException(struserId);
            //else if (actualUser != null) throw new ImpossibleStateException("register");
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
    /**
     * Logea en el sistema un usuario
     * @param struserId  struserId es el nombre se usuario
     * @param password  password es la contraseña
     */
    public void login(String struserId, String password) throws Exception{
        //post: es crea un usuari i es posa d'usuari actiu.
        try {
            recomendationChanged=true;
            recomendationChangedSlope=true;
            recomendationChangedKNN=true;
            int userId = Integer.valueOf((struserId));
            if (actualUser != null) {
                throw new ImpossibleStateException("login");
            } else if (usersList.containsKey(userId)) {
                if (usersList.get(userId).getPassword().equals(password)) {//logged
                    actualUser = usersList.get(userId);
                    //System.out.println("Sessió iniciada");
                } else {
                    throw new WrongDataException("login" + struserId + " "+ password);
                }
            }
            else if(String.valueOf(admin.getUserID()).equals(struserId)){
                if (admin.getPassword().equals(password)) actualUser=admin;
                else throw new WrongDataException("login" + struserId + " "+ password);
            }
            else throw new UserNotExistsException(struserId);
        }
        catch (Exception e){
            JOptionPane.showMessageDialog(null,"The username or password are not correct");
            System.out.println(e);
        }
    }
    /**
     * Saca del sistema al usuario activo
     */
    public void logout()throws Exception{
        if (actualUser==null) throw new NoUserLogedInException("logout");
        else{
            actualUser=null;
            //System.out.println("Logged Out");
        }

    }
    /**
     * Cambia la contraseña del usuario activo
     * @param newPass  newPass es la nueva contraseña
     */
    public  void editProfile(String newPass) throws Exception{
        if (actualUser==null) throw new NoUserLogedInException("editProfile");
        else{
            actualUser.setPassword(newPass);
            System.out.println("Password changed Out");
        }
    }
    /**
     * El administrador elimina al usuario delete_me
     * @param delete_me  delete_me es el usuario
     */
    public void deleteProfile(String delete_me) throws Exception{
        try {
            if(actualUser == null) throw new NoUserLogedInException("deleteProfile");
            else if(delete_me.equals(String.valueOf(admin.getUserID()))) throw new DeleteAdministratorException(delete_me);
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
    /**
     * El Usuario actual quiere una recomendación para el utilizando el algoritmo Slope
     * @param k  k es el numero de clusters que se quiere
     * @param maxValue maxValue es el numero maximo de las valoraciones
     * @return ArrayList<myPair>con las recomendación generada
     * @see myPair
     */
    private ArrayList<myPair> showRecommendedItemsSlope(int k, int maxValue) throws Exception{// to do------------------
        try {
            if (actualUser == null) throw new NoUserLogedInException("showRecommendedItemsSlope");
            else if (actualUser.getRol().equals(TipusRol.Administrador)) throw new NotAnUserException(String.valueOf(actualUser.getUserID()));
            else if (k<1 || k>usersList.size()) throw new WrongDataException("showRecommendedItemsSlope "+ k);
            else if (actualUser.getValoratedItems().size()==0)  throw new UserWithoutRatingsException("showRecommendedItemsSlope");
            else if (itemList.n_Items()==0) throw new NoItemsException("showRecommendedItemsSlope");

            //kmeans

            Kmeans kmeans = new Kmeans();
            kmeans.run(usersList,k);
            kmeans.printAllClusters();

            //slope one
            SlopeOne slopeOne = new SlopeOne();
            //cambiar por actual user
            ArrayList<myPair> p = slopeOne.getPredictions(actualUser, itemValoratedBy, maxValue);
            slopeOne.printResults();
            return p;
        }
        catch (Exception e){
            throw e;
        }

    }
     /**
     * El Usuario actual quiere una recomendación para el utilizando el algoritmo KNN
     * @param num_elem num_elem es el numero de elementos que se quiere
      * @param path path el el documento donde queremos leer los ratings
     * @return ArrayList<myPair> con las recomendación generada
     * @see myPair
     */
    private ArrayList<ArrayList> showRecommendedItemsKNN(int num_elem,String path ) throws Exception{//"Entradas_CSV/ratings.test.known.csv"
        if (actualUser == null) throw new NoUserLogedInException("showRecommendedItemsSlope");
        else if (actualUser.getRol().equals(TipusRol.Administrador)) throw new NotAnUserException(String.valueOf(actualUser.getUserID()));
        else if (actualUser.getValoratedItems().size()==0)  throw new UserWithoutRatingsException("showRecommendedItemsSlope");
        else if (itemList.n_Items()==0) throw new NoItemsException("showRecommendedItemsKNN") ;
        else if (num_elem<1) throw new WrongDataException("showRecommendedItemsKNN");
        //leer valoraciones know
        ArrayList <Item> v_items = new ArrayList<Item>();
        ArrayList <Double> valorations = new ArrayList<Double>();

        /*
        ArrayList <Double> va = new ArrayList<Double>();
        ArrayList<Vector<String>> readed_ratings = new ArrayList<Vector<String>>();

        try {
            ControladorPersistenciaRatings reader = new ControladorPersistenciaRatings();
            readed_ratings = reader.Lector_Ratings(path);
        }
        catch (Exception e){
            throw e;
        }
        ArrayList <String> validador = new ArrayList<String>();
        for (Vector<String> vs : readed_ratings) {
            Item nou_it= new Item(Integer.valueOf(vs.get(1)));
            String aux = String.valueOf(nou_it.getID());
            if (!validador.contains(aux)) {//No existeix item
                it.add(nou_it);
                va.add(Double.valueOf(vs.get(2)));
                validador.add(aux);
            }
        }*/
        ArrayList <valoratedItem> va = actualUser.getValoratedItems();
        for (int i = 0; i < va.size(); i++ ) {

            valoratedItem aux = va.get(i);
            v_items.add(aux.getItem());
            valorations.add(Double.valueOf(aux.getValoracio()));

        }
        //k-neighbours
        K_Neareast_Neightbour knn = new K_Neareast_Neightbour();
        ArrayList<ArrayList > Result = knn.Algorithm(num_elem, itemList, v_items, valorations);
        return Result;
    }
    /**
     * El Usuario actual quiere una recomendación para el utilizando el algoritmo Slope
     * @param k_slope num_elem es el número de clusters que se quiere
     * @param max_slope max_slope es el valor maximo de una valoración
     * @return ArrayList<myPair> con las recomendación generada
     * @see myPair
     */
    public ArrayList<Integer> doSlope(int k_slope, int max_slope) throws Exception{
        try{
            if (recomendationChangedSlope)
                lastRecomendationSlope = dominiSingelton.showRecommendedItemsSlope(k_slope, max_slope);
            recomendationChangedSlope=false;
            ArrayList<Integer> r = new ArrayList<>();
            for(int i = 0; i < lastRecomendationSlope.size(); ++i) {
                r.add(lastRecomendationSlope.get(i).getItemID());
            }
            return r;
        }
        catch (Exception e){
            throw e;
        }
    }
    /**
     * El Usuario actual quiere una recomendación para el utilizando el algoritmo KNN
     * @return ArrayList<myPair> con las recomendación generada
     * @see myPair
     */
    public ArrayList<Integer> doKNN() throws Exception{
        try{
            if (recomendationChangedKNN)
                lastRecomendationKNN = dominiSingelton.showRecommendedItemsKNN(itemList.n_Items(), ratingPath);
            recomendationChangedKNN=false;
            ArrayList<Item> Aux = lastRecomendationKNN.get(0);
            ArrayList<Integer> r = new ArrayList<>();
            for(int i = 0; i < Aux.size(); ++i)
                r.add(Aux.get(i).getID());

            return r;
        }
        catch (Exception e){
            throw e;
        }
    }
    /**
     * El Usuario actual quiere una recomendación para el utilizando los dos algoritmos
     * @param k_slope k_slope es el numero de clusters que se quiere para el Slope
     * @param max_slope max_slope es el valor maximo de una recomendación
     * @return ArrayList<myPair> con las recomendación generada
     * @see myPair
     */
    public ArrayList<Integer> doRecomendation(int k_slope, int max_slope) throws Exception{
        try{
            if (recomendationChanged) {
                ArrayList<myPair> slope = lastRecomendationSlope;
                ArrayList<ArrayList> knn = lastRecomendationKNN;

                if (recomendationChangedSlope){
                    slope = dominiSingelton.showRecommendedItemsSlope(k_slope, max_slope);
                    lastRecomendationSlope=slope;
                }

                if (recomendationChangedKNN){
                    knn = dominiSingelton.showRecommendedItemsKNN(itemList.n_Items(), ratingPath);
                    lastRecomendationKNN=knn;
                }

                HybridApproach h = new HybridApproach();
                ArrayList<ArrayList> Aux = h.Algoritmo(lastRecomendationSlope, lastRecomendationKNN, itemList.n_Items(), actualUser.getValoratedItems().size());

                lastRecomendation = Aux;
                recomendationChanged=false;
                recomendationChangedSlope=false;
                recomendationChangedKNN=false;
            }
            ArrayList<Item> aux = lastRecomendation.get(0);

            ArrayList<Integer> r = new ArrayList<>();
            for(int i = 0; i < aux.size(); ++i) {
                r.add(aux.get(i).getID());
            }
            return r;
        }
        catch (Exception e){
            throw e;
        }
    }
    public float evaluateRecomendationGeneral() throws Exception{
        try{
            ArrayList <myPair> nou = new ArrayList<myPair>();
            ArrayList<Item> a = lastRecomendation.get(0);
            for (Item m: a){
                myPair my = new myPair(m.getID(), 0);
                nou.add(my);
            }
            return evaluateRecomendation(nou);
        }
        catch (Exception e){
            throw e;
        }
    }
    public float evaluateRecomendationSlope() throws Exception{
        try{
            return evaluateRecomendation(lastRecomendationSlope);
        }
        catch (Exception e){
            throw e;
        }
    }
    public float evaluateRecomendationKNN() throws Exception{
        try{
            ArrayList <myPair> nou = new ArrayList<myPair>();
            ArrayList<Item> a = lastRecomendationKNN.get(0);
            for (Item m: a){
                myPair my = new myPair(m.getID(), 0);
                nou.add(my);
            }
            return evaluateRecomendation(nou);
        }
        catch (Exception e){
            throw e;
        }
    }

    /**
     * El Usuario actual quiere comparar la recomendación que ha obtenido con una ideal utilizando un algoritmo DCG
     * @return float con la calidad de la recomendación
     */
    private float evaluateRecomendation(ArrayList<myPair> lastRecomendation1) throws Exception{ //co
        if (actualUser==null) throw new NoUserLogedInException("evaluateRecomendation");
        else if (lastRecomendation1.size()==0) throw new EmptyLastRecomendationException("evaluateRecomendation");
        try{
            ArrayList <Vector<String>> ratings_leidos = UnKnown;
            for(Vector<String> v : ratings_leidos) {
                for (String s : v) System.out.println(s);
                System.out.println("===================");
            }
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
            for (myPair m: lastRecomendation1){//eliminar mis predicciones que no estan en unknown
                for (myPair m2: ratingsOrdenados){
                    if(m2.getItemID()==m.getItemID()){
                        lastRecomendationAuxiliar.add(m);
                    }
                }
            }

            //for (myPair m: lastRecomendationAuxiliar) System.out.println(m.getItemID()+" "+m.getValoration());
            ArrayList <myPair> ratingsFiltrados = new ArrayList<myPair>();
            for (myPair m2: ratingsOrdenados){//eliminar de known los que no haya prediccion
                for (myPair m: lastRecomendationAuxiliar){
                    if (m.getItemID()==m2.getItemID()){
                        ratingsFiltrados.add(m2);
                    }
                }
            }
            //System.out.println(" ");
            //for (myPair m4: ratingsFiltrados) System.out.println(m4.getItemID()+" "+m4.getValoration());
            //System.out.println(ratingsFiltrados.size());
            //System.out.println(lastRecomendation.size());
            //System.out.println(lastRecomendationAuxiliar.size());
            if(lastRecomendationAuxiliar.size()==0 || ratingsFiltrados.size()==0) throw new UserWithoutRatingsException("evaluateRecomendation");
            RateRecomendation r = new RateRecomendation();
            return r.execute(lastRecomendationAuxiliar, ratingsFiltrados);

        }
        catch (Exception e){
            throw e;
        }
    }
    /**
     * El Usuario actual quiere seleccionar un item para interactuar con el.
     * @param id id del item
     */
    public void selectItem(String id)throws Exception{
        for (Item m: itemList.getItems()){
            if (String.valueOf(m.getID()).equals(id)){
                selectedItem=m;
            }
        }
        throw new ItemNotExistsException("selectItem");
    }
    /**
     * El Usuario actual quiere ver los valores del item seleccionado
     * @return ArrayList<String> con los valores
     * @see Item
     */
    public ArrayList<String> showItemInfoValues()throws Exception{
        if (selectedItem==null) throw new NoItemSelectedException("showItemInfoValues");
        return selectedItem.getValors();
    }
    /**
     * El Usuario actual quiere ver los Atributos del item seleccionado
     * @return ArrayList<String> con los valores
     * @see Item
     */
    public ArrayList<String> showItemInfoAtributes()throws Exception{
        if (selectedItem==null) throw new NoItemSelectedException("showItemInfoAtributes");
        ArrayList <String> lista=new ArrayList<String>();
        for(Atribute a: selectedItem.getTipus().getAtributes()){
            lista.add(a.getName());
        }
        return lista;
    }
    public String showItemInfoValoration() throws Exception{
        if (selectedItem==null) throw new NoItemSelectedException("showItemInfoAtributes");
        /*for (Item m: itemList.getItems()){
            if(m.getID()==selectedItem.getID()) return String.valueOf(m.getID());
        }*/
        for (valoratedItem va: actualUser.getValoratedItems()){
            if(va.getItem().getID()==selectedItem.getID()) return String.valueOf(va.getValoracio());
        }
        throw new NoItemsException("showItemInfoValoration");
    }

    /**
     * El Usuario actual quiere valorar un item
     * @param idItem idItem es el id del item
     * @param val val es la valoración del item
     * @see Item
     */
    public void rateItem(String idItem, float val) throws Exception{
        if(actualUser==null) throw new NoUserLogedInException("rateItem");
        boolean hay=false;
        for (Item m: itemList.getItems()){
            if (String.valueOf(m.getID()).equals(idItem)){
                hay=true;
            }
        }
        if (!hay) throw new ItemNotExistsException("rateItem,"+idItem);
        hay=false;
        if(actualUser.searchUsedItem(Integer.valueOf(idItem))!=null){
            valoratedItem m=actualUser.searchUsedItem(Integer.valueOf(idItem));
            actualUser.getValoratedItems().remove(m);
            actualUser.addvaloratedItem(Integer.valueOf(idItem), val);
            hay = true;
        }
        if(!hay){
            actualUser.addvaloratedItem(Integer.valueOf(idItem), val);
        }
        recomendationChanged=true;
        recomendationChangedSlope=true;
        recomendationChangedKNN=true;
    }
    /**
     * Se quieren ver todos los items del sistema
     * @return Vector<String> son los id de todos los items
     * @see Item
     */
    public Vector<Integer> AllItems()throws Exception{
        if (itemList.n_Items()==0) throw new NoItemsException("AllItems");
        Vector <Integer> totsItems = new Vector<Integer>();
        for(Item i: itemList.getItems()){
            System.out.println(i.getID());
            totsItems.add(i.getID());
        }
        return totsItems;
    }
    /**
     * Se quieren ver todos los items valorados
     * @return Vector<Integer> son los id de todos los items
     * @see Item
     */
    public ArrayList<Integer> getRatedItems() throws Exception {
        if (actualUser==null) throw new NoUserLogedInException("ShowRatedItems");
        else if(actualUser.getValoratedItems().size()==0) throw new NoRatedItemsException(String.valueOf(actualUser.getUserID()));
        ArrayList<Integer> valorations = new ArrayList<Integer>();
        try {
            for(int i = 0; i < actualUser.getValoratedItems().size(); ++i) {
                valorations.add(actualUser.getValoratedItems().get(i).getItem().getID());
            }
        } catch (Exception e) {
            throw e;
        }
        return valorations;
    }
    /**
     * Se quieren guardar los cambios de los items en el sistema
     */
  public void saveItems() throws Exception{
        //if (actualUser==null) throw new NoUserLogedInException("saveItems");
        try{
            String path = "./SaveData/items.csv";
            ControladorPersistenciaItem ctrlItem= new ControladorPersistenciaItem();
            ctrlItem.Escritor_Items(path, itemList);
        }
        catch (Exception e){
            throw e;
        }
    }

    public void saveRatings() throws Exception{
        //if (actualUser==null) throw new NoUserLogedInException("saveRatings");
        try{
            String path = "./SaveData/ratings.csv";
            ControladorPersistenciaRatings ctrlRating= new ControladorPersistenciaRatings();
            ctrlRating.Escritor_Ratings(path,usersList);
        }
        catch (Exception e){
            throw e;
        }
    }


    public void saveUnkown() throws Exception{
        //if (actualUser==null) throw new NoUserLogedInException("saveRatings");
        try{
            String path = "./SaveData/unknown.csv";
            ControladorPersistenciaRatings ctrlRating = new ControladorPersistenciaRatings();
            ctrlRating.Escritor_Unknown(path,UnKnown);
            System.out.println("llego aqui");
        }
        catch (Exception e){
            throw e;
        }
    }

    public void crearCarpeta(){
        String directoryName = ".";
        File directorio = new File(directoryName+"/SaveData");
        if (!directorio.exists()) {
            if (directorio.mkdirs()) {
                System.out.println("Directorio creado");
            } else {
                System.out.println("Error al crear directorio");
            }
        }
        directorio = new File(directoryName+"/SaveData"+"/Recomendations");
        if (!directorio.exists()) {
            if (directorio.mkdirs()) {
                System.out.println("Directorio creado");
            } else {
                System.out.println("Error al crear directorio");
            }
        }
    }
    /**
     * Se quieren guardar los cambios de las recomendaciones en el sistema
     * @param s es el documento donde se quiere guardar
     */
    public void saveRecomendation(String s ) throws Exception{
        if (actualUser==null) throw new NoUserLogedInException("saveRatings");
        try{

            Path dir = Paths.get("");
            String path = dir.toAbsolutePath().toString()+"/SaveData/Recomendation/";
            ControladorPersistenciaRecomendation ctrlRecomendation= new ControladorPersistenciaRecomendation();
            if(s == "Hybrid")  {
                ArrayList <myPair> nou = new ArrayList<myPair>();
                ArrayList<Item> a = lastRecomendation.get(0);
                for (Item m: a){
                    myPair my = new myPair(m.getID(), 0);
                    nou.add(my);
                }
                ctrlRecomendation.Escritor_Recomendation(path, nou);
            }
            if(s == "CB") {
                ArrayList <myPair> nou = new ArrayList<myPair>();
                ArrayList<Item> a = lastRecomendationKNN.get(0);
                for (Item m: a){
                    myPair my = new myPair(m.getID(), 0);
                    nou.add(my);
                }
                ctrlRecomendation.Escritor_Recomendation(path, nou);
            }
            if(s == "CF") ctrlRecomendation.Escritor_Recomendation(path, lastRecomendationSlope);
        }
        catch (Exception e){
            throw e;
        }
    }
    private Item createItemLocal(String atributs, String valors) throws Exception{
        try {
            recomendationChanged=true;
            recomendationChangedSlope=true;
            recomendationChangedKNN=true;
            return createItemPath(atributs, valors, itemList, itemTypeList);
        }
        catch (Exception e){
            throw e;
        }
    }

    /**
     * El administrador quiere crear un item nuevo
     * @param atributs atributs es el string de atributos
     * @param valors valors es el string de valores
     */
    public Item createItem(String atributs, String valors) throws Exception{
        //if (actualUser==null) throw new NoUserLogedInException("createItem");
        try {

            recomendationChanged=true;
            recomendationChangedSlope=true;
            recomendationChangedKNN=true;
            Item i= createItemPath(atributs, valors, itemList, itemTypeList);
            itemList.anyadir_item(i);
            return i;
        }
        catch (Exception e){
            throw e;
        }
    }
    /**
     * Función privada del algoritmo knn
     */
    private void createItemKNN(String atributs, String valors, Conjunt_Items ListaItems, Map <String, TipusItem> ListaTiposItems) throws Exception{
        if (actualUser==null) throw new NoUserLogedInException("createItemKNN");
        try {
            createItemPath(atributs, valors,ListaItems,ListaTiposItems);
        }
        catch (Exception e){
            throw e;
        }
    }
    /**
     * El administrador quiere crear un item nuevo guardandolo en una lista en concreto
     * @param atributs atributs es el string de atributos
     * @param valors valors es el string de valores
     */
    private Item createItemPath(String atributs, String valors, Conjunt_Items ListaItems, Map <String, TipusItem> ListaTiposItems) throws Exception{
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
        int pos_id=-1;
        for (int i = 0; i <datos.length; ++i) {
            if(datos[i].equals("id")) pos_id=i;
            else{
                Atribute aux = new Atribute(datos[i], "");
                va.add(aux);
                vsa.add(datos[i]);

            }
        }

        //miramos si no existe item
        if (pos_id==-1) throw new IsNotItemException("create item");
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
        else if(ListaTiposItems.size()==0){//no existe
            ti = new TipusItem(va);
            ListaTiposItems.put(ID_ti, ti);
            new_type_item=true;
        }
        else throw new TooManyItemTypeException("create Item");

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
            if ((i.equals("False") || i.equals("True")) && a.getType()!="String" && a.getType()!="Vector de String"){
                a.setTipus("Boolean");
                a.setRellevant(true);
            }
            else if(i.contains(";")){
                a.setTipus("Vector de String");
                a.setRellevant(true);
            }
            else if(i.length()==10 && i.charAt(0)<='9' && i.charAt(0)>='0' && i.charAt(1)<='9' && i.charAt(1)>='0' && i.charAt(2)<='9' && i.charAt(2)>='0'
                    && i.charAt(3)<='9' && i.charAt(3)>='0' && i.charAt(4)=='-' && i.charAt(5)<='9' && i.charAt(5)>='0' && i.charAt(6)<='9' && i.charAt(6)>='0'
                    && i.charAt(7)=='-' && i.charAt(8)>='0' && i.charAt(8)<='9' && i.charAt(9)<='9' && i.charAt(9)>='0'  && a.getType()!="String" && a.getType()!="Vector de String"){
                a.setTipus("Data");
                a.setRellevant(true);
            }
            else if(i.equals("")  && a.getType()!="String" && a.getType()!="Vector de String"){
                if (a.getType().equals("")){
                    a.setTipus("Buit");
                    a.setRellevant(false);
                }
            }
            else {
                for (int p=0;p<i.length();++p){
                    if (!((i.charAt(p)>='0' && i.charAt(p)<='9') || i.charAt(p)=='.')) ranged = false;
                }
                if(ranged  && a.getType()!="String" && a.getType()!="Vector de String"){
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
                            if (a.getUpper()<aux) a.setUpper(aux);
                            if (a.getLower()>aux) a.setLower(aux);
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
                    if( a.getType()!="Vector de String") {
                        a.setTipus("String");
                        a.setRellevant(true);
                    }
                }
            }
        }


        //creamos item
        int id = Integer.valueOf(datos_valors.get(pos_id));
        Item i =new Item(id, ti, vsv);
        i.setAtr(atributs);
        i.setString(valors);
        //i.setDadesInicials(atributs, valors);
        /*if (!(ListaItems.existeix_item(id))){
            ListaItems.anyadir_item(i);
        }*/
        return i;

    }
    /**
     * El administrador quiere borrar un item
     * @param deleteme deleteme es el id del item que se quiere eliminar
     */
    public void deleteItem(String deleteme) throws Exception{
        if (!actualUser.getRol().equals(TipusRol.Administrador)) throw new NotAnAdministratorException(String.valueOf(actualUser.getUserID()));
        else if (!itemList.existeix_item(Integer.parseInt(deleteme))) throw new ItemNotExistsException(deleteme);
        Item it = itemList.get_item(Integer.parseInt(deleteme));
        for (Item m: itemList.getItems()){
            if(String.valueOf(m.getID()).equals(deleteme)) it=m;
        }
        System.out.println(itemList.existeix_item(it.getID()));
        //borrar de cada usuario el item si lo ha valorado
        for (int i: usersList.keySet()){
            User u = usersList.get(i);
            ArrayList <valoratedItem> valorated = u.getValoratedItems();
            valorated.remove(it);
        }
        //borrar de el vector de itemValoratedBy
        itemValoratedBy.remove(it.getID());
        //borrar de itemList

        System.out.println(itemList.eliminar_item(it));
        //si es item selected==null
        recomendationChanged=true;
        recomendationChangedSlope=true;
        recomendationChangedKNN=true;
        if (selectedItem==it) selectedItem=null;
    }
    /**
     * El administrador quiere cargar items
     * @param path path es la direccion al fichero que se quiere cargar
     */
    public void loadItems(String path) throws Exception{//"Entradas_CSV/items.csv"
        //pre: actualUser es admin
        try {
            //if (actualUser == null) throw new NoUserLogedInException("loadItems");

            Vector<String> mat_items = new Vector<String>();
            ControladorPersistenciaItem reader = new ControladorPersistenciaItem();
            mat_items = reader.Lector_Items(path);
            ArrayList <Item> aux = new ArrayList<Item>();
            for (int i = 1; i < mat_items.size(); ++i) {
                aux.add(dominiSingelton.createItemLocal(mat_items.get(0), mat_items.get(i)));
            }
            itemList.setItems(aux);
            selectedItem = itemList.getItems().get(0);
            recomendationChanged=true;
            recomendationChangedSlope=true;
            recomendationChangedKNN=true;
        }
        catch (Exception e){
            //JOptionPane.showMessageDialog(null,"El fichero introducido no tiene el formato válido");
            throw e;
        }
    }
    /**
     * El administrador quiere cargar valoraciones
     * @param path path es la direccion al fichero que se quiere cargar
     */
    public void loadRates(String path) throws Exception{//falta añadir item usado a la lista de items usados
        //pre: actualUser es admin
        try {
            //if (actualUser==null) throw new NoUserLogedInException("loadRates");
            //if(!actualUser.getRol().equals(TipusRol.Administrador))

            ratingPath = path;
            recomendationChanged=true;
            recomendationChangedSlope=true;
            recomendationChangedKNN=true;
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
        catch (Exception e){
            throw e;
        }
    }
    /**
     * El administrador quiere cargar una recomendación
     * @param path path es la direccion al fichero que se quiere cargar
     */
    public ArrayList loadRecomendation(String s, String path) throws Exception{
        if (actualUser==null) throw new NoUserLogedInException("loadRecomendation");
        try {
            recomendationChanged = true;
            recomendationChangedSlope=true;
            recomendationChangedKNN=true;
            ControladorPersistenciaRecomendation reader = new ControladorPersistenciaRecomendation();
            ArrayList <String> leido = reader.Lector_Recomendation(path);

            ArrayList <ArrayList> creado = new ArrayList<ArrayList>();
            ArrayList<Integer> i_Aux = new ArrayList<>();
            ArrayList <Double> v_Aux = new ArrayList<Double>();

            for (String linea: leido){
                boolean coma=false;
                String item="";
                String val="";
                for(int i=0;i<linea.length();++i){
                    String letra=String.valueOf(linea.charAt(i));
                    if (!coma && letra.equals(",")){
                        coma=true;
                        i++;
                    }
                    else{
                        if (!coma){
                            item+=letra;
                        }
                        else{
                            val+=letra;
                        }
                    }
                }
                int Item = Integer.valueOf(item);
                Double Value = Double.valueOf(val);
                i_Aux.add(Item);
                v_Aux.add(Value);
            }
            creado.add(i_Aux);
            creado.add(v_Aux);
            if(s == "Hybrid") {
                lastRecomendation = creado;
                return lastRecomendation;
            }
            if(s == "CB") {
                lastRecomendationKNN = creado;
                return lastRecomendationKNN;
            }
            if(s == "CF") {
                lastRecomendationSlope = creado.get(0);
                return lastRecomendationSlope;
            }
        }
        catch (Exception e){
            throw e;
        }
    return null;
    }

    /**
     * El administrador quiere eliminar un usuario
     * @param delete_me delete_me es el usuario  que se quiere eliminar
     */
    public void deleteUser(String delete_me) throws Exception{
        if(actualUser==null || delete_me.equals("-1")) throw new ImpossibleStateException("deleteUser");
        boolean trobat=false;
        User delete=usersList.get(Integer.valueOf(delete_me));
        if (delete==null) throw new UserNotExistsException("deleteUser");
        //eliminar item valorated by usuari
        for (valoratedItem v: delete.getValoratedItems()){
            Item m=v.getItem();
            ArrayList <User> usuaris = itemValoratedBy.get(m.getID());
            usuaris.remove(delete);
        }
        usersList.remove(delete.getUserID());
/*
            for (ItemUsat i: ratesList){
                if ( delete_me.equals(StringValueOf(i.getUsuari().getUserID())) ratesList.delete(i);
            }
            usersList.delete(delete_me);
*/
    }

    /**
     * El administrador quiere crear un usuario
     * @param create_me create_me es el usuario y contraseña que se le asignan
     */
    public void createUser( String create_me) throws Exception{
        if(actualUser.getRol().equals(TipusRol.Administrador) ){
            if (!usersList.containsKey(create_me)){
                User nou = new User(Integer.valueOf(create_me));
                usersList.put(Integer.valueOf(create_me), nou);
                recomendationChanged=true;
                recomendationChangedSlope=true;
                recomendationChangedKNN=true;
            }

        }
        else throw new NotAnAdministratorException("createUser");
    }
    /**
     * Se quiere ver todos los usuarios guardados en el sistema
     * @return Vector <String> con el id de todos los usuarios
     */
    public Vector <String> showAllUsers( )throws Exception{
        if (usersList.size()==0) throw new NoUsersException("showAllUsers");
        Vector <String> vs = new Vector<String>();
        for (User u: usersList.values()){
            System.out.println(u.getUserID());
            vs.add(String.valueOf(u.getUserID()));
        }
        return vs;
    }
    /**
     * Se devuelve el usuario actual
     * @return User
     */
    public User getActualUser() {
        return actualUser;
    }
    /**
     * Se devuelve el id del usuario actual
     * @return Integer
     */
    public Integer getActualUserID() {
        return actualUser.getUserID();
    }
    /**
     * Se devuelve el tipo del usuario actual
     * @return User
     */
    public String getTypeActualUser() {
        if(actualUser.getRol() == TipusRol.Administrador) return "admin";
        else return "user";
    }

    public boolean itemsLoaded(){
        if (itemList.n_Items()==0) return false;
        return true;
    }

    public boolean usersLoaded(){
        if (usersList.size()==0) return false;
        return true;
    }

    public boolean unknownLoaded(){
        if (UnKnown.size()==0) return false;
        return true;
    }

    public void loadUnKnown(String path) throws Exception{
        try {
            ControladorPersistenciaRatings ctrRec = new ControladorPersistenciaRatings();
            UnKnown = ctrRec.Lector_Ratings(path);
        }
        catch (Exception e) {
            throw e;
        }
    }
    public void deleteAll(){
        usersList = new HashMap<Integer, User>();
        selectedItem = null;
        itemList = new Conjunt_Items();
        itemTypeList = new HashMap<String, TipusItem>();
        itemValoratedBy = new HashMap<Integer,ArrayList<User>>();
        lastRecomendation = new ArrayList<ArrayList>();
        lastRecomendationSlope = new ArrayList<myPair>();
        lastRecomendationKNN = new ArrayList<ArrayList>();
        recomendationChanged=true;
        recomendationChangedSlope=true;
        recomendationChangedKNN=true;
        UnKnown = new ArrayList <Vector<String>>();
    }


    /*
     * Se devuelve el un map con todos los usuarios del sistema
     * @return Map<Integer,User> donde el integer es el usuario
     */
    public Map<Integer,User> getUsersList() { return usersList; }

    public Integer itemFavourite() throws Exception{
        int maxID = 0;
        if(actualUser == null) throw new NoUserLogedInException("loadRecomendation");
        else {
            ArrayList<valoratedItem> listaValorados = actualUser.getValoratedItems();
            float maxValue = 0;
            for(valoratedItem v : listaValorados) {
                if(maxValue < v.getValoracio()) {
                    maxValue = v.getValoracio();
                    maxID = v.getItem().getID();
                }
            }
        }
        return maxID;
    }
    public Integer avgRating() throws Exception{
        int avg = 0;
        if(actualUser == null) throw new NoUserLogedInException("loadRecomendation");
        else {
            ArrayList<valoratedItem> listaValorados = actualUser.getValoratedItems();
            for(valoratedItem v : listaValorados) {
                avg += v.getValoracio();
            }
        }
        return avg/numRated();
    }

    public Integer numRated() throws Exception{
        if(actualUser == null) throw new NoUserLogedInException("loadRecomendation");
        else {
            return actualUser.getValoratedItems().size();
        }
    }

    public void aux(){
        for (Item m: itemList.getItems()){
            System.out.println(m.getString());
        }
    }

    public void enseñaItemTypeAtr(){
        for (String s: itemTypeList.keySet()){
            for (Atribute a: itemTypeList.get(s).getAtributes()){
                System.out.println(a.getName()+":  "+a.getType()+" | ");
                if (a.getType().equals("Rang")){
                    System.out.println(a.getLower()+" | "+a.getUpper());
                }
            }
        }
    }
}
