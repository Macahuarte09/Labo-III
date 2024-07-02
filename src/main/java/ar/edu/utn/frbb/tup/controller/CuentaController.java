package ar.edu.utn.frbb.tup.controller;

import ar.edu.utn.frbb.tup.model.Cuenta;
import ar.edu.utn.frbb.tup.model.exception.CuentaAlreadyExistsException;
import ar.edu.utn.frbb.tup.model.exception.TipoCuentaAlreadyExistsException;
import ar.edu.utn.frbb.tup.service.CuentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cuentas")
public class CuentaController {

    @Autowired
    private CuentaService cuentaService;

    @PostMapping("/alta")
    public String altaCuenta(@RequestBody Cuenta cuenta, @RequestParam long dniTitular) {
        try {
            cuentaService.darDeAltaCuenta(cuenta, dniTitular);
            return "Cuenta creada con éxito";
        } catch (Exception | CuentaAlreadyExistsException | TipoCuentaAlreadyExistsException e) {
            return "Error al crear la cuenta: " + e.getMessage();
        }
    }
}