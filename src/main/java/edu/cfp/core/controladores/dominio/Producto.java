package edu.cfp.core.controladores.dominio;


import lombok.*;
import org.apache.el.stream.Stream;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Producto {
    private Integer idProducto;
    private String nombre;
    private Double precio;
    private Integer cantidad = 0;


}

