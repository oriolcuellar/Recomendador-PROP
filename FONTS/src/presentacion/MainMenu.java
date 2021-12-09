package FONTS.src.presentacion;

import FONTS.src.domini.controladors.ControladorDomini;
import FONTS.src.domini.controladors.ControladorPresentacion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenu {

    ControladorPresentacion CtrlPres = ControladorPresentacion.getInstance();
    private static JFrame frame;
    private JPanel panel;
    private JButton settingsButton;
    private JButton showRatedItemsButton;
    private JButton showAllItemsButton;
    private JButton showRecomendedItemsButton;

    public MainMenu() {


        settingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ControladorDomini dom = ControladorDomini.getInstance();
                try {
                    disableButtons();
                    dom.login("-1", "-1");
                    dom.loadItems("EXE/Entradas_CSV/items.csv");
                    dom.loadRates("EXE/Entradas_CSV/ratings.db.csv");
                    dom.logout();
                    dom.login("12", "12");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                enableButtons();
            }
        });
        showRatedItemsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    disableButtons();
                    int x = frame.getX();
                    int y = frame.getY();
                    CtrlPres.changeShowRatedItemsView(x,y);
                    frame.dispose();
                    showRatedItemsButton.setEnabled(true);
                } catch (Exception e) {
                    enableButtons();
                    e.printStackTrace();
                }
            }
        });
        showRecomendedItemsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    disableButtons();
                    int x = frame.getX();
                    int y = frame.getY();
                    CtrlPres.changeShowRecomendedItemsView(x,y);
                    frame.dispose();
                    showRatedItemsButton.setEnabled(true);
                } catch (Exception e) {
                    enableButtons();
                    e.printStackTrace();
                }
            }
        });
        showAllItemsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    disableButtons();
                    int x = frame.getX();
                    int y = frame.getY();
                    CtrlPres.changeShowAllItemsView(x,y);
                    frame.dispose();
                    showRatedItemsButton.setEnabled(true);
                } catch (Exception e) {
                    enableButtons();
                    e.printStackTrace();
                }
            }
        });
    }
    public void showWindow(int x, int y) {
        enableButtons();
        frame = new JFrame("Sistema Recomanador");
        frame.setContentPane(this.panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setBounds(x,y,600,600);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    public void disableButtons() {
        showRecomendedItemsButton.setEnabled(false);
        settingsButton.setEnabled(false);
        showAllItemsButton.setEnabled(false);
        showRatedItemsButton.setEnabled(false);
    }

    public void enableButtons() {
        settingsButton.setEnabled(true);
        showRatedItemsButton.setEnabled(true);
        showAllItemsButton.setEnabled(true);
        showRecomendedItemsButton.setEnabled(true);
    }
}
