package ar.edu.utn.frbb.tup.persistence;

import ar.edu.utn.frbb.tup.model.Transferencia;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface TransferenciaDao {

    void guardarTransferencia(Transferencia transferencia);

    List<Transferencia> findTransfersByID(long numeroCuenta);

    List<Transferencia> findAllTransfers();
}