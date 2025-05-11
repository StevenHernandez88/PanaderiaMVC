package persistencia;


import java.io.*;
import java.util.*;
import modelo.Producto;

public class ArchivoProducto {
    private static final String RUTA = "C:\\Users\\PC\\Downloads\\Proyectos\\Productos\\productos.dat";

    public void guardarProductos(Producto producto) throws IOException {
        List<Producto> productos = cargarProductos(); // cargar los ya guardados
        productos.add(producto); // añadir nuevo producto

        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(RUTA));
        oos.writeObject(productos); // guardar la lista completa
        oos.close();
    }


    public List<Producto> cargarProductos() {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(RUTA));
            return (List<Producto>) ois.readObject();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
}