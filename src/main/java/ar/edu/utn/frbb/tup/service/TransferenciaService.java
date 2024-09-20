package ar.edu.utn.frbb.tup.service;

import ar.edu.utn.frbb.tup.controller.dto.TransferenciaDto;
import ar.edu.utn.frbb.tup.model.Transferencia;
import ar.edu.utn.frbb.tup.model.exception.*;

import java.util.List;

public interface TransferenciaService {

    Transferencia realizarTransferencia(TransferenciaDto transferenciaDto) throws Exception, NoAlcanzaException, CantidadNegativaException, CuentaNoEncontradaException, TipoMonedaException;

    List<Transferencia> find(long id);

    List<Transferencia> findAll();
}