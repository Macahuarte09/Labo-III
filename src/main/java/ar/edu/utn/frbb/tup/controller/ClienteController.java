package ar.edu.utn.frbb.tup.controller;

import ar.edu.utn.frbb.tup.model.Cliente;
import ar.edu.utn.frbb.tup.model.exception.ClienteAlreadyExistsException;
import ar.edu.utn.frbb.tup.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping
        public ResponseEntity<List<Cliente>> getClientes() {
        List<Cliente> clientes = clienteService.obtenerTodosLosClientes();
        return ResponseEntity.ok(clientes);
    }

    @PostMapping("/alta")
    public String altaCliente(@RequestBody Cliente cliente) {   //public String altaCliente(@RequestBody ClienteDto clienteDto)
        try {
            clienteService.darDeAltaCliente(cliente);
            return "Cliente creado con éxito";
        } catch (ClienteAlreadyExistsException e) {
            return "Error: " + e.getMessage();
        }
    }

    @GetMapping("/clientes/{dni}")
    public ResponseEntity<Cliente> getCliente(@PathVariable long dni) {
        Cliente cliente = clienteService.buscarClientePorDni(dni);
        if (cliente != null) {
            return ResponseEntity.ok(cliente);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}