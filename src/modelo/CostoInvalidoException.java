
package modelo;

public class CostoInvalidoException extends Exception {
    public CostoInvalidoException() {
        super("El costo de producción no puede ser mayor al de venta.");
    }
}