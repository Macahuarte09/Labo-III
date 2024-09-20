package ar.edu.utn.frbb.tup.persistence.impl;

import ar.edu.utn.frbb.tup.model.Cliente;
import ar.edu.utn.frbb.tup.persistence.ClienteDao;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class ClienteDaoImpl implements ClienteDao {

    private static final Map<Long, Cliente> repositorioClientes = new HashMap<>();

    @Override
    public Cliente saveCliente(Cliente cliente) {
        repositorioClientes.put(cliente.getDni(), cliente);
        return cliente;
    }

    @Override
    public Cliente findCliente(long dni) {
        return repositorioClientes.get(dni);
    }

    @Override
    public List<Cliente> findAll(){
        return new ArrayList<>(repositorioClientes.values());
    }

    @Override
    public Cliente updateCliente(Cliente cliente){
        repositorioClientes.put(cliente.getDni(), cliente);
        return cliente;
    }

    @Override
    public void deleteCliente(long dni){
        repositorioClientes.remove(dni);
    }
}