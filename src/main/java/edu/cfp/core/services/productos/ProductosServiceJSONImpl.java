package edu.cfp.core.services.productos;


import edu.cfp.core.controladores.dominio.Producto;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

@Primary
@Service("JSON")
@ConditionalOnProperty(
        value = "productos.estrategia",
        havingValue = "EN_JSON")
public class ProductosServiceJSONImpl implements ProductoService {

    public List<Producto> getProductos() {
        List<Producto> productos;
        try {
            productos = new ObjectMapper()
                    .readValue(this.getClass().getResourceAsStream("/productos.json"),
                new TypeReference<List<Producto>>() {});

            return productos;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Producto saveProductos(Producto producto) {

        return producto;
    }



}
