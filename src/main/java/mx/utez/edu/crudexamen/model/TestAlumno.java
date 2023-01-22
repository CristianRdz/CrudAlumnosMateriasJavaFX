package mx.utez.edu.crudexamen.model;

import com.db4o.ObjectSet;

import java.util.ArrayList;
import java.util.List;

public class TestAlumno {
    public static void main(String[] args) {
        DaoAlumno daoAlumno = new DaoAlumno();
        daoAlumno.crearEstudiante("Juan", "Perez", 20,new ArrayList<>());
        daoAlumno.crearEstudiante("Maria", "Gomez", 21,new ArrayList<>());
        daoAlumno.crearEstudiante("Pedro", "Lopez", 22,new ArrayList<>());
        daoAlumno.crearEstudiante("Luis", "Martinez", 23,new ArrayList<>());
        daoAlumno.crearEstudiante("Ana", "Garcia", 24,new ArrayList<>());
        daoAlumno.crearEstudiante("Jose", "Hernandez", 25,new ArrayList<>());
        daoAlumno.crearEstudiante("Carlos", "Gonzalez", 26,new ArrayList<>());
        daoAlumno.crearEstudiante("Rosa", "Rodriguez", 27,new ArrayList<>());
        daoAlumno.crearEstudiante("Luisa", "Diaz", 28,new ArrayList<>());
        System.out.println("Alumnos creados");
        List<Alumno> alumnos = daoAlumno.obtenerAlumnos();
        for (Alumno a : alumnos) {
            System.out.println(a);
        }

    }
}
