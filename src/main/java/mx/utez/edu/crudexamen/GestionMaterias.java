package mx.utez.edu.crudexamen;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import mx.utez.edu.crudexamen.model.Alumno;
import mx.utez.edu.crudexamen.model.DaoMateria;
import mx.utez.edu.crudexamen.model.Materia;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class GestionMaterias {
    DaoMateria daoMateria = new DaoMateria();
    @FXML
    private TableView<Materia> tablaMaterias;
    @FXML
    private TableColumn<Alumno, Integer> colClave;
    @FXML
    private TableColumn<Alumno, String> colNombre;
    @FXML
    private TextField txtId, txtBuscar;

    @FXML
    private void agregarMateria() throws IOException {
        try {
            TextInputDialog inputdialog = new TextInputDialog();
            inputdialog.setContentText("Nombre: ");
            inputdialog.setHeaderText("Crear materia");
            Optional<String> result = inputdialog.showAndWait();
            if (result.get().length() > 0) {
                daoMateria.crearMateria(result.get());
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Informacion");
                alert.setHeaderText("Materia creada");
                alert.setContentText("La materia " + result.get() + " ha sido creada");
                alert.showAndWait();
                refrescarTabla();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Error al crear materia");
                alert.setContentText("No se ha podido crear la materia");
                alert.showAndWait();
            }
        }catch (Exception e) {
        }
    }

    @FXML
    private void modificarMateria() {
        try{
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
            } else if (daoMateria.existeMateria(Integer.parseInt(txtId.getText()))) {
                Materia materia = daoMateria.obtenerMateria(Integer.parseInt(txtId.getText()));
                TextInputDialog inputDialog2 = new TextInputDialog(materia.getNombre());
                inputDialog2.setContentText("Nombre: ");
                inputDialog2.setHeaderText("Modificar materia");
                Optional<String> result = inputDialog2.showAndWait();
                if (result.get().length() > 0) {
                    daoMateria.actualizarMateria(Integer.parseInt(txtId.getText()), result.get());
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Informacion");
                    alert.setHeaderText("Materia modificada");
                    alert.setContentText("La materia " + result.get() + " ha sido modificada");
                    alert.showAndWait();
                    refrescarTabla();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Error al modificar materia");
                    alert.setContentText("No se ha podido modificar la materia");
                    alert.showAndWait();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Error al eliminar");
                alert.setContentText("No existe una materia con el id " + txtId.getText());
                alert.showAndWait();
            }
        }catch (Exception e) {
        }
    }

    @FXML
    private void eliminarMateria() {
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
        } else if (daoMateria.existeMateria(Integer.parseInt(txtId.getText()))) {
            //Se preguntara si se desea eliminar el alumno
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmacion");
            alert.setHeaderText("Confirmacion de eliminacion");
            alert.setContentText("¿Desea eliminar el alumno con id " + txtId.getText() + "?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                int id = Integer.parseInt(txtId.getText());
                daoMateria.eliminarMateria(id);
                Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                alert1.setTitle("Informacion");
                alert1.setHeaderText("Materia eliminada");
                alert1.setContentText("La materia con id " + id + " ha sido eliminada");
                alert1.showAndWait();
                refrescarTabla();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error al eliminar");
            alert.setContentText("No existe una materia con el id " + txtId.getText());
            alert.showAndWait();
        }
    }

    @FXML
    private void buscarMateria() {
        List<Materia> materias = daoMateria.buscarMaterias(txtBuscar.getText());
        tablaMaterias.setItems(FXCollections.observableArrayList(materias));
        colClave.setCellValueFactory(new PropertyValueFactory<>("clave"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
    }

    @FXML
    private void volver() throws IOException {
        MenuApp.setRoot("menu");
    }

    public void refrescarTabla() {
        List<Materia> materias = daoMateria.listarMaterias();
        tablaMaterias.setItems(FXCollections.observableArrayList(materias));
        colClave.setCellValueFactory(new PropertyValueFactory<>("clave"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        tablaMaterias.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED, event -> {
            try {
                Materia materia = tablaMaterias.getSelectionModel().getSelectedItem();
                String clave = String.valueOf(materia.getClave());
                txtId.setText(clave);
            } catch (Exception e) {
            }
        });
    }

    public void initialize() {
        refrescarTabla();
    }
}
