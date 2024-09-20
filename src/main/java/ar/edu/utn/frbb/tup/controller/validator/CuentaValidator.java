package ar.edu.utn.frbb.tup.controller.validator;

import ar.edu.utn.frbb.tup.controller.dto.CuentaDto;
import ar.edu.utn.frbb.tup.model.exception.CantidadNegativaException;
import ar.edu.utn.frbb.tup.model.exception.DatoIngresadoInvalidoException;
import org.springframework.stereotype.Component;

@Component
public class CuentaValidator {

    public void validate(CuentaDto cuentaDto) throws CantidadNegativaException, DatoIngresadoInvalidoException {
        validateTipoCuenta(cuentaDto);
        validateTipoMoneda(cuentaDto);
        validateBalance(cuentaDto);
        validateDniTitular(cuentaDto);
    }

    private void validateTipoCuenta(CuentaDto cuentaDto) throws DatoIngresadoInvalidoException {
        if (cuentaDto.getTipoCuenta() == null || cuentaDto.getTipoCuenta().isEmpty()) {
            throw new DatoIngresadoInvalidoException("El tipo de cuenta es obligatorio");
        }
        if (!"C".equals(cuentaDto.getTipoCuenta()) && !"A".equals(cuentaDto.getTipoCuenta())) {
            throw new DatoIngresadoInvalidoException("El tipo de cuenta no es correcto");
        }
    }

    private void validateTipoMoneda(CuentaDto cuentaDto) throws DatoIngresadoInvalidoException {
        if (cuentaDto.getMoneda() == null || cuentaDto.getMoneda().isEmpty()) {
            throw new DatoIngresadoInvalidoException("El tipo de moneda es obligatorio");
        }
    }

    private void validateBalance(CuentaDto cuentaDto) throws CantidadNegativaException {
        if (cuentaDto.getBalance() < 0) {
            throw new CantidadNegativaException("El balance no puede ser negativo");
        }
    }

    public void validateDniTitular(CuentaDto cuentaDto) throws DatoIngresadoInvalidoException {
        if (cuentaDto.getDniTitular()<=0){
            throw new DatoIngresadoInvalidoException("El numero DNI no puede ser negativo");
        }
    }
}