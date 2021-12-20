package FONTS.src.presentacion;

import FONTS.src.domini.controladors.ControladorPresentacion;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.net.URL;
import javax.imageio.ImageIO;

public class showAtributes {
    ControladorPresentacion CtrlPres = ControladorPresentacion.getInstance();

    private static JFrame frame;
    private JButton backButton;
    private JPanel panel;
    private JButton valorate;
    private JList valors;
    private JList atributes;
    private JLabel valoration;
    private JLabel item;
    private JScrollPane scrollPane;
    private JTable tabla;
    private JLabel label;
    private String path;

    public showAtributes(String id) throws IOException {

        item.setText("ID: " + id);

        scrollPane.getHorizontalScrollBar().setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = new Color(134,114,62);
                this.trackColor  = new Color(187,165,107);
            }
        });

       // DefaultListModel atributosList = new DefaultListModel();
        ArrayList<String> atributos = CtrlPres.getAtributos();
        /*System.out.println(atributos.size());
        for(int i = 0; i < atributos.size(); ++i) {
            atributosList.addElement(atributos.get(i));
        }*/

        //atributes.setModel(atributosList);
        //atributes.setLayoutOrientation(JList.HORIZONTAL_WRAP);

        //DefaultListModel ValoresList = new DefaultListModel();
        ArrayList<String> valores = CtrlPres.getValors();
        /*System.out.println(valores.size());
        for(int i = 0; i < valores.size(); ++i) {
            atributosList.addElement(valores.get(i));
        }*/

        //valors.setModel(ValoresList);
        //valors.setLayoutOrientation(JList.HORIZONTAL_WRAP);

        String valoracion = CtrlPres.getValoration(String.valueOf(id));
        valoration.setText("valoration: " + valoracion);

        scrollPane.getVerticalScrollBar().getComponent(0).setBackground(new Color(134,114,62));
        scrollPane.getVerticalScrollBar().getComponent(1).setBackground(new Color(134,114,62));

        DefaultTableModel model = new DefaultTableModel(null, atributos.toArray());
        model.addRow(valores.stream().toArray());
        tabla = new JTable(model);

        tabla.setRowHeight(20);

        TableColumnModel columnModel = tabla.getColumnModel();
        for (int i = 0; i < valores.size(); i++){
            if(atributos.get(i).equals("img_url")) path = valores.get(i);
            columnModel.getColumn(i).setPreferredWidth(100);
        }
        int width = 100 * valores.size();
        System.out.println(width);
        tabla.setMinimumSize(new Dimension(width, 40));
        tabla.setMaximumSize(new Dimension(width, 40));
        tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        scrollPane.setViewportView(tabla);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int x = frame.getX();
                int y = frame.getY();
                CtrlPres.inicializePresentation(x, y);
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
                    valoration.setText("valoration: " + new_valoration);
                }
            }
        });
        try {
            URL url = new URL(path);
            BufferedImage image = ImageIO.read(url);
            label.setIcon(new ImageIcon(image));
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public void showWindow(int x, int y) {
        frame = new JFrame("Sistema Recomanador");
        frame.setContentPane(this.panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setBounds(x,y,900,900);
        frame.setResizable(false);
        frame.setVisible(true);
    }
}
