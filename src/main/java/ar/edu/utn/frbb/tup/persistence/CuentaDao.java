package ar.edu.utn.frbb.tup.persistence;

import ar.edu.utn.frbb.tup.model.Cuenta;
import ar.edu.utn.frbb.tup.model.exception.CuentaNoEncontradaException;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CuentaDao {

    Cuenta saveCuenta(Cuenta cuenta);

    Cuenta findCuenta(long numeroCuenta);

    List<Cuenta> findAll();

    Cuenta updateCuenta(Cuenta cuenta);

    void deleteCuenta(long id);
}