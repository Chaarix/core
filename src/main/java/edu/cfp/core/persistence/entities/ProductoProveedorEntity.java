package edu.cfp.core.persistence.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(
        name = "producto_proveedor",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {"producto_id", "proveedor_id"}
                )
        }
)
@Data
public class ProductoProveedorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "producto_id", nullable = false)
    private ProductoEntity producto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "proveedor_id", nullable = false)
    private ProveedorEntity proveedor;

    @Column(nullable = false)
    private Double precioCosto;
}