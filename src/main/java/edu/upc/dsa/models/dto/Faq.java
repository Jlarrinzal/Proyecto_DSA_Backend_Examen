package edu.upc.dsa.models.dto;

public class Faq {

    String pregunta;

    String respuesta;


    public Faq(){}
    public Faq(String pregunta, String respuesta){
        this.setPregunta(pregunta);
        this.setRespuesta(respuesta);
    }

    public String getPregunta() {
        return pregunta;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }
}
