package controlador;

import interfaz.AñadirProducto;
import interfaz.VentanaInicio;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import modelo.CantidadInvalidaException;
import modelo.CostoInvalidoException;
import modelo.Producto;
import persistencia.ArchivoProducto;


public class ControladorAñadirProducto {
    
    private AñadirProducto vista;
    private VentanaInicio ventanaPrincipal;

    public ControladorAñadirProducto(AñadirProducto vista, VentanaInicio ventanaPrincipal) {
        this.vista = vista;
        this.ventanaPrincipal = ventanaPrincipal;
    }

    public void agregarProducto() {
        try {
            String nombre = vista.getNombretxt().getText();
            String tipo = (String) vista.getTipoBox().getSelectedItem();
            double precio = Double.parseDouble(vista.getPreciotxt().getText());
            double precioProduccion = Double.parseDouble(vista.getPrecioproducciontxt().getText());
            int cantidad = Integer.parseInt(vista.getCantidadtxt().getText());
            boolean especial = vista.getCheck().isSelected();
            boolean estado = true;

            Producto producto = new Producto(nombre, tipo, precioProduccion, precio, cantidad, especial, estado);
            ArchivoProducto archivo = new ArchivoProducto();
            archivo.guardarProductos(producto);

            JOptionPane.showMessageDialog(null, "Producto guardado con éxito.");
            limpiarCampos();
            ventanaPrincipal.actualizarTabla();

        } catch (CostoInvalidoException e) {
            Logger.getLogger(ControladorAñadirProducto.class.getName()).log(Level.SEVERE, null, e);
            JOptionPane.showMessageDialog(vista, "Costo inválido", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (CantidadInvalidaException e) {
            Logger.getLogger(ControladorAñadirProducto.class.getName()).log(Level.SEVERE, null, e);
            JOptionPane.showMessageDialog(vista, "Cantidad inválida", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IOException | NumberFormatException e) {
            Logger.getLogger(ControladorAñadirProducto.class.getName()).log(Level.SEVERE, null, e);
            JOptionPane.showMessageDialog(vista, "Error al guardar el producto", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarCampos() {
        vista.getNombretxt().setText("");
        vista.getPreciotxt().setText("");
        vista.getPrecioproducciontxt().setText("");
        vista.getCantidadtxt().setText("");
        vista.getCheck().setSelected(false);
    }

}
