package mx.utez.edu.crudexamen;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class MenuControlador {
    @FXML
    private ImageView imgAlumnos,imgMaterias;


    @FXML
    protected void irAlumnos() throws IOException {
        MenuApp.setRoot("gestionAlumnos");

    }
    @FXML
    protected void irMaterias() throws IOException {
        MenuApp.setRoot("gestionMaterias");

    }
    public void initialize() {
        Image imagenMaterias = new Image(getClass().getResource("/img/subjects.png").toExternalForm());
        imgMaterias.setImage(imagenMaterias);
        Image imagenAlumnos = new Image(getClass().getResource("/img/alumnos.png").toExternalForm());
        imgAlumnos.setImage(imagenAlumnos);
    }
}