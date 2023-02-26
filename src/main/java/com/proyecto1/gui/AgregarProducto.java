package com.proyecto1.gui;
import com.proyecto1.containers.Graph;
import com.proyecto1.containers.Vector;
import javax.swing.JOptionPane;
import com.proyecto1.models.Product;
import com.proyecto1.models.Warehouse;
import java.awt.Color;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

// @author andresbucarello

public class AgregarProducto extends MenuComponent {
    //Se crea una variable (importada anteriormente) de vector
    Vector<Warehouse> almacenes;
    Warehouse almacenSeleccionadoW;
    String nombre = "";
    int stock;
    //Se crea una ComboboxModel con la cantidad de productos disponibles
    DefaultComboBoxModel<String> disponibles = new DefaultComboBoxModel();
    
    /**
     * Creates new form AgregarProducto
     */
    //Funcion para agregar producto:
    public AgregarProducto(MainPanel mainMenuPanel) {
        super(mainMenuPanel);
        initComponents();
        //Aqui obtenemos los datos del usuario de su seleccion de almacenes
        almacenes= Graph.getInstance().warehouses;
        cargarCombo(listaAlmacenes);
        botonGuardar.setEnabled(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    //Un verificador para ver si el producto escrito por el usuairo existe o no en los almacenes
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        titulo1 = new javax.swing.JLabel();
        titulo2 = new javax.swing.JLabel();
        listaAlmacenes = new javax.swing.JComboBox<>();
        jSeparator1 = new javax.swing.JSeparator();
        titulo3 = new javax.swing.JLabel();
        fieldNombre = new javax.swing.JTextField();
        jSeparator2 = new javax.swing.JSeparator();
        titulo4 = new javax.swing.JLabel();
        fieldCantidad = new javax.swing.JTextField();
        jSeparator3 = new javax.swing.JSeparator();
        botonGuardar = new javax.swing.JButton();
        retrocederPanel = new javax.swing.JPanel();
        retrocederText = new javax.swing.JLabel();

        setMaximumSize(new java.awt.Dimension(960, 720));
        setMinimumSize(new java.awt.Dimension(960, 720));
        setPreferredSize(new java.awt.Dimension(960, 720));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        titulo1.setFont(new java.awt.Font("Silom", 0, 65)); // NOI18N
        titulo1.setForeground(javax.swing.UIManager.getDefaults().getColor("Actions.Yellow"));
        titulo1.setText("AGREGAR PRODUCTO");
        add(titulo1, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 70, -1, -1));

        titulo2.setFont(new java.awt.Font("Silom", 1, 24)); // NOI18N
        titulo2.setText("SELECCIONE EL ALMACEN :");
        add(titulo2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 240, 380, -1));

