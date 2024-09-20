package ar.edu.utn.frbb.tup.controller.validator;

import ar.edu.utn.frbb.tup.controller.dto.ClienteDto;
import ar.edu.utn.frbb.tup.model.TipoPersona;
import ar.edu.utn.frbb.tup.model.exception.DatoIngresadoInvalidoException;
import org.springframework.stereotype.Component;

@Component
public class ClienteValidator {

    public void validate(ClienteDto clienteDto) throws DatoIngresadoInvalidoException {
        validateNombre(clienteDto);
        validateApellido(clienteDto);
        validateDNI(clienteDto);
        validateFechaNacimiento(clienteDto);
        validateTipoPersona(clienteDto);
    }

    private void validateNombre(ClienteDto clienteDto) throws DatoIngresadoInvalidoException {
        if (clienteDto.getNombre() == null){
            throw new DatoIngresadoInvalidoException("El nombre es obligatorio");
        }
    }

    private void validateApellido(ClienteDto clienteDto) throws DatoIngresadoInvalidoException {
        if (clienteDto.getApellido() == null){
            throw new DatoIngresadoInvalidoException("El apellido es obligatorio");
        }
    }

    private void validateDNI(ClienteDto clienteDto) throws DatoIngresadoInvalidoException {
        if (clienteDto.getDni() <= 0){
            throw new DatoIngresadoInvalidoException("El DNI es obligatorio y debe ser un numero positivo");
        }
    }

    private void validateFechaNacimiento(ClienteDto clienteDto) throws DatoIngresadoInvalidoException{
        if(clienteDto.getFechaNacimiento() == null) {
            throw new DatoIngresadoInvalidoException("La fecha de nacimiento es obligatoria");
        }
    }

    private void validateTipoPersona(ClienteDto clienteDto) throws DatoIngresadoInvalidoException{
        try {
            TipoPersona.fromString(clienteDto.getTipoPersona());
        } catch (IllegalArgumentException e) {
            throw new DatoIngresadoInvalidoException("Tipo de persona no valido");
        }
    }
}