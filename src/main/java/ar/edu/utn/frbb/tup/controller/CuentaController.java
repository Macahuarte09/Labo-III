package ar.edu.utn.frbb.tup.controller;

import ar.edu.utn.frbb.tup.controller.dto.CuentaDto;
import ar.edu.utn.frbb.tup.controller.validator.CuentaValidator;
import ar.edu.utn.frbb.tup.model.Cliente;
import ar.edu.utn.frbb.tup.model.Cuenta;
import ar.edu.utn.frbb.tup.model.exception.CuentaAlreadyExistsException;
import ar.edu.utn.frbb.tup.model.exception.CuentaNoSoportadaException;
import ar.edu.utn.frbb.tup.model.exception.TipoCuentaAlreadyExistsException;
import ar.edu.utn.frbb.tup.service.CuentaService;
import ar.edu.utn.frbb.tup.service.impl.CuentaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cuentas")
public class CuentaController {

    @Autowired
    private CuentaService cuentaService;

    @Autowired
    private CuentaValidator cuentaValidator;

    @GetMapping
    public ResponseEntity<List<CuentaDto>> findAll() {
        List<CuentaDto> cuentas = cuentaService.findAll();
        return new ResponseEntity<>(cuentas, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CuentaDto> getCuentaByID(@PathVariable("id") long id) {
        Cuenta cuenta = cuentaService.find(id);
        Cliente titular = cuenta.getTitular();
        CuentaDto cuentaDto = new CuentaDto();
        cuentaDto.setNumeroCuenta(cuenta.getNumeroCuenta());
        cuentaDto.setFechaCreacion(cuenta.getFechaCreacion());
        cuentaDto.setBalance(cuenta.getBalance());
        cuentaDto.setTipoCuenta(cuenta.getTipoCuenta().toString());
        cuentaDto.setMoneda(cuenta.getMoneda().toString());
        cuentaDto.setDniTitular(titular.getDni());
        cuentaDto.setNombreTitular(titular.getNombre());
        cuentaDto.setApellidoTitular(titular.getApellido());
        return new ResponseEntity<>(cuentaDto, HttpStatus.OK);
    }

    @PostMapping("/alta")
    public Cuenta altaCuenta(@RequestBody CuentaDto cuentaDto) throws CuentaNoSoportadaException, TipoCuentaAlreadyExistsException, CuentaAlreadyExistsException {
        cuentaValidator.validate(cuentaDto);
        return cuentaService.darDeAltaCuenta(cuentaDto);
    }
}
