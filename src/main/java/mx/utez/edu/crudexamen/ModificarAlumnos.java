package mx.utez.edu.crudexamen;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import mx.utez.edu.crudexamen.model.Alumno;
import mx.utez.edu.crudexamen.model.DaoAlumno;
import mx.utez.edu.crudexamen.utils.RegexCheck;

import java.io.IOException;
import java.util.ArrayList;

public class ModificarAlumnos {
    DaoAlumno daoAlumno = new DaoAlumno();
    RegexCheck regexCheck = new RegexCheck();
    @FXML
    TextField txtNombre, txtApellidos, txtEdad, txtId;
    @FXML
    Button btnModificar;
    Alumno alumno = new Alumno();



    @FXML
    private void volver() throws IOException {
        MenuApp.setRoot("gestionAlumnos");
        MenuApp.setData(null);
    }

    private boolean validarCampos() {
        if (txtNombre.getText().isEmpty() || txtApellidos.getText().isEmpty() || txtEdad.getText().isEmpty()) {
            btnModificar.setDisable(true);
            return false;
        }
        if (!regexCheck.verificarCampos(txtNombre.getText()) || !regexCheck.verificarCampos(txtApellidos.getText()) || !regexCheck.verificarEdad(txtEdad.getText())) {
            btnModificar.setDisable(true);
            return false;

        }
        btnModificar.setDisable(false);
        return true;
    }

    @FXML
    private void limpiarCampos() {
        txtNombre.setText("");
        txtApellidos.setText("");
        txtEdad.setText("");
    }

    @FXML
    private void validarEdad() {
        if (regexCheck.verificarEdad(txtEdad.getText())) {
            txtEdad.setStyle("-fx-border-color:  green");
        } else {
            txtEdad.setStyle("-fx-border-color: red");
        }
        validarCampos();
    }

    @FXML
    private void validarNombre() {
        if (regexCheck.verificarCampos(txtNombre.getText())) {
            txtNombre.setStyle("-fx-border-color:  green");
        } else {
            txtNombre.setStyle("-fx-border-color: red");
        }
        validarCampos();
    }

    @FXML
    private void validarApellidos() {
        if (regexCheck.verificarCampos(txtApellidos.getText())) {
            txtApellidos.setStyle("-fx-border-color:  green");
        } else {
            txtApellidos.setStyle("-fx-border-color: red");
        }
        validarCampos();
    }

    @FXML
    private void modificarAlumno() throws IOException {
        if (validarCampos()) {
            daoAlumno.actualizarEstudiante(Integer.parseInt(txtId.getText()), txtNombre.getText(), txtApellidos.getText(), Integer.parseInt(txtEdad.getText()));
            limpiarCampos();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Información");
            alert.setHeaderText("Alumno modificado");
            alert.setContentText("El alumno se ha modificado correctamente");
            alert.showAndWait();
            MenuApp.setRoot("gestionAlumnos");
            MenuApp.setData(null);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Ha ocurrido un error");
            alert.setContentText("Ocurrió un error al crear el alumno, por favor verifique que los campos no estén vacíos");
            alert.showAndWait();
            MenuApp.setRoot("gestionAlumnos");
        }
    }
    public void initialize() {
        if (MenuApp.getData() != null) {
            alumno = (Alumno) MenuApp.getData();
            txtNombre.setText(alumno.getNombre());
            txtApellidos.setText(alumno.getApellidos());
            txtEdad.setText(String.valueOf(alumno.getEdad()));
            txtId.setText(String.valueOf(alumno.getId()));
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Ha ocurrido un error");
            alert.setContentText("Ocurrió un error al cargar los datos del alumno");
            alert.showAndWait();
        }
    }
}
