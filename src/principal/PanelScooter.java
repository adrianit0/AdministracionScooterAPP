/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package principal;

import conexion.ConectorTCP;
import dialog.CrudScooter;
import entidades.Scooter;
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
public class PanelScooter extends javax.swing.JDialog {

    private PaneEdiccion parent;
    private DefaultTableModel model;
    
    
    public PanelScooter(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        this.parent = (PaneEdiccion) parent;
        
        this.setLocationRelativeTo(null);
        
        model = (DefaultTableModel) tablaScooter.getModel();
        
        actualizarTabla();
    }
    
    public void actualizarTabla() {
        ConectorTCP.getInstance().realizarConexion("getScootersBBDD", null, new CallbackRespuesta() {
            @Override
            public void success(Map<String, String> contenido) {
                model.setRowCount(0);   // Reiniciamos el contenido de la tabla
                
                List<Scooter> lista = util.Util.convertMapToList(Scooter.class, contenido);
                
                for (Scooter s : lista) {
                    parent.addRowToTable(model, new Object[] {
                        s.getId(),
                        s.getNoSerie(),
                        s.getMatricula(),
                        s.getCodigo()
                    });
                }
            }

            @Override
            public void error(Map<String, String> contenido, Util.CODIGO codigoError) {
                System.out.println("ERROR: " + contenido.get("error"));
            }
            
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelScooter = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tablaScooter = new javax.swing.JTable();
        botonEditar = new javax.swing.JButton();
        botonEliminar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBackground(new java.awt.Color(153, 255, 102));

        panelScooter.setBackground(new java.awt.Color(51, 51, 51));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Scooters");

        tablaScooter.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "noSerie", "Matricula", "Codigo"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(tablaScooter);

        botonEditar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        botonEditar.setText("Editar scooter");
        botonEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonEditarActionPerformed(evt);
            }
        });

        botonEliminar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        botonEliminar.setText("Eliminar scooter");
        botonEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonEliminarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelScooterLayout = new javax.swing.GroupLayout(panelScooter);
        panelScooter.setLayout(panelScooterLayout);
        panelScooterLayout.setHorizontalGroup(
            panelScooterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelScooterLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelScooterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelScooterLayout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(panelScooterLayout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 664, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelScooterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(botonEditar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 175, Short.MAX_VALUE)
                            .addComponent(botonEliminar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 175, Short.MAX_VALUE))))
                .addContainerGap())
        );
        panelScooterLayout.setVerticalGroup(
            panelScooterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelScooterLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelScooterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 389, Short.MAX_VALUE)
                    .addGroup(panelScooterLayout.createSequentialGroup()
                        .addComponent(botonEditar)
                        .addGap(18, 18, 18)
                        .addComponent(botonEliminar)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelScooter, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelScooter, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void botonEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonEditarActionPerformed
        abrirEdiccionScooter();
    }//GEN-LAST:event_botonEditarActionPerformed

    private void botonEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonEliminarActionPerformed
        eliminarScooter();
    }//GEN-LAST:event_botonEliminarActionPerformed

    private void abrirEdiccionScooter() {
        int id = tablaScooter.getSelectedRow();
        
        if (id>=0) {
            String rowId = tablaScooter.getValueAt(id, 0).toString();
            
            int seleccionado = Integer.parseInt(rowId);
            
            System.out.println("SELECCIONADO: " + seleccionado);
            
            CrudScooter crud = new CrudScooter (parent, this, true, seleccionado);
            crud.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "No tienes seleccionado ninguna scooter");
        }
    }
    
    private void eliminarScooter () {
        int id = tablaScooter.getSelectedRow();
        
        if (id>=0) {
            int eleccion = JOptionPane.showConfirmDialog(parent, "Quieres eliminar esta scooter?");
            if (eleccion==0) {
                String rowId = tablaScooter.getValueAt(id, 0).toString();
            
                int seleccionado = Integer.parseInt(rowId);
                
                Map<String,String> parametros = new HashMap<String,String>();
                parametros.put("id", seleccionado+"");
                
                ConectorTCP.getInstance().realizarConexion("deleteScooter", parametros, new CallbackRespuesta() {
                    @Override
                    public void success(Map<String, String> contenido) {
                        JOptionPane.showMessageDialog(null, "Se ha eliminado la scooter");
                        actualizarTabla();
                    }

                    @Override
                    public void error(Map<String, String> contenido, Util.CODIGO codigoError) {
                        JOptionPane.showMessageDialog(null, "No se ha podido eliminar la scooter: " + contenido.get("error"));
                    }
                    
                });
            }
        } else {
            JOptionPane.showMessageDialog(null, "No tienes seleccionado ninguna scooter");
        }
    }
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botonEditar;
    private javax.swing.JButton botonEliminar;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JPanel panelScooter;
    private javax.swing.JTable tablaScooter;
    // End of variables declaration//GEN-END:variables
}