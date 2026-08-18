package edu.cfp.core.controladores;

import edu.cfp.core.configuraciones.ConfigurationParameters;
import edu.cfp.core.controladores.dominio.Producto;
import edu.cfp.core.services.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/productos")
@CrossOrigin(origins = "*") // Permite que JavaFX se conecte sin restricciones de red
public class ProductosControllerRest {

    @Autowired
    @Lazy
    private ProductoService productosService;

    @Autowired
    private ConfigurationParameters configuracionParameters;

    // Lista estática en memoria para que persistan los "fake-productos" mientras el servidor esté encendido
    private static final List<Producto> fakeProductosLista = new ArrayList<>(Arrays.asList(
            new Producto(1, "pelota", 10000.0, 2),
            new Producto(2, "libros", 20000.0, 9),
            new Producto(3, "Tableta de chocolate", 80000.0, 5)
    ));

    // Generador de IDs automáticos para los nuevos fake-productos
    private static final AtomicInteger idGenerator = new AtomicInteger(4);


    // 1. ENDPOINT REAL (Conectado a tu Base de Datos PostgreSQL a través del servicio)
    @GetMapping
    public ResponseEntity<?> getProductos() {
        System.out.println("params: " + configuracionParameters.toString());
        List<Producto> productos = productosService.getProductos();
        return ResponseEntity.ok(productos);
    }

    // 2. ENDPOINT SIMULADO: Devuelve la lista dinámica en memoria
    @GetMapping("/fake-productos")
    public ResponseEntity<?> fakeProductosAPI() {
        return ResponseEntity.ok(fakeProductosLista);
    }

    // 3. NUEVO ENDPOINT POST: Recibe el JSON de JavaFX y agrega el producto a la lista simulada
    @PostMapping("/fake-productos")
    public ResponseEntity<?> guardarProductoFake(@RequestBody Producto nuevoProducto) {
        try {
            // Asignamos un ID autoincremental único
            nuevoProducto.setIdProducto(idGenerator.getAndIncrement());

            // Agregamos el nuevo objeto recibido a nuestra lista en memoria
            fakeProductosLista.add(nuevoProducto);

            System.out.println("¡Producto recibido y guardado con éxito!: " + nuevoProducto.getNombre());

            // Respondemos con el objeto creado y estado 201 (Created)
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoProducto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al procesar el producto: " + e.getMessage());
        }
    }

    // 4. ENDPOINT POST REAL: (Guardalo acá para cuando configures tu PostgreSQL de forma definitiva)
    @PostMapping
    public ResponseEntity altaProducto(@RequestBody Producto producto) {
        productosService.saveProducto(producto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(producto.getIdProducto())
                .toUri();
        return ResponseEntity.created(location).body(producto);
    }

    @DeleteMapping("fake-productos/{idProducto}")
    // === ELIMINAR EN ENTORNO SIMULADO (Lista en memoria) ===
    public ResponseEntity<?> deleteProducto(@PathVariable Integer idProducto) {
        // Buscamos si existe el producto en la lista estática por su ID
        boolean removido = fakeProductosLista.removeIf(p -> p.getIdProducto().equals(idProducto));

        if (removido) {
            System.out.println("¡Producto fake eliminado con éxito!: ID " + idProducto);
            return ResponseEntity.noContent().build(); // Código 204 (Éxito, sin contenido)
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se encontró el producto fake con ID: " + idProducto); // Código 404
        }

    }

    @PutMapping("/fake-productos/{idProducto}")
    public ResponseEntity<?> modificacionProducto(
            @PathVariable Integer idProducto,       // ✅ CORRECCIÓN: Lee el ID desde la URL
            @RequestBody Producto datosNuevos      // ✅ CORRECCIÓN: Recibe el JSON con los cambios desde JavaFX
    ) {
        try {
            // Buscamos el producto en la lista en memoria usando el ID de la URL
            Producto productoEncontrado = fakeProductosLista.stream()
                    .filter(p -> p.getIdProducto().equals(idProducto))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

            // ✅ CORRECCIÓN: Reemplazamos los datos viejos por los nuevos que mandó el usuario
            productoEncontrado.setNombre(datosNuevos.getNombre());
            productoEncontrado.setCantidad(datosNuevos.getCantidad());
            productoEncontrado.setPrecio(datosNuevos.getPrecio());

            System.out.println("¡Producto fake modificado con éxito!: ID " + idProducto);
            return ResponseEntity.ok(productoEncontrado); // Devuelve 200 OK con el producto actualizado

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
