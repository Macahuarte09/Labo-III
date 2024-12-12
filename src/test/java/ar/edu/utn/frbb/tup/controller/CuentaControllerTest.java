package ar.edu.utn.frbb.tup.controller;

import ar.edu.utn.frbb.tup.controller.dto.CuentaDto;
import ar.edu.utn.frbb.tup.model.Cuenta;
import ar.edu.utn.frbb.tup.model.TipoCuenta;
import ar.edu.utn.frbb.tup.model.exception.*;
import ar.edu.utn.frbb.tup.service.CuentaService;
import ar.edu.utn.frbb.tup.controller.validator.CuentaValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CuentaControllerTest {

    @Mock
    private CuentaService cuentaService;

    @Mock
    private CuentaValidator cuentaValidator;

    @InjectMocks
    private CuentaController cuentaController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindAll_ReturnsEmptyList() {
        // Configuro el mock para devolver una lista vacía
        when(cuentaService.findAll()).thenReturn(Collections.emptyList());

        // Ejecuto el controlador
        ResponseEntity<List<Cuenta>> response = cuentaController.findAll();

        // Verifico el resultado
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isEmpty());
    }

    @Test
    public void testFindAll_ReturnsListOfCuentas() {
        Cuenta cuenta1 = new Cuenta();
        cuenta1.setNumeroCuenta(1L);
        Cuenta cuenta2 = new Cuenta();
        cuenta2.setNumeroCuenta(2L);

        List<Cuenta> cuentas = Arrays.asList(cuenta1, cuenta2);

        // Configuro el mock
        when(cuentaService.findAll()).thenReturn(cuentas);

        // Ejecuto el controlador
        ResponseEntity<List<Cuenta>> response = cuentaController.findAll();

        // Verifico el resultado
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
    }

    @Test
    public void testGetCuentaByNumeroDeCuenta() throws CuentaNoEncontradaException {
        // Crear una cuenta simulada
        Cuenta cuenta = new Cuenta();
        cuenta.setNumeroCuenta(1L);

        // Configurar el mock
        when(cuentaService.findByID(1L)).thenReturn(cuenta);

        ResponseEntity<Cuenta> response = cuentaController.getCuentaByNumeroDeCuenta(1L);

        // Verifico el resultado
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(cuenta, response.getBody());
    }

    @Test
    public void testAltaCuenta() throws CuentaNoSoportadaException, TipoCuentaAlreadyExistsException, CuentaAlreadyExistsException, CantidadNegativaException, DatoIngresadoInvalidoException, ClienteNoEncontradoException {
        CuentaDto cuentaDto = getCuentaDto();
        Cuenta cuenta = new Cuenta(cuentaDto);

        when(cuentaService.darDeAltaCuenta(cuentaDto)).thenReturn(cuenta);

        ResponseEntity<?> respuesta = cuentaController.altaCuenta(cuentaDto);

        assertEquals(HttpStatus.CREATED, respuesta.getStatusCode());
        assertEquals(cuenta, respuesta.getBody());
    }
    public CuentaDto getCuentaDto(){
        CuentaDto cuentaDto = new CuentaDto();
        cuentaDto.setBalance(1000);
        cuentaDto.setTipoCuenta("C");
        cuentaDto.setMoneda("Pesos");
        cuentaDto.setDniTitular(45037310);
        return cuentaDto;
    }

    @Test
    public void testEliminarCuenta() throws CuentaNoEncontradaException {
        // Ejecuto el controlador
        ResponseEntity<String> response = cuentaController.eliminarCuenta(1L);

        // Verifico el resultado
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Cuenta eliminada correctamente", response.getBody());

        // Verifico el metodo
        verify(cuentaService, times(1)).eliminarCuenta(1L);
    }
}