        listaAlmacenes.setForeground(new java.awt.Color(51, 109, 174));
        listaAlmacenes.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        listaAlmacenes.setBorder(javax.swing.BorderFactory.createTitledBorder("Almacenes"));
        listaAlmacenes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                listaAlmacenesActionPerformed(evt);
            }
        });
        add(listaAlmacenes, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 230, 410, 60));

        jSeparator1.setForeground(new java.awt.Color(0, 0, 0));
        add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 300, 880, 20));

        titulo3.setFont(new java.awt.Font("Silom", 1, 24)); // NOI18N
        titulo3.setText("INGRESE EL NOMBRE DEL PRODUCTO :");
        add(titulo3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 320, 490, 30));

        fieldNombre.setForeground(new java.awt.Color(51, 109, 174));
        add(fieldNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 320, 410, 30));

        jSeparator2.setForeground(new java.awt.Color(0, 0, 0));
        add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 380, 880, 20));

        titulo4.setFont(new java.awt.Font("Silom", 1, 24)); // NOI18N
        titulo4.setText("INGRESE LA CANTIDAD DEL PRODUCTO :");
        add(titulo4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 400, 500, 40));

        fieldCantidad.setForeground(new java.awt.Color(51, 109, 174));
        add(fieldCantidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 400, 410, 30));

        jSeparator3.setForeground(new java.awt.Color(0, 0, 0));
        add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 470, 880, 20));

        botonGuardar.setFont(new java.awt.Font("Silom", 0, 24)); // NOI18N
        botonGuardar.setText("Guardar");
        botonGuardar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        botonGuardar.setEnabled(false);
        botonGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonGuardarActionPerformed(evt);
            }
        });
        add(botonGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 640, 180, -1));

        retrocederPanel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        retrocederPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                retrocederPanelMouseEntered(evt);
            }
        });

        retrocederText.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        retrocederText.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        retrocederText.setText("<");
        retrocederText.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                retrocederTextMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                retrocederTextMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                retrocederTextMouseExited(evt);
            }
        });

        javax.swing.GroupLayout retrocederPanelLayout = new javax.swing.GroupLayout(retrocederPanel);
        retrocederPanel.setLayout(retrocederPanelLayout);
        retrocederPanelLayout.setHorizontalGroup(
            retrocederPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(retrocederText, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
        );
        retrocederPanelLayout.setVerticalGroup(
            retrocederPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(retrocederText, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
        );

        add(retrocederPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 70, 50));
    }// </editor-fold>//GEN-END:initComponents
    private boolean encontrarProducto(String nombreProducto){
        for (Warehouse almacen : almacenes) {
            if(almacenSeleccionadoW.name.equals(almacen.name)){
                for (Product producto : almacen.products){
                    if(nombreProducto.equalsIgnoreCase(producto.name)){
                        return true;
                    }
                }
            }    
        }
        return false;
    }
    //Agregar un nuevo almacen
    private void cargarCombo(JComboBox c) {
        c.setModel(disponibles);
        for (Warehouse almacen: almacenes) {
            disponibles.addElement(almacen.name);
        }
    }
    //Llamamos la funcion vectores anteriormente creada y creamos una funcion para verificar los warehouses
    private Warehouse buscarWearhouse(Vector<Warehouse> almacenes, String nombre){
        for (Warehouse almacen : almacenes) {
            if(nombre.equals(almacen.name)){
                return almacen;    
            }
        }
        return null;
    }
    //Funcion para verificar si el input del usuario es un numero
    private int validarInt(String num){
        try{
            stock=Integer.parseInt(num);
            if(stock<=0){
                JOptionPane.showMessageDialog(null, " ERROR LA CANTIDAD INGRSADA NO ES VALIDA ");
                fieldCantidad.setText("");
                return 0;
            }
            fieldCantidad.setEnabled(false);
            return stock;
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, " ERROR LA CANTIDAD INGRSADA NO ES VALIDA ");
            return 0;
        }
    }
    //Funcion para validar si el nombre del producto escrito por el usuario es un String o si este ya se encuentra
    //en el almacen
    private String validarStr(String nombreProducto){
        try{
            boolean encontrado=encontrarProducto(nombreProducto);
            if(encontrado || nombreProducto.isEmpty()){
                if(encontrado){
                    JOptionPane.showMessageDialog(null, " EL PRODUCTO YA EXISTE, EL NOMBRE NO ES VALIDO: ");
                }else{
                    JOptionPane.showMessageDialog(null, " ERROR EL NOMBRE INGRESADO NO ES VALIDO: ");
                }
                fieldNombre.setText("");
                return "";
            }
            listaAlmacenes.setEnabled(false);
            return nombreProducto;
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, " ERROR EL NOMBRE INGRESADO NO ES VALIDO: ");
            fieldNombre.setText("");
            return "";
        }
    }
    
    private boolean validateStr() {
        String nombreProducto=fieldNombre.getText();
        nombre=validarStr(nombreProducto);
        if(!nombre.isEmpty()){
            return true;
        }
        return false;
    }

    private boolean validateInt() {
        String num =fieldCantidad.getText();
        stock = validarInt(num);
        if(stock>0){
            return true;
        }
        return false;
    }
    //Boton para guardar los inputs del usuario
    private void botonGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonGuardarActionPerformed
        if (!this.validateStr()) return;
        if (!this.validateInt()) return;
        Product producto=new Product(nombre, stock);
        for (Warehouse almacen : almacenes) {
            if(almacenSeleccionadoW.name.equals(almacen.name)){
                almacen.products.pushBack(producto);
                fieldCantidad.setText("");
                fieldCantidad.setEnabled(true);
                listaAlmacenes.setEnabled(true);
                fieldNombre.setText("");
                fieldNombre.setEnabled(true);
                Graph.getInstance().needsSave = true;
                stock = 0;
                return;
            }
        }
    }//GEN-LAST:event_botonGuardarActionPerformed
    
    private void listaAlmacenesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_listaAlmacenesActionPerformed
        String almacenSeleccionado=listaAlmacenes.getSelectedItem().toString();
        almacenSeleccionadoW=buscarWearhouse(almacenes, almacenSeleccionado);
    }//GEN-LAST:event_listaAlmacenesActionPerformed

    private void retrocederTextMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_retrocederTextMouseClicked
        this.backToMainMenu();
    }//GEN-LAST:event_retrocederTextMouseClicked
    //Esta funcion es para que cuando el usuario quiera regresar al menu principal la ventana cambie de color
    //Dependiendo si el mouse esta sobre la ventana o no
    private void retrocederTextMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_retrocederTextMouseEntered
        retrocederPanel.setBackground(Color.red);
        retrocederText.setForeground(Color.black);
    }//GEN-LAST:event_retrocederTextMouseEntered
    //Esta funcion es para que cuando el usuario quiera regresar al menu principal la ventana cambie de color
    //Dependiendo si el mouse esta sobre la ventana o no
    private void retrocederTextMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_retrocederTextMouseExited
        retrocederText.setForeground(Color.black);
        retrocederPanel.setBackground(Color.white);
    }//GEN-LAST:event_retrocederTextMouseExited

    private void retrocederPanelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_retrocederPanelMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_retrocederPanelMouseEntered

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botonGuardar;
    private javax.swing.JTextField fieldCantidad;
    private javax.swing.JTextField fieldNombre;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JComboBox<String> listaAlmacenes;
    private javax.swing.JPanel retrocederPanel;
    private javax.swing.JLabel retrocederText;
    private javax.swing.JLabel titulo1;
    private javax.swing.JLabel titulo2;
    private javax.swing.JLabel titulo3;
    private javax.swing.JLabel titulo4;
    // End of variables declaration//GEN-END:variables
}
