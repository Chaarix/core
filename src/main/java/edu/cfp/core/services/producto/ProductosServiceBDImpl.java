package edu.cfp.core.services.producto;

import edu.cfp.core.dominio.Producto;
import edu.cfp.core.persistence.entities.ProductoEntity;
import edu.cfp.core.persistence.repositories.ProductosRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service("PRODUCTOS_BD")
@ConditionalOnProperty(
        value = "productos.estrategia",
        havingValue = "EN_BD"
)
public class ProductosServiceBDImpl implements ProductoService {

    private final ProductosRepository productosRepository;

    public ProductosServiceBDImpl(ProductosRepository productosRepository) {
        this.productosRepository = productosRepository;

        log.info("Se está construyendo ProductosServiceBDImpl");
    }

    @Override
    public List<Producto> getProductos() {

        return productosRepository.findAll()
                .stream()
                .map(productoEntity -> {

                    Producto producto = new Producto();

                    producto.setId(productoEntity.getId());
                    producto.setNombre(productoEntity.getNombre());
                    producto.setPrecio(
                            String.valueOf(productoEntity.getPrecio())
                    );
                    producto.setStock(
                            String.valueOf(productoEntity.getStock())
                    );

                    return producto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public void saveProducto(Producto producto) {

        ProductoEntity productoEntity = new ProductoEntity();

        productoEntity.setNombre(producto.getNombre());

        productoEntity.setPrecio(
                Double.valueOf(producto.getPrecio())
        );

        productoEntity.setStock(
                Integer.valueOf(producto.getStock())
        );

        ProductoEntity guardado =
                productosRepository.save(productoEntity);

        // Si el ID lo genera PostgreSQL/JPA,
        // lo devolvemos al objeto de dominio.
        producto.setId(guardado.getId());
    }

    @Override
    public void deleteProducto(Integer id) {

        productosRepository.deleteById(id);
    }

    @Override
    public void updateProducto(Integer id, Producto producto) {
        ProductoEntity productoEntity =
                productosRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException("Producto no encontrado"));

        productoEntity.setNombre(producto.getNombre());
        productoEntity.setPrecio(Double.valueOf(producto.getPrecio()));
        productoEntity.setStock(Integer.valueOf(producto.getStock()));

        productosRepository.save(productoEntity);
    }
}