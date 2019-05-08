/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conexion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.CallbackRespuesta;
import util.PaqueteCliente;
import util.PaqueteServidor;

/**
 *
 * @author agarcia.gonzalez
 */
public class ConectorTCP {
    
    private int paqueteId;
    private String nick;
    private String token;
    
    private boolean realizandoConexion;
    private List<PaqueteServidor> peticiones;
    
    // Singleton
    private static ConectorTCP instance;
    
    private final long TIMEOUT = 30000;
    
    private ConectorTCP() {
        paqueteId=10;
        peticiones = new ArrayList<>();
    }
    
    public static ConectorTCP getInstance () {
        if (instance==null) {
            iniciarServidor ();
        }
        
        return instance;
    }
    
    public static boolean iniciarServidor () {
        instance=new ConectorTCP();
        return true;
    }
    
    public void realizarConexion (String uri, Map<String,String> parametros, CallbackRespuesta response) {
        // Ponemos los valores para realizar la conexión
        PaqueteServidor paquete = new PaqueteServidor();
        paquete.setIdPaquete(getPaqueteID());
        paquete.setNick(nick);
        paquete.setToken(token);
        paquete.setArgumentos(parametros);
        paquete.setUri(uri);
        paquete.setCallback(response);
        
        if (realizandoConexion) {
            peticiones.add(paquete);
        } else {
            //realizar conexión
            RealizarConexion conexion = new RealizarConexion (paquete);
            conexion.start();
        }
    }
    
    
    
    private String getPaqueteID () {
        if (paqueteId==100)
            paqueteId=10;
        return Integer.toString(paqueteId++);
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
    
    /**
     ==========================================================
     
     *  CLASES PARA REALIZAR LA CONEXION ASINCRONAMENTE
     
     * ==========================================================
     */
    
    
    class RealizarConexion extends Thread {
        
        private PaqueteServidor paquete;
        
        public RealizarConexion (PaqueteServidor paquete) {
            this.paquete = paquete;
        }
        
        @Override
        public void run () {
            
        }
    }
    
    class TimeoutConexion extends Thread {
        
        private RealizarConexion con;
        
        public TimeoutConexion (RealizarConexion con) {
            this.con = con;
        }
        
        @Override
        public void run () {
            try {
                this.join(TIMEOUT);
                System.out.println("TIME OUT");
            } catch (InterruptedException ex) {
                System.out.println("ERROR");
            }
        }
    }
}
