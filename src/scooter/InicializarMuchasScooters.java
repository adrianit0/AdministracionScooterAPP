/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scooter;

/**
 *
 * @author agarcia.gonzalez
 */
public class InicializarMuchasScooters {
    public static void main(String[] args) {
        crearScooter(1, 123123, 36.512899f, -6.276407f, 0.82f);
        crearScooter(2, 135246, 36.510626f, -6.276703f, 1f);
        crearScooter(3, 111111, 36.508984f, -6.277094f, 0.23f);
        crearScooter(4, 222222, 36.511192f, -6.271775f, 0.13f);
        crearScooter(5, 333333, 36.506559f, -6.268625f, 0.56f);
    }
    
    public static void crearScooter (int serie, int codigo, float lat, float lon, float bateria) {
        Scooter scooter = new Scooter();
        
        scooter.setNoSerie("Scooter#S"+serie);
        scooter.setBateria(100f);
        scooter.setCodigo(codigo);
        scooter.setBloqueado(true); // Empezará bloqueada hasta que haga login
        scooter.setBateria(bateria);
        
        scooter.setLatitud(lat);
        scooter.setLongitud(lon);
        
        ScooterController controlador = new ScooterController (scooter);
        controlador.start();
    }
}