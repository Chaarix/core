package edu.cfp.core.persistence.repositories;

import edu.cfp.core.persistence.entities.ClienteEntity;
import edu.cfp.core.persistence.entities.ProveedorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProveedorRepository  extends JpaRepository<ProveedorEntity,Integer> {
}
