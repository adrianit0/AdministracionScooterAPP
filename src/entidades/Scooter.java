package entidades;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;


public class Scooter  implements java.io.Serializable {


     private Integer id;
     private String noSerie;
     private Integer modelo;
     private String matricula;
     private int codigo;
     private double precioCompra;

    public Scooter() {
        
    }


    public Scooter(String noSerie, Integer modelo, String matricula, int codigo, double precioCompra) {
        this.noSerie = noSerie;
        this.modelo = modelo;
        this.matricula = matricula;
        this.codigo = codigo;
        this.precioCompra = precioCompra;
    }

    public String getNoSerie() {
        return noSerie;
    }

    public void setNoSerie(String noSerie) {
        this.noSerie = noSerie;
    }
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getModelo() {
        return this.modelo;
    }
    
    public void setModelo(Integer modelo) {
        this.modelo = modelo;
    }
    public String getMatricula() {
        return this.matricula;
    }
    
    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }
    public int getCodigo() {
        return this.codigo;
    }
    
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }
    public double getPrecioCompra() {
        return this.precioCompra;
    }
    
    public void setPrecioCompra(double precioCompra) {
        this.precioCompra = precioCompra;
    }
}


