package ar.edu.utn.frbb.tup.persistence;

import ar.edu.utn.frbb.tup.model.Cliente;

import java.util.List;

public interface ClienteDao {

    Cliente saveCliente(Cliente cliente);

    Cliente findCliente(long dni, boolean b);

    List<Cliente> findAll();
}