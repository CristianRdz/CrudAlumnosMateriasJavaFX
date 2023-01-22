package mx.utez.edu.crudexamen.model;

import java.util.List;

public class TestMateria {
    public static void main(String[] args) {
        DaoMateria daoMateria = new DaoMateria();
        daoMateria.crearMateria("Matematicas");
        daoMateria.crearMateria("Español");
        daoMateria.crearMateria("Ingles");
        daoMateria.crearMateria("Fisica");
        daoMateria.crearMateria("Quimica");
        daoMateria.crearMateria("Biologia");
        List<Materia> materias = daoMateria.listarMaterias();
        for (Materia materia : materias) {
            System.out.println(materia);
        }
    }
}
