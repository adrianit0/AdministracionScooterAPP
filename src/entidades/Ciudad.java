package entidades;

import java.util.Set;
import util.ItemReducido;

public class Ciudad  implements ItemReducido {


     private Integer id;
     private String nombre;
     private String provincia;

    public Ciudad() {
    }

	
    public Ciudad(String nombre, String provincia) {
        this.nombre = nombre;
        this.provincia = provincia;
    }
    public Ciudad(String nombre, String provincia, Set sedes, Set empleados) {
       this.nombre = nombre;
       this.provincia = provincia;
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
    public String getProvincia() {
        return this.provincia;
    }
    
    public void setProvincia(String provincia) {
        this.provincia = provincia;
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


