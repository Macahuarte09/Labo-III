package ar.edu.utn.frbb.tup.service;

import ar.edu.utn.frbb.tup.model.Cliente;
import ar.edu.utn.frbb.tup.model.exception.ClienteAlreadyExistsException;

import java.util.List;

public interface ClienteService {

    void darDeAltaCliente(Cliente cliente) throws ClienteAlreadyExistsException;

    List<Cliente> obtenerTodosLosClientes();
}