package ar.edu.utn.frbb.tup.persistence.impl;

import ar.edu.utn.frbb.tup.model.Cliente;
import ar.edu.utn.frbb.tup.persistence.ClienteDao;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ClienteDaoImpl implements ClienteDao {

    private static Map<Long, Cliente> repositorioClientes = new HashMap<>();

    @Override
    public Cliente saveCliente(Cliente cliente) {
        Random random = new Random();
        cliente.setDni(random.nextLong());
        return repositorioClientes.put(cliente.getDni(), cliente);
    }

    @Override
    public Cliente findCliente(long dni, boolean b) {
        for (Cliente cliente: repositorioClientes.values()){
            if(cliente.getDni()==dni){
                return cliente;
            }
        }
        return null;
    }

    @Override
    public List<Cliente> findAll(){
        return new ArrayList<>(repositorioClientes.values());
    }
}