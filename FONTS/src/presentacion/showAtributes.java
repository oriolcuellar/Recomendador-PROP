package FONTS.src.presentacion;

import FONTS.src.domini.controladors.ControladorPresentacion;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;

import static FONTS.src.domini.controladors.ControladorPresentacion.*;

public class showAtributes {
    ControladorPresentacion CtrlPres = ControladorPresentacion.getInstance();

    private static JFrame frame;
    private JButton backButton;
    private JPanel panel;
    private JButton settingsButton;
    private JButton valorate;
    private JList valors;
    private JList atributtes;
    private JLabel valoration;
    private JLabel item;
    private JScrollPane scrollPane;

    public showAtributes(int id) {

        item.setText(String.valueOf(id));

        scrollPane.getHorizontalScrollBar().setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = new Color(134,114,62);
                this.trackColor  = new Color(187,165,107);
            }
        });

        DefaultListModel atributosList = new DefaultListModel();
        ArrayList<String> atributos = CtrlPres.getAtributos();
        for(int i = 0; i < atributos.size(); ++i) {
            atributosList.addElement(atributos.get(i));
        }

        atributtes.setModel(atributosList);

        DefaultListModel ValoresList = new DefaultListModel();
        ArrayList<String> valores = CtrlPres.getValors();
        for(int i = 0; i < valores.size(); ++i) {
            atributosList.addElement(valores.get(i));
        }

        valors.setModel(ValoresList);

        String valoracion = CtrlPres.getValoration(String.valueOf(id));
        valoration.setText(valoracion);

        scrollPane.getVerticalScrollBar().getComponent(0).setBackground(new Color(134,114,62));
        scrollPane.getVerticalScrollBar().getComponent(1).setBackground(new Color(134,114,62));

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int x = frame.getX();
                int y = frame.getY();
                CtrlPres.inicializePresentation(x, y);
                frame.dispose();
            }
        });
        settingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int x = frame.getX();
                int y = frame.getY();
                CtrlPres.changeProfileView(x,y);
                frame.dispose();
            }
        });
        valorate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int input = JOptionPane.showConfirmDialog(null, "Vols sobreescriure la valoracio?", "Selecciona una opció...",
                        JOptionPane.OK_CANCEL_OPTION);
                if (input == 0) {

                    String new_valoration = JOptionPane.showInputDialog(null,
                            "Escriu la nova valoració", null);
                    CtrlPres.SetValoration(String.valueOf(id), new_valoration);
                }
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
}
