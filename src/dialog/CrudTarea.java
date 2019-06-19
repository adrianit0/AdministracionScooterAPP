/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dialog;

import conexion.ConectorTCP;
import entidades.Tarea;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import principal.PaneEdiccion;
import principal.PanelEmpleados;
import principal.PanelTareas;
import util.CallbackRespuesta;
import util.ItemReducido;
import util.Util;

/**
 *
 * @author agarcia.gonzalez
 */
public class CrudTarea extends javax.swing.JDialog {

    private boolean esEditar;
    private PanelTareas panelTareas;
    
    private List empleadoList;
    private List tipoTareas;
    private List estadoTareas;
    
    private PaneEdiccion paneEdiccion;
    
    /**
     * Creates new form CrudEmpleado
     */
    public CrudTarea(java.awt.Frame parent, PanelTareas panelTareas, boolean modal, boolean esEditar, int id) {
        super(parent, modal);
        initComponents();
        
        this.esEditar = esEditar;
        this.panelTareas = panelTareas;
        
        campoId.setEnabled(false);
        
        textoTitulo.setText(esEditar ? "Editar tarea" : "Nueva tarea");
        
        paneEdiccion = (PaneEdiccion) parent;
        empleadoList = paneEdiccion.getEmpleados();
        tipoTareas = paneEdiccion.getTipoTareas();
        estadoTareas = paneEdiccion.getEstadoTareas();
        
        rellenarComboBox (comboEmpleado, empleadoList);
        rellenarComboBox (comboTipoTarea, tipoTareas);
        rellenarComboBox (comboEstadoTarea, estadoTareas);
        
        this.setLocationRelativeTo(null);
        
        if (esEditar)
            botonAceptar.setEnabled(false);
        
        if (esEditar) {
            Map<String,String> parametros = new HashMap<>();
            parametros.put("id", id+"");

            ConectorTCP.getInstance().realizarConexion("getTarea", parametros, new CallbackRespuesta(){
                @Override
                public void success(Map<String, String> contenido) {
                    campoId.setText(contenido.get("id"));
                    textoNombre.setText(contenido.get("nombre"));
                    textoObservacion.setText(contenido.get("observacion"));
                    Date fechaAsignacion = new Date(Long.parseLong(contenido.get("fechaAsignacion")));
                    datePicker.setValue(fechaAsignacion);
                    textoEstimacion.setText(contenido.get("estimacion"));
                    
                    seleccionarValorCombo(comboEmpleado, empleadoList, Integer.parseInt(contenido.get("empleadoId")));
                    seleccionarValorCombo(comboTipoTarea, tipoTareas, Integer.parseInt(contenido.get("tipoTareaId")));
                    seleccionarValorCombo(comboEstadoTarea, estadoTareas, Integer.parseInt(contenido.get("estadoTareaId")));

                    botonAceptar.setEnabled(true);
                }

                @Override
                public void error(Map<String, String> contenido, Util.CODIGO codigoError) {
                    JOptionPane.showMessageDialog(null, "No se ha cargado la tarea con ID " + id);
                }
            });
        }
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
        Tarea tarea = convertirEnTarea();
        
        Map<String,String> parametros = util.Util.convertObjectToMap(tarea);
        Integer tipoTareaId = seleccionarKeyCombo(comboTipoTarea, tipoTareas);
        Integer estadoTareaId = seleccionarKeyCombo(comboEstadoTarea, estadoTareas);
        Integer empleadoId = seleccionarKeyCombo(comboEmpleado, empleadoList);
        if (tipoTareaId!=null)
            parametros.put("tipoTareaId",  tipoTareaId.toString());
        if (estadoTareaId!=null)
            parametros.put("estadoTareaId", estadoTareaId.toString());
        if (empleadoId!=null)
            parametros.put("empleadoId", empleadoId.toString());
        
        String uri = esEditar ? "updateTarea" : "createTarea";
        
        ConectorTCP.getInstance().realizarConexion(uri, parametros, new CallbackRespuesta() {
            @Override
            public void success(Map<String, String> contenido) {
                JOptionPane.showMessageDialog(null, esEditar ? "La tarea se ha modificado correctamente" : "La tarea se ha creado correctamente");
                panelTareas.actualizarTabla();
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
    
    private Tarea convertirEnTarea () {
        Tarea tarea = new Tarea ();
        
        if (campoId.getText()!=null && !campoId.getText().isEmpty())
            tarea.setId(Integer.parseInt(campoId.getText()));
        tarea.setNombre(textoNombre.getText());
        tarea.setObservaciones(textoObservacion.getText());
        tarea.setEstimacion(Integer.parseInt(textoEstimacion.getText()));
        java.util.Date fechaAsignacion = (java.util.Date) datePicker.getValue();
        if (fechaAsignacion!=null)
            tarea.setFechaAsignacion(fechaAsignacion.getTime());
        
        return tarea;
    }
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
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
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        botonAceptar = new javax.swing.JButton();
        botonCancelar = new javax.swing.JButton();
        textoNombre = new javax.swing.JTextField();
        textoObservacion = new javax.swing.JTextField();
        textoEstimacion = new javax.swing.JTextField();
        comboEmpleado = new javax.swing.JComboBox<>();
        comboTipoTarea = new javax.swing.JComboBox<>();
        comboEstadoTarea = new javax.swing.JComboBox<>();
        datePicker = new javax.swing.JSpinner();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        textoTitulo.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        textoTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        textoTitulo.setText("Editar tarea");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("ID:");

        campoId.setEditable(false);

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Nombre:");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("Observaciones:");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("Fecha asignación:");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("Estimación:");

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel11.setText("Empleado:");

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel12.setText("Tipo tarea:");

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel13.setText("Estado tarea:");

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

        comboEmpleado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Descnonocido" }));
        comboEmpleado.setEnabled(false);

        comboTipoTarea.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Descnonocido" }));
        comboTipoTarea.setEnabled(false);

        comboEstadoTarea.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Descnonocido" }));
        comboEstadoTarea.setEnabled(false);

        datePicker.setModel(new javax.swing.SpinnerDateModel());

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
                        .addComponent(textoNombre))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(textoObservacion))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(datePicker))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(textoEstimacion))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(comboEmpleado, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(comboTipoTarea, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(comboEstadoTarea, 0, 249, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
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
                    .addComponent(textoNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(textoObservacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(datePicker, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(textoEstimacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(comboEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(comboTipoTarea, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(comboEstadoTarea, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
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
    private javax.swing.JComboBox<String> comboEmpleado;
    private javax.swing.JComboBox<String> comboEstadoTarea;
    private javax.swing.JComboBox<String> comboTipoTarea;
    private javax.swing.JSpinner datePicker;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JTextField textoEstimacion;
    private javax.swing.JTextField textoNombre;
    private javax.swing.JTextField textoObservacion;
    private javax.swing.JLabel textoTitulo;
    // End of variables declaration//GEN-END:variables
}
