package mx.utez.edu.crudexamen.model;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.config.EmbeddedConfiguration;

import java.util.ArrayList;
import java.util.List;

public class DaoMateria {
    ObjectContainer getConnection() {
        EmbeddedConfiguration config = Db4oEmbedded.newConfiguration();
        config.common().objectClass(Alumno.class).cascadeOnUpdate(true);
        config.common().objectClass(Materia.class).cascadeOnUpdate(true);
        config.common().objectClass(Alumno.class).cascadeOnActivate(true);
        config.common().objectClass(Materia.class).cascadeOnActivate(true);
        return Db4oEmbedded.openFile(config, "base.db4o");
    }

    public void crearMateria(String nombre) {
        ObjectContainer db = getConnection();
        try {
            ObjectSet result = db.queryByExample(new Materia());
            Materia materia = new Materia();
            if (result.size() == 0) {
                materia.setClave(1);
            } else {
                Materia ultimo = (Materia) result.get(result.size() - 1);
                materia.setClave(ultimo.getClave() + 1);
            }
            materia.setNombre(nombre);
            db.store(materia);
            db.commit();
        } catch (Exception e) {
            e.printStackTrace();
            db.rollback();
        } finally {
            db.close();
        }
    }

    public boolean existeMateria(Integer clave) {
        ObjectContainer db = getConnection();
        try {
            Materia materia = new Materia();
            materia.setClave(clave);
            ObjectSet result = db.queryByExample(materia);
            return result.size() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            db.close();
        }
    }
    public Materia obtenerMateria(Integer clave) {
        ObjectContainer db = getConnection();
        try {
            Materia materia = new Materia();
            materia.setClave(clave);
            ObjectSet result = db.queryByExample(materia);
            if (result.hasNext()) {
                return (Materia) result.next();
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

    public void actualizarMateria(Integer clave, String nombre) {
        ObjectContainer db = getConnection();
        try {
            ObjectSet<Materia> result = db.queryByExample(new Materia(clave, null));
            if (result.hasNext()) {
                Materia materia = result.next();
                materia.setNombre(nombre);
                db.store(materia);
                db.commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
            db.rollback();
        } finally {
            db.close();
        }
    }
    public void eliminarMateria(Integer clave) {
        ObjectContainer db = getConnection();
        try {
            ObjectSet<Materia> result = db.queryByExample(new Materia(clave, null));
            while (result.hasNext()) {
                Materia materia = result.next();
                ObjectSet <Alumno> alumnosConMateria = db.queryByExample(new Alumno());
                while (alumnosConMateria.hasNext()){
                    Alumno alumno = alumnosConMateria.next();
                    if (alumno.getMaterias().contains(materia)){
                        alumno.getMaterias().remove(materia);
                        db.store(alumno);
                        db.commit();
                    }
                }
                db.delete(materia);
                db.commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
            db.rollback();
        } finally {
            db.close();
        }
    }

    public List<Materia> listarMaterias() {
        ObjectContainer db = getConnection();
        try {
            ObjectSet<Materia> result = db.queryByExample(Materia.class);
            List<Materia> materias = new ArrayList<>();
            while (result.hasNext()) {
                materias.add(result.next());
            }
            return materias;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            db.close();
        }
    }

    public List<Materia> buscarMaterias(String palabra) {
        ObjectContainer db = getConnection();
        try {
            List<Materia> materias = new ArrayList<>();
            ObjectSet<Materia> result = db.queryByExample(Materia.class);
            while (result.hasNext()) {
                Materia materia = result.next();
                String nombre = materia.getNombre().toLowerCase();
                String clave = materia.getClave().toString().toLowerCase();
                palabra = palabra.toLowerCase();
                if (nombre.contains(palabra) || clave.contains(palabra)) {
                    materias.add(materia);
                }
            }
            return materias;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            db.close();
        }
    }

}
