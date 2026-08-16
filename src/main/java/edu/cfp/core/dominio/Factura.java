package edu.cfp.core.dominio;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Factura {

    private Integer id;

    private String producto;
    private double precioFinal;
    private double precioUnitario;
    private double cantidad;
    private String metodoDePago;
    private String cliente;
    private String fechaEmision;
    private String numeroDeFactura;
    private String DNI;
}
