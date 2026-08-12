package edu.cfp.core.dominio;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import lombok.*;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Factura {

    private String producto;
    private double precioFinal;
    private double precioUnitario;
    private double cantidad;
    private String metodoDePago;
    private String cliente;
    private String fechaEmison;
    private String numeroDeFactura;
    private String DNI;
}
