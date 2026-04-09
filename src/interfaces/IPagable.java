package interfaces;

public interface IPagable {

    double getCuota();

    double getSaldoDeuda();

    boolean pagar(double cantidad);

}
