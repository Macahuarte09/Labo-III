package ar.edu.utn.frbb.tup.persistence.impl;

import java.util.*;

import ar.edu.utn.frbb.tup.model.Cliente;
import ar.edu.utn.frbb.tup.model.Cuenta;
import ar.edu.utn.frbb.tup.persistence.CuentaDao;
import org.springframework.stereotype.Service;

@Service
public class CuentaDaoImpl implements CuentaDao {

    private static Map<Long, Cuenta> repositorioCuentas = new HashMap<>();

    @Override
    public Cuenta saveCuenta(Cuenta cuenta) {
        Random random = new Random();
        cuenta.setNumeroCuenta(random.nextLong());
        return repositorioCuentas.put(cuenta.getNumeroCuenta(), cuenta);
    }

    @Override
    public Cuenta findCuenta(long numeroCuenta) {
        for (Cuenta cuenta : repositorioCuentas.values()) {
            if (cuenta.getNumeroCuenta() == numeroCuenta) {
                return cuenta;
            }
        }
        return null;
    }

    @Override
    public List<Cuenta> findAll(){
        return new ArrayList<>(repositorioCuentas.values());
    }
}