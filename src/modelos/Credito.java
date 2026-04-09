package modelos;

import java.text.DecimalFormat;

import interfaces.IMostrable;
import interfaces.IPagable;
import interfaces.IRetirable;
import interfaces.ITransaccionable;

public class Credito extends Cuenta implements IPagable, IRetirable, IMostrable, ITransaccionable {

    private double valorPrestado;
    private double tasaInteres;
    private int plazo;
    private double valorRetirado;

    public Credito(String titular, String numero, double valorPrestado, double tasaInteres, int plazo) {
        super(titular, numero);
        this.valorPrestado = valorPrestado;
        this.tasaInteres = tasaInteres;
        this.plazo = plazo;
        this.valorRetirado = 0;
    }

    public double getValorPrestado() {
        return valorPrestado;
    }

    public double getTasaInteres() {
        return tasaInteres;
    }

    public int getPlazo() {
        return plazo;
    }

    public double getValorRetirado() {
        return valorRetirado;
    }

    @Override
    public double getDisponibleRetiro() {
        return valorPrestado - valorRetirado;
    }

    @Override
    public boolean retirar(double valor) {
        if (valor > 0 && valor <= getDisponibleRetiro()) {
            valorRetirado += valor;
            return true;
        }
        return false;
    }

    @Override
    public double getSaldoDeuda() {
        return valorPrestado - getSaldo();
    }

    @Override
    public double getCuota() {
        double tasaReal = tasaInteres / 100;
        return valorPrestado * Math.pow(1 + tasaReal, plazo) * tasaReal / (Math.pow(1 + tasaReal, plazo) - 1);
    }

    @Override
    public boolean pagar(double valor) {
        if (valor > 0 && getSaldoDeuda() > 0) {
            var intereses = getSaldoDeuda() * tasaInteres / 100;
            var abonoCapital = valor - intereses;
            return depositar(abonoCapital);
        }
        return false;
    }

    @Override
    public String[] getDatos() {
        DecimalFormat df = new DecimalFormat("#,##0.00");
        return new String[] {
                "Crédito",
                getNumero(),
                getTitular(),
                "Saldo Adeudado $" + df.format(getSaldoDeuda()) + " Saldo Pagado $ " + df.format(getSaldo())
                        + " Disponible Retiro $" + df.format(getDisponibleRetiro()),
                "Valor Préstamo $" + df.format(valorPrestado) + " Tasa Interés " + df.format(tasaInteres) + "% Plazo"
                        + df.format(plazo) + " Cuota $" + df.format(getCuota())
        };
    }

    @Override
    public String toString() {
        return "Crédito [Numero=" + getNumero() + ", Titular=" + getTitular() + "]";
    }

    @Override
    public boolean procesarTransaccion(TipoTransaccion tipo, double valor) {
        switch (tipo) {
            case DEPOSITO:
                return pagar(valor);
            case RETIRO:
                return retirar(valor);
        }
        return false;
    }

    @Override
    public double getSaldoPorTransaccion(TipoTransaccion tipo) {
        return tipo == TipoTransaccion.RETIRO ? getDisponibleRetiro() : getSaldoDeuda();
    }

}
