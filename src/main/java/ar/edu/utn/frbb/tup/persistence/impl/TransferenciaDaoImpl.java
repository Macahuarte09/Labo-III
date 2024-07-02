package ar.edu.utn.frbb.tup.persistence.impl;

import ar.edu.utn.frbb.tup.model.Cuenta;
import ar.edu.utn.frbb.tup.model.Transferencia;
import ar.edu.utn.frbb.tup.persistence.TransferenciaDao;

import java.util.List;

public class TransferenciaDaoImpl implements TransferenciaDao {
    public void guardarTransferencia(Transferencia transferencia) {}

    @Override
    public List<Transferencia> buscarTransferenciasPorCuenta(String numeroCuenta) {
        return Cuenta;
    }
}
