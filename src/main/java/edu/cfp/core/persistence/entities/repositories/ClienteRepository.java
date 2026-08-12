package edu.cfp.core.persistence.entities.repositories;

import edu.cfp.core.controladores.dominio.Cliente;
import edu.cfp.core.persistence.entities.ClienteEntity; // Asegúrate de que apunte a tu clase Cliente
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<ClienteEntity, Long> {
    // Al heredar de JpaRepository<Cliente, Long>, Spring ya sabe que el ID es de tipo Long
}
