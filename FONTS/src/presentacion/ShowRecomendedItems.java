package FONTS.src.presentacion;

import FONTS.src.domini.controladors.ControladorPresentacion;
import FONTS.src.domini.model.myPair;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Vector;

public class ShowRecomendedItems {

    ControladorPresentacion CtrlPres = ControladorPresentacion.getInstance();

    private static JFrame frame;
    private JButton backButton;
    private JPanel panel;
    private JButton settingsButton;
    private JList list1;
    private JScrollPane scrollPane;

    public ShowRecomendedItems() {

        scrollPane.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = new Color(134,114,62);
                this.trackColor  = new Color(187,165,107);

            }
        });

        scrollPane.getVerticalScrollBar().getComponent(0).setBackground(new Color(134,114,62));
        scrollPane.getVerticalScrollBar().getComponent(1).setBackground(new Color(134,114,62));

        DefaultListModel demoList = new DefaultListModel();
        ArrayList<myPair> items = CtrlPres.getRecomendedItems();
        for(int i = 0; i < 50; ++i) {
            demoList.addElement(items.get(i).getItemID());
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
        MouseListener mouseListener = new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    String selectedItem = (String) list1.getSelectedValue();
                    int x = frame.getX();
                    int y = frame.getY();
                    CtrlPres.inicializePresentation(x,y);
                    frame.setVisible(false);
                }
            }
        };
        list1.addMouseListener(mouseListener);
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
