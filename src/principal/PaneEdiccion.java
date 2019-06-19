/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package principal;

import conexion.ConectorTCP;
import entidades.Empleado;
import itemReducido.Ciudad;
import entidades.Tarea;
import itemReducido.*;
import java.awt.Frame;
import java.awt.MouseInfo;
import static java.awt.MouseInfo.getPointerInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import util.CallbackRespuesta;
import util.Paquete;
import util.Util;

/**
 *
 * @author agarcia.gonzalez
 */
public class PaneEdiccion extends javax.swing.JFrame {

    // Mover posicion
    private boolean moving = false;
    private Point start;
    
    private Principal parent;
    private String token;
    private String nick;
    
    // Item reducidos
    private List<Ciudad> ciudades;
    private List<Puesto> puestos;
    private List<Sede> sedes;
    private List<Modelo> modelos;
    private List<Empleado> empleados;
    private List<Tipotarea> tipoTareas;
    private List<Estadotarea> estadoTareas;
    
    // Empleados
    private Map<Integer,Tarea> tareas;
    
    private List<JPanel> paneles;
    
    private javax.swing.JFrame frame;
    
    /**
     * Creates new form PaneEdiccion
     */
    public PaneEdiccion(Principal parent, String nick, String token) {
        //this.setUndecorated(true);
        
        initComponents();
        
        this.setSize(715, 485);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(true);
        
        this.parent = parent;
        this.nick = nick;
        this.token = token;
        this.frame = this;
        
        // Paneles para cambiar, tiene que estar en el mismo orden que se encuentre en la interfaz
        paneles = new ArrayList<JPanel>();
        paneles.add (panelInicio);
        
        cargarItemReducidos ();
    }
    
    private void cargarItemReducidos () {
        ConectorTCP server = ConectorTCP.getInstance();
        
        server.realizarConexion("getCiudades", new CallbackRespuesta() {
            @Override
            public void success(Map<String, String> contenido) {
                ciudades = util.Util.convertMapToList(Ciudad.class, contenido);
            }

            @Override
            public void error(Map<String, String> contenido, Util.CODIGO codigoError) {
                System.err.println("No se han podido coger las ciudades. " + contenido.get("error"));
            }
        });
        
        server.realizarConexion ("getPuestos", new CallbackRespuesta() {
            @Override
            public void success(Map<String, String> contenido) {
                puestos = util.Util.convertMapToList(Puesto.class, contenido);
            }

            @Override
            public void error(Map<String, String> contenido, Util.CODIGO codigoError) {
                System.err.println("No se han podido coger los puestos de trabajo. " + contenido.get("error"));
            }
        });
        
        server.realizarConexion ("getSedes", new CallbackRespuesta() {
            @Override
            public void success(Map<String, String> contenido) {
                sedes = util.Util.convertMapToList(Sede.class, contenido);
            }

            @Override
            public void error(Map<String, String> contenido, Util.CODIGO codigoError) {
                System.err.println("No se han podido coger las sedes. " + contenido.get("error"));
            }
        });
        
        server.realizarConexion ("getModelos", new CallbackRespuesta() {
            @Override
            public void success(Map<String, String> contenido) {
                modelos = util.Util.convertMapToList(Modelo.class, contenido);
            }

            @Override
            public void error(Map<String, String> contenido, Util.CODIGO codigoError) {
                System.err.println("No se han podido coger los modelos de las Scooters. " + contenido.get("error"));
            }
        });
        
        server.realizarConexion ("getEstadoTareas", new CallbackRespuesta() {
            @Override
            public void success(Map<String, String> contenido) {
                estadoTareas = util.Util.convertMapToList(Estadotarea.class, contenido);
            }

            @Override
            public void error(Map<String, String> contenido, Util.CODIGO codigoError) {
                System.err.println("No se han podido coger los estados de las tareas de las Scooters. " + contenido.get("error"));
            }
        });
        
        server.realizarConexion ("getTipoTareas", new CallbackRespuesta() {
            @Override
            public void success(Map<String, String> contenido) {
                tipoTareas = util.Util.convertMapToList(Tipotarea.class, contenido);
            }

            @Override
            public void error(Map<String, String> contenido, Util.CODIGO codigoError) {
                System.err.println("No se han podido coger los tipo de las tareas. " + contenido.get("error"));
            }
        });
        
        server.realizarConexion ("getEmpleados", new CallbackRespuesta() {
            @Override
            public void success(Map<String, String> contenido) {
                empleados = util.Util.convertMapToList(Empleado.class, contenido);
            }

            @Override
            public void error(Map<String, String> contenido, Util.CODIGO codigoError) {
                System.err.println("No se han podido coger los empleados. " + contenido.get("error"));
            }
        });
        
    }
    
    public void addRowToTable (DefaultTableModel modelFactura, Object[] contenido) {
        modelFactura.addRow(contenido);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tablaFacturas = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        botonEmpleado = new javax.swing.JButton();
        botonScooter = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        botonBonos = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        botonCliente = new javax.swing.JButton();
        botonTarea = new javax.swing.JButton();
        jLayeredPane1 = new javax.swing.JLayeredPane();
        panelInicio = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        tablaFacturas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nombre", "Fecha"
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
        jScrollPane1.setViewportView(tablaFacturas);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(715, 485));

