package servicios;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import modelos.Ahorro;
import modelos.Corriente;
import modelos.Credito;
import modelos.Cuenta;
import modelos.TipoCuenta;

public class CuentaServicio {

    private static List<Cuenta> cuentas = new ArrayList<>();

    public static String[] encabezados = new String[] { "Tipo", "Número", "Titular", "Saldo", "Descripción" };

    public static Cuenta getCuenta(int posicion) {
        if (posicion >= 0 && posicion < cuentas.size()) {
            return cuentas.get(posicion);
        }
        return null;
    }

    public static void mostrar(JTable tbl) {
        String[][] datos = new String[cuentas.size()][encabezados.length];

        int fila = 0;
        for (Cuenta cuenta : cuentas) {
            int columna = 0;
            for (String dato : cuenta.getDatos()) {
                datos[fila][columna] = dato;
                columna++;
            }
            fila++;
        }

        DefaultTableModel dtm = new DefaultTableModel(datos, encabezados);
        tbl.setModel(dtm);
    }

    public static Cuenta agregar(TipoCuenta tipo,
            String titular,
            String numero,
            double sobregiro,
            double tasaInteres,
            double valorPrestado,
            int plazo) {
        Cuenta cuenta = null;
        switch (tipo) {
            case AHORROS:
                cuenta = new Ahorro(titular, numero, tasaInteres);
                break;
            case CORRIENTE:
                cuenta = new Corriente(titular, numero, sobregiro);
                break;
            case CREDITO:
                cuenta = new Credito(titular, numero, valorPrestado, tasaInteres, plazo);
                break;
        }
        cuentas.add(cuenta);
        return cuenta;
    }

    public static boolean eliminar(int posicion) {
        if (posicion >= 0 && posicion < cuentas.size()) {
            cuentas.remove(posicion);
            return true;
        }
        return false;
    }

}
