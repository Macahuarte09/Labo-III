package ar.edu.utn.frbb.tup.model;

import ar.edu.utn.frbb.tup.controller.dto.CuentaDto;
import com.fasterxml.jackson.annotation.JsonBackReference;

import java.time.LocalDateTime;
import java.util.Random;

public class Cuenta {

    private long numeroCuenta;
    LocalDateTime fechaCreacion;
    double balance;
    TipoCuenta tipoCuenta;

    @JsonBackReference
    Cliente titular;

    TipoMoneda moneda;

    public Cuenta() {
        this.numeroCuenta = Math.abs(new Random().nextInt()) + 1;
        this.fechaCreacion = LocalDateTime.now();
        this.balance = 0;
    }

    public Cuenta(CuentaDto cuentaDto){
        this.tipoCuenta = TipoCuenta.fromString(cuentaDto.getTipoCuenta());
        this.moneda = TipoMoneda.fromString(cuentaDto.getMoneda());
        this.fechaCreacion = LocalDateTime.now();
        this.balance = 0;
        this.numeroCuenta = new Random().nextLong();
    }

    public Cliente getTitular() {
        return titular;
    }

    public void setTitular(Cliente titular) {
        this.titular = titular;
    }

    public TipoCuenta getTipoCuenta() {
        return tipoCuenta;
    }

    public Cuenta setTipoCuenta(TipoCuenta tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
        return this;
    }

    public TipoMoneda getMoneda() {
        return moneda;
    }

    public Cuenta setMoneda(TipoMoneda moneda) {
        this.moneda = moneda;
        return this;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public Cuenta setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
        return this;
    }

    public double getBalance() {
        return balance;
    }

    public Cuenta setBalance(double balance) {
        this.balance = balance;
        return this;
    }

    public void setNumeroCuenta(long numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public long getNumeroCuenta() {
        return numeroCuenta;
    }

    public void debitar(double monto) throws Exception {
        if (monto > balance) {
            throw new Exception("Saldo insuficiente");
        }
        this.balance -= monto;
    }

    public void acreditar(double monto) {
        this.balance += monto;
    }
}
