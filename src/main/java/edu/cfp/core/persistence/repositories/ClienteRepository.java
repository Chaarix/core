package edu.cfp.core.persistence.repositories;

import edu.cfp.core.persistence.entities.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<ClienteEntity,Integer> {
}
