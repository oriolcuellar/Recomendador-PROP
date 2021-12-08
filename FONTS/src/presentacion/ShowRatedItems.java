package FONTS.src.presentacion;

import FONTS.src.domini.controladors.ControladorPresentacion;
import FONTS.src.domini.model.myPair;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;

public class ShowRatedItems {
    ControladorPresentacion CtrlPres = ControladorPresentacion.getInstance();

    private static JFrame frame;
    private JButton backButton;
    private JPanel panel;
    private JButton settingsButton;
    private JList list1;
    private JScrollPane scrollPane;

    public ShowRatedItems () {

        DefaultListModel demoList = new DefaultListModel();
        ArrayList<Integer> items = CtrlPres.getRatedItems();
        for(int i = 0; i < items.size(); ++i) {
            demoList.addElement(items.get(i));
        }
        list1.setModel(demoList);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                frame.setVisible(false);
                int x = frame.getX();
                int y = frame.getY();
                CtrlPres.inicializePresentation(x,y);
            }
        });
    }

    public void showWindow(int x, int y) {
        frame = new JFrame("Sistema Recomanador");
        frame.setContentPane(this.panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setBounds(x,y,600,600);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    public void setInvisible() {
        frame.setVisible(false);
    }
}
