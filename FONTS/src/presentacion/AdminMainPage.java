package FONTS.src.presentacion;

import FONTS.src.domini.controladors.ControladorDomini;
import FONTS.src.domini.controladors.ControladorPresentacion;

import javax.swing.*;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminMainPage {
    ControladorPresentacion CtrlPres = ControladorPresentacion.getInstance();
    private static JFrame frame;
    private JPanel panel;
    private JButton settingsButton;
    private JButton deleteItemButton;
    private JButton deleteUserButton;
    private JButton uploadUsersRatingsButton;
    private JButton uploadItemsButton;
    private JButton uploadRateValorationButton;
    private JButton DeleteButton;
    private JFileChooser fileChooser = new JFileChooser();
    private boolean items;
    private boolean ratings;
    private boolean unkown;


    public AdminMainPage(){
        items = CtrlPres.itemsLoaded();
        ratings = CtrlPres.usersLoaded();
        unkown = CtrlPres.unknownLoaded();

        uploadItemsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    disableButtons();
                    fileChooser.showOpenDialog(fileChooser);
                    String path = fileChooser.getSelectedFile().getAbsolutePath();
                    CtrlPres.loadItems(path);
                    items = CtrlPres.itemsLoaded();
                } catch (Exception e) {
                    enableButtons();
                    items = CtrlPres.itemsLoaded();
                    JOptionPane.showMessageDialog(null,"The chosen file does not have the correct format");
                    e.printStackTrace();
                }
                enableButtons();
            }
        });
        uploadUsersRatingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    disableButtons();
                    if(items) {
                        fileChooser.showOpenDialog(fileChooser);
                        String path = fileChooser.getSelectedFile().getAbsolutePath();
                        CtrlPres.loadRates(path);
                        ratings = CtrlPres.usersLoaded();
                    } else JOptionPane.showMessageDialog(null,"You have to load items before doing anything else");

                } catch (Exception e) {
                    enableButtons();
                    ratings = CtrlPres.usersLoaded();
                    JOptionPane.showMessageDialog(null,"The chosen file does not have the correct format");
                    e.printStackTrace();
                }
                enableButtons();
            }
        });

        uploadRateValorationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    disableButtons();
                    fileChooser.showOpenDialog(fileChooser);
                    String path = fileChooser.getSelectedFile().getAbsolutePath();
                    CtrlPres.loadUnKnown(path);
                    unkown = CtrlPres.unknownLoaded();
                } catch (Exception e) {
                    enableButtons();
                    unkown = CtrlPres.unknownLoaded();
                    JOptionPane.showMessageDialog(null,"The chosen file does not have the correct format");
                    e.printStackTrace();
                }
                enableButtons();
            }
        });

        settingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int x = frame.getX();
                int y = frame.getY();
                if(items && ratings && unkown) {
                    CtrlPres.changeProfileView(x, y);
                    frame.dispose();
                } else if(items) JOptionPane.showMessageDialog(null,"You have to load ratings before doing anything else");
                else JOptionPane.showMessageDialog(null,"You have to load items and ratings before doing anything else");
            }
        });

        DeleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    disableButtons();
                    CtrlPres.deleteAllData();
                    items = CtrlPres.itemsLoaded();
                    ratings = CtrlPres.usersLoaded();
                    unkown = CtrlPres.unknownLoaded();
                } catch (Exception e) {
                    enableButtons();
                    JOptionPane.showMessageDialog(null,"The chosen file does not have the correct format");
                    e.printStackTrace();
                }
                enableButtons();
            }
        });

        deleteItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    disableButtons();
                    String itemDeleted = JOptionPane.showInputDialog(null,
                            "Write the id of the item to delete", null);
                    CtrlPres.deleteItem(itemDeleted);
                    items = CtrlPres.itemsLoaded();
                } catch (Exception e) {
                    enableButtons();
                    JOptionPane.showMessageDialog(null,"The chosen file does not have the correct format");
                    e.printStackTrace();
                }
                enableButtons();
            }
        });

        deleteUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    disableButtons();
                    String userDeleted = JOptionPane.showInputDialog(null,
                            "Write the id of the user to delete", null);
                    CtrlPres.deleteUser(userDeleted);
                    ratings = CtrlPres.itemsLoaded();
                } catch (Exception e) {
                    enableButtons();
                    JOptionPane.showMessageDialog(null,"The chosen file does not have the correct format");
                    e.printStackTrace();
                }
                enableButtons();
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
        uploadItemsButton.setEnabled(false);
        settingsButton.setEnabled(false);
        uploadUsersRatingsButton.setEnabled(false);
        deleteItemButton.setEnabled(false);
        deleteUserButton.setEnabled(false);
        uploadRateValorationButton.setEnabled(false);
        DeleteButton.setEnabled(false);
    }

    public void enableButtons() {
        uploadItemsButton.setEnabled(true);
        settingsButton.setEnabled(true);
        uploadUsersRatingsButton.setEnabled(true);
        deleteItemButton.setEnabled(true);
        deleteUserButton.setEnabled(true);
        uploadRateValorationButton.setEnabled(true);
        DeleteButton.setEnabled(true);

    }
    public void setInvisible() {
        frame.dispose();
    }
}
