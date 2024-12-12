package ar.edu.utn.frbb.tup.model;

import ar.edu.utn.frbb.tup.controller.dto.CuentaDto;
import ar.edu.utn.frbb.tup.model.exception.NoAlcanzaException;
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
        Random random = new Random();
        int lowerBound = 1000000;
        int upperBound = 9000000;
        this.numeroCuenta = lowerBound + random.nextInt(upperBound);
        this.fechaCreacion = LocalDateTime.now();
        this.balance = 0;
    }

    public Cuenta(CuentaDto cuentaDto){
        this.tipoCuenta = TipoCuenta.fromString(cuentaDto.getTipoCuenta());
        this.moneda = TipoMoneda.fromString(cuentaDto.getMoneda());
        this.fechaCreacion = LocalDateTime.now();
        this.balance = cuentaDto.getBalance();
        Random random = new Random();
        int lowerBound = 1000000;
        int upperBound = 9000000;
        this.numeroCuenta = lowerBound + random.nextInt(upperBound); //genera numero de 7 digitos
    }
    

    public Cuenta(int i, double v) {
    }

    public Cuenta(long l, String pesos, int i) {
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

    public void debitar(double monto) throws NoAlcanzaException {
        if (monto > balance) {
            throw new NoAlcanzaException("Saldo insuficiente");
        }
        this.balance -= monto;
    }

    public void acreditar(double monto) {
        this.balance += monto;
    }
}
