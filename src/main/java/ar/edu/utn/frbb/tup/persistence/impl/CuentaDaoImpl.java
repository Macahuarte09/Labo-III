package ar.edu.utn.frbb.tup.persistence.impl;
import java.util.*;
import ar.edu.utn.frbb.tup.model.Cuenta;
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
    public Cuenta obtenerCuentaPorNumero(String numeroCuenta) {
        try {
            long numero = Long.parseLong(numeroCuenta);
            return repositorioCuentas.get(numero);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    @Override
    public void actualizarCuenta(Cuenta cuenta) {
        if (repositorioCuentas.containsKey(cuenta.getNumeroCuenta())) {
            repositorioCuentas.put(cuenta.getNumeroCuenta(), cuenta);
        } else {
            throw new IllegalArgumentException("Cuenta no encontrada: " + cuenta.getNumeroCuenta());
        }
    }
}