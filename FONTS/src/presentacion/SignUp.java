package FONTS.src.presentacion;

import FONTS.src.domini.controladors.ControladorPresentacion;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class SignUp{
    ControladorPresentacion CtrlPres = ControladorPresentacion.getInstance();
    private static JFrame frame;
    private JPanel panel;
    private JPasswordField passwordPasswordField;
    private JTextField usernameTextField;
    private JButton backButton;
    private JLabel username;
    private JLabel password;
    private JButton SignUpButton;
    private JPasswordField repeatPasswordField;

    public SignUp(){
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int x = frame.getX();
                int y = frame.getY();
                CtrlPres.changeLogInView(x,y);
                frame.dispose();
            }
        });
        SignUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    disableButtons();
                    if (Objects.equals(passwordPasswordField.getText(), repeatPasswordField.getText())) {
                        CtrlPres.singUp(usernameTextField.getText(), passwordPasswordField.getText());
                        int x = frame.getX();
                        int y = frame.getY();
                        CtrlPres.changeLogInView(x, y);
                        frame.dispose();
                    }
                    else JOptionPane.showMessageDialog(null,"The password does not match");
                } catch (Exception e) {
                    enableButtons();
                    if(passwordPasswordField.getText().isEmpty() && repeatPasswordField.getText().isEmpty())
                        JOptionPane.showMessageDialog(null,"password field is empty");
                    else JOptionPane.showMessageDialog(null,"The username already exists or is composed by characters");
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
        backButton.setEnabled(false);
    }

    public void enableButtons() {
        backButton.setEnabled(true);
    }
}
