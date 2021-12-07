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
        showRatedItemsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JOptionPane.showMessageDialog(null,"Show Rated Items Pushed");
            }
        });
        showRecomendedItemsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ControladorDomini dom = ControladorDomini.getInstance();
                try {
                    dom.login("-1", "-1");
                    dom.loadItems("EXE/Entradas_CSV/items.csv");
                    dom.loadRates("EXE/Entradas_CSV/ratings.db.csv");
                    dom.logout();
                    dom.login("12", "12");
                    dom.showRecommendedItemsSlope(6, 10);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        showAllItemsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                CtrlPres.changeShowAllItemsView();
            }
        });
    }
    public void showWindow() {
        frame = new JFrame("Sistema Recomanador");
        frame.setContentPane(new MainMenu().panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setMinimumSize(new Dimension(600, 600));
        frame.setResizable(false);
        frame.setVisible(true);
    }

    public void setInvisible() {
        frame.setVisible(false);
    }
}
