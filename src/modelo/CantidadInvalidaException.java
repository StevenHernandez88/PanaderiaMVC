
package modelo;

public class CantidadInvalidaException extends Exception {
    public CantidadInvalidaException() {
        super("La cantidad no puede ser negativa.");
    }
}