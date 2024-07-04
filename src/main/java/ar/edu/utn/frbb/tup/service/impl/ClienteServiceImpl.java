package ar.edu.utn.frbb.tup.service.impl;

import ar.edu.utn.frbb.tup.model.Cliente;
import ar.edu.utn.frbb.tup.model.exception.ClienteAlreadyExistsException;
import ar.edu.utn.frbb.tup.service.ClienteService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClienteServiceImpl implements ClienteService {

    private List<Cliente> clientes = new ArrayList<>();

    @Override
    public void darDeAltaCliente(Cliente cliente) throws ClienteAlreadyExistsException {
        for (Cliente c : clientes) {
            if (c.getDni() == cliente.getDni()) {
                throw new ClienteAlreadyExistsException("El cliente ya existe.");
            }
        }
        clientes.add(cliente);
    }

    @Override
    public List<Cliente> obtenerTodosLosClientes() {
        return new ArrayList<>(clientes);
    }
}
