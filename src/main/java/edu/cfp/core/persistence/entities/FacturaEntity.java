package edu.cfp.core.persistence.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity(name="factura")
@Data
public class FacturaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)

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
