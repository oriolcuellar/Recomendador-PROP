package FONTS.src.domini.controladors;

import FONTS.src.domini.model.Item;
import FONTS.src.presentacion.*;
import javax.swing.*;
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

    public static void changeShowRecomendedItemsView(int x, int y,String s) {
        ShowRecomendedItems showRecomendedItems = new ShowRecomendedItems(s);
        showRecomendedItems.showWindow(x,y);
    }

    public static void changeShowRatedItemsView(int x, int y) {
        ShowRatedItems showRatedItems = new ShowRatedItems();
        showRatedItems.showWindow(x,y);
    }

    public static void changeShowAtributesView(int x, int y, String id)  {
        showAtributes showAtributes = new showAtributes(id);
        showAtributes.showWindow(x,y);
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
     * @see Item
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

    public void loadItems(String path) {
       try {
           CtrlDom.loadItems(path);
       } catch (Exception e) {
           System.out.println(e);
       }
    }

    public void loadRates(String path) {
        try {
            CtrlDom.loadRates(path);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void loadUnKnown(String path) {
        try {
            CtrlDom.loadUnKnown(path);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

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

    public void login(String username, String password) {
        try {
            CtrlDom.login(username,password);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public void singUp(String username, String password) {
        try {
            CtrlDom.register(username,password);
        } catch (Exception e) {
            System.out.println(e);
            CtrlDom.getActualUser().getUserID();
        }
    }

    public boolean isAdmin() {
        return CtrlDom.getTypeActualUser() == "admin";
    }

    public void logout() {
        try {
            CtrlDom.logout();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public int getUsersSize() {
        int d = 0;
        try {
            d = CtrlDom.getUsersList().size();
        } catch (Exception e) {
            System.out.println(e);
        }
        return d;
    }

    public int itemFavourite() {
        int d = 0;
        try {
            d = CtrlDom.itemFavourite();
        } catch (Exception e) {
            System.out.println(e);
        }
        return d;
    }

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
            CtrlDom.saveRatings("./EXE/Data/ratings.csv");
            CtrlDom.saveItems("./EXE/Data/items.csv");
        } catch(Exception e) {
            JOptionPane.showMessageDialog(null, e + "The item does not exist","Error ", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void deleteUser(String id) {
        try {
            if(id != "-1") CtrlDom.deleteUser(id);
        } catch(Exception e) {
            JOptionPane.showMessageDialog(null, "The user does not exist","Error ", JOptionPane.ERROR_MESSAGE);
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
