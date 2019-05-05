/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author adrian
 */
public class Util {
    
    private static final String hostServerName = "localhost";
    private static final int servicePort = 4445;
    
    /**
     * Convierte codigos dificiles de enviar por letras distinguibles.
     * 
     * Además permite modificar el codigo sin que este afecte al cliente y servidor
     * siempre y cuando ambos usen la misma versión del protocolo.
     * 
     * Igualmente no se recomienda modificar los códigos de error una vez ha sido
     * seleccionado
     */
    public enum CODIGO {
        // 0 - 99. Juego
        desconocido             (-1),
        error                   (3),
        ponerMenu               (30),
        
        // 200 - 299. Mensajes de confirmación (Status OK)
        ok                      (200),
        
        // 400 - 449. Errores del cliente
        forbidden               (403),  // Intentar acceder sin tener privilegios necesarios
        notFound                (404),
        sessionExpired          (440),
        
        // 450 - 499. Errores del servidor
        internalError           (450),
        
        // 500 - 599. UDP
        identificarse           (500),
        conectado               (501),
        loginFailed             (502),
        
        registrarse             (550),
        registrado              (551),
        
        desconectar             (580)   // Cierra la sesión eliminando el token.
        
        ;
        
        int codigo;
        CODIGO (int codigo) {
            this.codigo = codigo;
        }
        
        public int getCodigo() {
            return codigo;
        }
        
        public static CODIGO fromCode (int code) {
            CODIGO[] cod = CODIGO.values();
            for (CODIGO c : cod) {
                if (c.getCodigo()==code)
                    return c;
            }
            
            return CODIGO.desconocido;
        }
        
        public static CODIGO fromCode (String code) {
            CODIGO cod = CODIGO.desconocido;
            try {
                cod = Util.CODIGO.fromCode(Integer.parseInt(code));
            } catch (NumberFormatException e) {
                cod = Util.CODIGO.desconocido;
                System.err.println("Error de parseo del texto "+code+". Error: "+e.getMessage());
            }
            return cod;
        }
    };
    
    // TODO: Poner al menos una manera de codificar el texto de manera online.
    // Es más seguro que ir en texto plano. Luego tiene que volver a ser igual
    // en el decode.
    public enum ENCRIPTADOR {
        plain, hybridCriptography //<- Este es el que usaré
    };

    private static final String separator = ";";
    private static final String separatorArgs = "[:]";
    private static final ENCRIPTADOR encriptacion = ENCRIPTADOR.plain;
    
    /**
     * Convierte una cadena de texto en un paquete 
     * 
     * TODO: Convertir el paquete en un DTO
     */
    public static Paquete unpack (String cadena) {
        String[] decoded = decode(cadena);
        
        if (decoded==null || decoded.length<3) {
            System.err.println("Error Util::unpack: El paquete no se ha formado correctamente.");
            //throw new Exception();
            return null;
        }
        
        // Extraemos el contenido del paquete
        CODIGO cod = CODIGO.fromCode(decoded[0]);
        String nick = decoded[1];
        String token = decoded[2];
        String uri = decoded[3];
        Map<String,String> parametros = new HashMap<>();
        if (decoded.length>=4) {
            for (int i = 4; i < decoded.length; i++) {
                String[] type = decoded[i].split(separatorArgs);
                if (type.length<2) {
                    // NO ES UN ARGUMENTO.
                    // TODO: devolver correctamente el mensaje de error
                    System.err.println("La variable " + decoded[i] + " no es un parametro");
                    continue;
                }
                parametros.put(type[0], type[1]);
            }
        }
        
        // Lo almacenamos en un objeto de tipo Paquete
        Paquete pack = new Paquete();
        pack.setCodigo(cod);
        pack.setNick(nick);
        pack.setToken(token);
        pack.setUri(uri);
        pack.setArgumentos(parametros);
        
        // Lo devolvemos
        return pack;
    }
    
    /**
     * Convierte un paquete en un String.
     * 
     * TODO: Convertir el paquete en un DTO
     */
    public static String pack (Paquete paquete) {
        String parametros = "";
        for (Map.Entry<String, String> entry : paquete.getArgumentos().entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            
            parametros += key + ":" + value +";";
        }
        
        String encoded = encode (paquete.getCodigo(), paquete.getNick(), paquete.getToken(), paquete.getUri(), parametros);
        
        return encoded;
    }
    
