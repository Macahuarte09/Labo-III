package ar.edu.utn.frbb.tup.persistence.impl;

import java.util.*;

import ar.edu.utn.frbb.tup.model.Cuenta;
import ar.edu.utn.frbb.tup.model.exception.CuentaNoEncontradaException;
import ar.edu.utn.frbb.tup.persistence.CuentaDao;
import org.springframework.stereotype.Service;

@Service
public class CuentaDaoImpl implements CuentaDao {

    private static final Map<Long, Cuenta> repositorioCuentas = new HashMap<>();

    @Override
    public Cuenta saveCuenta(Cuenta cuenta) {
        repositorioCuentas.put(cuenta.getNumeroCuenta(), cuenta);
        return cuenta;
    }

    @Override
    public Cuenta findCuenta(long numeroCuenta) {
        return repositorioCuentas.get(numeroCuenta);
    }

    @Override
    public List<Cuenta> findAll() {
        return new ArrayList<>(repositorioCuentas.values());
    }

    @Override
    public Cuenta updateCuenta(Cuenta cuenta) {
        repositorioCuentas.put(cuenta.getNumeroCuenta(), cuenta);
        return cuenta;
    }

    @Override
    public void deleteCuenta(long id){
        repositorioCuentas.remove(id);
    }

}