package ar.edu.utn.frbb.tup.persistence.impl;

import ar.edu.utn.frbb.tup.model.Transferencia;
import ar.edu.utn.frbb.tup.persistence.TransferenciaDao;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TransferenciaDaoImpl implements TransferenciaDao {

    private List<Transferencia> transferencias = new ArrayList<>();

    @Override
    public void guardarTransferencia(Transferencia transferencia) {
        transferencias.add(transferencia);
    }

    @Override
    public List<Transferencia> buscarTransferenciasPorCuenta(long numeroCuenta) {
        List<Transferencia> resultado = new ArrayList<>();
        for (Transferencia transferencia : transferencias) {
            if (transferencia.getCuentaOrigen().getNumeroCuenta() == numeroCuenta ||
                    transferencia.getCuentaDestino().getNumeroCuenta() == numeroCuenta) {
                resultado.add(transferencia);
            }
        }
        return resultado;
    }
}