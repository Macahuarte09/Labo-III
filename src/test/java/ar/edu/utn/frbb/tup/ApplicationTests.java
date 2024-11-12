package ar.edu.utn.frbb.tup;

import ar.edu.utn.frbb.tup.service.ClienteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ApplicationTests {

	@Autowired
	private ClienteService clienteService;

	@Test
	void contextLoads() {
		// Verificar que el servicio no sea null
		assert clienteService != null;
	}
}

