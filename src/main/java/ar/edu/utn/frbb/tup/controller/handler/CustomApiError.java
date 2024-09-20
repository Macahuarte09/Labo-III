package ar.edu.utn.frbb.tup.controller.handler;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.HashMap;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomApiError {

    private Map<String, String> tipoException = new HashMap<>();

    public CustomApiError() {}

    public Map<String, String> getInfoExceptions() {
        return tipoException;
    }

    public void agregarInfoException(String nombreException, String message) {
        this.tipoException.put(nombreException, message);
    }
}