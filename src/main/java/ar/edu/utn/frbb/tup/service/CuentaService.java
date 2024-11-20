package ar.edu.utn.frbb.tup.service;

import ar.edu.utn.frbb.tup.controller.dto.CuentaDto;
import ar.edu.utn.frbb.tup.model.Cuenta;
import ar.edu.utn.frbb.tup.model.exception.*;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface CuentaService {

    Cuenta darDeAltaCuenta(CuentaDto cuentaDto) throws CuentaAlreadyExistsException, TipoCuentaAlreadyExistsException, CuentaNoSoportadaException, ClienteNoEncontradoException;

    boolean tipoCuentaEstaSoportada(Cuenta cuenta);

    Cuenta findByID(long id) throws CuentaNoEncontradaException;

    List<Cuenta> findAll();

    void eliminarCuenta(long id) throws CuentaNoEncontradaException;
}