package ar.edu.utn.frbb.tup.persistence;

import ar.edu.utn.frbb.tup.model.Transferencia;

import java.util.List;

public interface TransferenciaDao {
    void guardarTransferencia(Transferencia transferencia);
    List<Transferencia> buscarTransferenciasPorCuenta(String numeroCuenta);
}
