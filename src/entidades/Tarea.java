package entidades;

import itemReducido.Estadotarea;
import itemReducido.Tipotarea;
import java.util.Date;
import util.ItemReducido;


public class Tarea {

     private Integer id;
     private Empleado empleado;
     private Estadotarea estadotarea;
     private Tipotarea tipotarea;
     private String nombre;
     private String observaciones;
     private long fechaAsignacion;
     private int estimacion;

    public Tarea() {
    }

    public Tarea(Empleado empleado, String nombre, long fechaAsignacion, int estimacion) {
       this.empleado = empleado;
       this.nombre = nombre;
       this.fechaAsignacion = fechaAsignacion;
       this.estimacion = estimacion;
    }
   
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
    
    public Empleado getEmpleado() {
        return this.empleado;
    }
    
    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }
    public Estadotarea getEstadotarea() {
        return this.estadotarea;
    }
    
    public void setEstadotarea(Estadotarea estadotarea) {
        this.estadotarea = estadotarea;
    }
    public Tipotarea getTipotarea() {
        return this.tipotarea;
    }
    
    public void setTipotarea(Tipotarea tipotarea) {
        this.tipotarea = tipotarea;
    }
    public String getNombre() {
        return this.nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public long getFechaAsignacion() {
        return this.fechaAsignacion;
    }
    
    public void setFechaAsignacion (long fechaAsignacion) {
        this.fechaAsignacion = fechaAsignacion;
    }
    public int getEstimacion() {
        return this.estimacion;
    }
    
    public void setEstimacion(int estimacion) {
        this.estimacion = estimacion;
    }
}