    /**
     * Convierte el texto del servidor a una sola linea
     */
    private static String encode (CODIGO code) {
        return encriptar(encriptacion, code.getCodigo()+"");
    }
    
    /**
     * Convierte el texto del servidor a una sola linea
     */
    private static String encode (CODIGO code, String... contenido) {
        return encriptar(encriptacion, code.getCodigo()+separator+String.join(separator, contenido));
    }
    
    /**
     * Desencripta el contenido de la cadena en un array de String
     */
    private static String[] decode (String cadena) {
        return desencriptar(encriptacion,cadena).replace("\0","").split(separator);
    }
    
    /**
     * Encripta el código para presentar una mejor seguridad en la transferencia
     * de datos del servidor-cliente.
     * 
     * De momento no encripta nada, pero el método ya está creado.
     * 
     * TODO: Utilizar encriptación asimétrica (clave publica-privada) para la encriptación.
     */
    private static String encriptar (ENCRIPTADOR cod, String texto) {
        return texto;
    }
    
    
    /**
     * Desencripta un texto. El método de encriptacion debe ser el mismo que lleva.
     * 
     */
    private static String desencriptar (ENCRIPTADOR cod, String texto) {
        return texto;
    }
    
    public static boolean enviarPaqueteUDP (Paquete paqueteEnviar, CallbackRespuesta response) {
        DatagramSocket socket;
        Paquete paqueteRecibir = null;
        try {
            socket = new DatagramSocket();

            InetAddress address = InetAddress.getByName(hostServerName);

            String textoEnviar = Util.pack(paqueteEnviar);
            byte[] buf = textoEnviar.getBytes();
            DatagramPacket packetToSend = new DatagramPacket(buf, buf.length, address, servicePort);

            socket.send(packetToSend);

            byte[] recibir = new byte[1024];
            DatagramPacket packetToReceive = new DatagramPacket(recibir, recibir.length);

            socket.receive(packetToReceive);
            String received = new String(packetToReceive.getData(), 0, packetToReceive.getLength());
            paqueteRecibir = Util.unpack(received);

            socket.close();
            CODIGO cod = paqueteRecibir.getCodigo();
            if (cod==CODIGO.error || cod.getCodigo()>=400) {
                response.error(paqueteRecibir==null?null:paqueteRecibir.getArgumentos());
                return false;
            }
            
            response.success(paqueteRecibir.getArgumentos());
            return true;
        } catch (SocketException | UnknownHostException ex) {
            System.out.println("Error: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
        }

        response.error(paqueteRecibir==null?null:paqueteRecibir.getArgumentos());
        return false;
    }
    
    /**
     * Envia un texto UDP al servidor y devuelve su respuesta.
     * 
     * @deprecated use {@link #enviarPaqueteUDP()} instead.
     */
    @Deprecated
    public static String enviarTextoUDP(String textoEnviar) {
        DatagramSocket socket;
        try {
            socket = new DatagramSocket();

            InetAddress address = InetAddress.getByName(hostServerName);

            
            byte[] buf = textoEnviar.getBytes();
            DatagramPacket packetToSend = new DatagramPacket(buf, buf.length, address, 4445);

            socket.send(packetToSend);

            byte[] recibir = new byte[1024];
            DatagramPacket packetToReceive = new DatagramPacket(recibir, recibir.length);

            socket.receive(packetToReceive);

            String received = new String(packetToReceive.getData(), 0, packetToReceive.getLength());

            socket.close();

            return received;
        } catch (SocketException | UnknownHostException ex) {
            System.out.println("Error: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
        }

        return "";
    }

    /**
     * Comprueba que la sesión ha expirado
     */
    public static boolean seguirConectado(CODIGO cod) {
        Boolean logout = cod == CODIGO.sessionExpired;
        if (logout) {
            System.out.println("ERROR 440: La sesión ha expirado.");
        }
        
        return logout;
    }
    
    // Mejoraría cambiar el cifrado
    public static String crearTokenUsuario () {
        return Integer.toString(Math.abs(Double.toString(Math.random()*Math.random()).hashCode()));
    }
}