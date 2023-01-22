package mx.utez.edu.crudexamen.utils;

public class RegexCheck {
//Clase creada para validar formularios
    public RegexCheck() {
    }
    
    public boolean verificarCorreo(String correo) {
        //Codigo de expresión regular que valida si el string es un correo valido
        return correo.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
    }

    public boolean verificarEdad(String telefono) {
         //Codigo de expresión regular que valida si el string es un numero de telefono valido
        return telefono.matches("[0-9]{1,2}");
    }

    public boolean verificarCampos(String campo) {
         //Codigo de expresión regular que valida si el string es un nombre propio
        return campo.matches("^([A-Z][a-z]+([ ]?[a-z]?['-]?[A-Z][a-z]+)*)$");
    }

    public boolean verificarDir(String campo) {
         //Codigo de expresión regular que valida si el string es una palabra
        return campo.length()>1;
    }
}
