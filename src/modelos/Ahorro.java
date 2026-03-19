package modelos;

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

}
