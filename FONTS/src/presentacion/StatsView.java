package FONTS.src.presentacion;

import FONTS.src.domini.controladors.ControladorPresentacion;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StatsView {
    ControladorPresentacion CtrlPres = ControladorPresentacion.getInstance();
    private static JFrame frame;
    private JPanel panel;
    private JButton favouriteItem;
    private JLabel avgRating;
    private JLabel numRated;
    private JButton backButton;

    public StatsView(){
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
        
    }

    public void enableButtons() {
        
    }
    public void setInvisible() {
        frame.dispose();
    }
}
