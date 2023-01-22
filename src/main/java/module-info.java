module mx.utez.edu.crudexamen {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires db4o;


    opens mx.utez.edu.crudexamen to javafx.fxml;
    opens mx.utez.edu.crudexamen.model to db4o, javafx.base;
    exports mx.utez.edu.crudexamen;
}