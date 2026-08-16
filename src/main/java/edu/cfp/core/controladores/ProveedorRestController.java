package edu.cfp.core.controladores;

import edu.cfp.core.dominio.Proveedor;
import edu.cfp.core.exceptions.ResourceNotFoundException;
import edu.cfp.core.services.proveedores.ProveedorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/proveedores")
public class ProveedorRestController {

    private final ProveedorService proveedorService;

    public ProveedorRestController(ProveedorService proveedorService) {
        this.proveedorService = proveedorService;
    }

    // GET /proveedores
    @GetMapping
    public ResponseEntity<List<Proveedor>> getProveedores() {

        return ResponseEntity.ok(
                proveedorService.getProveedor()
        );
    }

    // GET /proveedores/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Proveedor> getProveedor(
            @PathVariable Long id) {

        Proveedor proveedor = proveedorService.getProveedor()
                .stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Proveedor no encontrado con ID: " + id
                        )
                );

        return ResponseEntity.ok(proveedor);
    }

    // POST /proveedores
    @PostMapping
    public ResponseEntity<Proveedor> altaProveedor(
            @RequestBody Proveedor proveedor) {

        proveedorService.saveProveedor(proveedor);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(proveedor.getId())
                .toUri();

        return ResponseEntity
                .created(location)
                .body(proveedor);
    }

    // PUT /proveedores/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Proveedor> modificarProveedor(
            @PathVariable Long id,
            @RequestBody Proveedor proveedor) {

        proveedorService.updateProveedor(id, proveedor);

        proveedor.setId(Math.toIntExact(id));

        return ResponseEntity.ok(proveedor);
    }

    // DELETE /proveedores/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProveedor(
            @PathVariable Long id) {

        proveedorService.deleteProveedor(id);

        return ResponseEntity.noContent().build();
    }
}