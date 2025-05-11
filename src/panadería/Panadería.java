
package panadería;

import controlador.ControladorVentanaInicio;
import interfaz.VentanaInicio;

public class Panadería {

    public static void main(String[] args) {
        VentanaInicio vista = new VentanaInicio();
        ControladorVentanaInicio controlador = new ControladorVentanaInicio(vista);
        vista.setControlador(controlador);
        vista.setVisible(true);
    }
    
}
