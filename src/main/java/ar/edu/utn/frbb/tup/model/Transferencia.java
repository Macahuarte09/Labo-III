package ar.edu.utn.frbb.tup.model;

import ar.edu.utn.frbb.tup.controller.dto.TransferenciaDto;

import java.time.LocalDateTime;

public class Transferencia {

    private long cuentaOrigen;
    private long cuentaDestino;
    private double monto;
    private TipoMoneda moneda;
    private LocalDateTime fecha;

    public Transferencia() {
        this.fecha = LocalDateTime.now();
    }

    public Transferencia(TransferenciaDto transferenciaDto) {
        this.cuentaOrigen = transferenciaDto.getCuentaOrigen();
        this.cuentaDestino = transferenciaDto.getCuentaDestino();
        this.monto = transferenciaDto.getMonto();
        this.moneda = TipoMoneda.fromString(transferenciaDto.getMoneda());
        this.fecha = LocalDateTime.now();
    }

    public long getCuentaOrigen() {
        return cuentaOrigen;
    }

    public void setCuentaOrigen(long cuentaOrigen) {
        this.cuentaOrigen = cuentaOrigen;
    }

    public long getCuentaDestino() {
        return cuentaDestino;
    }

    public void setCuentaDestino(long cuentaDestino) {
        this.cuentaDestino = cuentaDestino;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {this.monto = monto;}

    public TipoMoneda getMoneda() {return moneda;}

    public void setMoneda(TipoMoneda moneda) {this.moneda = moneda;}

    public LocalDateTime getFecha() {return fecha;}

    public void setFecha(LocalDateTime fecha) {this.fecha = fecha;}
}