package mx.utez.edu.crudexamen.model;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.config.EmbeddedConfiguration;

import java.util.ArrayList;
import java.util.List;

public class DaoAlumno {


    ObjectContainer getConnection() {
        EmbeddedConfiguration config = Db4oEmbedded.newConfiguration();
        config.common().objectClass(Alumno.class).cascadeOnUpdate(true);
        config.common().objectClass(Materia.class).cascadeOnUpdate(true);
        config.common().objectClass(Alumno.class).cascadeOnActivate(true);
        config.common().objectClass(Materia.class).cascadeOnActivate(true);
        return Db4oEmbedded.openFile(config, "base.db4o");
    }

    public void crearEstudiante(String nombre, String apellidos, Integer edad, List<Materia> materias) {
        ObjectContainer db = getConnection();
        try {
            ObjectSet result = db.queryByExample(new Alumno());
            Alumno alumno = new Alumno();
            if (result.size() == 0) {
                alumno.setId(1);
            } else {
                Alumno ultimo = (Alumno) result.get(result.size() - 1);
                alumno.setId(ultimo.getId() + 1);
            }
            alumno.setNombre(nombre);
            alumno.setApellidos(apellidos);
            alumno.setEdad(edad);
            alumno.setMaterias(materias);
            db.store(alumno);
            db.commit();
        } catch (Exception e) {
            e.printStackTrace();
            db.rollback();
        } finally {
            db.close();
        }
    }
    public boolean existeAlumno(Integer id) {
        ObjectContainer db = getConnection();
        try {
            Alumno alumno = new Alumno();
            alumno.setId(id);
            ObjectSet result = db.queryByExample(alumno);
            return result.size() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            db.close();
        }
    }
    public Alumno obtenerAlumno(Integer id) {
        ObjectContainer db = getConnection();
        try {
            Alumno alumno = new Alumno();
            alumno.setId(id);
            ObjectSet result = db.queryByExample(alumno);
            if (result.size() > 0) {
                return (Alumno) result.get(0);
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            db.close();
        }
    }


    public void actualizarEstudiante(Integer id, String nombre, String apellidos, Integer edad) {
        ObjectContainer db = getConnection();
        try {
            ObjectSet<Alumno> result = db.queryByExample(new Alumno(id, null, null, null, null));
            if (result.hasNext()) {
                Alumno alumno = result.next();
                alumno.setNombre(nombre);
                alumno.setApellidos(apellidos);
                alumno.setEdad(edad);
                db.store(alumno);
                db.commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
            db.rollback();
        } finally {
            db.close();
        }
    }

    public boolean agregarMateria(Integer id, Integer Clave) {
        ObjectContainer db = getConnection();
        try {
            ObjectSet resultado = db.queryByExample(new Materia(Clave, null));
            Materia temp = new Materia();
            if (resultado.hasNext())
                temp = (Materia) resultado.next();
            resultado = db.queryByExample(new Alumno(id, null, null, null, null));
            Alumno alumno = new Alumno();
            if (resultado.hasNext())
                alumno = (Alumno) resultado.next();
            List<Materia> temporal = alumno.getMaterias();
            if (temporal.contains(temp))
                return false;
            temporal.add(temp);
            alumno.setMaterias(temporal);
            db.store(alumno);
            db.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            db.rollback();
            return false;
        } finally {
            db.close();
        }
    }
    public boolean desAsignarMateria(Integer id, Integer Clave) {
        ObjectContainer db = getConnection();
        try {
            ObjectSet resultado = db.queryByExample(new Materia(Clave, null));
            Materia temp = new Materia();
            if (resultado.hasNext())
                temp = (Materia) resultado.next();
            resultado = db.queryByExample(new Alumno(id, null, null, null, null));
            Alumno alumno = new Alumno();
            if (resultado.hasNext())
                alumno = (Alumno) resultado.next();
            List<Materia> temporal = alumno.getMaterias();
            if (!temporal.contains(temp))
                return false;
            temporal.remove(temp);
            alumno.setMaterias(temporal);
            db.store(alumno);
            db.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            db.rollback();
            return false;
        } finally {
            db.close();
        }
    }

    public void eliminarEstudiante(Integer id) {
        ObjectContainer db = getConnection();
        try {
            ObjectSet<Alumno> result = db.queryByExample(new Alumno(id, null, null, null, null));
            if (result.hasNext()) {
                Alumno alumno = result.next();
                db.delete(alumno);
                db.commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
            db.rollback();
        } finally {
            db.close();
        }
    }

    public List<Alumno> buscarAlumnos(String palabra) {
        ObjectContainer db = getConnection();
        try {
            List<Alumno> alumnos = new ArrayList<>();
            ObjectSet<Alumno> result = db.queryByExample(Alumno.class);
            while (result.hasNext()) {
                Alumno alumno = result.next();
                String nombre = alumno.getNombre().toLowerCase();
                String apellidos = alumno.getApellidos().toLowerCase();
                String edad = alumno.getEdad().toString().toLowerCase();
                String materias = alumno.getMaterias().toString().toLowerCase();
                palabra = palabra.toLowerCase();
                if (nombre.contains(palabra)
                        || apellidos.contains(palabra)
                        || edad.contains(palabra)
                        || materias.contains(palabra)) {
                    alumnos.add(alumno);
                }
            }
            return alumnos;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            db.close();
        }
    }

    public List<Alumno> obtenerAlumnos() {
        ObjectContainer db = getConnection();
        try {
            List<Alumno> alumnos = new ArrayList<>();
            ObjectSet<Alumno> result = db.query(Alumno.class);
            while (result.hasNext()) {
                alumnos.add(result.next());
            }
            return alumnos;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            db.close();
        }
    }


}
