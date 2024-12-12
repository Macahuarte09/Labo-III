package ar.edu.utn.frbb.tup.persistence;

import ar.edu.utn.frbb.tup.model.Cliente;
import ar.edu.utn.frbb.tup.persistence.ClienteDao;
import ar.edu.utn.frbb.tup.persistence.impl.ClienteDaoImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ClienteDaoTest {

    private ClienteDao clienteDao;

    @BeforeEach
    void setUp() {
        clienteDao = new ClienteDaoImpl();  // Se inicializa el DAO antes de cada test
    }

    @Test
    void testSaveCliente() {
        Cliente cliente = new Cliente(12345678L, "Maria emilia");
        Cliente savedCliente = clienteDao.saveCliente(cliente);
        assertNotNull(savedCliente);
        assertEquals(cliente.getDni(), savedCliente.getDni());
    }

    @Test
    void testFindCliente() {
        Cliente cliente = new Cliente(12345678L, "Maria emilia");
        clienteDao.saveCliente(cliente);

        Cliente foundCliente = clienteDao.findCliente(12345678L);
        assertNotNull(foundCliente);
        assertEquals(cliente.getDni(), foundCliente.getDni());
        assertEquals(cliente.getNombre(), foundCliente.getNombre());
    }

    @Test
    void testFindAll() {
        Cliente cliente1 = new Cliente(12345678L, "Maria emilia");
        Cliente cliente2 = new Cliente(87654321L, "Maria Lopez");
        clienteDao.saveCliente(cliente1);
        clienteDao.saveCliente(cliente2);

        assertEquals(1, clienteDao.findAll().size());
    }

    @Test
    void testUpdateCliente() {
        Cliente cliente = new Cliente(12345678L, "Maria emilia");
        clienteDao.saveCliente(cliente);

        // Actualizar cliente
        cliente.setNombre("Maria emilia");
        Cliente updatedCliente = clienteDao.updateCliente(cliente);

        assertNotNull(updatedCliente);
        assertEquals("Maria emilia", updatedCliente.getNombre());
    }

    @Test
    void testDeleteCliente() {
        Cliente cliente = new Cliente(12345678L, "Maria emilia");
        clienteDao.saveCliente(cliente);

        clienteDao.deleteCliente(12345678L);
        Cliente deletedCliente = clienteDao.findCliente(12345678L);

        assertNull(deletedCliente);
    }
}
