package edu.cfp.core.controladores;

import edu.cfp.core.configuraciones.ConfigurationParameters;
import edu.cfp.core.dominio.Producto;
import edu.cfp.core.services.producto.ProductoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/productos")
public class ProductosControllerRest {

    private final ProductoService productosService;
    private final ConfigurationParameters configurationParameters;

    public ProductosControllerRest(ProductoService productosService, ConfigurationParameters configurationParameters) {this.productosService = productosService;this.configurationParameters = configurationParameters;}

    @GetMapping
    public ResponseEntity<List<Producto>> getProductos() {

        System.out.println("params: " + configurationParameters);

        List<Producto> productos = productosService.getProductos();

        return ResponseEntity.ok(productos);
    }



    @PostMapping
    public ResponseEntity<Producto> altaProducto(
            @RequestBody Producto producto) {

        productosService.saveProducto(producto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(producto.getId())
                .toUri();

        return ResponseEntity
                .created(location)
                .body(producto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> borrarProducto(
            @PathVariable Integer id) {

        productosService.deleteProducto(id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizarProducto(
            @PathVariable Integer id,
            @RequestBody Producto producto) {

        productosService.updateProducto(id, producto);

        producto.setId(id);

        return ResponseEntity.ok(producto);
    }
}