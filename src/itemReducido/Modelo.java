package itemReducido;

import util.ItemReducido;


public class Modelo implements ItemReducido {


     private Integer id;
     private String marca;
     private String modelo;

    public Modelo() {
    }
    
    public Modelo (Integer id) {
        this.id = id;
    }
	
    public Modelo(String marca, String modelo) {
        this.marca = marca;
        this.modelo = modelo;
    }
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    public String getMarca() {
        return this.marca;
    }
    
    public void setMarca(String marca) {
        this.marca = marca;
    }
    public String getModelo() {
        return this.modelo;
    }
    
    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    @Override
    public String toString() {
        return marca + " " + modelo;
    }
    
    

    @Override
    public int getIndex() {
        return id;
    }

    @Override
    public String getValue() {
        return toString();
    }
}