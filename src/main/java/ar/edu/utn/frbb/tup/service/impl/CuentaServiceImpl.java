package ar.edu.utn.frbb.tup.service.impl;

import ar.edu.utn.frbb.tup.controller.dto.CuentaDto;
import ar.edu.utn.frbb.tup.model.Cliente;
import ar.edu.utn.frbb.tup.model.Cuenta;
import ar.edu.utn.frbb.tup.model.TipoCuenta;
import ar.edu.utn.frbb.tup.model.exception.CuentaAlreadyExistsException;
import ar.edu.utn.frbb.tup.model.exception.CuentaNoSoportadaException;
import ar.edu.utn.frbb.tup.model.exception.TipoCuentaAlreadyExistsException;
import ar.edu.utn.frbb.tup.persistence.CuentaDao;
import ar.edu.utn.frbb.tup.service.ClienteService;
import ar.edu.utn.frbb.tup.service.CuentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CuentaServiceImpl implements CuentaService {

    private final CuentaDao cuentaDao;
    private final ClienteService clienteService;

    @Autowired
    public CuentaServiceImpl(CuentaDao cuentaDao, ClienteService clienteService) {
        this.cuentaDao = cuentaDao;
        this.clienteService = clienteService;
    }

    @Override
    public Cuenta darDeAltaCuenta(CuentaDto cuentaDto) throws CuentaAlreadyExistsException, CuentaNoSoportadaException, TipoCuentaAlreadyExistsException {
        Cuenta cuenta = new Cuenta(cuentaDto);

        if (!tipoCuentaEstaSoportada(cuenta)) {
            throw new CuentaNoSoportadaException("El tipo de cuenta no está soportado.");
        }

        if (cuentaDao.findCuenta(cuenta.getNumeroCuenta()) != null) {
            throw new CuentaAlreadyExistsException("La cuenta ya existe.");
        }

        clienteService.agregarCuenta(cuenta, cuentaDto.getDniTitular());
        cuentaDao.saveCuenta(cuenta);

        return cuenta;
    }

    @Override
    public boolean tipoCuentaEstaSoportada(Cuenta cuenta) {
        return cuenta.getTipoCuenta() == TipoCuenta.CUENTA_CORRIENTE || cuenta.getTipoCuenta() == TipoCuenta.CAJA_AHORRO;
    }

    @Override
    public Cuenta find(long id) {
        return cuentaDao.findCuenta(id);
    }

    @Override
    public List<CuentaDto> findAll() {
        return cuentaDao.findAll().stream().map(cuenta -> {
            Cliente titular = cuenta.getTitular();
            CuentaDto cuentaDto = new CuentaDto();
            cuentaDto.setNumeroCuenta(cuenta.getNumeroCuenta());
            cuentaDto.setFechaCreacion(cuenta.getFechaCreacion());
            cuentaDto.setBalance(cuenta.getBalance());
            cuentaDto.setTipoCuenta(cuenta.getTipoCuenta().toString());
            cuentaDto.setMoneda(cuenta.getMoneda().toString());
            cuentaDto.setDniTitular(titular.getDni());
            cuentaDto.setNombreTitular(titular.getNombre());
            cuentaDto.setApellidoTitular(titular.getApellido());
            return cuentaDto;
        }).collect(Collectors.toList());
    }
}
