package com.ejemplo.comprasproductos;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import io.micronaut.views.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Controller("/")
public class CompraController {

    @Get("/")
    public ModelAndView<Map<String, Object>> index() {
        return new ModelAndView<>("index", new HashMap<>());
    }

    @Post(uri = "/comprar", consumes = MediaType.APPLICATION_FORM_URLENCODED)
    public ModelAndView<Map<String, Object>> comprar(@Body Producto producto) {
        Map<String, Object> model = new HashMap<>();

        // Validar datos
        if (producto.getCantidad() <= 0) {
            model.put("mensaje", "Lo siento, ingrese una cantidad positiva.");
            return new ModelAndView<>("resultado", model);
        }

        if (producto.getPrecio() < 0) {
            model.put("mensaje", "Lo siento, el precio no puede ser negativo.");
            return new ModelAndView<>("resultado", model);
        }

        double total = producto.getPrecio() * producto.getCantidad();
        model.put("mensaje", "Total a pagar por " + producto.getNombre() + ": S/ " + total);
        return new ModelAndView<>("resultado", model);
    }
}
