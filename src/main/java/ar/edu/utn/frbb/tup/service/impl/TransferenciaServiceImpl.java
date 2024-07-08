package ar.edu.utn.frbb.tup.service.impl;

import ar.edu.utn.frbb.tup.model.CantidadNegativaException;
import ar.edu.utn.frbb.tup.model.Cuenta;
import ar.edu.utn.frbb.tup.model.NoAlcanzaException;
import ar.edu.utn.frbb.tup.model.Transferencia;
import ar.edu.utn.frbb.tup.persistence.CuentaDao;
import ar.edu.utn.frbb.tup.persistence.TransferenciaDao;
import ar.edu.utn.frbb.tup.service.TransferenciaService;
import org.springframework.stereotype.Service;

@Service
public class TransferenciaServiceImpl implements TransferenciaService {

    private TransferenciaDao transferenciaDao;
    private CuentaDao cuentaDao;

    public TransferenciaServiceImpl(TransferenciaDao transferenciaDao, CuentaDao cuentaDao) {
        this.transferenciaDao = transferenciaDao;
        this.cuentaDao = cuentaDao;
    }

    @Override
    public void realizarTransferencia(long cuentaOrigenNumero, long cuentaDestinoNumero, double monto) throws Exception, NoAlcanzaException, CantidadNegativaException {
        Cuenta cuentaOrigen = cuentaDao.findCuenta(cuentaOrigenNumero);
        Cuenta cuentaDestino = cuentaDao.findCuenta(cuentaDestinoNumero);

        if (cuentaOrigen == null) {
            throw new Exception("Cuenta origen no encontrada");
        }
        if (cuentaDestino == null) {
            throw new Exception("Cuenta destino no encontrada");
        }

        if (cuentaOrigen.getBalance() < monto) {
            throw new Exception("Saldo insuficiente en la cuenta origen");
        }

        cuentaOrigen.debitar(monto);
        cuentaDestino.acreditar(monto);

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