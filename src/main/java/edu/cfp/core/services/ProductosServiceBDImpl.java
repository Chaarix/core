package edu.cfp.core.services;

import edu.cfp.core.controladores.dominio.Producto;
import edu.cfp.core.persistence.entities.ProductoEntity;
import edu.cfp.core.persistence.entities.repositories.ProductosRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
@Service("BD")
@ConditionalOnProperty(
        value="productos.estrategia",
        havingValue = "EN_BD")

 public class ProductosServiceBDImpl implements ProductoService {

        @Autowired
        private ProductosRepository productosRepository;


        public List<Producto> getProductos() {
            List<Producto> productos = productosRepository.findAll().
                    stream().map(productoEntity -> {
                        Producto producto = new Producto();
                        producto.setIdProducto(productoEntity.getId());
                        producto.setNombre(productoEntity.getNombre());
                        producto.setPrecio(productoEntity.getPrecio());
                        producto.setCantidad(productoEntity.getCantidad());
                        return producto;
                    }).collect(Collectors.toList());

            return productos;
        }

    @Override
    public void saveProductos(Producto producto) {

    }

    @Override
        public void saveProducto(Producto producto) {
            ProductoEntity productoEntity= new ProductoEntity();
            productoEntity.setNombre(producto.getNombre());
            productoEntity.setPrecio(producto.getPrecio());
            productoEntity.setCantidad(producto.getCantidad());

            productosRepository.save(productoEntity);
        }
    }
