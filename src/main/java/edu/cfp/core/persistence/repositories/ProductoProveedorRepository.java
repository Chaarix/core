package edu.cfp.core.persistence.repositories;

import edu.cfp.core.persistence.entities.ProductoProveedorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductoProveedorRepository
        extends JpaRepository<ProductoProveedorEntity, Integer> {

    List<ProductoProveedorEntity> findByProveedorId(Integer proveedorId);

    List<ProductoProveedorEntity> findByProductoId(Integer productoId);


}