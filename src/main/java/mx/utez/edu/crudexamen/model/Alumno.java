package mx.utez.edu.crudexamen.model;

import java.io.Serializable;
import java.util.List;

public class Alumno implements Serializable {
    // atributos
    // constructores (vacio y especifico)
    // encapsulamiento
    // toString
    // implements Serializable
    private Integer id;
    private String nombre;
    private String apellidos;
    private Integer edad;
    private List <Materia> materias;

    public Alumno() {
    }

    public Alumno(Integer id, String nombre, String apellidos, Integer edad, List<Materia> materias) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.edad = edad;
        this.materias = materias;
    }

    @Override
    public String toString() {
        return "Alumno{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", edad=" + edad +
                ", materias=" + materias +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public List<Materia> getMaterias() {
        return materias;
    }

    public void setMaterias(List<Materia> materias) {
        this.materias = materias;
    }
}
