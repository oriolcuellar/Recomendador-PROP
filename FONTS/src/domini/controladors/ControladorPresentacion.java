package FONTS.src.domini.controladors;

import FONTS.src.domini.model.myPair;
import FONTS.src.domini.model.valoratedItem;
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

    public static void changeShowRecomendedItemsView(int x, int y) {
        ShowRecomendedItems showRecomendedItems = new ShowRecomendedItems();
        showRecomendedItems.showWindow(x,y);
    }

    public static void changeShowRatedItemsView(int x, int y) {
        ShowRatedItems showRatedItems = new ShowRatedItems();
        showRatedItems.showWindow(x,y);
    }

    public static void changeShowAtributesView(int x, int y, int id)  {
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

    public static void changeProfileView(int x, int y){
        ProfileView profileView = new ProfileView();
        profileView.showWindow(x,y);
    }

    public static void changeStatsView(int x, int y){
        StatsView statsView = new StatsView();
        statsView.showWindow(x,y);
    }

    public static void changeLoadingView(int x, int y)  {
        uploadItems.showWindow(x,y);
    }



    public static Vector<String> getAllItems(){
        Vector<String> s = new Vector<>();
        try {
            s = CtrlDom.showAllItems();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,e + "\nNo items Loaded");
            System.out.println(s.get(0));
        }
        return s;
    }

    public static ArrayList<valoratedItem> getRatedItems(){
        ArrayList<valoratedItem> s = new ArrayList<valoratedItem>();
        try {
            s = CtrlDom.getActualUser().getValoratedItems();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"No items Rated");
            System.out.println(s.get(0));
        }
        return s;
    }

    public static ArrayList<myPair> getRecomendedItemsSlope(){
        ArrayList<myPair> s = new ArrayList<>();
        try {
            s = CtrlDom.doSlope(6,10);
        } catch (Exception e) {
            principalView.enableButtons();
            JOptionPane.showMessageDialog(null,"No items Recomended");
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

    public void login(String username, String password) {
        try {
            CtrlDom.login(username,password);
        } catch (Exception e) {
            System.out.println(e);
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

    public void setInvisible() {
        uploadItems.setInvisible();
    }
}
