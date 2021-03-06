package itemReducido;

import util.ItemReducido;

public class Estadotarea implements ItemReducido {


     private Integer id;
     private String nombre;
     
    public Estadotarea() {
    }
    
    public Estadotarea(Integer id){
        this.id=id;
    }

	
    public Estadotarea(String nombre) {
        this.nombre = nombre;
    }
   
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getNombre() {
        return this.nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    @Override
    public String toString() {
        return nombre;
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