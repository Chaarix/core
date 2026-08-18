package edu.cfp.core.services;

import edu.cfp.core.controladores.dominio.Producto;
import edu.cfp.core.persistence.entities.repositories.ProductosRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Lazy
@Service("MEMORY")
@ConditionalOnProperty(
        value = "productos.estrategia",
        havingValue = "EN_MEMORIA")
public class ProductosServiceImpl implements ProductoService {
    @Autowired
    private ProductosRepository productosRepository;


    private List<Producto> productos = new ArrayList<>(Arrays.asList(
            new Producto(1, "Smart tv",10000.0,2),
            new Producto(2, "Notebook", 20000.0,9),
            new Producto(3, "Tablet", 80000.0,5)
    ));
    public List<Producto> getProductos(){
        return productos;
    }

    @Override
    public void saveProductos(Producto producto) {

    }

    @Override
    public void saveProducto(Producto producto) {

    }

}
