package edu.cfp.core.services.factura;

import edu.cfp.core.dominio.Factura;
import edu.cfp.core.persistence.entities.FacturaEntity;
import edu.cfp.core.persistence.repositories.FacturaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Primary
@Slf4j
@Service("FACTURAS_BD")
@ConditionalOnProperty(
        value = "facturas.estrategia",
        havingValue = "EN_BD"
)
public class FacturaServiceBDImpl implements FacturaService {

    private final FacturaRepository facturaRepository;

    public FacturaServiceBDImpl(FacturaRepository facturaRepository) {
        this.facturaRepository = facturaRepository;
    }

    @Override
    public List<Factura> getFactura() {

        return facturaRepository.findAll()
                .stream()
                .map(facturaEntity -> {

                    Factura factura = new Factura();

                    factura.setId(facturaEntity.getId());
                    factura.setProducto(facturaEntity.getProducto());
                    factura.setPrecioFinal(facturaEntity.getPrecioFinal());
                    factura.setPrecioUnitario(facturaEntity.getPrecioUnitario());
                    factura.setCantidad(facturaEntity.getCantidad());
                    factura.setMetodoDePago(facturaEntity.getMetodoDePago());
                    factura.setCliente(facturaEntity.getCliente());
                    factura.setFechaEmision(facturaEntity.getFechaEmision());
                    factura.setNumeroDeFactura(facturaEntity.getNumeroDeFactura());
                    factura.setDNI(facturaEntity.getDNI());

                    return factura;

                })
                .collect(Collectors.toList());
    }

    @Override
    public void saveFactura(Factura factura) {

        FacturaEntity facturaEntity = new FacturaEntity();

        facturaEntity.setProducto(factura.getProducto());
        facturaEntity.setPrecioFinal(factura.getPrecioFinal());
        facturaEntity.setPrecioUnitario(factura.getPrecioUnitario());
        facturaEntity.setCantidad(factura.getCantidad());
        facturaEntity.setMetodoDePago(factura.getMetodoDePago());
        facturaEntity.setCliente(factura.getCliente());
        facturaEntity.setFechaEmision(factura.getFechaEmision());
        facturaEntity.setNumeroDeFactura(factura.getNumeroDeFactura());
        facturaEntity.setDNI(factura.getDNI());

        FacturaEntity guardada =
                facturaRepository.save(facturaEntity);

        factura.setId(guardada.getId());
    }

    @Override
    public void deleteFactura(Integer id) {

        if (!facturaRepository.existsById(id)) {
            throw new RuntimeException(
                    "Factura no encontrada con ID: " + id
            );
        }

        facturaRepository.deleteById(id);
    }

    @Override
    public void updateFactura(Integer id, Factura factura) {

        FacturaEntity facturaEntity =
                facturaRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Factura no encontrada con ID: " + id
                                )
                        );

        facturaEntity.setProducto(factura.getProducto());
        facturaEntity.setPrecioFinal(factura.getPrecioFinal());
        facturaEntity.setPrecioUnitario(factura.getPrecioUnitario());
        facturaEntity.setCantidad(factura.getCantidad());
        facturaEntity.setMetodoDePago(factura.getMetodoDePago());
        facturaEntity.setCliente(factura.getCliente());
        facturaEntity.setFechaEmision(factura.getFechaEmision());
        facturaEntity.setNumeroDeFactura(factura.getNumeroDeFactura());
        facturaEntity.setDNI(factura.getDNI());

        facturaRepository.save(facturaEntity);
    }
}