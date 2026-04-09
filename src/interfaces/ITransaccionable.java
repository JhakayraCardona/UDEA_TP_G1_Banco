package interfaces;

import modelos.TipoTransaccion;

public interface ITransaccionable {

    boolean procesarTransaccion(TipoTransaccion tipo, double valor);
}
