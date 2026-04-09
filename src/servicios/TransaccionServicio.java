package servicios;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import interfaces.ITransaccionable;
import modelos.Cuenta;
import modelos.TipoTransaccion;
import modelos.Transaccion;

public class TransaccionServicio {
    private static List<Transaccion> transacciones = new ArrayList<>();

    public static String[] encabezados = new String[] { "Cuenta", "Tipo", "ValorTransaccion", "Saldo" };

    public static void mostrar(JTable tbl) {
        String[][] datos = new String[transacciones.size()][encabezados.length];

        int fila = 0;
        for (Transaccion transaccion : transacciones) {
            int columna = 0;
            for (String dato : transaccion.getDatos()) {
                datos[fila][columna] = dato;
                columna++;
            }
            fila++;
        }

        DefaultTableModel dtm = new DefaultTableModel(datos, encabezados);
        tbl.setModel(dtm);
    }

    public static Transaccion agregar(Cuenta cuenta, TipoTransaccion tipo, double valor) {
        Transaccion transaccion = null;
        if (((ITransaccionable)cuenta).procesarTransaccion(tipo, valor)) {
            transaccion = new Transaccion(cuenta, tipo, valor, cuenta.getSaldoPorTransaccion(tipo));
            transacciones.add(transaccion);
        }
        return transaccion;
    }
}
