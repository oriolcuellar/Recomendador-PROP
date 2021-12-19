package FONTS.src.presentacion;

import FONTS.src.domini.controladors.ControladorPresentacion;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProfileView {
    ControladorPresentacion CtrlPres = ControladorPresentacion.getInstance();
    private static JFrame frame;
    private JPanel panel;
    private JButton logoutButton;
    private JLabel id;
    private JLabel username;
    private JButton backButton;
    private JButton statsButton;

    public ProfileView(){
        Integer userID = CtrlPres.getActualUserId();
        String s = userID.toString();
        id.setText(s);
        if(CtrlPres.isAdmin()) statsButton.setVisible(false);
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    disableButtons();
                    if(CtrlPres.isAdmin()) {
                        int input = JOptionPane.showConfirmDialog(null, "Do you want to save the changes?", "Choose",
                                JOptionPane.OK_CANCEL_OPTION);
                        if (input == 0) {
                            CtrlPres.saveAll();
                        }
                    }
                    int x = frame.getX();
                    int y = frame.getY();
                    CtrlPres.logout();
                    CtrlPres.changeLogInView(x,y);
                    frame.dispose();
                } catch (Exception e) {
                    enableButtons();
                    e.printStackTrace();
                }
                enableButtons();
            }
        });
        statsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    disableButtons();
                    int x = frame.getX();
                    int y = frame.getY();
                    CtrlPres.changeStatsView(x,y);
                    frame.dispose();
                } catch (Exception e) {
                    enableButtons();
                    e.printStackTrace();
                }
                enableButtons();
            }
        });
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    disableButtons();
                    int x = frame.getX();
                    int y = frame.getY();
                    if(CtrlPres.isAdmin()) CtrlPres.changeAdminMainView(x,y);
                    else CtrlPres.inicializePresentation(x,y);
                    frame.dispose();
                } catch (Exception e) {
                    enableButtons();
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
        //frame.setResizable(false);
        frame.setVisible(true);
    }

    public void disableButtons() {
        logoutButton.setEnabled(false);
        backButton.setEnabled(false);
        statsButton.setEnabled(false);
    }

    public void enableButtons() {
        logoutButton.setEnabled(true);
        backButton.setEnabled(true);
        statsButton.setEnabled(true);
    }
    public void setInvisible() {
        frame.dispose();
    }
}
