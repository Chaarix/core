package edu.cfp.core.dominio;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class ProductoProveedor {

    private Integer id;

    private Integer productoId;

    private Integer proveedorId;

    private Double precioCosto;
}