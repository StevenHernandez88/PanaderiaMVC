package util;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import modelo.Producto;

public class ExportadorCSV {
    
    public static void exportarProductos(List<Producto> productos, String rutaCSV) throws IOException {
        FileWriter writer = new FileWriter(rutaCSV);
        writer.append("Nombre,Costo Producción,Costo Venta,Cantidad,Extra\n");
        for (Producto p : productos) {
            writer.append(String.format("%s,%.2f,%.2f,%d,%s\n",
                    p.getNombre(), p.getCostoProduccion(), p.getCostoVenta(), p.getCantidad()));
        }
        writer.close();
    }     
}
