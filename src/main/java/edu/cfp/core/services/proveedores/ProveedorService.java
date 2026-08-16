package edu.cfp.core.services.proveedores;

import edu.cfp.core.dominio.Proveedor;

import java.util.List;

public interface ProveedorService {
    public List<Proveedor> getProveedor();

    void saveProveedor(Proveedor proveedor);

    void deleteProveedor(Long id);

    void updateProveedor(Long id, Proveedor proveedor);
}

