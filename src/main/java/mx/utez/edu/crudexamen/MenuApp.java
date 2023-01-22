package mx.utez.edu.crudexamen;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuApp extends Application {
    static Object dataForShare;
    static String rootForShare;
    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("menu"), 600, 480);
        stage.setScene(scene);
        stage.setTitle("CRUD Examen");
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        rootForShare = fxml;
        scene.setRoot(loadFXML(fxml));
    }
    static String getRoot(){
        return rootForShare;
    }
    static void setData(Object data){
        dataForShare = data;
    }
    static Object getData(){
        return dataForShare;
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MenuApp.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }
}