package ar.edu.utn.frbb.tup.service.impl;

import ar.edu.utn.frbb.tup.controller.dto.ClienteDto;
import ar.edu.utn.frbb.tup.controller.dto.CuentaDto;
import ar.edu.utn.frbb.tup.model.Cliente;
import ar.edu.utn.frbb.tup.model.Cuenta;
import ar.edu.utn.frbb.tup.model.exception.ClienteAlreadyExistsException;
import ar.edu.utn.frbb.tup.model.exception.TipoCuentaAlreadyExistsException;
import ar.edu.utn.frbb.tup.persistence.ClienteDao;
import ar.edu.utn.frbb.tup.service.ClienteService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClienteServiceImpl implements ClienteService {

    private final ClienteDao clienteDao;

    public ClienteServiceImpl(ClienteDao clienteDao) {
        this.clienteDao = clienteDao;
    }

    @Override
    public Cliente darDeAltaCliente(ClienteDto clienteDto) throws ClienteAlreadyExistsException {
        Cliente cliente = new Cliente(clienteDto);

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
        return cliente;
    }

    @Override
    public void agregarCuenta(Cuenta cuenta, long dniTitular) throws TipoCuentaAlreadyExistsException {
        Cliente cliente = buscarClientePorDni(dniTitular);
        if (cliente != null) {
            cliente.getCuentas().add(cuenta);
            cuenta.setTitular(cliente);
            clienteDao.saveCliente(cliente);
        } else {
            throw new IllegalArgumentException("Cliente no encontrado con DNI: " + dniTitular);
        }
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

    public List<CuentaDto> obtenerCuentasPorDniTitular(long dni) {
        Cliente cliente = buscarClientePorDni(dni);
        return cliente.getCuentas().stream().map(cuenta -> {
            CuentaDto cuentaDto = new CuentaDto();
            cuentaDto.setNumeroCuenta(cuenta.getNumeroCuenta());
            cuentaDto.setFechaCreacion(cuenta.getFechaCreacion());
            cuentaDto.setBalance(cuenta.getBalance());
            cuentaDto.setTipoCuenta(cuenta.getTipoCuenta().toString());
            cuentaDto.setMoneda(cuenta.getMoneda().toString());
            cuentaDto.setDniTitular(cliente.getDni());
            cuentaDto.setNombreTitular(cliente.getNombre());
            cuentaDto.setApellidoTitular(cliente.getApellido());
            return cuentaDto;
        }).collect(Collectors.toList());
    }
}
