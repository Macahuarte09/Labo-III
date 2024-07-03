package ar.edu.utn.frbb.tup.service;

import ar.edu.utn.frbb.tup.model.CantidadNegativaException;
import ar.edu.utn.frbb.tup.model.Cuenta;
import ar.edu.utn.frbb.tup.model.NoAlcanzaException;
import ar.edu.utn.frbb.tup.model.Transferencia;
import ar.edu.utn.frbb.tup.persistence.CuentaDao;
import ar.edu.utn.frbb.tup.persistence.TransferenciaDao;
import ar.edu.utn.frbb.tup.persistence.impl.TransferenciaDaoImpl;

public class TransferenciaService {
    private TransferenciaDaoImpl transferenciaDao;
    private CuentaDao cuentaDao;

    public TransferenciaService(TransferenciaDaoImpl transferenciaDao, CuentaDao cuentaDao) {
        this.transferenciaDao = transferenciaDao;
        this.cuentaDao = cuentaDao;
    }

    public void realizarTransferencia(long cuentaOrigenNumero, long cuentaDestinoNumero, double monto) throws Exception, NoAlcanzaException, CantidadNegativaException {
        Cuenta cuentaOrigen = cuentaDao.findCuenta(cuentaOrigenNumero);
        Cuenta cuentaDestino = cuentaDao.findCuenta(cuentaDestinoNumero);

        // Verificar que ambas cuentas existan
        if (cuentaOrigen == null) {
            throw new Exception("Cuenta origen no encontrada");
        }
        if (cuentaDestino == null) {
            throw new Exception("Cuenta destino no encontrada");
        }

        if (cuentaOrigen.getBalance() < monto) {
            throw new Exception("Saldo insuficiente en la cuenta origen");
        }

        // Actualizar saldos
        cuentaOrigen.debitar(monto);
        cuentaDestino.acreditar(monto);

        // Crear una nueva instancia de Transferencia
        Transferencia transferencia = new Transferencia();
        transferencia.setMonto(monto);
        transferencia.setCuentaOrigen(cuentaOrigen);
        transferencia.setCuentaDestino(cuentaDestino);
        //transferencia.setFecha(LocalDateTime.now());
        //transferencia.setEstado("COMPLETADA");

        transferenciaDao.guardarTransferencia(transferencia);

        cuentaDao.actualizarCuenta(cuentaOrigen);
        cuentaDao.actualizarCuenta(cuentaDestino);
    }
}
