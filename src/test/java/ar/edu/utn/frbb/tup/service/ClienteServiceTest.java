package ar.edu.utn.frbb.tup.service;

import ar.edu.utn.frbb.tup.controller.dto.ClienteDto;
import ar.edu.utn.frbb.tup.model.Cliente;
import ar.edu.utn.frbb.tup.model.Cuenta;
import ar.edu.utn.frbb.tup.model.exception.ClienteAlreadyExistsException;
import ar.edu.utn.frbb.tup.model.exception.ClienteMenorDeEdadException;
import ar.edu.utn.frbb.tup.model.exception.ClienteNoEncontradoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClienteServiceTest {

    @Mock
    private ClienteService clienteService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testDarDeAltaCliente() throws ClienteAlreadyExistsException, ClienteMenorDeEdadException {
        ClienteDto clienteDto = new ClienteDto();
        clienteDto.setNombre("luciano");
        clienteDto.setApellido("Salotto");
        clienteDto.setDni(12345678L);
        clienteDto.setFechaNacimiento(LocalDate.of(1990, 1, 1));

        Cliente cliente = new Cliente();
        cliente.setDni(12345678L);
        cliente.setNombre("Luciano");

        when(clienteService.darDeAltaCliente(clienteDto)).thenReturn(cliente);

        Cliente result = clienteService.darDeAltaCliente(clienteDto);

        assertEquals(cliente, result);
        verify(clienteService, times(1)).darDeAltaCliente(clienteDto);
    }

    @Test
    void testDarDeAltaCliente_AlreadyExists() throws ClienteAlreadyExistsException, ClienteMenorDeEdadException {
        ClienteDto clienteDto = new ClienteDto();
        clienteDto.setDni(12345678L);

        when(clienteService.darDeAltaCliente(clienteDto)).thenThrow(new ClienteAlreadyExistsException("Cliente ya existe"));

        assertThrows(ClienteAlreadyExistsException.class, () -> clienteService.darDeAltaCliente(clienteDto));
    }

    @Test
    void testBuscarClientePorDni() throws ClienteNoEncontradoException {
        Cliente cliente = new Cliente();
        cliente.setDni(12345678L);
        cliente.setNombre("Maria");

        when(clienteService.buscarClientePorDni(12345678L)).thenReturn(cliente);

        Cliente result = clienteService.buscarClientePorDni(12345678L);

        assertEquals(cliente, result);
    }

    @Test
    void testBuscarClientePorDni_NotFound() throws ClienteNoEncontradoException {
        when(clienteService.buscarClientePorDni(99999999L)).thenThrow(new ClienteNoEncontradoException("Cliente no encontrado"));

        assertThrows(ClienteNoEncontradoException.class, () -> clienteService.buscarClientePorDni(99999999L));
    }
}
