package ar.edu.utn.frbb.tup.service;

import org.springframework.stereotype.Service;
import java.util.Random;

// Simula invocar un servicio externo "Banelco", cuando la cuentaDestino no esta creada en nuestro sistema bancario.

@Service
public class Banelco {

    // 1 de cada 10 veces, podemos simular que la cuentaDestino existe.
    public boolean cuentaExiste(long cuentaDestino){
        Random random = new Random();
        int randomNumber = random.nextInt(10);
        return randomNumber == 0;
    }

    public void acreditar(double monto, long cuentaDestinoNumero){
        return;
    }
}
