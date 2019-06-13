/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dialog;

import conexion.ConectorTCP;
import entidades.Empleado;
import entidades.Scooter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import principal.PaneEdiccion;
import principal.PanelScooter;
import util.CallbackRespuesta;
import util.ItemReducido;
import util.Util;

/**
 *
 * @author agarcia.gonzalez
 */
public class CrudScooter extends javax.swing.JDialog {

    private PanelScooter panelScooter;
    
    private List modelos;
    
    private PaneEdiccion paneEdiccion;
    
    public CrudScooter(java.awt.Frame parent, PanelScooter panelScooter, boolean modal, int id) {
        super(parent, modal);
        initComponents();
        
        this.panelScooter = panelScooter;
        
        campoId.setEnabled(false);
        
        paneEdiccion = (PaneEdiccion) parent;
        
        modelos = paneEdiccion.getModelos();
        
        rellenarComboBox (comboModelo, modelos);
        
        this.setLocationRelativeTo(null);
        
        
        Map<String,String> parametros = new HashMap<>();
        parametros.put("id", id+"");

        ConectorTCP.getInstance().realizarConexion("getScooterBBDD", parametros, new CallbackRespuesta(){
            @Override
            public void success(Map<String, String> contenido) {
                campoId.setText(contenido.get("id"));
                textoNoSerie.setText(contenido.get("noSerie"));
                textoMatricula.setText(contenido.get("matricula"));
                textoCodigo.setText(contenido.get("codigo"));
                textoPrecio.setText(contenido.get("dni"));

                seleccionarValorCombo(comboModelo, modelos, Integer.parseInt(contenido.get("modelo")));

                botonAceptar.setEnabled(true);

                panelScooter.actualizarTabla();
            }

            @Override
            public void error(Map<String, String> contenido, Util.CODIGO codigoError) {
                JOptionPane.showMessageDialog(null, "No se ha cargado al empleado con ID " + id);
            }
        });
    }
    
    private void seleccionarValorCombo (JComboBox combo, List<ItemReducido> lista, int keyValue) {
        combo.setEditable(true);
        for (ItemReducido item : lista) {
            if (item.getIndex()==keyValue) {
                combo.setSelectedItem(item.getValue());
            }
        }
        combo.setEditable(false);
    }
    
    private Integer seleccionarKeyCombo (JComboBox combo, List<ItemReducido> lista) {
        String valor = combo.getSelectedItem().toString();
        for (ItemReducido item : lista) {
            if (item.getValue().equals(valor)) 
                return item.getIndex();
        }
        
        return null;
    }
    
    private void rellenarComboBox (JComboBox combo, List lista) {
        combo.setModel(new DefaultComboBoxModel (lista.toArray()));
        
        //combo.setEditable(true);
        combo.setEnabled(true);
    }
    
    public void aceptar() {
        if (textoMatricula.getText().length()>7) {
            JOptionPane.showMessageDialog(null, "Error: La longitud de la matricula no puede ser superior a 7 carácteres" );
            return;
        }
        
        Scooter scooter = convertirEnScooter();
        
        Map<String,String> parametros = util.Util.convertObjectToMap(scooter);
        Integer puestoId = seleccionarKeyCombo(comboModelo, modelos);
        if (puestoId!=null)
            parametros.put("puestoId", puestoId.toString());
        
        ConectorTCP.getInstance().realizarConexion("updateScooter", parametros, new CallbackRespuesta() {
            @Override
            public void success(Map<String, String> contenido) {
                JOptionPane.showMessageDialog(null, "La scooter se ha modificado correctamente");
                panelScooter.actualizarTabla();
                cerrar();
            }

            @Override
            public void error(Map<String, String> contenido, Util.CODIGO codigoError) {
                JOptionPane.showMessageDialog(null, "Error: " + contenido.get("error"));
            }
            
        });
    }
    
    private void cerrar() {
        this.dispose();
    }
    
    private Scooter convertirEnScooter () {
        Scooter scooter = new Scooter ();
        
        if (campoId.getText()!=null && !campoId.getText().isEmpty())
            scooter.setId(Integer.parseInt(campoId.getText()));
        scooter.setNoSerie(textoNoSerie.getText());
        scooter.setMatricula(textoMatricula.getText());
        scooter.setCodigo(Integer.parseInt(textoCodigo.getText()));
        
        String precioCompra = textoPrecio.getText();
        if (precioCompra!=null&&!precioCompra.isEmpty())
            scooter.setPrecioCompra(Double.parseDouble(precioCompra));
        
        return scooter;
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        textoTitulo = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        campoId = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        botonAceptar = new javax.swing.JButton();
        botonCancelar = new javax.swing.JButton();
        textoNoSerie = new javax.swing.JTextField();
        textoMatricula = new javax.swing.JTextField();
        textoCodigo = new javax.swing.JTextField();
        textoPrecio = new javax.swing.JTextField();
        comboModelo = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        textoTitulo.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        textoTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        textoTitulo.setText("Editar Scooter");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("ID:");

        campoId.setEditable(false);

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("NoSerie:");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("Matricula:");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("Codigo:");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("Precio compra:");

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel11.setText("Modelo:");

        botonAceptar.setText("Aceptar");
        botonAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonAceptarActionPerformed(evt);
            }
        });

        botonCancelar.setText("Cancelar");
        botonCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonCancelarActionPerformed(evt);
            }
        });

        textoNoSerie.setEditable(false);

        comboModelo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Descnonocido" }));
        comboModelo.setEnabled(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(textoTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(campoId))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(textoNoSerie))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(textoMatricula))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(textoCodigo))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(textoPrecio))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(comboModelo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 80, Short.MAX_VALUE)
                        .addComponent(botonCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(botonAceptar, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(76, 76, 76)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(textoTitulo)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(campoId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textoNoSerie, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(textoMatricula, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(textoCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(textoPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(comboModelo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(botonAceptar)
                    .addComponent(botonCancelar))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void botonCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonCancelarActionPerformed
        cerrar();
    }//GEN-LAST:event_botonCancelarActionPerformed

    private void botonAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonAceptarActionPerformed
        aceptar();
    }//GEN-LAST:event_botonAceptarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botonAceptar;
    private javax.swing.JButton botonCancelar;
    private javax.swing.JTextField campoId;
    private javax.swing.JComboBox<String> comboModelo;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JTextField textoCodigo;
    private javax.swing.JTextField textoMatricula;
    private javax.swing.JTextField textoNoSerie;
    private javax.swing.JTextField textoPrecio;
    private javax.swing.JLabel textoTitulo;
    // End of variables declaration//GEN-END:variables
}
