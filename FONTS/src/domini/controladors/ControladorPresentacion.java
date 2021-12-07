package FONTS.src.domini.controladors;

import FONTS.src.presentacion.*;

import java.util.Vector;

public class ControladorPresentacion {

    private static ControladorPresentacion CtrlPres;
    private static ControladorDomini CtrlDom = ControladorDomini.getInstance();
    private ControladorPresentacion() {
    }

    public static ControladorPresentacion getInstance() {
        if(CtrlPres == null) CtrlPres = new ControladorPresentacion();
        return CtrlPres;
    }

    public static void inicializePresentation() {
        MainMenu principalView = new MainMenu();
        principalView.showWindow();
    }

    public static void changeShowAllItemsView() {
        ShowAllItems showAllItems = new ShowAllItems();
        showAllItems.showWindow();
    }

    public static Vector<String> getAllItems() {
        return CtrlDom.showAllItems();
    }
}
