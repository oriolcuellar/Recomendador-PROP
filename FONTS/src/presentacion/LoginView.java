package FONTS.src.presentacion;

import FONTS.src.domini.controladors.ControladorPresentacion;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginView {
    ControladorPresentacion CtrlPres = ControladorPresentacion.getInstance();
    private static JFrame frame;
    private JPanel panel;
    private JPasswordField passwordPasswordField;
    private JTextField usernameTextField;
    private JButton signupButton;
    private JLabel username;
    private JLabel password;
    private JButton signinButton;

    public LoginView(){
        signinButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    disableButtons();
                    CtrlPres.login(usernameTextField.getText(), passwordPasswordField.getText());
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
        signupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    if(CtrlPres.getUsersSize() == 0)
                        JOptionPane.showMessageDialog(null,"An Administrador should enter before doing this option");
                    else {
                        disableButtons();
                        int x = frame.getX();
                        int y = frame.getY();
                        CtrlPres.changeSignUpView(x, y);
                        frame.dispose();
                    }
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
        signupButton.setEnabled(false);
    }

    public void enableButtons() {
        signupButton.setEnabled(true);
    }
    public void setInvisible() {
        frame.dispose();
    }
}
