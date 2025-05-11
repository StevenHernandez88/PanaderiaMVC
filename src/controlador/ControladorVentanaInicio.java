
package controlador;

import interfaz.ActualizarProducto;
import interfaz.AñadirProducto;
import interfaz.VentanaInicio;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import modelo.CantidadInvalidaException;
import modelo.CostoInvalidoException;
import modelo.Producto;
import persistencia.ArchivoProducto;


public class ControladorVentanaInicio {
     private VentanaInicio vista;
    private ArchivoProducto archivoProducto;
    

    public ControladorVentanaInicio(VentanaInicio vista) {
        this.vista = vista;
        this.archivoProducto = new ArchivoProducto();
    }

    public void cargarProductosTabla() {
        List<Producto> productos = archivoProducto.cargarProductos();
        DefaultTableModel model = (DefaultTableModel) vista.getInformaciontbl().getModel();
        model.setRowCount(0);

        for (Producto p : productos) {
            model.addRow(new Object[]{
                p.getNombre(), p.getTipo(), p.getCostoVenta(),
                p.getCostoProduccion(), p.getCantidad(), p.isEspecial(), p.isEstado()
            });
        }
    }
    
    
    
    public void ordenarProductos(int opcion) {
        List<Producto> productos = archivoProducto.cargarProductos();
        
        int opcionSeleccionada = vista.getOrdenarbtn().getSelectedIndex();

        switch (opcionSeleccionada) {
            case 0: // Nombre
                productos.sort((p1, p2) -> p1.getNombre().compareToIgnoreCase(p2.getNombre()));
                break;
            case 1: // Precio
                productos.sort((p1, p2) -> Double.compare(p1.getCostoVenta(), p2.getCostoVenta()));
                break;
            case 2: // Cantidad
                productos.sort((p1, p2) -> Integer.compare(p1.getCantidad(), p2.getCantidad()));
                break;
        }

        DefaultTableModel model = (DefaultTableModel) vista.getInformaciontbl().getModel();
        model.setRowCount(0);
        for (Producto p : productos) {
            model.addRow(new Object[]{
                p.getNombre(), p.getTipo(), p.getCostoVenta(),
                p.getCostoProduccion(), p.getCantidad(), p.isEspecial(), p.isEstado()
            });
        }
    }

    public void exportarCSV() {
        JFileChooser fileChooser = new JFileChooser();
        int seleccion = fileChooser.showSaveDialog(vista);

        if (seleccion == JFileChooser.APPROVE_OPTION) {
            File archivo = fileChooser.getSelectedFile();
            String ruta = archivo.getAbsolutePath();
            if (!ruta.toLowerCase().endsWith(".csv")) {
                ruta += ".csv";
            }

            try (FileWriter writer = new FileWriter(ruta)) {
                DefaultTableModel model = (DefaultTableModel) vista.getInformaciontbl().getModel();
                int colCount = model.getColumnCount();

                for (int i = 0; i < colCount; i++) {
                    writer.append(model.getColumnName(i));
                    if (i < colCount - 1) writer.append(",");
                }
                writer.append("\n");

                for (int row = 0; row < model.getRowCount(); row++) {
                    for (int col = 0; col < colCount; col++) {
                        Object value = model.getValueAt(row, col);
                        writer.append(value != null ? value.toString() : "");
                        if (col < colCount - 1) writer.append(",");
                    }
                    writer.append("\n");
                }

                writer.flush();
                JOptionPane.showMessageDialog(vista, "Archivo exportado exitosamente.");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(vista, "Error al exportar: " + e.getMessage());
            }
        }
    }

    public void abrirVentanaAñadirProducto() {
        new AñadirProducto(vista).setVisible(true);
    }

    public void abrirVentanaActualizarProducto() throws CostoInvalidoException {
        JTable tabla = vista.getInformaciontbl();
        int fila = tabla.getSelectedRow();

        if (fila != -1) {
            String nombre = (String) tabla.getValueAt(fila, 0);
            String tipo = (String) tabla.getValueAt(fila, 1);
            double precio = (double) tabla.getValueAt(fila, 2);
            double precioProd = (double) tabla.getValueAt(fila, 3);
            int cantidad = (int) tabla.getValueAt(fila, 4);
            boolean especial = (boolean) tabla.getValueAt(fila, 5);
            boolean estado = (boolean) tabla.getValueAt(fila, 6);


            Producto producto = null;
            try {
                producto = new Producto(nombre, tipo, precioProd, precio, cantidad, especial, estado);
            } catch (CantidadInvalidaException ex) {
                Logger.getLogger(ControladorVentanaInicio.class.getName()).log(Level.SEVERE, null, ex);
            }
            new ActualizarProducto(vista, producto).setVisible(true);
        } else {
            JOptionPane.showMessageDialog(vista, "Selecciona un producto para editar");
        }
    }
}