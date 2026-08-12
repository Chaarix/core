package edu.cfp.core.services;

import edu.cfp.core.controladores.dominio.Producto;
import lombok.extern.slf4j.Slf4j;
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
        private List<Producto> productos =  new ArrayList<>(Arrays.asList(
                new Producto(1, "tv", "150.000", "1"),
                new Producto(2, "Alfajor", "870", "24"),
                new Producto(3, "Manzana", "3000", "12")
        ));



    public ProductosServiceImpl() {
        log.info("Se esta construyendo un obj de la clase ProductosServiceImpl");
    }


    @Override
    public List<Producto> getProductos() {
        return List.of();
    }

    @Override
    public void saveProductos(Producto producto) {

    }

    @Override
    public void saveProducto(Producto producto) {

    }
}
