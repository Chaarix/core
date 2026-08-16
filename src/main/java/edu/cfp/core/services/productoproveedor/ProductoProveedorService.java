package edu.cfp.core.services.productoproveedor;

import edu.cfp.core.dominio.ProductoProveedor;

import java.util.List;

public interface ProductoProveedorService {

    List<ProductoProveedor> getProductoProveedor();

    void saveProductoProveedor(ProductoProveedor productoProveedor);

    void deleteProductoProveedor(Integer id);

    void updateProductoProveedor(
            Integer id,
            ProductoProveedor productoProveedor
    );

    List<ProductoProveedor> getPorProveedor(Integer idProveedor);

    List<ProductoProveedor> getPorProducto(Integer idProducto);

    void asociar(ProductoProveedor productoProveedor);
}