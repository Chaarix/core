package edu.cfp.core.services.cliente;

import edu.cfp.core.dominio.Cliente;
import edu.cfp.core.dominio.Producto;
import edu.cfp.core.persistence.entities.ClienteEntity;
import edu.cfp.core.persistence.entities.ProductoEntity;
import edu.cfp.core.persistence.repositories.ClienteRepository;
import edu.cfp.core.persistence.repositories.ProductosRepository;
import edu.cfp.core.services.producto.ProductoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Primary
@Slf4j
@Service("CLIENTES_BD")
@ConditionalOnProperty(
        value = "clientes.estrategia",
        havingValue = "EN_BD"
)
public class ClienteServiceBDImpl implements ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteServiceBDImpl(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Override
    public List<Cliente> getCliente() {
        return clienteRepository.findAll()
                .stream()
                .map(clienteEntity -> {

                    Cliente cliente = new Cliente();

                    cliente.setId(clienteEntity.getId());
                    cliente.setNombreEntidad(clienteEntity.getNombreEntidad());
                    cliente.setTipoIdentificacion(clienteEntity.getTipoIdentificacion());
                    cliente.setTelefonoEntidad(clienteEntity.getTelefonoEntidad());
                    cliente.setCuitcuilEntidad(clienteEntity.getCuitcuilEntidad());
                    cliente.setCondicionIva(clienteEntity.getCondicionIva());
                    cliente.setDniEntidad(clienteEntity.getDniEntidad());
                    cliente.setPais(clienteEntity.getPais());
                    cliente.setEmailEntidad(clienteEntity.getEmailEntidad());
                    cliente.setCiudad(clienteEntity.getCiudad());
                    cliente.setProvincia(clienteEntity.getProvincia());


                    return cliente;
                })
                .collect(Collectors.toList());
    }

    @Override
    public void saveCliente(Cliente cliente) {

        ClienteEntity clienteEntity = new ClienteEntity();

        clienteEntity.setNombreEntidad(cliente.getNombreEntidad());
        clienteEntity.setTipoIdentificacion(cliente.getTipoIdentificacion());
        clienteEntity.setTelefonoEntidad(cliente.getTelefonoEntidad());
        clienteEntity.setCuitcuilEntidad(cliente.getCuitcuilEntidad());
        clienteEntity.setCondicionIva(cliente.getCondicionIva());
        clienteEntity.setDniEntidad(cliente.getDniEntidad());
        clienteEntity.setPais(cliente.getPais());
        clienteEntity.setEmailEntidad(cliente.getEmailEntidad());
        clienteEntity.setCiudad(cliente.getCiudad());
        clienteEntity.setProvincia(cliente.getProvincia());

        ClienteEntity guardado =
                clienteRepository.save(clienteEntity);

        cliente.setId(guardado.getId());
    }

    @Override
    public void deleteCliente(Long id) {
        if (!clienteRepository.existsById(Math.toIntExact(id))) {
            throw new RuntimeException("Cliente no encontrado con ID: " + id);
        }

        clienteRepository.deleteById(Math.toIntExact(id));
    }

    @Override
    public void updateCliente(Long id, Cliente cliente) {

        ClienteEntity clienteEntity =
                clienteRepository.findById(Math.toIntExact(id))
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Cliente no encontrado con ID: " + id
                                ));

        clienteEntity.setNombreEntidad(cliente.getNombreEntidad());
        clienteEntity.setTipoIdentificacion(cliente.getTipoIdentificacion());
        clienteEntity.setTelefonoEntidad(cliente.getTelefonoEntidad());
        clienteEntity.setCuitcuilEntidad(cliente.getCuitcuilEntidad());
        clienteEntity.setCondicionIva(cliente.getCondicionIva());
        clienteEntity.setDniEntidad(cliente.getDniEntidad());
        clienteEntity.setPais(cliente.getPais());
        clienteEntity.setEmailEntidad(cliente.getEmailEntidad());
        clienteEntity.setCiudad(cliente.getCiudad());
        clienteEntity.setProvincia(cliente.getProvincia());

        clienteRepository.save(clienteEntity);
    }

}



