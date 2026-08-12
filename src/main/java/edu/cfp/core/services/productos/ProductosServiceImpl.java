package edu.cfp.core.services.productos;

import edu.cfp.core.controladores.dominio.Producto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Lazy
@Service("MEMORY")
@ConditionalOnProperty(
        value = "productos.estrategia",
        havingValue = "EN_MEMORIA")
public class ProductosServiceImpl implements ProductoService {




    public ProductosServiceImpl() {
        log.info("Se esta construyendo un obj de la clase ProductosServiceImpl");
    }


    @Override
    public List<Producto> getProductos() {
        return List.of();
    }

    @Override
    public Producto saveProductos(Producto producto) {

        return producto;
    }


    public void saveProducto(Producto producto) {

    }
}
