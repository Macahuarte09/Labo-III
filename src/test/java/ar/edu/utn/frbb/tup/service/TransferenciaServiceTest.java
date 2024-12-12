package ar.edu.utn.frbb.tup.service;

import ar.edu.utn.frbb.tup.controller.dto.ClienteDto;
import ar.edu.utn.frbb.tup.controller.dto.CuentaDto;
import ar.edu.utn.frbb.tup.controller.dto.TransferenciaDto;
import ar.edu.utn.frbb.tup.model.Cuenta;
import ar.edu.utn.frbb.tup.model.Transferencia;
import ar.edu.utn.frbb.tup.model.exception.CuentaNoEncontradaException;
import ar.edu.utn.frbb.tup.model.exception.NoAlcanzaException;
import ar.edu.utn.frbb.tup.persistence.CuentaDao;
import ar.edu.utn.frbb.tup.persistence.TransferenciaDao;
import ar.edu.utn.frbb.tup.service.impl.TransferenciaServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TransferenciaServiceTest {

    @Mock
    CuentaDao cuentaDao;

    @Mock
    TransferenciaDao transferenciaDao;

    @InjectMocks
    TransferenciaServiceImpl transferenciaService;

    @BeforeEach
    public void setUp() {
        // MockitoExtension se encarga de la inicialización de los mocks
    }

    @Test
    public void testRealizarTransferencia() throws Exception {
        // Arrange
        TransferenciaDto transferenciaDto = new TransferenciaDto();
        transferenciaDto.setCuentaOrigen(1L);
        transferenciaDto.setCuentaDestino(2L);
        transferenciaDto.setMonto(100);
        transferenciaDto.setMoneda("Pesos");

        Cuenta cuentaOrigen = new Cuenta(1L, "Pesos", 1000);
        Cuenta cuentaDestino = new Cuenta(2L, "Pesos", 500);

        when(cuentaDao.findCuenta(1L)).thenReturn(cuentaOrigen);
        when(cuentaDao.findCuenta(2L)).thenReturn(cuentaDestino);

        // Act
        Transferencia transferencia = transferenciaService.realizarTransferencia(transferenciaDto);

        // Assert
        assertNotNull(transferencia);
        assertEquals(1L, transferencia.getCuentaOrigen());
        assertEquals(2L, transferencia.getCuentaDestino());
        assertEquals(100, transferencia.getMonto());

        verify(transferenciaDao, times(1)).guardarTransferencia(any(Transferencia.class));
    }

    @Test
    public void testRealizarTransferencia_ErrorTipoMoneda() throws Exception {
        // Arrange
        TransferenciaDto transferenciaDto = getTransferenciaDto();

        CuentaDto cuentaDtoOrigen = getCuentaDtoOrigen();
        Cuenta cuentaOrigen = new Cuenta(cuentaDtoOrigen);

        when(cuentaDao.findCuenta(1L)).thenReturn(cuentaOrigen);

        // Act & Assert
        assertThrows(Exception.class, () -> transferenciaService.realizarTransferencia(transferenciaDto));
    }

    @Test
    public void testRealizarTransferencia_ErrorCuentaOrigenNoEncontrada() throws Exception {
        // Arrange
        TransferenciaDto transferenciaDto = new TransferenciaDto();
        transferenciaDto.setCuentaOrigen(1L);
        transferenciaDto.setCuentaDestino(2L);
        transferenciaDto.setMonto(100);
        transferenciaDto.setMoneda("Pesos");

        when(cuentaDao.findCuenta(1L)).thenReturn(null); // Cuenta origen no encontrada

        // Act & Assert
        Exception exception = assertThrows(Exception.class, () -> {
            transferenciaService.realizarTransferencia(transferenciaDto);
        });

        assertEquals("Cuenta origen no encontrada", exception.getMessage());
    }



    public CuentaDto getCuentaDtoOrigen() {
        CuentaDto cuentaDto = new CuentaDto();
        cuentaDto.setBalance(1000);
        cuentaDto.setTipoCuenta("C");
        cuentaDto.setMoneda("Pesos");
        cuentaDto.setDniTitular(45037310);
        return cuentaDto;
    }

    public CuentaDto getCuentaDtoDestino() {
        CuentaDto cuentaDto = new CuentaDto();
        cuentaDto.setBalance(1000);
        cuentaDto.setTipoCuenta("C");
        cuentaDto.setMoneda("Pesos");
        cuentaDto.setDniTitular(45037311);
        return cuentaDto;
    }

    public ClienteDto getClienteDto() {
        ClienteDto clienteDto = new ClienteDto();
        clienteDto.setNombre("macarena");
        clienteDto.setApellido("huarte");
        clienteDto.setDni(12345678L);
        clienteDto.setFechaNacimiento(LocalDate.of(1997, 10, 17));
        clienteDto.setTipoPersona("F");
        return clienteDto;
    }

    public TransferenciaDto getTransferenciaDto() {
        TransferenciaDto transferenciaDto = new TransferenciaDto();
        transferenciaDto.setCuentaOrigen(1L);
        transferenciaDto.setCuentaDestino(2L);
        transferenciaDto.setMonto(99);
        transferenciaDto.setMoneda("Pesos");
        return transferenciaDto;
    }
}
