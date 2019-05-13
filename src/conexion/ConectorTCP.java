/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conexion;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.CallbackRespuesta;
import util.PaqueteCliente;
import util.PaqueteServidor;
import util.Util;
import util.Util.CODIGO;

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
    
    private boolean conectado;
    private Socket echoSocket;
    private PrintWriter out;
    private BufferedReader in;
    
    // Singleton
    private static ConectorTCP instance;
    
    private final long TIMEOUT = 30000;
    private final String hostServerName="localhost";
    private final int port = 4444;
    
    // Test values
    private String outMessage;
    private String inMessage;
    
    private ConectorTCP() {
        paqueteId=10;
        peticiones = new ArrayList<>();
        conectado=false;
        iniciar();
    }
    
    public static ConectorTCP getInstance () {
        if (instance==null) {
            iniciarServidor ();
        }
        
        return instance;
    }
    
    private boolean iniciar() {
        try {
            echoSocket = new Socket(hostServerName, port);
            out = new PrintWriter(echoSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
            conectado=true;
        } catch (IOException ex) {
            System.err.println("Error de comunicación: " + ex.getMessage());
            conectado=false;
        }
        return conectado;
    }
    
    public static boolean iniciarServidor () {
        instance=new ConectorTCP();
        return true;
    }
    
    public void realizarConexion (String uri, Map<String,String> parametros, CallbackRespuesta response) {
        realizarConexion(nick,token,uri,getPaqueteID(),parametros,response);
    }

    // Para tests
    public void realizarConexion (String nick, String token, String uri, String paqueteid, Map<String,String> parametros, CallbackRespuesta response) {
        if (!conectado) {
            if (!iniciar ()) {
                RuntimeException e = new RuntimeException ("No se ha podido realizar la conexión");
                parametros.put("error", e.getMessage());
                response.error(parametros, Util.CODIGO.notConnection);
                throw e;
            }
        }
            
        // Ponemos los valores para realizar la conexión
        PaqueteServidor paquete = new PaqueteServidor();
        paquete.setIdPaquete(paqueteid);
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
    
    public void realizarConexion (String trama, CallbackRespuesta response) {
        PaqueteServidor paquete = Util.unpackToServer(trama);
        
        if (realizandoConexion) {
            peticiones.add(paquete);
        } else {
            //realizar conexión
            RealizarConexion conexion = new RealizarConexion (paquete);
            conexion.start();
        }
    }
    
    public synchronized void nextQuery () {
        if (peticiones.size()>0) {
            RealizarConexion conexion = new RealizarConexion (peticiones.remove(0));
            conexion.start();
        } else {
            realizandoConexion=false;
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

    public String getOutMessage() {
        return outMessage;
    }

    public String getInMessage() {
        return inMessage;
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
            TimeoutConexion timeOut = new TimeoutConexion (this);
            
            // Conectar juego, con un try-with-resource
            // al ser TRY-WITH-RESOURCE estos se cierran solo al terminar la llave
            try  {
                 // Como primer valor le enviamos el nombre y nº de jugadores al servidor
                String request = Util.packFromServer(paquete);
                
                outMessage = request;

                // Le envio la info al servidor
                out.println(request);

                try {
                    // Leo la respuesta del servidor
                    String respuesta = in.readLine();
                    
                    inMessage = respuesta;

                    // Muestro la respuesta sin procesar (Solo para debug)
                    //System.out.println("Respuesta: "+respuesta);
                    
                    PaqueteCliente paqueteCliente = Util.unpackToCliente(respuesta);

                    CODIGO codigo = paqueteCliente.getCodigo();
                    
                    // Ejecutamos una parte u otra del callback según si devuelve o no errores
                    if (codigo.getCodigo()>=200 && codigo.getCodigo()<=299) {
                        paquete.getCallback().success(paqueteCliente.getArgumentos());
                    } else {
                        paquete.getCallback().error(paqueteCliente.getArgumentos(), paqueteCliente.getCodigo());
                    }
                } catch (SocketException err) {
                    System.err.println("Error en el envio de datos. " + err.toString());
                }

                //System.out.println("Proceso terminado!!!");
                timeOut.interrupt();
                
                nextQuery();

            } catch (UnknownHostException e) {
                System.err.println("No se conoce el host: " + hostServerName);
            } catch (IOException e) {
                System.err.println("No hay conexión para " + hostServerName);
            } 
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
                if (!this.isInterrupted())  {
                    System.out.println("TIME OUT");
                    con.interrupt();
                    in.close();
                }
            } catch (InterruptedException ex) {
                System.out.println("ERROR: " + ex.getMessage());
            } catch (IOException ex) {
                throw new RuntimeException("No se ha podido finalizar", ex);
            }
        }
    }
}
