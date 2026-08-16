package edu.cfp.core.services.factura;

import edu.cfp.core.dominio.Factura;


import java.util.List;

public interface FacturaService {
    public List<Factura> getFactura();

    void saveFactura(Factura factura);

    void deleteFactura(Integer id);

    void updateFactura(Integer id, Factura factura);
}
