package edu.cfp.core.services.proveedores;

import edu.cfp.core.dominio.Proveedor;
import edu.cfp.core.persistence.entities.ProveedorEntity;
import edu.cfp.core.persistence.repositories.ProveedorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Primary
@Slf4j
@Service("PROVEEDORES_BD")
@ConditionalOnProperty(
        value = "proveedores.estrategia",
        havingValue = "EN_BD"
)
public class ProveedorServiceBDImpl implements ProveedorService {

    private final ProveedorRepository proveedorRepository;

    public ProveedorServiceBDImpl(ProveedorRepository proveedorRepository) {
        this.proveedorRepository = proveedorRepository;
    }

    @Override
    public List<Proveedor> getProveedor() {

        return proveedorRepository.findAll()
                .stream()
                .map(proveedorEntity -> {

                    Proveedor proveedor = new Proveedor();

                    proveedor.setId(proveedorEntity.getId());
                    proveedor.setNombreEntidad(proveedorEntity.getNombreEntidad());
                    proveedor.setTipoIdentificacion(proveedorEntity.getTipoIdentificacion());
                    proveedor.setTelefonoEntidad(proveedorEntity.getTelefonoEntidad());
                    proveedor.setCuitcuilEntidad(proveedorEntity.getCuitcuilEntidad());
                    proveedor.setCondicionIva(proveedorEntity.getCondicionIva());
                    proveedor.setDniEntidad(proveedorEntity.getDniEntidad());
                    proveedor.setPais(proveedorEntity.getPais());
                    proveedor.setEmailEntidad(proveedorEntity.getEmailEntidad());
                    proveedor.setCiudad(proveedorEntity.getCiudad());
                    proveedor.setProvincia(proveedorEntity.getProvincia());

                    return proveedor;

                })
                .collect(Collectors.toList());
    }

    @Override
    public void saveProveedor(Proveedor proveedor) {

        ProveedorEntity proveedorEntity = new ProveedorEntity();

        proveedorEntity.setNombreEntidad(proveedor.getNombreEntidad());
        proveedorEntity.setTipoIdentificacion(proveedor.getTipoIdentificacion());
        proveedorEntity.setTelefonoEntidad(proveedor.getTelefonoEntidad());
        proveedorEntity.setCuitcuilEntidad(proveedor.getCuitcuilEntidad());
        proveedorEntity.setCondicionIva(proveedor.getCondicionIva());
        proveedorEntity.setDniEntidad(proveedor.getDniEntidad());
        proveedorEntity.setPais(proveedor.getPais());
        proveedorEntity.setEmailEntidad(proveedor.getEmailEntidad());
        proveedorEntity.setCiudad(proveedor.getCiudad());
        proveedorEntity.setProvincia(proveedor.getProvincia());

        ProveedorEntity guardado =
                proveedorRepository.save(proveedorEntity);

        proveedor.setId(guardado.getId());
    }

    @Override
    public void deleteProveedor(Long id) {

        if (!proveedorRepository.existsById(Math.toIntExact(id))) {
            throw new RuntimeException(
                    "Proveedor no encontrado con ID: " + id
            );
        }

        proveedorRepository.deleteById(Math.toIntExact(id));
    }

    @Override
    public void updateProveedor(Long id, Proveedor proveedor) {

        ProveedorEntity proveedorEntity =
                proveedorRepository.findById(Math.toIntExact(id))
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Proveedor no encontrado con ID: " + id
                                )
                        );

        proveedorEntity.setNombreEntidad(proveedor.getNombreEntidad());
        proveedorEntity.setTipoIdentificacion(proveedor.getTipoIdentificacion());
        proveedorEntity.setTelefonoEntidad(proveedor.getTelefonoEntidad());
        proveedorEntity.setCuitcuilEntidad(proveedor.getCuitcuilEntidad());
        proveedorEntity.setCondicionIva(proveedor.getCondicionIva());
        proveedorEntity.setDniEntidad(proveedor.getDniEntidad());
        proveedorEntity.setPais(proveedor.getPais());
        proveedorEntity.setEmailEntidad(proveedor.getEmailEntidad());
        proveedorEntity.setCiudad(proveedor.getCiudad());
        proveedorEntity.setProvincia(proveedor.getProvincia());

        proveedorRepository.save(proveedorEntity);
    }
}