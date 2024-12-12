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
    public void testGetClienteByDNI() throws ClienteNoEncontradoException {
        ClienteDto clienteDto = getClienteDto();
        Cliente cliente = new Cliente(clienteDto);

        when(clienteService.buscarClientePorDni(cliente.getDni())).thenReturn(cliente);

        ResponseEntity<Cliente> respuesta = clienteController.getClienteByID(cliente.getDni());

        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertEquals(cliente, respuesta.getBody());
    }

    @Test
    public void testAltaCliente() throws ClienteAlreadyExistsException, DatoIngresadoInvalidoException, ClienteMenorDeEdadException {
        ClienteDto clienteDto = getClienteDto();
        Cliente cliente = new Cliente(clienteDto);

        when(clienteService.darDeAltaCliente(clienteDto)).thenReturn(cliente);

        ResponseEntity<?> respuesta = clienteController.altaCliente(clienteDto);

        assertEquals(HttpStatus.CREATED, respuesta.getStatusCode());
        assertEquals(cliente, respuesta.getBody());
    }

    public ClienteDto getClienteDto(){
        ClienteDto clienteDto = new ClienteDto();
        clienteDto.setNombre("Macarena");
        clienteDto.setApellido("Huarte");
        clienteDto.setDni(12345678L);
        clienteDto.setFechaNacimiento(LocalDate.of(2003, 2, 11));
        clienteDto.setTipoPersona("F");
        return clienteDto;
    }

}