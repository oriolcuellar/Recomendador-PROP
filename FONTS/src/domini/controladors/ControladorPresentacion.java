package FONTS.src.domini.controladors;

import FONTS.src.domini.model.Item;
import FONTS.src.presentacion.*;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Vector;

public class ControladorPresentacion {

    private static ControladorPresentacion CtrlPres;
    private static ControladorDomini CtrlDom = ControladorDomini.getInstance();
    static MainMenu principalView = new MainMenu();
    static UploadItems uploadItems = new UploadItems();
    private ControladorPresentacion() {
    }

    public static ControladorPresentacion getInstance() {
        if(CtrlPres == null) CtrlPres = new ControladorPresentacion();
        return CtrlPres;
    }

    public static void inicializePresentation(int x, int y) {
        principalView.showWindow(x,y);
    }

    public static void changeShowAllItemsView(int x, int y) {
        ShowAllItems showAllItems = new ShowAllItems();
        showAllItems.showWindow(x,y);
    }

    public static void changeShowRecomendedItemsView(int x, int y,String s, boolean b, int n) {
        ShowRecomendedItems showRecomendedItems = new ShowRecomendedItems(s, b, n);
        showRecomendedItems.showWindow(x,y);
    }

    public static void changeShowRatedItemsView(int x, int y) {
        ShowRatedItems showRatedItems = new ShowRatedItems();
        showRatedItems.showWindow(x,y);
    }

