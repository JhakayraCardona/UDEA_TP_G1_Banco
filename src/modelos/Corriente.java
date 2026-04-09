package modelos;

import java.text.DecimalFormat;

import interfaces.IConsignable;
import interfaces.IMostrable;
import interfaces.IRetirable;
import interfaces.ITransaccionable;

public class Corriente extends Cuenta implements IConsignable, IRetirable, IMostrable, ITransaccionable {

    private double sobregiro;

    public Corriente(String titular, String numero, double sobregiro) {
        super(titular, numero);
        this.sobregiro = sobregiro;
    }

    public double getSobregiro() {
        return sobregiro;
    }

    @Override
    public boolean consignar(double cantidad) {
        return depositar(cantidad);
    }

    @Override
    public double getDisponibleRetiro() {
        return getSaldo() + sobregiro;
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
    public String[] getDatos() {
        DecimalFormat df = new DecimalFormat("#,##0.00");
        return new String[] {
                "Corriente",
                getNumero(),
                getTitular(),
                df.format(getSaldo()),
                "Sobregiro " + df.format(sobregiro)
        };
    }

    @Override
    public String toString() {
        return "Corriente [Numero=" + getNumero() + ", Titular=" + getTitular() + "]";
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
