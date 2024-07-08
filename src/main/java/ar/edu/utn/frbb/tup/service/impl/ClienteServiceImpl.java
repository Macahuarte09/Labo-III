package ar.edu.utn.frbb.tup.service.impl;

import ar.edu.utn.frbb.tup.model.Cliente;
import ar.edu.utn.frbb.tup.model.Cuenta;
import ar.edu.utn.frbb.tup.model.exception.ClienteAlreadyExistsException;
import ar.edu.utn.frbb.tup.model.exception.TipoCuentaAlreadyExistsException;
import ar.edu.utn.frbb.tup.persistence.ClienteDao;
import ar.edu.utn.frbb.tup.service.ClienteService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteServiceImpl implements ClienteService {

    private final ClienteDao clienteDao;

    public ClienteServiceImpl(ClienteDao clienteDao) {
        this.clienteDao = clienteDao;
    }

    @Override
    public void darDeAltaCliente(Cliente cliente) throws ClienteAlreadyExistsException {
        if (clienteDao.findCliente(cliente.getDni(), false) != null) {
            throw new ClienteAlreadyExistsException("Ya existe un cliente con DNI " + cliente.getDni());
        }

        if(cliente.getFechaNacimiento() == null) {
            throw new IllegalArgumentException("La fecha de nacimiento no puede ser nula");
        }

        if (cliente.getEdad() < 18) {
            throw new IllegalArgumentException("El cliente debe ser mayor a 18 años");
        }

        clienteDao.saveCliente(cliente);
    }

    @Override
    public void agregarCuenta(Cuenta cuenta, long dniTitular) throws TipoCuentaAlreadyExistsException {
        Cliente titular = buscarClientePorDni(dniTitular);
        cuenta.setTitular(titular);
        if (titular.tieneCuenta(cuenta.getTipoCuenta(), cuenta.getMoneda())) {
            throw new TipoCuentaAlreadyExistsException("El cliente ya posee una cuenta de ese tipo y moneda");
        }
        titular.addCuenta(cuenta);
        clienteDao.saveCliente(titular);
    }

    @Override
    public Cliente buscarClientePorDni(long dni) {
        Cliente cliente = clienteDao.findCliente(dni, true);
        if (cliente == null) {
            throw new IllegalArgumentException("El cliente no existe");
        }
        return cliente;
    }

    @Override
    public List<Cliente> obtenerTodosLosClientes() {
        return clienteDao.findAll();
    }
}