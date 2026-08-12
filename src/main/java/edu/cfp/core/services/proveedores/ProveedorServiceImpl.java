package edu.cfp.core.services.proveedores;

import edu.cfp.core.controladores.dominio.Proveedor;
import edu.cfp.core.persistence.entities.ProveedorEntity;
import edu.cfp.core.persistence.entities.repositories.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Lazy
@Service("PROVEEDOR_DATABASE_AUX")
@ConditionalOnProperty(
        value = "proveedores.estrategia",
        havingValue = "EN_BD")
public class ProveedorServiceImpl implements ProveedorService {

    @Autowired
    private ProveedorRepository proveedorRepository;

    @Override
    public Proveedor saveProveedor(Proveedor proveedor) {
        // 1. Creamos la entidad que entiende JPA/PostgreSQL
        ProveedorEntity entity = new ProveedorEntity();

        // 2. Pasamos los datos desde 'proveedor' hacia 'entity' usando los setters
        entity.setNombreEntidad(proveedor.getNombreEntidad());
        entity.setTipoIdentificacion(proveedor.getTipoIdentificacion());
        entity.setTelefonoEntidad(proveedor.getTelefonoEntidad());
        entity.setCuitcuilEntidad(proveedor.getCuitcuilEntidad());
        entity.setCondicionIva(proveedor.getCondicionIva());
        entity.setDniEntidad(proveedor.getDniEntidad());
        entity.setPais(proveedor.getPais());
        entity.setEmailEntidad(proveedor.getEmailEntidad());
        entity.setCiudad(proveedor.getCiudad());
        entity.setProvincia(proveedor.getProvincia());

        // 3. Guardamos la entidad en PostgreSQL
        ProveedorEntity entityGuardada = proveedorRepository.save(entity);

        // 4. Le asignamos al objeto de dominio el ID generado automáticamente por la BD
        proveedor.setId(entityGuardada.getId());

        // 5. Devolvemos el objeto del dominio listo para el controlador
        return proveedor;
    }
}
