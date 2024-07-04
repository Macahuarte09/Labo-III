package ar.edu.utn.frbb.tup.controller;

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

    @PostMapping("/alta")
    public ResponseEntity<String> altaCuenta(@RequestBody Cuenta cuenta) {
        try {
            cuentaService.darDeAltaCuenta(cuenta);
            return new ResponseEntity<>("Cuenta creada con éxito", HttpStatus.CREATED);
        } catch (CuentaAlreadyExistsException | TipoCuentaAlreadyExistsException e) {
            return new ResponseEntity<>("Error al crear la cuenta: " + e.getMessage(), HttpStatus.CONFLICT);
        } catch (CuentaNoSoportadaException e) {
            return new ResponseEntity<>("Error al crear la cuenta: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Error inesperado: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}