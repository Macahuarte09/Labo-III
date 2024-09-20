package ar.edu.utn.frbb.tup.controller.validator;

import ar.edu.utn.frbb.tup.controller.dto.TransferenciaDto;
import ar.edu.utn.frbb.tup.model.exception.DatoIngresadoInvalidoException;
import org.springframework.stereotype.Component;

@Component
public class TransferenciaValidator {

    public void validate(TransferenciaDto transferenciaDto) throws DatoIngresadoInvalidoException {
        validateMonto(transferenciaDto);
        validateMoneda(transferenciaDto);
    }

    public void validateMonto(TransferenciaDto transferenciaDto) throws DatoIngresadoInvalidoException {
        if (transferenciaDto.getMonto()<=0){
            throw new DatoIngresadoInvalidoException("El monto ingresado debe ser positivo");
        }
    }

    private void validateMoneda(TransferenciaDto transferenciaDto) throws DatoIngresadoInvalidoException {
        if (transferenciaDto.getMoneda() == null || transferenciaDto.getMoneda().isEmpty()) {
            throw new DatoIngresadoInvalidoException("El tipo de moneda es obligatorio");
        }
    }
}