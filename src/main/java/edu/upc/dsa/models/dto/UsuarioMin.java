package edu.upc.dsa.models.dto;

public class UsuarioMin {


    String nickname;

    String fecha;

    double puntos;

    String avatar;

    public UsuarioMin(){}

    public UsuarioMin(String nickname,String fecha, double puntos, String avatar){
        this.setNickname(nickname);
        this.setFecha(fecha);
        this.setPuntos(puntos);
        this.setAvatar(avatar);
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public double getPuntos() {
        return puntos;
    }

    public void setPuntos(double puntos) {
        this.puntos = puntos;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
