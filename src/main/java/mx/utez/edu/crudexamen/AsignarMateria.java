package mx.utez.edu.crudexamen;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import mx.utez.edu.crudexamen.model.Alumno;
import mx.utez.edu.crudexamen.model.DaoAlumno;
import mx.utez.edu.crudexamen.model.DaoMateria;
import mx.utez.edu.crudexamen.model.Materia;

import java.io.IOException;
import java.util.List;

public class AsignarMateria {
    DaoMateria daoMateria = new DaoMateria();
    DaoAlumno daoAlumno = new DaoAlumno();
    Alumno alumno = new Alumno();
    @FXML
    private TableView<Materia> tablaMaterias, tablaMateriasAlumno;
    @FXML
    private TableColumn<Alumno, Integer> colClave, colClaveAlumno;
    @FXML
    private TableColumn<Alumno, String> colNombre, colNombreAlumno;
    @FXML
    private TextField txtId, txtBuscar, txtClaveAsignar, txtClaveDesasignar;

    @FXML
    private void buscarMateria() {
        List<Materia> materias = daoMateria.buscarMaterias(txtBuscar.getText());
        tablaMaterias.setItems(FXCollections.observableArrayList(materias));
        colClave.setCellValueFactory(new PropertyValueFactory<>("clave"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
    }

    @FXML
    private void volver() throws IOException {
        MenuApp.setRoot("gestionAlumnos");
    }

    @FXML
    private void cargarAlumno() {
        if (!txtId.getText().isEmpty() && txtId.getText().matches("[0-9]+")) {
            alumno = daoAlumno.obtenerAlumno(Integer.parseInt(txtId.getText()));
            if (alumno != null) {
                refrescarTablas();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("No se encontro el alumno con el id: " + txtId.getText());
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("El id debe ser un numero");
            alert.showAndWait();
        }
    }

    @FXML
    private void asignarMateria() {
        if (!txtClaveAsignar.getText().isEmpty() && txtClaveAsignar.getText().matches("[0-9]+")) {
            if (daoMateria.existeMateria(Integer.parseInt(txtClaveAsignar.getText()))) {
                if (alumno != null) {
                    Materia materia = daoMateria.obtenerMateria(Integer.parseInt(txtClaveAsignar.getText()));
                    if (daoAlumno.agregarMateria(alumno.getId(), materia.getClave())) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Informacion");
                        alert.setHeaderText("La materia se asigno correctamente");
                        alert.showAndWait();
                        alumno = daoAlumno.obtenerAlumno(alumno.getId());
                        refrescarTablas();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText("El alumno ya tiene asignada la materia");
                        alert.showAndWait();
                    }


                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("No se pudo asignar la materia");
                    alert.showAndWait();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("No se encontro la materia con la clave: " + txtClaveAsignar.getText());
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("La clave debe ser un numero");
            alert.showAndWait();
        }

    }

    @FXML
    public void desAsignarMateria() {
        if (!txtClaveDesasignar.getText().isEmpty() && txtClaveDesasignar.getText().matches("[0-9]+")) {
            if (daoMateria.existeMateria(Integer.parseInt(txtClaveDesasignar.getText()))) {
                if (alumno != null) {
                    Materia materia = daoMateria.obtenerMateria(Integer.parseInt(txtClaveDesasignar.getText()));
                    if (daoAlumno.desAsignarMateria(alumno.getId(), materia.getClave())) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Informacion");
                        alert.setHeaderText("La materia se desasigno correctamente");
                        alert.showAndWait();
                        alumno = daoAlumno.obtenerAlumno(alumno.getId());
                        refrescarTablas();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText("El alumno no tiene asignada la materia");
                        alert.showAndWait();
                    }
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("No se encontro la materia con la clave: " + txtClaveDesasignar.getText());
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("La clave debe ser un numero");
            alert.showAndWait();
        }
    }

    public void refrescarTablas() {
        List<Materia> materias = daoMateria.listarMaterias();
        tablaMaterias.setItems(FXCollections.observableArrayList(materias));
        colClave.setCellValueFactory(new PropertyValueFactory<>("clave"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        tablaMaterias.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED, event -> {
            try {
                Materia materia = tablaMaterias.getSelectionModel().getSelectedItem();
                String clave = String.valueOf(materia.getClave());
                txtClaveAsignar.setText(clave);
            } catch (Exception e) {
            }
        });
        if (alumno != null) {
            List<Materia> materiasAlumno = alumno.getMaterias();
            tablaMateriasAlumno.setItems(FXCollections.observableArrayList(materiasAlumno));
            colClaveAlumno.setCellValueFactory(new PropertyValueFactory<>("clave"));
            colNombreAlumno.setCellValueFactory(new PropertyValueFactory<>("nombre"));
            tablaMateriasAlumno.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED, event -> {
                try {
                    Materia materia = tablaMateriasAlumno.getSelectionModel().getSelectedItem();
                    String clave = String.valueOf(materia.getClave());
                    txtClaveDesasignar.setText(clave);
                } catch (Exception e) {
                }
            });
        }
    }

    public void initialize() {
        if (MenuApp.getData() != null) {
            alumno = (Alumno) MenuApp.getData();
            txtId.setText(String.valueOf(alumno.getId()));
        }
        refrescarTablas();
    }
}
