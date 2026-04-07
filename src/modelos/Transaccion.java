package modelos;

import java.text.DecimalFormat;

public class Transaccion {
    private Cuenta cuenta;
    private TipoTransaccion tipo;
    private double valor;
    private double saldo;

    public Transaccion(Cuenta cuenta, TipoTransaccion tipo, double valor, double saldo) {
        this.cuenta = cuenta;
        this.tipo = tipo;
        this.valor = valor;
        this.saldo = saldo;
    }

    public Cuenta getCuenta() {
        return cuenta;
    }

    public TipoTransaccion getTipo() {
        return tipo;
    }

    public double getValor() {
        return valor;
    }

    public double getSaldo() {
        return saldo;
    }

    public String[] getDatos() {
        DecimalFormat df = new DecimalFormat("#,##0.00");
        return new String[] {
                cuenta.toString(),
                tipo.toString(),
                df.format(valor),
                df.format(saldo)
        };
    }
}
