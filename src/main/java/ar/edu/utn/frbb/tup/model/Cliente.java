package ar.edu.utn.frbb.tup.model;

import ar.edu.utn.frbb.tup.controller.dto.ClienteDto;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class Cliente extends Persona{

    private TipoPersona tipoPersona;
    private String banco;
    private LocalDateTime fechaAlta;
    @JsonManagedReference
    private Set<Cuenta> cuentas = new HashSet<>();

    public Cliente() {
        this.fechaAlta = LocalDateTime.now();
    }

    public Cliente(String nombre, String apellido, long dni, TipoPersona tipoPersona) {
        super(nombre, apellido, dni);
        this.tipoPersona = tipoPersona;
        this.banco = "Banco De Ando";
        this.fechaAlta = LocalDateTime.now();
    }

    public Cliente(ClienteDto clienteDto){
        super(clienteDto.getNombre(), clienteDto.getApellido(), clienteDto.getDni());
        this.setFechaNacimiento(clienteDto.getFechaNacimiento());
        this.banco = "Banco De Ando";
        this.tipoPersona = TipoPersona.fromString(clienteDto.getTipoPersona());
        this.fechaAlta = LocalDateTime.now();
    }

    public Cliente(long l, String juanPerez) {
    }

    public TipoPersona getTipoPersona() {
        return tipoPersona;
    }

    public void setTipoPersona(TipoPersona tipoPersona) {
        this.tipoPersona = tipoPersona;
    }

    public String getBanco() {
        return banco;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }

    public LocalDateTime getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(LocalDateTime fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public Set<Cuenta> getCuentas() {
        return cuentas;
    }

    public void addCuenta(Cuenta cuenta) {
        this.cuentas.add(cuenta);
        cuenta.setTitular(this);
    }

    public boolean tieneCuenta(TipoCuenta tipoCuenta, TipoMoneda moneda) {
        for (Cuenta cuenta:
                cuentas) {
            if (tipoCuenta.equals(cuenta.getTipoCuenta()) && moneda.equals(cuenta.getMoneda())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "tipoPersona=" + tipoPersona +
                ", banco='" + banco + '\'' +
                ", fechaAlta=" + fechaAlta +
                ", cuentas=" + cuentas +
                '}';
    }
}

