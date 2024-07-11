package ar.edu.utn.frbb.tup.controller.validator;

import ar.edu.utn.frbb.tup.controller.dto.ClienteDto;
import ar.edu.utn.frbb.tup.model.TipoPersona;
import org.springframework.stereotype.Component;

@Component
public class ClienteValidator {

    public void validate(ClienteDto clienteDto) {
        if(clienteDto.getFechaNacimiento() == null) {
            throw new IllegalArgumentException("Fecha de nacimiento es requerida");
        }
        try {
            TipoPersona.fromString(clienteDto.getTipoPersona());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Tipo de persona no valido");
        }
    }
}