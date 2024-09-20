package ar.edu.utn.frbb.tup.model;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Banco {
    private List<Cliente> clientes = new ArrayList<>();

    public List<Cliente> getClientes() {
        return clientes;
    }

    public void setClientes(List<Cliente> clientes) {
        this.clientes = clientes;
    }

    private double comisionPorGastosDeTransferenciaEnPesos = 0;

    private double comisionPorGastosDeTransferenciaEnDolares = 0;

    public double getComisionPorGastosDeTransferenciaEnPesos() {return comisionPorGastosDeTransferenciaEnPesos;}

    public void setComisionPorGastosDeTransferenciaEnPesos(double comisionPorGastosDeTransferenciaEnPesos) {this.comisionPorGastosDeTransferenciaEnPesos = comisionPorGastosDeTransferenciaEnPesos;}

    public double getComisionPorGastosDeTransferenciaEnDolares() {return comisionPorGastosDeTransferenciaEnDolares;}

    public void setComisionPorGastosDeTransferenciaEnDolares(double comisionPorGastosDeTransferenciaEnDolares) {this.comisionPorGastosDeTransferenciaEnDolares = comisionPorGastosDeTransferenciaEnDolares;}

    public void guardarComisionEnPesos(double nuevaComision){
        comisionPorGastosDeTransferenciaEnPesos+=nuevaComision;
    }

    public void guardarComisionEnDolares(double nuevaComision){
        comisionPorGastosDeTransferenciaEnDolares+=nuevaComision;
    }
}

