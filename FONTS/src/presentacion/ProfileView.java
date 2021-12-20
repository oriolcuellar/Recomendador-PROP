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
    private JButton backButton;
    private JButton statsButton;
    private JButton changePasswordButton;
    private JButton deleteProfileButton;

    public ProfileView(){
        Integer userID = CtrlPres.getActualUserId();
        String s = userID.toString();
        id.setText(s);
        if(CtrlPres.isAdmin()){
            statsButton.setVisible(false);
            deleteProfileButton.setVisible(false);
        }
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

        changePasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    disableButtons();
                    String password1 = JOptionPane.showInputDialog(null,
                            "Write the password", null);
                    String password2 = JOptionPane.showInputDialog(null,
                            "Repeat the password", null);
                    if(password1.equals(password2)) CtrlPres.editProfile(password1);
                    else JOptionPane.showMessageDialog(null, "The password does not match");
                } catch (Exception e) {
                    enableButtons();
                    JOptionPane.showMessageDialog(null,"Error: Repeat the process");
                    e.printStackTrace();
                }
                enableButtons();
            }
        });

        deleteProfileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    disableButtons();
                    String userDeleted = JOptionPane.showInputDialog(null,
                            "Write CONFIRM to delete the profile\n Be sure all your data will be deleted", null);
                   System.out.println(userDeleted);
                    if(userDeleted.endsWith("CONFIRM")) {
                        CtrlPres.deleteUser(userDeleted);
                        CtrlPres.logout();
                        int x = frame.getX();
                        int y = frame.getY();
                        CtrlPres.changeLogInView(x,y);
                        frame.dispose();
                    }
                    else JOptionPane.showMessageDialog(null,"The profile was not deleted");
                } catch (Exception e) {
                    enableButtons();
                    JOptionPane.showMessageDialog(null, e);
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
        frame.setResizable(false);
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
