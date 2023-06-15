package edu.upc.dsa.models.dto;

public class Idioma {

    String correo;

    String idioma;

    public Idioma() {}

    public Idioma(String correo, String idioma){
        this.correo=correo;
        this.idioma=idioma;

    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }
}
