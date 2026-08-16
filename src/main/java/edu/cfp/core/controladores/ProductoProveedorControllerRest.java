package edu.cfp.core.controladores;

import edu.cfp.core.dominio.ProductoProveedor;
import edu.cfp.core.exceptions.ResourceNotFoundException;
import edu.cfp.core.services.productoproveedor.ProductoProveedorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/productos-proveedores")
public class ProductoProveedorControllerRest {

    private final ProductoProveedorService productoProveedorService;

    public ProductoProveedorControllerRest(
            ProductoProveedorService productoProveedorService) {

        this.productoProveedorService = productoProveedorService;
    }

    // GET /productos-proveedores
    @GetMapping
    public ResponseEntity<List<ProductoProveedor>> getProductoProveedor() {

        return ResponseEntity.ok(
                productoProveedorService.getProductoProveedor()
        );
    }

    // GET /productos-proveedores/proveedor/5
    // Devuelve todos los productos asociados al proveedor 5
    @GetMapping("/proveedor/{idProveedor}")
    public ResponseEntity<List<ProductoProveedor>> getPorProveedor(
            @PathVariable Integer idProveedor) {

        return ResponseEntity.ok(
                productoProveedorService.getPorProveedor(idProveedor)
        );
    }

    // GET /productos-proveedores/producto/10
    // Devuelve todos los proveedores que venden el producto 10
    @GetMapping("/producto/{idProducto}")
    public ResponseEntity<List<ProductoProveedor>> getPorProducto(
            @PathVariable Integer idProducto) {

        return ResponseEntity.ok(
                productoProveedorService.getPorProducto(idProducto)
        );
    }





    // PUT /productos-proveedores/5
    @PutMapping("/{id}")
    public ResponseEntity<ProductoProveedor> actualizar(
            @PathVariable Integer id,
            @RequestBody ProductoProveedor productoProveedor) {

        productoProveedorService.updateProductoProveedor(
                id,
                productoProveedor
        );

        productoProveedor.setId(id);

        return ResponseEntity.ok(productoProveedor);
    }

    // DELETE /productos-proveedores/5
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @PathVariable Integer id) {

        productoProveedorService.deleteProductoProveedor(id);

        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<?> asociarProductoProveedor(
            @RequestBody ProductoProveedor productoProveedor) {

        productoProveedorService.asociar(productoProveedor);

        return ResponseEntity.ok(productoProveedor);
    }
}