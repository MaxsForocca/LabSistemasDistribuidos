package com.ejemplo.carnetApplication;

import jakarta.inject.Singleton;

@Singleton
public class ComprobarUsuarioService {

    private final String usuarioRegistrado = "admin";
    private final String contraseniaRegistrada = "1234";

    public boolean comprobar(String usuario, String contrasenia) {
        try {
            return usuarioRegistrado.equals(usuario) && contraseniaRegistrada.equals(contrasenia);
        } catch (Exception e) {
            return false;
        }
    }
}
