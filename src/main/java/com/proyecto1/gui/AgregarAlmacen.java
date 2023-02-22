package com.proyecto1.gui;

import com.proyecto1.MainFrame;
import com.proyecto1.containers.Grafo;
import com.proyecto1.containers.Vector;
import com.proyecto1.models.Edge;
import com.proyecto1.models.Product;
import com.proyecto1.models.Wearhouse;
import java.awt.event.ActionEvent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

// @author andresbucarello

public class AgregarAlmacen extends javax.swing.JPanel {
    
//    int cantRutas;
    boolean comprobado;
    int index;
    int distancia;
    Vector<Wearhouse> almacenes;
    String nombre;
    Wearhouse almacenSeleccionado;
    Wearhouse almacenNuevo;
    
    /**
     * Creates new form Prubea
     */
    public AgregarAlmacen() {
        initComponents();
        almacenes=Grafo.getInstance().almacenes;
        Wearhouse almacenNuevo=new Wearhouse("vacio");
    }
    
    private Wearhouse buscarWearhouse(Vector<Wearhouse> almacenes,String nombre){
        for (Wearhouse almacen : almacenes) {
            if(nombre.equals(almacen.name)){
                return almacen;    
            }
        }
        return null;
    }
    
    private boolean encontrar(){
        for (Wearhouse almacen : almacenes) {
            if(nombre.equals(almacen.name)){
                return true;    
            }
        }
        return false;
    }
    
    private String validarStr(String str){
        try{
            boolean encontrado=encontrar();
            if(encontrado || str.length()==0){
                if(encontrado){
                    JOptionPane.showMessageDialog(null, " ERROR EL ALMACEN YA EXISTE ");
                }else{
                    JOptionPane.showMessageDialog(null, " ERROR EL NOMBRE INGRSADO NO ES VALIDO ");
                }
                fieldNombre.setText("");
                return "";
            }
            return str;
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, " ERROR EL NOMBRE INGRSADO NO ES VALIDO ");
            fieldNombre.setText("");
            return "";
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        titulo1 = new javax.swing.JLabel();
        titulo2 = new javax.swing.JLabel();
        fieldNombre = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        titulo3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        rutasDisponibles = new javax.swing.JList<>();
        botonAgregar = new javax.swing.JButton();
        titulo4 = new javax.swing.JLabel();
        botonAgregarAlmacen = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        rutasAgregadas = new javax.swing.JList<>();

        setFocusable(false);
        setMaximumSize(new java.awt.Dimension(630, 450));
        setMinimumSize(new java.awt.Dimension(630, 450));
        setPreferredSize(new java.awt.Dimension(630, 450));
        setRequestFocusEnabled(false);
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        titulo1.setFont(new java.awt.Font("Silom", 0, 48)); // NOI18N
        titulo1.setText("AGREGAR ALMACEN");
        add(titulo1, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 0, -1, -1));

        titulo2.setFont(new java.awt.Font("Silom", 1, 14)); // NOI18N
        titulo2.setText("INGRESE EL NOMBRE :");
        add(titulo2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 90, 160, -1));

        fieldNombre.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                fieldNombreFocusLost(evt);
            }
        });
        fieldNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fieldNombreActionPerformed(evt);
            }
        });
        add(fieldNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 90, 420, -1));

        jSeparator1.setForeground(new java.awt.Color(0, 0, 0));
        add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 130, 580, 10));

        titulo3.setFont(new java.awt.Font("Silom", 1, 14)); // NOI18N
        titulo3.setText("RUTAS DISPONIBLES");
        add(titulo3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 140, 220, 30));

        rutasDisponibles.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        rutasDisponibles.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rutasDisponiblesMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(rutasDisponibles);

        add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 170, 220, 220));

        botonAgregar.setFont(new java.awt.Font("Silom", 0, 14)); // NOI18N
        botonAgregar.setText(">>");
        botonAgregar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        botonAgregar.setEnabled(false);
        botonAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonAgregarActionPerformed(evt);
            }
        });
        add(botonAgregar, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 250, 60, 40));

        titulo4.setFont(new java.awt.Font("Silom", 1, 14)); // NOI18N
        titulo4.setText("RUTAS AGREGADAS");
        add(titulo4, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 140, 220, 30));

        botonAgregarAlmacen.setFont(new java.awt.Font("Silom", 0, 14)); // NOI18N
        botonAgregarAlmacen.setText("Agregar Almacen");
        botonAgregarAlmacen.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        botonAgregarAlmacen.setEnabled(false);
        botonAgregarAlmacen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonAgregarAlmacenActionPerformed(evt);
            }
        });
        add(botonAgregarAlmacen, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 400, 580, -1));

        rutasAgregadas.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane2.setViewportView(rutasAgregadas);

        add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 170, 220, 220));
    }// </editor-fold>//GEN-END:initComponents

    private void fieldNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fieldNombreActionPerformed
        String str=fieldNombre.getText();
        nombre=validarStr(str);
    }//GEN-LAST:event_fieldNombreActionPerformed

    private void botonAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonAgregarActionPerformed
        do{
            comprobado=false;
            String x=JOptionPane.showInputDialog(null, " INGRESE LA DISTANCIA CON EL ALMACEN " + rutasDisponibles.getSelectedValue());
            try{
                int distancia=Integer.parseInt(x);
                comprobado=true;
                String almacenVecino=rutasDisponibles.getSelectedValue();
                Wearhouse almacenVecinoW = buscarWearhouse(almacenes,almacenVecino);
                almacenNuevo.name=nombre;
//                Edge edge=new Edge(almacenNuevo,almacenVecino,distancia);
                
//                almacenSeleccionado=(Wearhouse) rutasDisponibles.getSelectedItem();
//                //almacenSeleccionado=rutasDisponibles.getSelec
//                Edge(Wearhouse almacen, Wearhouse almacenVecino, int distancia)
                // crear arista y agregar a la lista
                botonAgregar.setEnabled(false);
//                cantRutas = rutasAgregadas.getVisibleRowCount();
//                if (cantRutas >= 2 && !nombre.isEmpty()) {
//                    botonAgregarAlmacen.setEnabled(true);
//                }
            }catch(Exception e){
                JOptionPane.showMessageDialog(null, " ERROR LA DISTANCIA INGRSADA NO ES VALIDA"); 
            }   
        }while(!comprobado);
        
    }//GEN-LAST:event_botonAgregarActionPerformed

    private void botonAgregarAlmacenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonAgregarAlmacenActionPerformed
        Wearhouse almacen=new Wearhouse(nombre);
       //almacen.edges.pushBack(v);
        almacenes.pushBack(almacen);
    }//GEN-LAST:event_botonAgregarAlmacenActionPerformed

    private void rutasDisponiblesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rutasDisponiblesMouseClicked
        index= rutasDisponibles.getSelectedIndex();
        if (index != -1) {
            botonAgregar.setEnabled(true);
        }
    }//GEN-LAST:event_rutasDisponiblesMouseClicked

    private void fieldNombreFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_fieldNombreFocusLost
        String str=fieldNombre.getText();
        nombre=validarStr(str);
    }//GEN-LAST:event_fieldNombreFocusLost

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botonAgregar;
    private javax.swing.JButton botonAgregarAlmacen;
    private javax.swing.JTextField fieldNombre;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JList<String> rutasAgregadas;
    private javax.swing.JList<String> rutasDisponibles;
    private javax.swing.JLabel titulo1;
    private javax.swing.JLabel titulo2;
    private javax.swing.JLabel titulo3;
    private javax.swing.JLabel titulo4;
    // End of variables declaration//GEN-END:variables
}
