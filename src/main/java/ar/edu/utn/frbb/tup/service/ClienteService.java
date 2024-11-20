package ar.edu.utn.frbb.tup.service;
import ar.edu.utn.frbb.tup.controller.dto.ClienteDto;
import ar.edu.utn.frbb.tup.model.Cliente;
import ar.edu.utn.frbb.tup.model.Cuenta;
import ar.edu.utn.frbb.tup.model.exception.ClienteAlreadyExistsException;
import ar.edu.utn.frbb.tup.model.exception.ClienteMenorDeEdadException;
import ar.edu.utn.frbb.tup.model.exception.ClienteNoEncontradoException;
import org.springframework.stereotype.Service;
import java.util.List;

public interface ClienteService {

    Cliente darDeAltaCliente(ClienteDto clienteDto) throws ClienteAlreadyExistsException, ClienteMenorDeEdadException;

    void agregarCuenta(Cuenta cuenta, long dniTitular) throws ClienteNoEncontradoException;

    Cliente buscarClientePorDni(long dni) throws ClienteNoEncontradoException;

    List<Cliente> obtenerTodosLosClientes();

    Cliente actualizarCliente(ClienteDto clienteDto) throws ClienteNoEncontradoException;

    void eliminarCliente(long dni) throws ClienteNoEncontradoException ;
}