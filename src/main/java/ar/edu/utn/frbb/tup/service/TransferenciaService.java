package ar.edu.utn.frbb.tup.service;

import ar.edu.utn.frbb.tup.model.CantidadNegativaException;
import ar.edu.utn.frbb.tup.model.Cuenta;
import ar.edu.utn.frbb.tup.model.NoAlcanzaException;
import ar.edu.utn.frbb.tup.model.Transferencia;
import ar.edu.utn.frbb.tup.persistence.CuentaDao;
import ar.edu.utn.frbb.tup.persistence.TransferenciaDao;

public class TransferenciaService {
    private TransferenciaDao transferenciaDao;
    private CuentaDao cuentaDao;

    public TransferenciaService(TransferenciaDao transferenciaDao, CuentaDao cuentaDao) {
        this.transferenciaDao = transferenciaDao;
        this.cuentaDao = cuentaDao;
    }

    public void realizarTransferencia(String cuentaOrigen, String cuentaDestino, double monto) throws Exception, NoAlcanzaException, CantidadNegativaException {
        if (cuentaOrigen.equals(cuentaDestino)) {
            throw new Exception("La cuenta de origen y destino no pueden ser la misma.");
        }

        if (monto <= 0) {
            throw new Exception("El monto de la transferencia debe ser positivo.");
        }

        // Obtener cuentas desde el DAO
        Cuenta origen = cuentaDao.obtenerCuentaPorNumero(cuentaOrigen);
        Cuenta destino = cuentaDao.obtenerCuentaPorNumero(cuentaDestino);

        if (origen == null) {
            throw new Exception("La cuenta de origen no existe.");
        }

        if (destino == null) {
            throw new Exception("La cuenta de destino no existe.");
        }

        // Verificar saldo suficiente
        if (origen.getBalance() < monto) {
            throw new Exception("Saldo insuficiente en la cuenta de origen.");
        }

        // Realizar la transferencia
        origen.debitarDeCuenta((int) monto); // Convertimos el monto a int
        destino.setBalance(destino.getBalance() + (int) monto); // Convertimos el monto a int

        // Guardar las actualizaciones en las cuentas
        cuentaDao.saveCuenta(origen);
        cuentaDao.saveCuenta(destino);

        // Crear y guardar la transferencia
        Transferencia transferencia = new Transferencia(cuentaOrigen, cuentaDestino, monto);
        transferenciaDao.guardarTransferencia(transferencia);
    }
}
