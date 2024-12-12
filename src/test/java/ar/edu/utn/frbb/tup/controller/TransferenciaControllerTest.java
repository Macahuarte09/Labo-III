package ar.edu.utn.frbb.tup.controller;

import ar.edu.utn.frbb.tup.controller.dto.TransferenciaDto;
import ar.edu.utn.frbb.tup.model.Transferencia;
import ar.edu.utn.frbb.tup.model.TipoMoneda;
import ar.edu.utn.frbb.tup.service.TransferenciaService;
import ar.edu.utn.frbb.tup.controller.validator.TransferenciaValidator;
import ar.edu.utn.frbb.tup.model.exception.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TransferenciaControllerTest {

    @Mock
    private TransferenciaService transferenciaService;

    @Mock
    private TransferenciaValidator transferenciaValidator;

    @InjectMocks
    private TransferenciaController transferenciaController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAll() {
        // Crear transferencias simuladas
        Transferencia t1 = new Transferencia();
        t1.setCuentaOrigen(1111L);
        t1.setCuentaDestino(2222L);
        t1.setMonto(500.0);
        t1.setMoneda(TipoMoneda.PESOS);
        t1.setFecha(LocalDateTime.now());

        Transferencia t2 = new Transferencia();
        t2.setCuentaOrigen(1111L);
        t2.setCuentaDestino(2222L);
        t2.setMonto(300.0);
        t2.setMoneda(TipoMoneda.DOLARES);
        t2.setFecha(LocalDateTime.now());

        List<Transferencia> transferencias = Arrays.asList(t1, t2);

        // Configuro el mock
        when(transferenciaService.findAll()).thenReturn(transferencias);

        // Ejecuto el controlador
        ResponseEntity<List<Transferencia>> response = transferenciaController.getAll();

        // Verificar el resultado
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
    }

    @Test
    public void testGetByID() {
        long cuentaId = 1001L;
        Transferencia t1 = new Transferencia();
        t1.setCuentaOrigen(cuentaId);
        t1.setCuentaDestino(2001L);
        t1.setMonto(150.0);
        t1.setMoneda(TipoMoneda.PESOS);
        t1.setFecha(LocalDateTime.of(2024, 6, 15, 10, 0));

        List<Transferencia> transferencias = Arrays.asList(t1);

        // Configuro el mock
        when(transferenciaService.find(cuentaId)).thenReturn(transferencias);

        // Ejecuto
        ResponseEntity<Map<String, Object>> response = transferenciaController.getByID(cuentaId);

        // Verificar
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(cuentaId, response.getBody().get("numeroCuenta"));

        List<Map<String, Object>> transacciones = (List<Map<String, Object>>) response.getBody().get("transacciones");
        assertEquals(1, transacciones.size());
        assertEquals("DEBITO", transacciones.get(0).get("tipo"));
        assertEquals("Transferencia saliente", transacciones.get(0).get("descripcionBreve"));
        assertEquals(150.0, transacciones.get(0).get("monto"));
    }

    @Test
    public void testPostTransfer() throws Exception, CantidadNegativaException {
        // Creo una TransferenciaDto
        TransferenciaDto transferenciaDto = new TransferenciaDto();
        transferenciaDto.setCuentaOrigen(1001L);
        transferenciaDto.setCuentaDestino(2001L);
        transferenciaDto.setMonto(200.0);
        transferenciaDto.setMoneda("PESO");

        // Creo una Transferencia
        Transferencia transferencia = new Transferencia();
        transferencia.setCuentaOrigen(1001L);
        transferencia.setCuentaDestino(2001L);
        transferencia.setMonto(200.0);
        transferencia.setMoneda(TipoMoneda.PESOS);
        transferencia.setFecha(LocalDateTime.now());

        // Configuro el mock
        doNothing().when(transferenciaValidator).validate(transferenciaDto);
        when(transferenciaService.realizarTransferencia(transferenciaDto)).thenReturn(transferencia);

        // Ejecuto
        ResponseEntity<?> response = transferenciaController.postTransfer(transferenciaDto);

        // Verifico
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(transferencia, response.getBody());
    }
}

