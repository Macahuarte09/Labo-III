package ar.edu.utn.frbb.tup.service.impl;

import ar.edu.utn.frbb.tup.controller.dto.CuentaDto;
import ar.edu.utn.frbb.tup.model.Cuenta;
import ar.edu.utn.frbb.tup.model.TipoCuenta;
import ar.edu.utn.frbb.tup.model.TipoMoneda;
import ar.edu.utn.frbb.tup.model.exception.ClienteNoEncontradoException;
import ar.edu.utn.frbb.tup.model.exception.CuentaAlreadyExistsException;
import ar.edu.utn.frbb.tup.model.exception.CuentaNoEncontradaException;
import ar.edu.utn.frbb.tup.model.exception.CuentaNoSoportadaException;
import ar.edu.utn.frbb.tup.persistence.CuentaDao;
import ar.edu.utn.frbb.tup.service.ClienteService;
import ar.edu.utn.frbb.tup.service.CuentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CuentaServiceImpl implements CuentaService {

    CuentaDao cuentaDao;
    ClienteService clienteService;

    @Autowired
    public CuentaServiceImpl(CuentaDao cuentaDao, ClienteService clienteService) {
        this.cuentaDao = cuentaDao;
        this.clienteService = clienteService;
    }

    @Override
    public Cuenta darDeAltaCuenta(CuentaDto cuentaDto) throws CuentaAlreadyExistsException, CuentaNoSoportadaException, ClienteNoEncontradoException {
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
    public Cuenta findByID(long id) throws CuentaNoEncontradaException{
        Cuenta cuenta = cuentaDao.findCuenta(id);
        if (cuenta == null){
            throw new CuentaNoEncontradaException("La cuenta no existe");
        }
        return cuenta;
    }

    @Override
    public List<Cuenta> findAll() {
        return cuentaDao.findAll();
    }

    @Override
    public void eliminarCuenta(long id) throws CuentaNoEncontradaException {
        Cuenta cuenta = cuentaDao.findCuenta(id);
        if (cuenta == null){
            throw new CuentaNoEncontradaException("La cuenta no existe");
        }
        cuentaDao.deleteCuenta(id);
    }
}