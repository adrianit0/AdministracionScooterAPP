package test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import util.CallbackRespuesta;
import util.Paquete;
import util.Util;
import util.Util.CODIGO;

/**
 * @author Adrián
 */
public class AdministracionServerConnector {

    // Información de sesion
    private String token = "";
    private boolean logout = false;// Salirse de la sesión automaticamente (Pe: Si expira el token)
    
    public static void main(String[] args) throws IOException {
        (new AdministracionServerConnector()).execute();
    }
    
    public void execute() {
        identificarse();
    }

    public void menu() {
        Scanner sc = new Scanner(System.in);
        int opcion = 0;

        while (opcion != 3) {

            System.out.println("1 - Identificarse");
            System.out.println("2 - Registrarse");
            System.out.println("3 - Salir");
            System.out.print("Tu opción: ");

            opcion = sc.nextInt();
            boolean conectado = false;
            switch (opcion) {
                case 1:
                    //conectado = identificarse();
                    break;
                case 2:
                    //conectado = registrarse();
                    break;
                case 3:
                    System.out.println("Adios!");
                    break;
                default:
                    System.out.println("Opción erronea, elija otra opción");
                    break;
            }

            if (conectado) {
                
                logout = false;   // Devolvemos el valor a falso, por si se conecta de nuevo
            }
        }
        sc.close();
    }

    private void identificarse() {
        Map<String,String> parametros = new HashMap<String,String>();
        
        parametros.put("nick", "jose");
        parametros.put("pass", "1234");
        
        Paquete paqueteEnviar = new Paquete();
        paqueteEnviar.setCodigo(CODIGO.identificarse);
        paqueteEnviar.setNick("guest");
        paqueteEnviar.setToken("0");
        paqueteEnviar.setUri("login");
        paqueteEnviar.setArgumentos(parametros);
        
        /*Util.enviarPaqueteUDP(paqueteEnviar, new CallbackRespuesta() {
            @Override
            public void success(Map<String,String> contenido) {
                System.out.println("EXITO");
                token = parametros.get("token");
                
                for (Map.Entry<String, String> entry : contenido.entrySet()) {
                    String key = entry.getKey();
                    String value = entry.getValue();
                    
                    System.out.println(key + ": "+value);
                }
            }

            @Override
            public void error(Map<String,String> contenido) {
                System.out.println("ERROR: " + contenido.get("error"));
            }
            
        });*/
        
        
    }
/*
    private boolean registrarse() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Nombre de usuario: ");
        String usuario = sc.nextLine();
        System.out.print("Contraseña: ");
        String pass = sc.nextLine();
        System.out.print("Escribela de nuevo: ");
        String pass2 = sc.nextLine();

        if (!pass.equals(pass2)) {
            System.out.println("Las contraseñas no son la misma");
            return false;
        }

        String textoEnviar = Util.encode(CODIGO.registrarse, usuario, pass);

        String recibido = enviarTextoUDP(textoEnviar);

        // Si no devuelve nada mostramos error
        if (recibido.isEmpty()) {
            System.out.println("El servidor no ha devuelto nada.");
            return false;
        }

        // Decodificamos el contenido
        String[] descodificado = Util.decode(recibido);
        CODIGO codigo = CODIGO.fromCode(descodificado[0]);

        // Miramos si se conecta
        if (codigo != CODIGO.conectado) {
            System.out.println("No se ha podido conectar. Código: " + codigo.getCodigo());
            return false;
        }

        token = descodificado[2];

        return true;
    }
*/
    /**
     * Se desconecta del servidor y le manda la orden al servidor de eliminar el
     * token
     *//*
    private void desconectar() {
        // Enviamos el token con el codigo de desconexión, no necesitaremos respuesta.
        enviarTextoUDP(Util.encode(CODIGO.desconectar, token));

        // Reiniciamos todos los valores por defecto
        this.token = "";
        this.logout = false;
    }*/
}
