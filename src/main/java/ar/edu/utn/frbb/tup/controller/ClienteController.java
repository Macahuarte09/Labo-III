package ar.edu.utn.frbb.tup.controller;

import ar.edu.utn.frbb.tup.controller.dto.ClienteDto;
import ar.edu.utn.frbb.tup.controller.dto.CuentaDto;
import ar.edu.utn.frbb.tup.controller.validator.ClienteValidator;
import ar.edu.utn.frbb.tup.model.Cliente;
import ar.edu.utn.frbb.tup.model.Cuenta;
import ar.edu.utn.frbb.tup.model.exception.ClienteAlreadyExistsException;
import ar.edu.utn.frbb.tup.model.exception.TipoCuentaAlreadyExistsException;
import ar.edu.utn.frbb.tup.service.ClienteService;
import ar.edu.utn.frbb.tup.service.impl.ClienteServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ClienteValidator clienteValidator;

    @GetMapping
    public ResponseEntity<List<Cliente>> getClientes() {
        List<Cliente> clientes = clienteService.obtenerTodosLosClientes();
        return new ResponseEntity<>(clientes, HttpStatus.OK);
    }

    @GetMapping("/{dni}")
    public ResponseEntity<Cliente> getClienteByID(@PathVariable("dni") long dni) {
        Cliente cliente = clienteService.buscarClientePorDni(dni);
        return new ResponseEntity<>(cliente, HttpStatus.OK);
    }

    @PostMapping("/alta")
    public Cliente altaCliente(@RequestBody ClienteDto clienteDto) throws ClienteAlreadyExistsException {
        clienteValidator.validate(clienteDto);
        return clienteService.darDeAltaCliente(clienteDto);
    }

    @PostMapping("/cuentas")
    public ResponseEntity<Cliente> agregarCuenta(@RequestBody CuentaDto cuentaDto) throws TipoCuentaAlreadyExistsException {
        Cuenta cuenta = new Cuenta(cuentaDto);
        clienteService.agregarCuenta(cuenta, cuentaDto.getDniTitular());
        Cliente cliente = clienteService.buscarClientePorDni(cuentaDto.getDniTitular());
        return new ResponseEntity<>(cliente, HttpStatus.OK);
    }

    @GetMapping("/{dni}/cuentas")
    public ResponseEntity<List<CuentaDto>> getCuentasByDniTitular(@PathVariable("dni") long dni) {
        List<CuentaDto> cuentas = ((ClienteServiceImpl) clienteService).obtenerCuentasPorDniTitular(dni);
        return new ResponseEntity<>(cuentas, HttpStatus.OK);
    }
}
