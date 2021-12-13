package FONTS.src.presentacion;

import FONTS.src.domini.controladors.ControladorPresentacion;

import javax.swing.*;
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
    private JButton backButton;
    private JFileChooser fileChooser = new JFileChooser();

    public AdminMainPage() {

        uploadItemsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    disableButtons();
                    fileChooser.showOpenDialog(fileChooser);
                    String path = fileChooser.getSelectedFile().getAbsolutePath();
                    CtrlPres.loadItems(path);
                } catch (Exception e) {
                    enableButtons();
                    JOptionPane.showMessageDialog(null,"El fichero introducido no tiene el formato válido");
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
                    fileChooser.showOpenDialog(fileChooser);
                    String path = fileChooser.getSelectedFile().getAbsolutePath();
                    CtrlPres.loadRates(path);
                } catch (Exception e) {
                    enableButtons();
                    JOptionPane.showMessageDialog(null,"El fichero introducido no tiene el formato válido");
                    e.printStackTrace();
                }
                enableButtons();
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                int x = frame.getX();
                int y = frame.getY();
                CtrlPres.inicializePresentation(x,y);
                frame.dispose();
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
