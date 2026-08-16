package edu.cfp.core.persistence.repositories;

import edu.cfp.core.persistence.entities.ClienteEntity;
import edu.cfp.core.persistence.entities.FacturaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacturaRepository extends JpaRepository<FacturaEntity,Integer> {
}
