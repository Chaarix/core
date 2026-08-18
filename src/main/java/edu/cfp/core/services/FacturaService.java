package edu.cfp.core.services;

import edu.cfp.core.domain.Factura;

import java.util.List;

public interface FacturaService {

        public List<Factura>getFactura();


        public Factura saveFactura(Factura factura);

     void deleteFacturaById(Integer idFactura);

    }


