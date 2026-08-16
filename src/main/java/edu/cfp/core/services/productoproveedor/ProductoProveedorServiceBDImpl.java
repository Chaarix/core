package edu.cfp.core.services.productoproveedor;

import edu.cfp.core.dominio.ProductoProveedor;
import edu.cfp.core.persistence.entities.ProductoProveedorEntity;
import edu.cfp.core.persistence.entities.ProductoEntity;
import edu.cfp.core.persistence.entities.ProveedorEntity;
import edu.cfp.core.persistence.repositories.ProductoProveedorRepository;
import edu.cfp.core.persistence.repositories.ProductosRepository;
import edu.cfp.core.persistence.repositories.ProveedorRepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service("PRODUCTO_PROVEEDOR_BD")
public class ProductoProveedorServiceBDImpl
        implements ProductoProveedorService {

    private final ProductoProveedorRepository repository;
    private final ProductosRepository productosRepository;
    private final ProveedorRepository proveedorRepository;


    public ProductoProveedorServiceBDImpl(
            ProductosRepository productoRepository,
            ProveedorRepository proveedorRepository,
            ProductoProveedorRepository productoProveedorRepository) {

        this.productosRepository = productoRepository;
        this.proveedorRepository = proveedorRepository;
        this.repository = productoProveedorRepository;
    }
    /*
    public ProductoProveedorServiceBDImpl(
            ProductoProveedorRepository repository,
            ProductosRepository productosRepository,
            ProveedorRepository proveedorRepository
    ) {
        this.repository = repository;
        this.productosRepository = productosRepository;
        this.proveedorRepository = proveedorRepository;
    }


     */
    @Override
    public List<ProductoProveedor> getProductoProveedor() {

        return repository.findAll()
                .stream()
                .map(entity -> {

                    ProductoProveedor pp = new ProductoProveedor();

                    pp.setId(entity.getId());
                    pp.setProductoId(entity.getProducto().getId());
                    pp.setProveedorId(entity.getProveedor().getId());
                    pp.setPrecioCosto(entity.getPrecioCosto());

                    return pp;

                })
                .collect(Collectors.toList());
    }

    @Override
    public void saveProductoProveedor(
            ProductoProveedor productoProveedor) {

        ProductoEntity producto =
                productosRepository.findById(
                        productoProveedor.getProductoId()
                ).orElseThrow(() ->
                        new RuntimeException(
                                "Producto no encontrado"
                        )
                );

        ProveedorEntity proveedor =
                proveedorRepository.findById(
                        productoProveedor.getProveedorId()
                ).orElseThrow(() ->
                        new RuntimeException(
                                "Proveedor no encontrado"
                        )
                );

        ProductoProveedorEntity entity =
                new ProductoProveedorEntity();

        entity.setProducto(producto);
        entity.setProveedor(proveedor);
        entity.setPrecioCosto(
                productoProveedor.getPrecioCosto()
        );

        ProductoProveedorEntity guardado =
                repository.save(entity);

        productoProveedor.setId(guardado.getId());
    }

    @Override
    public void deleteProductoProveedor(Integer id) {

        if (!repository.existsById(id)) {
            throw new RuntimeException(
                    "Relación producto-proveedor no encontrada: " + id
            );
        }

        repository.deleteById(id);
    }

    @Override
    public void updateProductoProveedor(
            Integer id,
            ProductoProveedor productoProveedor) {

        ProductoProveedorEntity entity =
                repository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Relación producto-proveedor no encontrada"
                                )
                        );

        ProductoEntity producto =
                productosRepository.findById(
                        productoProveedor.getProductoId()
                ).orElseThrow(() ->
                        new RuntimeException(
                                "Producto no encontrado"
                        )
                );

        ProveedorEntity proveedor =
                proveedorRepository.findById(
                        productoProveedor.getProveedorId()
                ).orElseThrow(() ->
                        new RuntimeException(
                                "Proveedor no encontrado"
                        )
                );

        entity.setProducto(producto);
        entity.setProveedor(proveedor);
        entity.setPrecioCosto(
                productoProveedor.getPrecioCosto()
        );

        repository.save(entity);
    }

    @Override
    public List<ProductoProveedor> getPorProveedor(Integer idProveedor) {
        return List.of();
    }

    @Override
    public List<ProductoProveedor> getPorProducto(Integer idProducto) {
        return List.of();
    }
    @Override
    public void asociar(ProductoProveedor productoProveedor) {

        ProductoEntity producto = productosRepository
                .findById(productoProveedor.getProductoId())
                .orElseThrow(() ->
                        new RuntimeException("Producto no encontrado"));

        ProveedorEntity proveedor = proveedorRepository
                .findById(productoProveedor.getProveedorId())
                .orElseThrow(() ->
                        new RuntimeException("Proveedor no encontrado"));

        ProductoProveedorEntity entity = new ProductoProveedorEntity();

        entity.setProducto(producto);
        entity.setProveedor(proveedor);
        entity.setPrecioCosto(productoProveedor.getPrecioCosto());

        ProductoProveedorEntity guardado =
                repository.save(entity);

        productoProveedor.setId(guardado.getId());
    }
}