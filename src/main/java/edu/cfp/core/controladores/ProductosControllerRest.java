package edu.cfp.core.controladores;

import edu.cfp.core.configuraciones.ConfigurationParameters;
import edu.cfp.core.controladores.dominio.Producto;
import edu.cfp.core.services.productos.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/productos")
public class ProductosControllerRest {



    public List<Producto> productos = new ArrayList<>();

    @Autowired
    @Lazy
    private ProductoService productosService;

    @Autowired
    private ConfigurationParameters configurationParameters;

    @GetMapping
    public ResponseEntity<?> getProductos(){return ResponseEntity.ok(productos);}
    @GetMapping("/fakeproductos")
    public ResponseEntity<?> fakeProductosAPI() {


        return ResponseEntity.ok(productos);
    }

    @PostMapping
    public ResponseEntity<?> altaProducto(@RequestBody Producto producto) {
        // 1. Guardar y capturar el objeto persistido con su ID real de la BD
        Producto productoGuardado = productosService.saveProductos(producto);

        // 2. Construir la URI usando {id} tanto en el path como en el expand
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}") // Cambiado de {nombre} a {id}
                .buildAndExpand(productoGuardado.getId()) // Usamos el ID del objeto guardado
                .toUri();

        // 3. Retornar el código 201 Created con el objeto real de la base de datos
        return ResponseEntity.created(location).body(productoGuardado);
    }
    @DeleteMapping("/{producto}")
    public ResponseEntity borrarProducto(@PathVariable String producto) {
        Producto productoEncontrado = productos.stream().filter(
                        c -> c.getNombre().equalsIgnoreCase(producto)).
                findFirst().orElseThrow();
        productos.remove(productoEncontrado);
        return ResponseEntity.noContent().build();

    }
}
