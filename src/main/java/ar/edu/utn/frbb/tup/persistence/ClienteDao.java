package ar.edu.utn.frbb.tup.persistence;

import ar.edu.utn.frbb.tup.model.Cliente;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository

public interface ClienteDao {

    Cliente saveCliente(Cliente cliente);

    Cliente findCliente(long dni);

    List<Cliente> findAll();

    Cliente updateCliente(Cliente cliente);

    void deleteCliente(long dni);
}