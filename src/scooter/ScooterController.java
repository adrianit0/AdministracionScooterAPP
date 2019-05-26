/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scooter;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import conexion.ConectorTCP;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javax.swing.Spring.height;
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
    
    private final int width = 350;
    private final int height = 350;
    
    public ScooterController (Scooter scooter) {
        this.scooter = scooter;
    }
    
    @Override
    public void run() {
        conectar();
        
        // Creamos una imagen Qr en local
        generarImagenQr();
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
    
    private void generarImagenQr () {
        String root = "Qr";
        String ruta = root+"/Scooter" + scooter.getNoSerie() + ".png";
        String texto = "SC:"+scooter.getCodigo();
        
        try {
            // Miramos si existe la carpeta para añadir los QR para crearlo en caso de que no exista
            File carpeta = new File("./"+root);
            if (!carpeta.exists()) {
                carpeta.mkdir();
            }
            
            // Miramos si ya existe el archivo Qr, para no volver a generar más
            File archivo= new File ("./" + ruta);
            if (archivo.exists()) {
                System.out.println("El archivo " +archivo.getAbsolutePath()+" actualmente existe, no se volverá a crear." );
                return;
            }
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(texto, BarcodeFormat.QR_CODE, width, height);

            Path path = FileSystems.getDefault().getPath(ruta);
            System.out.println(path.toFile().getAbsoluteFile());
            MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
            
            System.out.println("Imagen generado correctamente");
        } catch (WriterException | IOException e) {
            System.err.println("ScooterController::generarImagenQr error: " + e.getMessage());
        }
        
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
