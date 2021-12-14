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
    private JLabel username;
    private JLabel id;

    public ProfileView(){
        id.setText("ASDFA");
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    disableButtons();
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
    }

    public void enableButtons() {
        logoutButton.setEnabled(true);
    }
    public void setInvisible() {
        frame.dispose();
    }
}
