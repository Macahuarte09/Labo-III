package ar.edu.utn.frbb.tup.service;

import ar.edu.utn.frbb.tup.controller.dto.CuentaDto;
import ar.edu.utn.frbb.tup.model.Cliente;
import ar.edu.utn.frbb.tup.model.Cuenta;

import ar.edu.utn.frbb.tup.model.TipoCuenta;
import ar.edu.utn.frbb.tup.model.TipoMoneda;
import ar.edu.utn.frbb.tup.model.exception.*;

import ar.edu.utn.frbb.tup.persistence.CuentaDao;
import ar.edu.utn.frbb.tup.service.impl.ClienteServiceImpl;
import ar.edu.utn.frbb.tup.service.impl.CuentaServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static ar.edu.utn.frbb.tup.model.TipoPersona.PERSONA_FISICA;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class CuentaServiceTest {
    @Mock
    private CuentaDao cuentaDao;

    @Mock
    private ClienteServiceImpl clienteService;

    @InjectMocks
    private CuentaServiceImpl cuentaService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testDarDeAltaCuenta() throws Exception, CuentaAlreadyExistsException, CuentaNoSoportadaException, ClienteNoEncontradoException {
        CuentaDto cuentaDto = getCuentaDto();

        when(cuentaDao.findCuenta(anyLong())).thenReturn(null);
        // La cuenta se guarda
        when(cuentaDao.saveCuenta(any(Cuenta.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Llamamos al metodo de servicio
        Cuenta cuenta = cuentaService.darDeAltaCuenta(cuentaDto);

        assertNotNull(cuenta);
    }
    // Creamos la cuenta
    public CuentaDto getCuentaDto() {
        CuentaDto cuentaDto = new CuentaDto();
        cuentaDto.setBalance(1234);
        cuentaDto.setTipoCuenta("C");
        cuentaDto.setMoneda("P");
        cuentaDto.setDniTitular(45037310);
        return cuentaDto;
    }



}