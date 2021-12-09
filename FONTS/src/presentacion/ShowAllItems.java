package FONTS.src.presentacion;

import FONTS.src.domini.controladors.ControladorPresentacion;
import org.w3c.dom.css.RGBColor;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

public class ShowAllItems {

    ControladorPresentacion CtrlPres = ControladorPresentacion.getInstance();

    private static JFrame frame;
    private JButton backButton;
    private JPanel panel;
    private JButton settingsButton;
    private JList list1;
    private JScrollPane scrollPane;

    public ShowAllItems() {

        scrollPane.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                Color c = new Color(187,165,107);
                this.thumbColor = Color.BLACK;
                this.trackColor  = c;
            }
        });

        DefaultListModel demoList = new DefaultListModel();
        Vector<String> items = CtrlPres.getAllItems();
        for(int i = 0; i < items.size(); ++i) {
            demoList.addElement(items.get(i));
        }
        list1.setModel(demoList);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int x = frame.getX();
                int y = frame.getY();
                CtrlPres.inicializePresentation(x,y);
                frame.setVisible(false);
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

}
