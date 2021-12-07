package FONTS.src.domini.controladors;

import FONTS.src.presentacion.*;

import javax.swing.*;
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

    public static Vector<String> getAllItems(){
        Vector<String> s = new Vector<>();
        try {
            s = CtrlDom.showAllItems();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"No items Loaded");
        }
        return s;
    }
}
