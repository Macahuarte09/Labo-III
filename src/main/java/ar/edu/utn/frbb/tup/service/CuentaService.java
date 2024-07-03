package ar.edu.utn.frbb.tup.service;

import ar.edu.utn.frbb.tup.model.Cuenta;
import ar.edu.utn.frbb.tup.model.exception.CuentaAlreadyExistsException;
import ar.edu.utn.frbb.tup.model.exception.CuentaNoSoportadaException;
import ar.edu.utn.frbb.tup.model.exception.TipoCuentaAlreadyExistsException;

import java.util.List;

public interface CuentaService {

    void darDeAltaCuenta(Cuenta cuenta) throws CuentaAlreadyExistsException, TipoCuentaAlreadyExistsException, CuentaNoSoportadaException;

    boolean tipoCuentaEstaSoportada(Cuenta cuenta);

    Cuenta find(long id);

    List<Cuenta>findAll();
}