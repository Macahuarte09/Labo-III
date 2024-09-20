package ar.edu.utn.frbb.tup.controller.dto;
import java.util.Random;
public class CuentaDto {

    private String tipoCuenta;
    private String moneda;
    private long dniTitular;
    private double balance;

    public long getDniTitular() {return dniTitular;}

    public String getMoneda() {
        return moneda;
    }

    public String getTipoCuenta() {
        return tipoCuenta;
    }

    public double getBalance() {return balance;}

    public void setDniTitular(long dniTitular) {this.dniTitular = dniTitular;}

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public void setTipoCuenta(String tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }

    public void setBalance(double balance) {this.balance = balance;}
}