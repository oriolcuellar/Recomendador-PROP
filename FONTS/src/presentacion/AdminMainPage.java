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
    private JFileChooser fileChooser = new JFileChooser();
    private boolean items = false;
    private boolean ratings = false;

    public AdminMainPage(){
        uploadItemsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    disableButtons();
                    fileChooser.showOpenDialog(fileChooser);
                    String path = fileChooser.getSelectedFile().getAbsolutePath();
                    CtrlPres.loadItems(path);
                    items = true;
                } catch (Exception e) {
                    enableButtons();
                    items = CtrlPres.getAllItems().size() != 0;
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
                        ratings = true;
                    } else JOptionPane.showMessageDialog(null,"You have to load items before doing anything else");

                } catch (Exception e) {
                    enableButtons();
                    ratings = false;
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
                if(items && ratings) {
                    CtrlPres.changeProfileView(x, y);
                    frame.dispose();
                } else if(items) JOptionPane.showMessageDialog(null,"You have to load ratings before doing anything else");
                else JOptionPane.showMessageDialog(null,"You have to load items and ratings before doing anything else");
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
        //frame.setResizable(false);
        frame.setVisible(true);
    }

    public void disableButtons() {
        uploadItemsButton.setEnabled(false);
        settingsButton.setEnabled(false);
        uploadUsersRatingsButton.setEnabled(false);
        deleteItemButton.setEnabled(false);
        deleteUserButton.setEnabled(false);
    }

    public void enableButtons() {
        uploadItemsButton.setEnabled(true);
        settingsButton.setEnabled(true);
        uploadUsersRatingsButton.setEnabled(true);
        deleteItemButton.setEnabled(true);
        deleteUserButton.setEnabled(true);
    }
    public void setInvisible() {
        frame.dispose();
    }
}