    public static void changeShowAtributesView(int x, int y, String id)  {
        try {
            showAtributes showAtributes = new showAtributes(id);
            showAtributes.showWindow(x,y);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void changeAdminMainView(int x, int y){
        AdminMainPage adminMainPage = new AdminMainPage();
        adminMainPage.showWindow(x,y);
    }

    public static void changeLogInView(int x, int y){
        LoginView loginView = new LoginView();
        loginView.showWindow(x,y);
    }
    /**
     * Función para canviar a la vista del Perfil
     * @see ProfileView
     */
    public static void changeProfileView(int x, int y){
        ProfileView profileView = new ProfileView();
        profileView.showWindow(x,y);
    }

    public static void changeStatsView(int x, int y){
        StatsView statsView = new StatsView();
        statsView.showWindow(x,y);
    }

    public static void changeSignUpView(int x, int y){
        SignUp signUp = new SignUp();
        signUp.showWindow(x,y);
    }

    public static void changeLoadingView(int x, int y)  {
        uploadItems.showWindow(x,y);
    }


    /**
     * Función obtener todos los items que hay cargados
     * @return Vector<Integer> son los id de todos los items
     */
    public static Vector<Integer> getAllItems(){
        Vector<Integer> s = new Vector<>();
        try {
            s = CtrlDom.AllItems();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,e + "\nNo items Loaded", "Error ", JOptionPane.ERROR_MESSAGE);
            System.out.println(s.get(0));
        }
        return s;
    }
    /**
     * Función obtener todos los items valorados por el usuario actual
     * @return ArrayList<Integer> son los id de todos los items valorados por el usuario actual
     */
    public static ArrayList<Integer> getRatedItems(){
        ArrayList<Integer> s = new ArrayList<>();
        try {
            s = CtrlDom.getRatedItems();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"No items Rated");
            System.out.println(s.get(0));
            JOptionPane.showMessageDialog(null,"No items Rated", "Error ", JOptionPane.ERROR_MESSAGE);
        }
        return s;
    }
    /**
     * Función obtener una recomendacion de items mediante el algoritmo de collaborative filtering
     * @return ArrayList<Integer> son los id de todos los items que se le van a recomendar al usuario ordenados de mejor a peor
     */
    public static ArrayList<Integer> getRecomendedItemsSlope(){
        ArrayList<Integer> s = new ArrayList<>();
        try {
            s = CtrlDom.doSlope(4,10);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"No items Recomended", "Error ", JOptionPane.ERROR_MESSAGE);
            System.out.println(s.get(0));
        }
        return s;
    }
    /**
     * Función obtener una recomendacion de items mediante el algoritmo de content based
     * @return ArrayList<Integer> son los id de todos los items que se le van a recomendar al usuario ordenados de mejor a peor
     */
    public static ArrayList<Integer> getRecomendedItemsCB(){
        ArrayList<Integer> s = new ArrayList<>();
        try {
            s = CtrlDom.doKNN();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"No items Recomended", "Error ", JOptionPane.ERROR_MESSAGE);
            System.out.println(s.get(0));
        }
        return s;
    }
    /**
     * Función obtener una recomendacion de items mediante el algoritmo hibrido
     * @return ArrayList<Integer> son los id de todos los items que se le van a recomendar al usuario ordenados de mejor a peor
     */
    public static ArrayList<Integer> getRecomendedItemsHybrid(){
        ArrayList<Integer> s = new ArrayList<>();
        try {
            s = CtrlDom.doRecomendation(4,10);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"No items Recomended", "Error ", JOptionPane.ERROR_MESSAGE);
            System.out.println(s.get(0));
        }
        return s;
    }
    /**
     * Carga un conjunto de items al sistema
     * @param path es el documento de donde se quiere leer
     */
    public void loadItems(String path) {
       try {
           CtrlDom.loadItems(path);
       } catch (Exception e) {
           System.out.println(e);
       }
    }
    /**
     * Carga un conjunto de usuarios y las valoraciones que le han dado a los items
     * @param path es el documento de donde se quiere leer
     */
    public void loadRates(String path) {
        try {
            CtrlDom.loadRates(path);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    /**
     * Carga un conjunto de usuarios con valoraciones ficticias que sirve para poder evaluar recomendaciones
     * @param path es el documento de donde se quiere leer
     */
    public void loadUnKnown(String path) {
        try {
            CtrlDom.loadUnKnown(path);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    /**
     * Carga la recomendacion del usuario actual
     * @param s es el tipo de algoritmo de recomendacion ("Hybrid", "CF", "CB")
     */
    public ArrayList loadRecomendation(String s) {
        try {
            Path path = Paths.get("");
            String directoryName = path.toAbsolutePath().toString();
            directoryName += "/SavedData";
            return CtrlDom.loadRecomendation(s,"./SavedData/Recomendations/recomentation" + s + CtrlDom.getActualUserID() + ".csv");
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }
    /**
     * Evalua la recomendacion que se le ha dado al usuario actual
     * @param s es el tipo de algoritmo de recomendacion ("Hybrid", "CF", "CB")
     */
    public void evaluateRecomendation(String s) {
        try {
            float f = 0;
            if(s == "Hybrid") f = CtrlDom.evaluateRecomendationGeneral();
            else if(s == "CB") f = CtrlDom.evaluateRecomendationKNN();
            else if(s == "CF") f = CtrlDom.evaluateRecomendationSlope();
            JOptionPane.showMessageDialog(null,f);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"It's impossible to evalute this recomendation","Error ", JOptionPane.ERROR_MESSAGE);
            System.out.println(e);
        }
    }
    /**
     * Se inicia la session un usuario
     * @param username id del usuario que quiere iniciar sesion
     * @param password contraseña del usuario que quiere iniciar sesion
     */
    public void login(String username, String password) {
        try {
            CtrlDom.login(username,password);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    /**
     * Secrea la cuenta de un usuario
     * @param username id del usuario que quiere crearse la cuenta
     * @param password contraseña del usuario que quiere crearse la cuenta
     */
    public void singUp(String username, String password) {
        try {
            CtrlDom.register(username,password);
        } catch (Exception e) {
            System.out.println(e);
            CtrlDom.getActualUser().getUserID();
        }
    }
    /**
     * Se modifica la contraseña del usuario que esta actualmente en el sistema
     * @param password contraseña nueva que ha introducido el usuario
     */
    public void editProfile(String password) {
        try {
            CtrlDom.editProfile(password);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    /**
     * Indica si el usuario que ha iniciado sesion es admin o no
     * @return devuelve cierto si el usuario es admin
     */
    public boolean isAdmin() {
        return CtrlDom.getTypeActualUser() == "admin";
    }
    /**
     * Se cierra la sesion del usuario actual
     */
    public void logout() {
        try {
            CtrlDom.logout();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Funcion para saber el tamaño de usuarios que hay cargados en el sistema
     * @return numero de usuarios cargados en el sistema
     */
    public int getUsersSize() {
        int d = 0;
        try {
            d = CtrlDom.getUsersList().size();
        } catch (Exception e) {
            System.out.println(e);
        }
        return d;
    }
    /**
     * Funcion para saber el item favorito del usuario actual
     * @return devuelve el item favorito del usuario actual
     */
    public int itemFavourite() {
        int d = 0;
        try {
            d = CtrlDom.itemFavourite();
        } catch (Exception e) {
            System.out.println(e);
        }
        return d;
    }
    /**
     * Funcion para saber la media de valoraciones del usuario actual
     * @return devuelve el item favorito del usuario actual
     */
    public int avgRating() {
        int d = 0;
        try {
            d = CtrlDom.avgRating();
        } catch (Exception e) {
            System.out.println(e);
        }
        return d;
    }

    public int numRated() {
        int d = 0;
        try {
            d = CtrlDom.numRated();
        } catch (Exception e) {
            System.out.println(e);
        }
        return d;
    }

    public Integer getActualUserId() {
        try {
            return CtrlDom.getActualUserID();
        } catch (Exception e) {
            System.out.println(e);
        }
        return 0;
    }

    public void setInvisible() {
        uploadItems.setInvisible();
    }

    public String getValoration(String id) {
        String s;
        try {

            s =  CtrlDom.showItemInfoValoration();
        } catch (Exception e) {
            s = "No té valoració";
        }
        return s;
    }

    public boolean itemsLoaded() {
        return CtrlDom.itemsLoaded();
    }

    public boolean usersLoaded() {
        return CtrlDom.usersLoaded();
    }

    public boolean unknownLoaded() {
        return CtrlDom.unknownLoaded();
    }

    public void deleteAllData() {
         CtrlDom.deleteAll();
    }

    public void deleteItem(String id) {
        try {
            CtrlDom.deleteItem(id);
        } catch(Exception e) {
            JOptionPane.showMessageDialog(null, e + "The item does not exist","Error ", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void saveAll() {
        try {
            CtrlDom.saveRatings();
            CtrlDom.saveItems();
            CtrlDom.saveUnkown();
        } catch(Exception e) {
            JOptionPane.showMessageDialog(null, e + "The item does not exist","Error ", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void saveRecomendation(String s) {
        try {
            CtrlDom.saveRecomendation(s);
        } catch(Exception e) {
            JOptionPane.showMessageDialog(null, e,"Error ", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void createItem(String atributes, String valors) {
        try {
            CtrlDom.createItem(atributes,valors);
        } catch(Exception e) {
            JOptionPane.showMessageDialog(null, e,"Error ", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void deleteUser(String id) {
        try {
            CtrlDom.deleteUser(id);
        } catch(Exception e) {
            JOptionPane.showMessageDialog(null, e + "The user does not exist","Error ", JOptionPane.ERROR_MESSAGE);
        }
    }

    public ArrayList<String> getAtributos() {

        ArrayList<String> a = new ArrayList<String> ();
        try {

            a =  CtrlDom.showItemInfoAtributes();
        } catch (Exception e) {
            System.out.println(e);
        }
        return a;
    }

    public ArrayList<String> getValors() {

        ArrayList<String> a = new ArrayList<String> ();
        try {
            a =  CtrlDom.showItemInfoValues();
        } catch (Exception e) {
            System.out.println(e);
        }
        return a;
    }

    public void selectItem(String id) {
        try {
            CtrlDom.selectItem(id);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void SetValoration(String id, String nv) {

        float new_valoration = Float.valueOf(nv);
        try {
            CtrlDom.rateItem(id, new_valoration);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"No s'ha pogut cambiar la valoració","Error ", JOptionPane.ERROR_MESSAGE);
        }
    }
}
