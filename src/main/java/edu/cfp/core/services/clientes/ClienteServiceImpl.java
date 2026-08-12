package edu.cfp.core.services.clientes;

import edu.cfp.core.controladores.dominio.Cliente;
import edu.cfp.core.persistence.entities.ClienteEntity;
import edu.cfp.core.persistence.entities.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Lazy
@Service("CLIENTE_DATABASE")
@ConditionalOnProperty(
        value = "clientes.estrategia",
        havingValue = "EN_BD")
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public Cliente saveCliente(Cliente cliente) {
        // 1. Creamos la entidad que entiende JPA/PostgreSQL
        ClienteEntity entity = new ClienteEntity();

        // 2. Pasamos los datos desde 'cliente' hacia 'entity' usando los setters
        entity.setNombreEntidad(cliente.getNombreEntidad());
        entity.setTipoIdentificacion(cliente.getTipoIdentificacion());
        entity.setTelefonoEntidad(cliente.getTelefonoEntidad());
        entity.setCuitcuilEntidad(cliente.getCuitcuilEntidad());
        entity.setCondicionIva(cliente.getCondicionIva());
        entity.setDniEntidad(cliente.getDniEntidad());
        entity.setPais(cliente.getPais());
        entity.setEmailEntidad(cliente.getEmailEntidad());
        entity.setCiudad(cliente.getCiudad());
        entity.setProvincia(cliente.getProvincia());

        // 3. Guardamos la entidad (ahora sí coincide el tipo esperado por el repositorio)
        ClienteEntity entityGuardada = clienteRepository.save(entity);

        // 4. Le asignamos al objeto de dominio el ID generado automáticamente por PostgreSQL
        cliente.setId(entityGuardada.getId());

        // 5. Devolvemos el objeto del dominio listo para el controlador
        return cliente;
    }
}
