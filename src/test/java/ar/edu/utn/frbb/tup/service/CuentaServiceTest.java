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

import java.util.Arrays;
import java.util.List;

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
    @Test
    public void testDarDeAltaCuenta_ErrorCuentaYaExiste() {
        CuentaDto cuentaDto = getCuentaDto();

        when(cuentaDao.findCuenta(anyLong())).thenReturn(new Cuenta());

        assertThrows(CuentaAlreadyExistsException.class, () -> cuentaService.darDeAltaCuenta(cuentaDto));
    }
    @Test
    public void testTipoCuentaEstaSoportada() {
        Cuenta cuenta = new Cuenta();
        cuenta.setTipoCuenta(TipoCuenta.CUENTA_CORRIENTE); // Ejemplo de tipo de cuenta soportado

        boolean result = cuentaService.tipoCuentaEstaSoportada(cuenta);

        assertTrue(result, "El tipo de cuenta debería estar soportado");
    }

    @Test
    public void testTipoCuentaNoEstaSoportada() {
        Cuenta cuenta = new Cuenta();
        cuenta.setTipoCuenta(null); // o cualquier otro valor no soportado

        boolean result = cuentaService.tipoCuentaEstaSoportada(cuenta);

        assertFalse(result, "El tipo de cuenta no debería estar soportado");
    }

    @Test
    public void testFindByID() throws CuentaNoEncontradaException {
        CuentaDto cuentaDto = getCuentaDto();
        Cuenta cuentaEsperada = new Cuenta(cuentaDto);

        long id = cuentaEsperada.getNumeroCuenta();

        when(cuentaDao.findCuenta(id)).thenReturn(cuentaEsperada);

        Cuenta cuentaObtenida = cuentaService.findByID(id);

        assertEquals(cuentaEsperada, cuentaObtenida);
    }

    @Test
    public void testFindByID_NoExiste() throws CuentaNoEncontradaException {
        long id = 90009;

        // Simula que no se encuentra la cuenta con el ID
        when(cuentaDao.findCuenta(id)).thenReturn(null);


        assertThrows(CuentaNoEncontradaException.class, () -> cuentaService.findByID(id));
    }

    @Test
    public void testFindAll() {
        List<Cuenta> cuentasMock = Arrays.asList(new Cuenta(), new Cuenta());
        when(cuentaDao.findAll()).thenReturn(cuentasMock);

        List<Cuenta> cuentas = cuentaService.findAll();

        assertNotNull(cuentas);
        assertEquals(2, cuentas.size());
    }
    @Test
    public void testEliminarCuenta() throws CuentaNoEncontradaException {
        long id = 1L;
        Cuenta cuenta = new Cuenta();
        cuenta.setNumeroCuenta(id);

        when(cuentaDao.findCuenta(id)).thenReturn(cuenta);
        doNothing().when(cuentaDao).deleteCuenta(id);

        cuentaService.eliminarCuenta(id);

        verify(cuentaDao, times(1)).deleteCuenta(id);
    }
    @Test
    public void testEliminarCuenta_NoExiste() {
        long id = 999L;
        when(cuentaDao.findCuenta(id)).thenReturn(null);

        assertThrows(CuentaNoEncontradaException.class, () -> cuentaService.eliminarCuenta(id));
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