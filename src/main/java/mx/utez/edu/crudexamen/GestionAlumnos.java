package mx.utez.edu.crudexamen;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import mx.utez.edu.crudexamen.model.Alumno;
import mx.utez.edu.crudexamen.model.DaoAlumno;
import mx.utez.edu.crudexamen.model.Materia;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class GestionAlumnos {
    DaoAlumno daoAlumno = new DaoAlumno();

    @FXML
    private TableView<Alumno> tablaAlumnos;
    @FXML
    private TableColumn<Alumno, Integer> colId;
    @FXML
    private TableColumn<Alumno, String> colNombre;
    @FXML
    private TableColumn<Alumno, String> colApellidos;
    @FXML
    private TableColumn<Alumno, Integer> colEdad;
    @FXML
    private TableColumn<Alumno, String> colMaterias;

    @FXML
    private TextField txtId, txtBuscar;

    @FXML
    private void agregarAlumno() throws IOException {
        MenuApp.setRoot("crearAlumnos");
    }

    @FXML
    private void eliminarAlumno() {
        if (txtId.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error al realizar la operación");
            alert.setContentText("El campo de id esta vacio");
            alert.showAndWait();
        } else if (!txtId.getText().matches("[0-9]+")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error al eliminar");
            alert.setContentText("El campo de id solo acepta numeros");
            alert.showAndWait();
        } else if (daoAlumno.existeAlumno(Integer.parseInt(txtId.getText()))) {
            //Se preguntara si se desea eliminar el alumno
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmacion");
            alert.setHeaderText("Confirmacion de eliminacion");
            alert.setContentText("¿Desea eliminar el alumno con id " + txtId.getText() + "?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                int id = Integer.parseInt(txtId.getText());
                daoAlumno.eliminarEstudiante(id);
                Alert alert2 = new Alert(Alert.AlertType.WARNING);
                alert2.setTitle("Eliminado");
                alert2.setHeaderText("Alumno eliminado");
                alert2.setContentText("El alumno se ha eliminado correctamente");
                alert2.showAndWait();
                txtId.setText("");
                refrescarTabla();
            }

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error al eliminar");
            alert.setContentText("El alumno con id " + txtId.getText() + " no existe");
            alert.showAndWait();
        }
    }

    @FXML
    private void modificarAlumno() {
        if (txtId.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error al realizar la operación");
            alert.setContentText("El campo de id esta vacio");
            alert.showAndWait();
        } else if (!txtId.getText().matches("[0-9]+")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error al eliminar");
            alert.setContentText("El campo de id solo acepta numeros");
            alert.showAndWait();
        } else if (daoAlumno.existeAlumno(Integer.parseInt(txtId.getText()))) {
            MenuApp.setData(daoAlumno.obtenerAlumno(Integer.parseInt(txtId.getText())));
            try {
                MenuApp.setRoot("modificarAlumnos");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error al eliminar");
            alert.setContentText("El alumno con id " + txtId.getText() + " no existe");
            alert.showAndWait();
        }
    }

    @FXML
    private void buscarAlumno() {
        List<Alumno> alumnos = daoAlumno.buscarAlumnos(txtBuscar.getText());
        tablaAlumnos.setItems(FXCollections.observableArrayList(alumnos));
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colApellidos.setCellValueFactory(new PropertyValueFactory<>("apellidos"));
        colEdad.setCellValueFactory(new PropertyValueFactory<>("edad"));
        colMaterias.setCellFactory(column -> {
            return new TableCell<Alumno, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (this.getTableRow() != null && this.getTableRow().getItem() != null) {
                        Alumno alumno = (Alumno) this.getTableRow().getItem();
                        List<Materia> materias = alumno.getMaterias();
                        this.setText(String.valueOf(materias.size()));
                    } else {
                        this.setText(null);
                    }
                }
            };
        });
    }

    @FXML
    private void volver() throws IOException {
        MenuApp.setRoot("menu");
    }
    @FXML
    private void asignarMaterias() throws IOException {
        if (txtId.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error al realizar la operación");
            alert.setContentText("El campo de id esta vacio");
            alert.showAndWait();
        } else if (!txtId.getText().matches("[0-9]+")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error al eliminar");
            alert.setContentText("El campo de id solo acepta numeros");
            alert.showAndWait();
        } else if (daoAlumno.existeAlumno(Integer.parseInt(txtId.getText()))) {
            MenuApp.setData(daoAlumno.obtenerAlumno(Integer.parseInt(txtId.getText())));
            try {
                MenuApp.setRoot("asignarMaterias");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error al eliminar");
            alert.setContentText("El alumno con id " + txtId.getText() + " no existe");
            alert.showAndWait();
        }

    }

    public void refrescarTabla() {
        List<Alumno> alumnos = daoAlumno.obtenerAlumnos();
        tablaAlumnos.setItems(FXCollections.observableArrayList(alumnos));
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colApellidos.setCellValueFactory(new PropertyValueFactory<>("apellidos"));
        colEdad.setCellValueFactory(new PropertyValueFactory<>("edad"));
        // Se contaran las materias en vez de colocarlas manualmente
        colMaterias.setCellFactory(column -> {
            return new TableCell<Alumno, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (this.getTableRow() != null && this.getTableRow().getItem() != null) {
                        Alumno alumno = (Alumno) this.getTableRow().getItem();
                        List<Materia> materias = alumno.getMaterias();
                        this.setText(String.valueOf(materias.size()));
                    } else {
                        this.setText(null);
                    }
                }
            };
        });
        tablaAlumnos.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED, event -> {
            try {
                Alumno alumno = tablaAlumnos.getSelectionModel().getSelectedItem();
                String id = alumno.getId().toString();
                txtId.setText(id);
            } catch (Exception e) {
            }
        });
    }


    public void initialize() {
        refrescarTabla();

    }

}

