package ar.edu.utn.frbb.tup.persistence.impl;

import ar.edu.utn.frbb.tup.model.Transferencia;
import ar.edu.utn.frbb.tup.persistence.TransferenciaDao;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class TransferenciaDaoImpl implements TransferenciaDao {

    private List<Transferencia> transferencias = new ArrayList<>();

    @Override
    public void guardarTransferencia(Transferencia transferencia) {
        transferencias.add(transferencia);
    }

    @Override
    public List<Transferencia> findTransfersByID(long numeroCuenta) {
        List<Transferencia> resultado = new ArrayList<>();
        for (Transferencia transferencia : transferencias) {
            if (transferencia.getCuentaOrigen() == numeroCuenta ||
                    transferencia.getCuentaDestino() == numeroCuenta) {
                resultado.add(transferencia);
            }
        }
        return resultado;
    }

    @Override
    public List<Transferencia> findAllTransfers(){return transferencias;}
}