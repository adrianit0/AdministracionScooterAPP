/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package principal;

import conexion.ConectorTCP;
import dialog.CrudCliente;
import entidades.Cliente;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import util.CallbackRespuesta;
import util.Util;

/**
 *
 * @author agarcia.gonzalez
 */
public class PanelCliente extends javax.swing.JDialog {

    private PaneEdiccion parent;
    private DefaultTableModel model;
    
    public PanelCliente(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        this.parent = (PaneEdiccion) parent;
        
        this.setLocationRelativeTo(null);
        
        model = (DefaultTableModel) tablaClientes.getModel();
        
        actualizarTabla();
    }
    
    public void actualizarTabla() {
        ConectorTCP.getInstance().realizarConexion("getClientes", null, new CallbackRespuesta() {
            @Override
            public void success(Map<String, String> contenido) {
                model.setRowCount(0);   // Reiniciamos el contenido de la tabla
                
                List<Cliente> lista = util.Util.convertMapToList(Cliente.class, contenido);
                
                for (Cliente e : lista) {
                    parent.addRowToTable(model, new Object[] {
                        e.getId(),
                        e.getNombre() + " " + e.getApellido1() + ((e.getApellido2()!=null) ? " " + e.getApellido2() : ""),
                        e.getEmail()
                    });
                }
            }

            @Override
            public void error(Map<String, String> contenido, Util.CODIGO codigoError) {
                System.out.println("ERROR: " + contenido.get("error"));
            }
            
        });
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelCliente = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tablaClientes = new javax.swing.JTable();
        botonEditar = new javax.swing.JButton();
        botonDarBaja = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBackground(new java.awt.Color(153, 255, 102));

        panelCliente.setBackground(new java.awt.Color(51, 51, 51));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Clientes");

        tablaClientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nombre", "Email"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(tablaClientes);
        if (tablaClientes.getColumnModel().getColumnCount() > 0) {
            tablaClientes.getColumnModel().getColumn(0).setResizable(false);
            tablaClientes.getColumnModel().getColumn(0).setPreferredWidth(1);
            tablaClientes.getColumnModel().getColumn(1).setPreferredWidth(10);
            tablaClientes.getColumnModel().getColumn(2).setPreferredWidth(15);
        }

        botonEditar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        botonEditar.setText("Editar Cliente");
        botonEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonEditarActionPerformed(evt);
            }
        });

        botonDarBaja.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        botonDarBaja.setText("Dar baja cliente");
        botonDarBaja.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonDarBajaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelClienteLayout = new javax.swing.GroupLayout(panelCliente);
        panelCliente.setLayout(panelClienteLayout);
        panelClienteLayout.setHorizontalGroup(
            panelClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelClienteLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelClienteLayout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(panelClienteLayout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 664, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(botonEditar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 175, Short.MAX_VALUE)
                            .addComponent(botonDarBaja, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 175, Short.MAX_VALUE))))
                .addContainerGap())
        );
        panelClienteLayout.setVerticalGroup(
            panelClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelClienteLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 389, Short.MAX_VALUE)
                    .addGroup(panelClienteLayout.createSequentialGroup()
                        .addComponent(botonEditar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(botonDarBaja)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelCliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelCliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void botonEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonEditarActionPerformed
        abrirEdiccionCliente();
    }//GEN-LAST:event_botonEditarActionPerformed

    private void botonDarBajaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonDarBajaActionPerformed
        darBajaCliente();
    }//GEN-LAST:event_botonDarBajaActionPerformed

    private void abrirEdiccionCliente() {
        int id = tablaClientes.getSelectedRow();
        
        if (id>=0) {
            String rowId = tablaClientes.getValueAt(id, 0).toString();
            
            int seleccionado = Integer.parseInt(rowId);
            
            
            CrudCliente crud = new CrudCliente (parent, this, true, seleccionado);
            crud.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "No tienes seleccionado ningún cliente");
        }
    }
    
    
    private void darBajaCliente () {
        int id = tablaClientes.getSelectedRow();
        
        if (id>=0) {
            int eleccion = JOptionPane.showConfirmDialog(parent, "Quieres dar de baja a este cliente?");
            if (eleccion==0) {
                String rowId = tablaClientes.getValueAt(id, 0).toString();
            
                int seleccionado = Integer.parseInt(rowId);
                
                Map<String,String> parametros = new HashMap<String,String>();
                parametros.put("id", seleccionado+"");
                
                ConectorTCP.getInstance().realizarConexion("bajaCliente", parametros, new CallbackRespuesta() {
                    @Override
                    public void success(Map<String, String> contenido) {
                        JOptionPane.showMessageDialog(null, "Se ha dado de baja al cliente");
                        actualizarTabla();
                    }

                    @Override
                    public void error(Map<String, String> contenido, Util.CODIGO codigoError) {
                        JOptionPane.showMessageDialog(null, "No se ha podido dar de baja al cliente: " + contenido.get("error"));
                    }
                    
                });
            }
        } else {
            JOptionPane.showMessageDialog(null, "No tienes seleccionado ningún cliente");
        }
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botonDarBaja;
    private javax.swing.JButton botonEditar;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JPanel panelCliente;
    private javax.swing.JTable tablaClientes;
    // End of variables declaration//GEN-END:variables
}
