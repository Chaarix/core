package edu.cfp.core.controladores;

import edu.cfp.core.dominio.Factura;
import edu.cfp.core.exceptions.ResourceNotFoundException;
import edu.cfp.core.services.factura.FacturaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/facturas")
public class FacturaControllerRest {

    private final FacturaService facturaService;

    public FacturaControllerRest(FacturaService facturaService) {
        this.facturaService = facturaService;
    }

    @GetMapping
    public ResponseEntity<List<Factura>> getFacturas() {

        return ResponseEntity.ok(
                facturaService.getFactura()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Factura> getFactura(
            @PathVariable Integer id) {

        Factura factura = facturaService.getFactura()
                .stream()
                .filter(f -> f.getId().equals(id))
                .findFirst()
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Factura no encontrada con ID: " + id
                        )
                );

        return ResponseEntity.ok(factura);
    }

    @PostMapping
    public ResponseEntity<Factura> altaFactura(
            @RequestBody Factura factura) {

        facturaService.saveFactura(factura);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(factura.getId())
                .toUri();

        return ResponseEntity
                .created(location)
                .body(factura);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Factura> modificarFactura(
            @PathVariable Integer id,
            @RequestBody Factura factura) {

        facturaService.updateFactura(id, factura);

        factura.setId(id);

        return ResponseEntity.ok(factura);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarFactura(
            @PathVariable Integer id) {

        facturaService.deleteFactura(id);

        return ResponseEntity.noContent().build();
    }
}