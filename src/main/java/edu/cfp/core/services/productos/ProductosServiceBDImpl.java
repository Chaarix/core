package edu.cfp.core.services.productos;

import edu.cfp.core.controladores.dominio.Producto;
import edu.cfp.core.persistence.entities.ProductoEntity;
import edu.cfp.core.persistence.entities.repositories.ProductosRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Lazy
@Service("MEMORY")
@ConditionalOnProperty(
        value = "productos.estrategia",
        havingValue = "EN_MEMORIA")
public class ProductosServiceBDImpl implements ProductoService {

    public ProductosServiceBDImpl() {
        log.info("Se esta construyendo un obj de la clase ProductosServiceImpl");
    }

    @Autowired
    private ProductosRepository productosRepository;

    public List<Producto> getProductos() {
        List<Producto> productoEntities = productosRepository.findAll().stream().map(productoEntity -> {
            Producto p = new Producto();
            p.setId(p.getId());
            p.setNombre(p.getNombre());
            p.setPrecio(p.getPrecio());
            p.setStock(p.getStock());
               return p;
        }).collect(Collectors.toList());
        return productoEntities;
    }

    @Override
    public Producto saveProductos(Producto producto) {
            ProductoEntity productoEntity = new ProductoEntity();
            productoEntity.setNombre(producto.getNombre());
            productoEntity.setPrecio(Double.valueOf(producto.getPrecio()));
            productoEntity.setStock(Integer.valueOf(producto.getStock()));
        return producto;
    }


    public void saveProducto(Producto producto) {
        ProductoEntity p = new ProductoEntity();
        p.setNombre(p.getNombre());
        p.setPrecio(p.getPrecio());
        p.setStock(p.getStock());

        productosRepository.save(p);
    }
}
