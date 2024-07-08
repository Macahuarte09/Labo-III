package ar.edu.utn.frbb.tup.controller;

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

    @GetMapping
    public ResponseEntity<List<Cuenta>> findAll() {
        List<Cuenta> cuentas = ((CuentaServiceImpl) cuentaService).findAll();
        return new ResponseEntity<>(cuentas, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cuenta> getCuentaByID(@PathVariable("id") long id) {
        Cuenta cuenta = cuentaService.find(id);
        return new ResponseEntity<>(cuenta, HttpStatus.OK);
    }

    @PostMapping("/alta")
    public ResponseEntity<String> altaCuenta(@RequestBody Cuenta cuenta) throws CuentaNoSoportadaException, TipoCuentaAlreadyExistsException, CuentaAlreadyExistsException {
        cuentaService.darDeAltaCuenta(cuenta);
        return new ResponseEntity<>("Cuenta creada con éxito", HttpStatus.CREATED);
    }
}