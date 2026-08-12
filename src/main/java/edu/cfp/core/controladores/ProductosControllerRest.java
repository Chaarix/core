package edu.cfp.core.controladores;

import edu.cfp.core.configuraciones.ConfigurationParameters;
import edu.cfp.core.controladores.dominio.Cliente;
import edu.cfp.core.controladores.dominio.Producto;
import edu.cfp.core.persistence.entities.ProductoEntity;
import edu.cfp.core.services.ProductoService;
import edu.cfp.core.services.ProductosServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/productos")
public class ProductosControllerRest {



    public List<Producto> productos;

    @Autowired
    @Lazy
    private ProductoService productosService;

    @Autowired
    private ConfigurationParameters configurationParameters;

    @GetMapping
    public ResponseEntity<?> getProductos(){

        System.out.println("params: " + configurationParameters.toString());
        productos = productosService.getProductos();
        return ResponseEntity.ok(productos);
    }
    @GetMapping("/fakeproductos")
    public ResponseEntity<?> fakeProductosAPI() {

        List<Producto> productos = new ArrayList<>(Arrays.asList(
                new Producto(1, "Juan", "123", "juancito"),
                new Producto(2, "Pepe", "321", "pepe"),
                new Producto(3, "Maria", "111", "maria")
        )
        );
        return ResponseEntity.ok(productos);
    }

    @PostMapping
    public ResponseEntity<?> altaProducto(@RequestBody Producto producto) {
        productosService.saveProductos(producto);

        // 3. Construir la URI de ubicación correcta (Ej: http://localhost:8080/tienda/api/v1/clientes/Juan)
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{nombre}") // CORRECCIÓN: Usar llaves {} para definir la variable en la URL
                .buildAndExpand(producto.getId()) // Reemplaza {nombre} por el valor real
                .toUri();

        // 4. Retornar el código 201 Created con la URL y el objeto insertado
        return ResponseEntity.created(location).body(producto);
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
