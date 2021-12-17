package FONTS.src.presentacion;

import FONTS.src.domini.controladors.ControladorPresentacion;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;

public class StatsView {
    ControladorPresentacion CtrlPres = ControladorPresentacion.getInstance();
    private static JFrame frame;
    private JPanel panel;
    private JButton favouriteItem;
    private JLabel avgRating;
    private JLabel numRated;
    private JButton backButton;

    public StatsView(){
        Integer num = CtrlPres.numRated();
        String sNum = num.toString();
        numRated.setText(sNum);
        Integer avg = CtrlPres.avgRating();
        String sAvg = avg.toString();
        avgRating.setText(sAvg);
        Integer fav = CtrlPres.itemFavourite();
        String sFav = fav.toString();
        if(fav != 0) favouriteItem.setText(sFav);
        else favouriteItem.setText("You have not valorated item");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int x = frame.getX();
                int y = frame.getY();
                CtrlPres.changeProfileView(x,y);
                frame.dispose();
            }
        });
        favouriteItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (favouriteItem.getText() != "You have not valorated item") {
                    Integer selectedItem = Integer.valueOf(favouriteItem.getText());
                    int x = frame.getX();
                    int y = frame.getY();
                    CtrlPres.changeShowAtributesView(x,y, selectedItem);
                    frame.dispose();
                }
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
