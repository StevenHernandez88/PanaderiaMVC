
package controlador;

import interfaz.ActualizarProducto;
import interfaz.VentanaInicio;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;
import javax.swing.JOptionPane;
import modelo.CantidadInvalidaException;
import modelo.CostoInvalidoException;
import modelo.Producto;
import persistencia.ArchivoProducto;

public class ControladorActualizarProducto {

    private ActualizarProducto vista;
    private VentanaInicio ventanaPrincipal;
    private Producto productoOriginal;

    public ControladorActualizarProducto(ActualizarProducto vista, VentanaInicio ventanaPrincipal, Producto producto) {
        this.vista = vista;
        this.ventanaPrincipal = ventanaPrincipal;
        this.productoOriginal = producto;
    }

    public void llenarCampos() {
        vista.getNombretxt().setText(productoOriginal.getNombre());
        vista.getTipoBox().setSelectedItem(productoOriginal.getTipo());
        vista.getPreciotxt().setText(String.valueOf(productoOriginal.getCostoVenta()));
        vista.getPrecioproducciontxt().setText(String.valueOf(productoOriginal.getCostoProduccion()));
        vista.getCantidadtxt().setText(String.valueOf(productoOriginal.getCantidad()));
        vista.getCheckespecial().setSelected(productoOriginal.isEspecial());
        vista.getCheckestado().setSelected(productoOriginal.isEstado());
    }

    public void actualizarProducto() {
        
        try {
            String nombre = vista.getNombretxt().getText();
            String tipo = (String) vista.getTipoBox().getSelectedItem();
            double precio = Double.parseDouble(vista.getPreciotxt().getText());
            double precioProduccion = Double.parseDouble(vista.getPrecioproducciontxt().getText());
            int cantidad = Integer.parseInt(vista.getCantidadtxt().getText());
            boolean especial = vista.getCheckespecial().isSelected();
            boolean estado = vista.getCheckestado().isSelected();

            Producto productoActualizado = new Producto(nombre, tipo, precioProduccion, precio, cantidad, especial, estado);

            // Reemplazar el producto en la lista
            ArchivoProducto archivo = new ArchivoProducto();
            List<Producto> productos = archivo.cargarProductos();
            productos.removeIf(p -> p.getNombre().equals(productoOriginal.getNombre()));
            productos.add(productoActualizado);

            // Guardar nuevamente toda la lista
            ObjectOutputStream oos = new ObjectOutputStream(new java.io.FileOutputStream("C:\\Users\\PC\\Downloads\\Proyectos\\Productos\\productos.dat"));
            oos.writeObject(productos);
            oos.close();

            JOptionPane.showMessageDialog(null, "Producto actualizado con éxito.");
            ventanaPrincipal.actualizarTabla();
            vista.dispose();

        } catch (CostoInvalidoException | CantidadInvalidaException | IOException | NumberFormatException e) {
            JOptionPane.showMessageDialog(vista, "Error al actualizar el producto", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
