package edu.cfp.core.dominio;


import lombok.*;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Producto {

    private Integer id;
    private String nombre;
    private String precio;
    private String stock;

}
