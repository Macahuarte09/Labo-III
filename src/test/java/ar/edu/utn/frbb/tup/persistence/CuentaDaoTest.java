package ar.edu.utn.frbb.tup.persistence;

import ar.edu.utn.frbb.tup.model.Cuenta;
import ar.edu.utn.frbb.tup.persistence.CuentaDao;
import ar.edu.utn.frbb.tup.persistence.impl.CuentaDaoImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CuentaDaoTest {

    private CuentaDao cuentaDao;

    @BeforeEach
    void setUp() {
        cuentaDao = new CuentaDaoImpl();
    }

    @Test
    void testSaveCuenta() {
        Cuenta cuenta = new Cuenta(12345, 1000.0);
        Cuenta savedCuenta = cuentaDao.saveCuenta(cuenta);
        assertNotNull(savedCuenta);
        assertEquals(cuenta.getNumeroCuenta(), savedCuenta.getNumeroCuenta());
    }

    @Test
    void testFindCuenta() {
        Cuenta cuenta = new Cuenta(12345, 1000.0);
        cuentaDao.saveCuenta(cuenta);

        Cuenta foundCuenta = cuentaDao.findCuenta(12345L);
        assertNotNull(foundCuenta);
        assertEquals(cuenta.getNumeroCuenta(), foundCuenta.getNumeroCuenta());
        assertEquals(cuenta.getBalance(), foundCuenta.getBalance());
    }

    @Test
    void testFindAll() {
        Cuenta cuenta1 = new Cuenta(12345, 1000.0);
        Cuenta cuenta2 = new Cuenta(67890, 2000.0);
        cuentaDao.saveCuenta(cuenta1);
        cuentaDao.saveCuenta(cuenta2);

        assertEquals(1, cuentaDao.findAll().size());
    }

    @Test
    void testUpdateCuenta() {
        Cuenta cuenta = new Cuenta(12345, 1000.0);
        cuentaDao.saveCuenta(cuenta);

        // Actualizar saldo
        cuenta.setBalance(1500.0);
        Cuenta updatedCuenta = cuentaDao.updateCuenta(cuenta);

        assertNotNull(updatedCuenta);
        assertEquals(1500.0, updatedCuenta.getBalance());
    }

    @Test
    void testDeleteCuenta() {
        Cuenta cuenta = new Cuenta(12345, 1000.0);
        cuentaDao.saveCuenta(cuenta);

        cuentaDao.deleteCuenta(12345L);
        Cuenta deletedCuenta = cuentaDao.findCuenta(12345L);

        assertNull(deletedCuenta);
    }
}
