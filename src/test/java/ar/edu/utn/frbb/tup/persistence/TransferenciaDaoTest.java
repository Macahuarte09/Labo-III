package ar.edu.utn.frbb.tup.persistence;

import ar.edu.utn.frbb.tup.model.Transferencia;
import ar.edu.utn.frbb.tup.persistence.TransferenciaDao;
import ar.edu.utn.frbb.tup.persistence.impl.TransferenciaDaoImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TransferenciaDaoTest {
    private TransferenciaDao transferenciaDao;

    @BeforeEach
    void setUp() {
        transferenciaDao = new TransferenciaDaoImpl();
    }

    @Test
    void testGuardarTransferencia() {
        Transferencia transferencia = new Transferencia(12345L, 67890L, 100.0);
        transferenciaDao.guardarTransferencia(transferencia);

        assertEquals(1, transferenciaDao.findAllTransfers().size());
    }

    @Test
    void testFindTransfersByID() {
        Transferencia transferencia1 = new Transferencia(12345L, 67890L, 100.0);
        Transferencia transferencia2 = new Transferencia(12345L, 54321L, 200.0);
        transferenciaDao.guardarTransferencia(transferencia1);
        transferenciaDao.guardarTransferencia(transferencia2);

        assertEquals(2, transferenciaDao.findTransfersByID(12345L).size());
    }

    @Test
    void testFindAllTransfers() {
        Transferencia transferencia1 = new Transferencia(12345L, 67890L, 100.0);
        Transferencia transferencia2 = new Transferencia(67890L, 12345L, 200.0);
        transferenciaDao.guardarTransferencia(transferencia1);
        transferenciaDao.guardarTransferencia(transferencia2);

        assertEquals(2, transferenciaDao.findAllTransfers().size());
    }

}