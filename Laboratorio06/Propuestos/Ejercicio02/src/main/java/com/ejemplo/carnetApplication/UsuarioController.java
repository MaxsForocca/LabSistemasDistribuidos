package com.ejemplo.carnetApplication;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
import io.micronaut.http.MediaType;
import io.micronaut.views.ModelAndView;

import jakarta.inject.Inject;
import java.util.HashMap;
import java.util.Map;

@Controller("/usuario")
public class UsuarioController {

    @Inject
    ComprobarUsuarioService servicio;

    @Get("/")
    public ModelAndView<Map<String, Object>> formularioLogin() {
        return new ModelAndView<>("login", new HashMap<>());
    }

    @Post(uri = "/comprobar", consumes = MediaType.APPLICATION_FORM_URLENCODED)
    public ModelAndView<Map<String, Object>> comprobar(@Body Usuario usuario) {
        Map<String, Object> modelo = new HashMap<>();
        try {
            boolean resultado = servicio.comprobar(usuario.getUsuario(), usuario.getContrasenia());

            if (resultado) {
                modelo.put("mensaje", "Usuario "+ usuario.getUsuario() +" autenticado correctamente");
                return new ModelAndView<>("resultadoUsuario", modelo);
            } else {
                modelo.put("mensaje", "Usuario o contraseña incorrectos");
                return new ModelAndView<>("resultadoUsuario", modelo);
            }
        } catch (Exception e) {
            modelo.put("mensaje", "Error al autenticar el usuario: " + e.getMessage());
            return new ModelAndView<>("resultadoUsuario", modelo);
        }
    }
}
