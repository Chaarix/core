package edu.cfp.core.controladores;

import edu.cfp.core.dominio.Cliente;
import edu.cfp.core.exceptions.ResourceNotFoundException;
import edu.cfp.core.services.cliente.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteRestController {

    @Autowired
    private ClienteService clienteService;


    // =========================
    // GET TODOS
    // =========================

    @GetMapping
    public ResponseEntity<List<Cliente>> getClientes() {

        return ResponseEntity.ok(
                clienteService.getCliente()
        );
    }


    // =========================
    // POST
    // =========================

    @PostMapping
    public ResponseEntity<Cliente> altaCliente(
            @RequestBody Cliente cliente) {

        clienteService.saveCliente(cliente);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(cliente.getId())
                .toUri();

        return ResponseEntity
                .created(location)
                .body(cliente);
    }


    // =========================
    // PUT
    // =========================

    @PutMapping("/{id}")
    public ResponseEntity<Cliente> actualizarCliente(
            @PathVariable Long id,
            @RequestBody Cliente cliente) {

        clienteService.updateCliente(id, cliente);

        cliente.setId(id);

        return ResponseEntity.ok(cliente);
    }


    // =========================
    // DELETE
    // =========================

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> borrarCliente(
            @PathVariable Long id) {

        clienteService.deleteCliente(id);

        return ResponseEntity.noContent().build();
    }
}