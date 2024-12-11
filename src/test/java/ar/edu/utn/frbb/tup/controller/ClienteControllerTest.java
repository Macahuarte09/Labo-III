package ar.edu.utn.frbb.tup.controller;

import ar.edu.utn.frbb.tup.controller.dto.ClienteDto;
import ar.edu.utn.frbb.tup.controller.validator.ClienteValidator;
import ar.edu.utn.frbb.tup.model.Cliente;
import ar.edu.utn.frbb.tup.model.exception.ClienteAlreadyExistsException;
import ar.edu.utn.frbb.tup.model.exception.ClienteMenorDeEdadException;
import ar.edu.utn.frbb.tup.model.exception.ClienteNoEncontradoException;
import ar.edu.utn.frbb.tup.model.exception.DatoIngresadoInvalidoException;
import ar.edu.utn.frbb.tup.service.ClienteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class ClienteControllerTest {
    @Mock
    private ClienteService clienteService;

    @Mock
    private ClienteValidator clienteValidator;

    @InjectMocks
    private ClienteController clienteController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetClientes() {
        // Configuro el mock para devolver una lista vacía
        when(clienteService.obtenerTodosLosClientes()).thenReturn(Collections.emptyList());

        ResponseEntity<List<Cliente>> response = clienteController.getClientes();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isEmpty());
    }

    @Test
    public void testGetClienteByDni() throws ClienteNoEncontradoException {
        Cliente cliente = new Cliente();
        cliente.setDni(12345678L);

        when(clienteService.buscarClientePorDni(12345678L)).thenReturn(cliente);

        ResponseEntity<Cliente> response = clienteController.getClienteByID(12345678L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(cliente, response.getBody());
    }

    @Test
    public void testAltaCliente() throws ClienteAlreadyExistsException, DatoIngresadoInvalidoException, ClienteMenorDeEdadException {
        ClienteDto clienteDto = new ClienteDto();
        clienteDto.setNombre("Macarena");
        clienteDto.setApellido("Huarte");
        clienteDto.setDni(45037310L);
        clienteDto.setFechaNacimiento(LocalDate.of(2003, 2, 11));

        Cliente cliente = new Cliente();
        cliente.setDni(45037310L);
        cliente.setNombre("Macarena");

        // Configuro el mock
        when(clienteService.darDeAltaCliente(clienteDto)).thenReturn(cliente);

        // Ejecuto el controller
        ResponseEntity<?> response = clienteController.altaCliente(clienteDto);

        // Verifico el resultado
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(cliente, response.getBody());
    }

}