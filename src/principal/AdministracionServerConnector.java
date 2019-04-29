package principal;

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
import java.util.Scanner;
import util.Util;
import util.Util.CODIGO;

/**
 *
 * Versión v3: Juego del ahorcado con menu.
 *
 * @author Adrián
 */
public class AdministracionServerConnector {

    private final String hostServerName = "localhost";
    private final int servicePort = 4445;

    // Información de sesion
    private String token = "";
    private boolean logout = false;// Salirse de la sesión automaticamente (Pe: Si expira el token)
    
    public static void main(String[] args) throws IOException {
        (new AdministracionServerConnector()).start();
    }

    public void start() {
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
                    conectado = identificarse();
                    break;
                case 2:
                    conectado = registrarse();
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

    private boolean identificarse() {
        Scanner sc = new Scanner(System.in);
        // Preguntamos los datos para conectarse
        System.out.print("Nombre de usuario: ");
        String usuario = sc.nextLine();
        System.out.print("Contraseña: ");
        String pass = sc.nextLine();

        // Codificamos los datos para la identificación
        String textoEnviar = Util.encode(CODIGO.identificarse, usuario, pass);

        // Enviamos y recibimos los datos con el servidor
        String recibido = enviarTextoUDP(textoEnviar);

        // Si no devuelve nada mostramos error
        if (recibido.isEmpty()) {
            return false;
        }

        // Decodificamos el contenido
        String[] descodificado = Util.decode(recibido);
        CODIGO codigo = CODIGO.fromCode(descodificado[0]);

        int cod = codigo.getCodigo();

        // Para conectarse al servidor queremos que nos devuelva solo
        // los codigos 501 (Identificarse) o 551 (Registrarse)
        if (codigo == CODIGO.loginFailed) {
            System.out.println("Usuario o contraseña equivocada.");
            return false;
        } else if (codigo != CODIGO.conectado && codigo != CODIGO.registrado) {
            System.out.println("No se ha podido conectar. Código: " + cod);
            return false;
        }
        
        token = descodificado[2];

        return true;
    }

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

    /**
     * Se desconecta del servidor y le manda la orden al servidor de eliminar el
     * token
     */
    private void desconectar() {
        // Enviamos el token con el codigo de desconexión, no necesitaremos respuesta.
        enviarTextoUDP(Util.encode(CODIGO.desconectar, token));

        // Reiniciamos todos los valores por defecto
        this.token = "";
        this.logout = false;
    }

    /**
     * Conecta con el servidor pero en modo UTP.
     */
    private void conectarJuego() throws IOException {
        // Conectar juego, con un try-with-resource
        try (   Socket echoSocket = new Socket(hostServerName, servicePort);
                PrintWriter out = new PrintWriter(echoSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
                    ) {
            
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
            String userInput = token; // Como primer valor le enviamos el token, para que se conecte a la partida
            String respuesta = "";
            String procesado = "";


            do {
                if (userInput.equals("exit")) {
                    break;
                }
                // Le envio la info al servidor
                out.println(userInput);
                
                try {
                    // Leo la respuesta del servidor
                    respuesta = in.readLine();
                    
                    // Muestro la respuesta sin procesar (Solo para debug)
                    //System.out.println("Respuesta: "+respuesta);
                    
                    // Procesamos la respuesta.
                    procesado = mostrarMensaje(respuesta);
                    
                    // Si la respuesta procesada es null o EXIT, me salgo del servidor
                    if (procesado == null || procesado.equals("EXIT")) {
                        break;
                    } 
                    
                    // Muestro la cadena procesada (Es decir, ya convertida para ser mostrada)
                    System.out.print(procesado);

                } catch (SocketException err) {
                    break;
                }

            } while ((userInput = stdIn.readLine()) != null);

        } catch (UnknownHostException e) {
            System.err.println("No se conoce el host: " + hostServerName);
        } catch (IOException e) {
            System.err.println("No hay conexión para " + hostServerName);
        }
    }

    public String mostrarMensaje(String respuesta) {
        if (respuesta == null || respuesta.length() == 0) {
            return "";
        }

        String[] opciones = Util.decode(respuesta);
        CODIGO cod = null;
        try {
            cod = CODIGO.fromCode(Integer.parseInt(opciones[0]));
        } catch (NumberFormatException e) {
            cod = CODIGO.desconocido;
            System.err.println("Error de parseo del texto " + opciones[0] + ". Error: " + e.getMessage());
        }

        if (opciones.length == 0) {
            return "";
        }

        switch (cod) {
            case desconocido:
                if (opciones.length > 0) {
                    return opciones[1];
                }
                return "";
            case error:
                return opciones[1];
            case salirDelJuego:
                return "EXIT";
            default:
                System.out.println("Opción desconocida: "+respuesta);

        }

        return "";
    }

    /**
     * Envia un texto UDP al servidor y devuelve su respuesta.
     */
    private String enviarTextoUDP(String textoEnviar) {
        DatagramSocket socket;
        try {
            socket = new DatagramSocket();

            InetAddress address = InetAddress.getByName(hostServerName);

            byte[] buf = textoEnviar.getBytes();
            DatagramPacket packetToSend = new DatagramPacket(buf, buf.length, address, 4445);

            socket.send(packetToSend);

            byte[] recibir = new byte[256];
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
    private void seguirConectado(CODIGO cod) {
        logout = cod == CODIGO.sessionExpired;
        if (logout) {
            System.out.println("ERROR 440: La sesión ha expirado.");
        }
    }
}
