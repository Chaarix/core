package edu.cfp.core.controladores.dominio;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Producto {

    @Id // ◄ CRUCIAL: Define la llave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Hace que el ID sea auto-incremental en Postgres (Serial)
    private Integer id;

    private String nombre;

    // Consejo: Cambia String a Double o BigDecimal si vas a hacer cálculos matemáticos con el precio
    private Double precio;

    // Consejo: Cambia String a Integer si vas a restar stock cuando alguien compre
    private Integer stock;

}
