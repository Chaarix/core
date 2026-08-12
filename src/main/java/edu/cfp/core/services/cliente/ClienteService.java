package edu.cfp.core.services.cliente;



import edu.cfp.core.dominio.Cliente;

import java.util.List;

public interface ClienteService {
    public List<Cliente> getCliente();

    void saveCliente(Cliente cliente);

    void deleteCliente(Long id);

    void updateCliente(Long id, Cliente cliente);
}
