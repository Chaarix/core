package edu.cfp.core.persistence.repositories;

import edu.cfp.core.persistence.entities.ProductoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductosRepository extends JpaRepository<ProductoEntity,Integer> {
}

