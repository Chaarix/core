package edu.cfp.core.services;

import edu.cfp.core.controladores.dominio.Producto;

import java.util.List;

public interface ProductoService {
    public List<Producto> getProductos();

    public void saveProductos(Producto producto);

    void saveProducto(Producto producto);
}
