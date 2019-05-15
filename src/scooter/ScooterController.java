/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scooter;

import conexion.ConectorTCP;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.CallbackRespuesta;
import util.Util;

/**
 *
 * @author agarcia.gonzalez
 */
public class ScooterController extends Thread {

    public Scooter scooter;
    private int intentos = 0;
    
    private final int maxIntentos = 10;
    private final long tiempoEntreIntento = 5000;
    
    public ScooterController (Scooter scooter) {
        this.scooter = scooter;
    }
    
    @Override
    public void run() {
        conectar();
    }
    
    private void conectar () {
        Map<String,String> parametros = Util.convertObjectToMap(scooter);
        
        ConectorTCP.getInstance().realizarConexion("loginScooter", parametros, new CallbackRespuesta() {
            @Override
            public void success(Map<String, String> contenido) {
                System.out.println("Conexión exitosa!");
                ejecutando();
            }

            @Override
            public void error(Map<String, String> contenido, Util.CODIGO codigoError) {
                intentos++;
                System.err.println("Error: " + codigoError.toString());
                
                System.out.println("Parametros: ");
                for (Map.Entry<String, String> entry : contenido.entrySet()) {
                    String key = entry.getKey();
                    String value = entry.getValue();
                    
                    System.out.println("\t" + key + ":" + value);
                }
                if (intentos<=maxIntentos) {
                    try {
                        Thread.sleep(tiempoEntreIntento);
                        conectar();
                    } catch (InterruptedException ex) {
                        System.err.println("ScooterController::conectar Error del callback " + ex.getMessage());
                    }
                } else {
                    System.err.println("ScooterController::conectar Error de intentos máximos, se ha sobrepassdo el límite máximo de intentos ");
                }
            }
        });
    }
    
    
    private void ejecutando () {
        // Crear una rutina de acciones a realizar cada cierto tiempo
        try {
            Thread.sleep(10000l);
        } catch (InterruptedException ex) {
            System.err.println("ScooterController(Client)::ejecutando error");
        }
        
        ejecutando();
    }
    
}