        jPanel2.setBackground(new java.awt.Color(51, 51, 51));

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton1.setText("Cerrar sesión");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        botonEmpleado.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        botonEmpleado.setText("Empleado");
        botonEmpleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonEmpleadoActionPerformed(evt);
            }
        });

        botonScooter.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        botonScooter.setText("Scooter");
        botonScooter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonScooterActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Versión 1.0.14");

        botonBonos.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        botonBonos.setText("Bonos");
        botonBonos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonBonosActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Inicio");

        botonCliente.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        botonCliente.setText("Clientes");
        botonCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonClienteActionPerformed(evt);
            }
        });

        botonTarea.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        botonTarea.setText("Tareas");
        botonTarea.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonTareaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(botonEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botonScooter, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botonBonos, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(botonCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botonTarea, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addComponent(jLabel7)
                .addGap(18, 18, 18)
                .addComponent(botonEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(botonScooter, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(botonBonos, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(botonCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(botonTarea, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jLayeredPane1.setBackground(new java.awt.Color(51, 255, 51));
        jLayeredPane1.setForeground(new java.awt.Color(255, 51, 153));

        javax.swing.GroupLayout jLayeredPane1Layout = new javax.swing.GroupLayout(jLayeredPane1);
        jLayeredPane1.setLayout(jLayeredPane1Layout);
        jLayeredPane1Layout.setHorizontalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jLayeredPane1Layout.setVerticalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 465, Short.MAX_VALUE)
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel4.setText("<html>Bienvenidos a la primera versión de la aplicación de administración de ScooterAPP.<br><br>\n");
        jLabel4.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 691, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 322, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 8, 0, 0, new java.awt.Color(255, 153, 0)));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setText("No hay errores. Todo parece ir bien!");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
        );

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel3.setText("Inicio");

        javax.swing.GroupLayout panelInicioLayout = new javax.swing.GroupLayout(panelInicio);
        panelInicio.setLayout(panelInicioLayout);
        panelInicioLayout.setHorizontalGroup(
            panelInicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelInicioLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelInicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelInicioLayout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        panelInicioLayout.setVerticalGroup(
            panelInicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelInicioLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3)
                .addGap(15, 15, 15)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(panelInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLayeredPane1, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(panelInicio, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void botonBonosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonBonosActionPerformed
        botonBonos();
    }//GEN-LAST:event_botonBonosActionPerformed

    private void botonScooterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonScooterActionPerformed
        botonScooter();
    }//GEN-LAST:event_botonScooterActionPerformed

    private void botonEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonEmpleadoActionPerformed
        botonEmpleado();
    }//GEN-LAST:event_botonEmpleadoActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        desconectar();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void botonClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonClienteActionPerformed
        botonCliente();
    }//GEN-LAST:event_botonClienteActionPerformed

    private void botonTareaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonTareaActionPerformed
        botonTareas();
    }//GEN-LAST:event_botonTareaActionPerformed

    private void botonInicio() {
        changeTab(0);
    }
    
    private void botonEmpleado(){
        PanelEmpleados panel = new PanelEmpleados (this, true);
        panel.setVisible(true);
    }
    
    private void botonCliente(){
        PanelCliente panel = new PanelCliente (this, true);
        panel.setVisible(true);
    }
    
    private void botonScooter() {
        PanelScooter panel = new PanelScooter (this, true);
        panel.setVisible(true);
    }
    
    private void botonBonos () {
        PanelBonos panel = new PanelBonos (this, true);
        panel.setVisible(true);
    }
    
    private void botonTareas () {
        PanelTareas panel = new PanelTareas(this, true);
        panel.setVisible(true);
    }
    
    private void desconectar() {
        // Preguntar si desconectar
    }
    
    private void changeTab (int pos) {
        if (pos<0|| pos>=paneles.size())
            return;
        
        for (JPanel p : paneles) {
            p.setVisible(false);
        }
        paneles.get(pos).setVisible(true);
    }

    public List<Ciudad> getCiudades() {
        return ciudades;
    }

    public void setCiudades(List<Ciudad> ciudades) {
        this.ciudades = ciudades;
    }

    public List<Puesto> getPuestos() {
        return puestos;
    }

    public void setPuestos(List<Puesto> puestos) {
        this.puestos = puestos;
    }

    public List<Sede> getSedes() {
        return sedes;
    }

    public List<Modelo> getModelos() {
        return modelos;
    }

    public void setModelos(List<Modelo> modelos) {
        this.modelos = modelos;
    }
    

    public void setSedes(List<Sede> sedes) {
        this.sedes = sedes;
    }

    public List<Empleado> getEmpleados() {
        return empleados;
    }

    public void setEmpleados(List<Empleado> empleados) {
        this.empleados = empleados;
    }

    public List<Tipotarea> getTipoTareas() {
        return tipoTareas;
    }

    public void setTipoTareas(List<Tipotarea> tipoTareas) {
        this.tipoTareas = tipoTareas;
    }

    public List<Estadotarea> getEstadoTareas() {
        return estadoTareas;
    }

    public void setEstadoTareas(List<Estadotarea> estadoTareas) {
        this.estadoTareas = estadoTareas;
    }
    
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botonBonos;
    private javax.swing.JButton botonCliente;
    private javax.swing.JButton botonEmpleado;
    private javax.swing.JButton botonScooter;
    private javax.swing.JButton botonTarea;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel panelInicio;
    private javax.swing.JTable tablaFacturas;
    // End of variables declaration//GEN-END:variables

}