package modelos;

import java.text.DecimalFormat;

import interfaces.IMostrable;
import interfaces.IPagable;
import interfaces.IRetirable;
import interfaces.ITransaccionable;

public class CreditoRotativo extends Cuenta implements IPagable, IRetirable, IMostrable, ITransaccionable {

    private double valorCupo;
    private double tasaInteres;
    private int plazo;
    private int cuotaActual = 0;

    public CreditoRotativo(String titular, String numero, double valorCupo, double tasaInteres, int plazo) {
        super(titular, numero);
        this.valorCupo = valorCupo;
        this.tasaInteres = tasaInteres;
        this.plazo = plazo;
        setSaldo(valorCupo);
    }

    public double getValorCupo() {
        return valorCupo;
    }

    public double getTasaInteres() {
        return tasaInteres;
    }

    public int getPlazo() {
        return plazo;
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
    public String[] getDatos() {
        DecimalFormat df = new DecimalFormat("#,##0.00");
        return new String[] {
                "Crédito",
                getNumero(),
                getTitular(),
                "Saldo Adeudado $" + df.format(getSaldoDeuda()) +  " Disponible Retiro $" + df.format(getDisponibleRetiro()),
                "Valor Cupo $" + df.format(valorCupo) + " Tasa Interés " + df.format(tasaInteres) + "% Plazo"
                        + df.format(plazo) + " Cuota $" + df.format(getCuota())
        };
    }

    @Override
    public double getDisponibleRetiro() {
        return getSaldo();
    }

    @Override
    public boolean retirar(double valor) {
        if (valor > 0 && valor <= getDisponibleRetiro()) {
            setSaldo(getSaldo() - valor);
            return true;
        }
        return false;
    }

    @Override
    public double getCuota() {
        double tasaReal = tasaInteres / 100;
        double plazoReal = plazo - cuotaActual;
        return getSaldoDeuda() * Math.pow(1 + tasaReal, plazoReal) * tasaReal / (Math.pow(1 + tasaReal, plazoReal) - 1);
    }

    @Override
    public double getSaldoDeuda() {
        return valorCupo - getSaldo();
    }

    @Override
    public boolean pagar(double valor) {
        if (valor > 0 && getSaldoDeuda() > 0) {
            var intereses = getSaldoDeuda() * tasaInteres / 100;
            var abonoCapital = valor - intereses;
            if (depositar(abonoCapital)) {
                cuotaActual++;
                return true;
            }
            ;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Crédito Rotativo [Numero=" + getNumero() + ", Titular=" + getTitular() + "]";
    }

}
