package edu.cfp.core.services.producto;

import edu.cfp.core.dominio.Producto;

import java.util.List;

public interface ProductoService {
    public List<Producto> getProductos();

    void saveProducto(Producto producto);

    void deleteProducto(Integer id);

    void updateProducto(Integer id, Producto producto);
}
