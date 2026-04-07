package modelos;

import java.text.DecimalFormat;

public class Ahorro extends Cuenta {

    private double tasaInteres;

    public Ahorro(String titular, String numero, double tasaInteres) {
        super(titular, numero);
        this.tasaInteres = tasaInteres;
    }

    @Override
    public boolean retirar(double valor) {
        if (valor > 0 && valor <= getSaldo()) {
            setSaldo(getSaldo() - valor);
            return true;
        }
        return false;
    }

    public double getTasaInteres() {
        return tasaInteres;
    }

    public void abonarIntereses() {
        setSaldo(getSaldo() * (1 + tasaInteres / 100));
    }

    @Override
    public String[] getDatos() {
        DecimalFormat df = new DecimalFormat("#,##0.00");
        return new String[] {
                "Ahorros",
                getNumero(),
                getTitular(),
                df.format(getSaldo()),
                "Tasa de Interés " + df.format(tasaInteres) + "%"
        };
    }

    @Override
    public String toString() {
        return "Ahorro [Numero=" + getNumero() + ", Titular=" + getTitular() + "]";
    }

    @Override
    public boolean procesarTransaccion(TipoTransaccion tipo, double valor) {
        switch (tipo) {
            case DEPOSITO:
                return depositar(valor);
            case RETIRO:
                return retirar(valor);
        }
        return false;
    }


}
