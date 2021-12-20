package FONTS.src.presentacion;

import FONTS.src.domini.controladors.ControladorDomini;
import FONTS.src.domini.controladors.ControladorPresentacion;

import javax.swing.*;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

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
    private JButton createItemButton;
    private JButton createUserButton;
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
                    JOptionPane.showMessageDialog(null,"Load item.csv file");
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
                    JOptionPane.showMessageDialog(null,"Load ratings.db.csv file");
                    fileChooser.showOpenDialog(fileChooser);
                    String path = fileChooser.getSelectedFile().getAbsolutePath();
                    CtrlPres.loadRates(path);

                    JOptionPane.showMessageDialog(null,"Load ratings.test.known.csv file");
                    fileChooser.showOpenDialog(fileChooser);
                    String path2 = fileChooser.getSelectedFile().getAbsolutePath();
                    CtrlPres.loadRates(path2);

                    ratings = CtrlPres.usersLoaded();
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
                    JOptionPane.showMessageDialog(null,"Load ratings.test.unknown.csv file");
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
                }
                else if(items && ratings) JOptionPane.showMessageDialog(null,"You have to load the evaluate recomendation file before doing anything else");
                else if(items && unkown) JOptionPane.showMessageDialog(null,"You have to load ratings before doing anything else");
                else if(ratings && unkown) JOptionPane.showMessageDialog(null,"You have to load items before doing anything else");
                else if(items) JOptionPane.showMessageDialog(null,"You have to load ratings and evaluate recomendation file before doing anything else");
                else if(ratings) JOptionPane.showMessageDialog(null,"You have to load items and evaluate recomendation file before doing anything else");
                else if(unkown) JOptionPane.showMessageDialog(null,"You have to load items and ratings before doing anything else");

                else JOptionPane.showMessageDialog(null,"You have to load items, ratings and evaluate recomendation file before doing anything else");
            }
        });

        DeleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    disableButtons();
                    int input = JOptionPane.showConfirmDialog(null, "Are you sure that you want to delete all the loaded data?", "Choose",
                            JOptionPane.OK_CANCEL_OPTION);
                    if (input == 0) {
                        CtrlPres.deleteAllData();
                        items = CtrlPres.itemsLoaded();
                        ratings = CtrlPres.usersLoaded();
                        unkown = CtrlPres.unknownLoaded();
                    }
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

        createUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    disableButtons();
                    String userCreate = JOptionPane.showInputDialog(null,
                            "Write the id of the user to create", null);
                    String password = JOptionPane.showInputDialog(null,
                            "Write the password", null);
                    CtrlPres.singUp(userCreate,password);
                    ratings = CtrlPres.usersLoaded();
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
                    JOptionPane.showMessageDialog(null,e);
                    e.printStackTrace();
                }
                enableButtons();
            }
        });
        createItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    disableButtons();
                    ArrayList<String> atributes = CtrlPres.getAtributos();
                    String valors = "";
                    String atribut = "";
                    for(String s : atributes) {
                        String val = JOptionPane.showInputDialog(null,
                                "Write the id of the user to delete", null);
                        atribut = atribut + "," + s;
                        valors =  valors + "," + val;
                    }
                    CtrlPres.createItem(atribut,valors);
                } catch (Exception e) {
                    enableButtons();
                    JOptionPane.showMessageDialog(null,e);
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
        createItemButton.setEnabled(false);
        createUserButton.setEnabled(false);
    }

    public void enableButtons() {
        uploadItemsButton.setEnabled(true);
        settingsButton.setEnabled(true);
        uploadUsersRatingsButton.setEnabled(true);
        deleteItemButton.setEnabled(true);
        deleteUserButton.setEnabled(true);
        uploadRateValorationButton.setEnabled(true);
        DeleteButton.setEnabled(true);
        createItemButton.setEnabled(true);
        createUserButton.setEnabled(true);

    }
    public void setInvisible() {
        frame.dispose();
    }
}
