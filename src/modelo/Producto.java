
package modelo;

import java.io.Serializable;

public class Producto implements Serializable{
    protected String nombre;
    protected String tipo;
    protected double costoProduccion;
    protected double costoVenta;
    protected int cantidad;
    protected boolean especial;
    protected boolean estado;

   public Producto(String nombre, String tipo, double costoProduccion, double costoVenta, int cantidad, boolean especial, boolean estado)
            throws CostoInvalidoException, CantidadInvalidaException {
        if (costoProduccion > costoVenta) throw new CostoInvalidoException();
        if (cantidad < 0) throw new CantidadInvalidaException();

        this.nombre = nombre;
        this.tipo=tipo;
        this.costoProduccion = costoProduccion;
        this.costoVenta = costoVenta;
        this.cantidad = cantidad;
        this.especial=especial;
        this.estado=estado;
    }
   

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getCostoProduccion() {
        return costoProduccion;
    }

    public void setCostoProduccion(double costoProduccion) {
        this.costoProduccion = costoProduccion;
    }

    public double getCostoVenta() {
        return costoVenta;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public boolean isEspecial() {
        return especial;
    }

    public void setEspecial(boolean especial) {
        this.especial = especial;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public void setCostoVenta(double costoVenta) {
        this.costoVenta = costoVenta;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
   
}
