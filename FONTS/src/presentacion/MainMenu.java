package FONTS.src.presentacion;

import FONTS.src.domini.controladors.ControladorDomini;
import FONTS.src.domini.controladors.ControladorPresentacion;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenu {

    ControladorPresentacion CtrlPres = ControladorPresentacion.getInstance();
    private static JFrame frame;
    private JPanel panel;
    private JButton settingsButton;
    private JButton showRatedItemsButton;
    private JButton showAllItemsButton;
    private JButton showRecomendedItemsByCFButton;
    private JLabel Label;
    private JButton ShowRecomendedItemsByHybridButton;
    private JButton showRecomendedItemsByCBButton;

    public MainMenu() {


        settingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ControladorDomini dom = ControladorDomini.getInstance();
                try {
                    disableButtons();
                    int x = frame.getX();
                    int y = frame.getY();
                    CtrlPres.changeProfileView(x,y);
                    frame.dispose();
                    settingsButton.setEnabled(true);
                } catch (Exception e) {
                    enableButtons();
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
        showRecomendedItemsByCFButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    disableButtons();
                    int input = JOptionPane.showConfirmDialog(null, "Do you want to load the recomendation?", "Choose",
                            JOptionPane.OK_CANCEL_OPTION);
                    String numRec = JOptionPane.showInputDialog(null, "Enter the number of items to be recomended", 50);
                    int n = Integer.parseInt(numRec);
                    boolean b = input != 0;
                    int x = frame.getX();
                    int y = frame.getY();
                    CtrlPres.changeShowRecomendedItemsView(x,y,"CF", b, n);
                    frame.dispose();
                    showRatedItemsButton.setEnabled(true);
                } catch (Exception e) {
                    enableButtons();
                    JOptionPane.showMessageDialog(null, "You have not save any recomendation");
                    e.printStackTrace();
                }
            }
        });
        showRecomendedItemsByCBButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    disableButtons();
                    int x = frame.getX();
                    int y = frame.getY();
                    CtrlPres.changeShowRecomendedItemsView(x,y,"CB", true, 50);
                    frame.dispose();
                    showRatedItemsButton.setEnabled(true);
                } catch (Exception e) {
                    enableButtons();
                    e.printStackTrace();
                }
            }
        });

        ShowRecomendedItemsByHybridButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    disableButtons();
                    int x = frame.getX();
                    int y = frame.getY();
                    CtrlPres.changeShowRecomendedItemsView(x,y,"Hybrid", true, 50);
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
        showRecomendedItemsByCFButton.setEnabled(false);
        settingsButton.setEnabled(false);
        showAllItemsButton.setEnabled(false);
        showRatedItemsButton.setEnabled(false);
        showRecomendedItemsByCBButton.setEnabled(false);
        ShowRecomendedItemsByHybridButton.setEnabled(false);
    }

    public void enableButtons() {
        settingsButton.setEnabled(true);
        showRatedItemsButton.setEnabled(true);
        showAllItemsButton.setEnabled(true);
        showRecomendedItemsByCFButton.setEnabled(true);
        showRecomendedItemsByCBButton.setEnabled(true);
        ShowRecomendedItemsByHybridButton.setEnabled(true);
    }
}
