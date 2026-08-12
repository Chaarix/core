package edu.cfp.core.controladores;

import edu.cfp.core.controladores.dominio.Cliente;
import edu.cfp.core.persistence.entities.ClienteEntity;
import edu.cfp.core.persistence.entities.repositories.ClienteRepository; // Inyectamos tu repositorio
import edu.cfp.core.exceptions.BadRequestException;
import edu.cfp.core.exceptions.ResourceNotFoundException;
import edu.cfp.core.services.clientes.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteRestController {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ClienteRepository clienteRepository; // 👈 Acceso directo a la BD para mantener intacto el Service

    @GetMapping
    public ResponseEntity<?> getClientes() {
        // 1. Buscamos todas las entidades físicas en PostgreSQL
        List<ClienteEntity> entidades = clienteRepository.findAll();
        List<Cliente> clientesDominio = new ArrayList<>();

        // 2. Mapeamos de ClienteEntity a tu objeto de dominio Cliente
        for (ClienteEntity entity : entidades) {
            Cliente c = new Cliente();
            c.setId(entity.getId());
            c.setNombreEntidad(entity.getNombreEntidad());
            c.setTipoIdentificacion(entity.getTipoIdentificacion());
            c.setTelefonoEntidad(entity.getTelefonoEntidad());
            c.setCuitcuilEntidad(entity.getCuitcuilEntidad());
            c.setCondicionIva(entity.getCondicionIva());
            c.setDniEntidad(entity.getDniEntidad());
            c.setPais(entity.getPais());
            c.setEmailEntidad(entity.getEmailEntidad());
            c.setCiudad(entity.getCiudad());
            c.setProvincia(entity.getProvincia());
            clientesDominio.add(c);
        }

        return ResponseEntity.ok(clientesDominio);
    }

    @GetMapping("/{usuario}")
    public ResponseEntity<?> getCliente(@PathVariable String usuario) {
        if (usuario.length() != 3) {
            throw new BadRequestException("El usuario no es valido");
        }

        // Buscamos la entidad directamente en la base de datos por el campo correspondiente
        return clienteRepository.findAll().stream()
                .filter(entity -> entity.getNombreEntidad().equalsIgnoreCase(usuario))
                .findFirst()
                .map(entity -> {
                    Cliente c = new Cliente();
                    c.setId(entity.getId());
                    c.setNombreEntidad(entity.getNombreEntidad());
                    c.setTipoIdentificacion(entity.getTipoIdentificacion());
                    c.setTelefonoEntidad(entity.getTelefonoEntidad());
                    c.setCuitcuilEntidad(entity.getCuitcuilEntidad());
                    c.setCondicionIva(entity.getCondicionIva());
                    c.setDniEntidad(entity.getDniEntidad());
                    c.setPais(entity.getPais());
                    c.setEmailEntidad(entity.getEmailEntidad());
                    c.setCiudad(entity.getCiudad());
                    c.setProvincia(entity.getProvincia());
                    return ResponseEntity.ok(c);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Cliente No encontrado"));
    }

    @PostMapping
    public ResponseEntity<?> altaCliente(@RequestBody Cliente cliente) {
        // Tu flujo original se mantiene idéntico utilizando tu servicio intacto
        Cliente clienteGuardado = clienteService.saveCliente(cliente);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(clienteGuardado.getId())
                .toUri();

        return ResponseEntity.created(location).body(clienteGuardado);
    }

    @PutMapping
    public ResponseEntity<?> modficacionCliente(@RequestBody Cliente cliente) {
        // Buscamos la entidad en PostgreSQL para verificar existencia
        ClienteEntity entity = clienteRepository.findById(cliente.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con ID: " + cliente.getId()));

        // Actualizamos los campos de la entidad con los datos recibidos del dominio
        entity.setNombreEntidad(cliente.getNombreEntidad());
        entity.setTelefonoEntidad(cliente.getTelefonoEntidad());
        entity.setTipoIdentificacion(cliente.getTipoIdentificacion());
        entity.setCuitcuilEntidad(cliente.getCuitcuilEntidad());
        entity.setCondicionIva(cliente.getCondicionIva());
        entity.setDniEntidad(cliente.getDniEntidad());
        entity.setPais(cliente.getPais());
        entity.setEmailEntidad(cliente.getEmailEntidad());
        entity.setCiudad(cliente.getCiudad());
        entity.setProvincia(cliente.getProvincia());

        clienteRepository.save(entity); // Guardamos la actualización en la BD
        return ResponseEntity.ok(cliente);
    }

    @DeleteMapping("/{usuario}")
    public ResponseEntity<?> borrarCliente(@PathVariable String usuario) {
        ClienteEntity entityEncontrada = clienteRepository.findAll().stream()
                .filter(entity -> entity.getNombreEntidad().equalsIgnoreCase(usuario))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado"));

        clienteRepository.delete(entityEncontrada); // Eliminación física de PostgreSQL
        return ResponseEntity.noContent().build();
    }
}
