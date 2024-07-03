package ar.edu.utn.frbb.tup.persistence;

import ar.edu.utn.frbb.tup.model.Cliente;
import ar.edu.utn.frbb.tup.model.Cuenta;

import java.util.List;

public interface CuentaDao {

    Cuenta saveCuenta(Cuenta cuenta);

    Cuenta findCuenta(long numeroCuenta);

    List<Cuenta> findAll();

    Cuenta obtenerCuentaPorNumero(String numeroCuenta);

    void actualizarCuenta(Cuenta cuenta);
}