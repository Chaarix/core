package edu.cfp.core.controladores;

import edu.cfp.core.controladores.dominio.Cliente;
import edu.cfp.core.exceptions.BadRequestException;
import edu.cfp.core.exceptions.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteRestController {


    private List<Cliente> clientes = new ArrayList<>();

    @GetMapping
    public ResponseEntity<?> getClientes() {
        return ResponseEntity.ok(clientes);
    }
    @GetMapping("/{usuario}")
    public ResponseEntity<?> getCliente(@PathVariable String usuario) {

        if (usuario.length() != 3) {
            throw new BadRequestException("El usuario no es valido");
        }



        return clientes.stream()
                .filter(
                cliente -> cliente.getNombreEntidad().equalsIgnoreCase(usuario))
                .findFirst()
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente  No encontrado"));

    }

    @PostMapping
    public ResponseEntity<?> altaCliente(@RequestBody Cliente cliente) {
        // 1. Simular la generación de un ID único si tu lista en memoria no lo tiene
        long nuevoId = clientes.size() + 1;
        cliente.setId(nuevoId);

        // 2. Guardar el nuevo cliente en la lista en memoria
        clientes.add(cliente);

        // 3. Construir la URI de ubicación correcta (Ej: http://localhost:8080/tienda/api/v1/clientes/Juan)
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{nombre}") // CORRECCIÓN: Usar llaves {} para definir la variable en la URL
                .buildAndExpand(cliente.getNombreEntidad()) // Reemplaza {nombre} por el valor real
                .toUri();

        // 4. Retornar el código 201 Created con la URL y el objeto insertado
        System.out.printf(String.valueOf(cliente));
        return ResponseEntity.created(location).body(cliente);

    }

    @PutMapping
    public ResponseEntity<?> modficacionCliente(@RequestBody Cliente cliente) {
        Cliente clienteEncontrado = clientes.stream().filter(
                c -> c.getId().equals(cliente.getId())) // Filtro por ID numérico
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con ID: " + cliente.getId()));
        clienteEncontrado.setNombreEntidad(cliente.getNombreEntidad());
        clienteEncontrado.setTelefonoEntidad(cliente.getTelefonoEntidad());
        clienteEncontrado.setTipoIdentificacion(cliente.getTipoIdentificacion());
        clienteEncontrado.setCuitcuilEntidad(cliente.getCuitcuilEntidad());
        clienteEncontrado.setCondicionIva(cliente.getCondicionIva());
        clienteEncontrado.setDniEntidad(cliente.getDniEntidad());
        clienteEncontrado.setPais(cliente.getPais());
        clienteEncontrado.setEmailEntidad(cliente.getEmailEntidad());
        clienteEncontrado.setCiudad(cliente.getCiudad());
        clienteEncontrado.setProvincia(cliente.getProvincia());
        return ResponseEntity.ok(clienteEncontrado);
    }
    @DeleteMapping("/{usuario}")
    public ResponseEntity borrarCliente(@PathVariable String usuario) {
        Cliente clienteEncontrado = clientes.stream().filter(
                        c -> c.getNombreEntidad().equalsIgnoreCase(usuario)).
                findFirst().orElseThrow();
        clientes.remove(clienteEncontrado);
        return ResponseEntity.noContent().build();

    }

}
