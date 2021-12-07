package FONTS.src.presentacion;

import FONTS.src.domini.controladors.ControladorPresentacion;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

        DefaultListModel demoList = new DefaultListModel();
        Vector<String> items = CtrlPres.getAllItems();
        for(int i = 0; i < items.size(); ++i) {
            demoList.addElement(items.get(i));
        }
        list1.setModel(demoList);


        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                frame.setVisible(false);
                CtrlPres.inicializePresentation();
            }
        });
    }

    public void showWindow() {
        frame = new JFrame("Sistema Recomanador");
        frame.setContentPane(new ShowAllItems().panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setMinimumSize(new Dimension(600, 600));
        frame.setResizable(false);
        frame.setVisible(true);

    }
}
