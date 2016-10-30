
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ArchivoFuente {

    private String nombre;
    private BufferedReader br;

    public ArchivoFuente(String nomArch) {
        try {
            nombre = nomArch;
            br = new BufferedReader(new FileReader(nomArch));
        } catch (FileNotFoundException ex) {
            Consola.mostrar("Error: Archivo no encontrado! (" + ex + ")\n");
            System.exit(1);
        }
    }

    public String getNombre() {
        return nombre;
    }

    public String leerRenglon() {
        String renglon = "";
        try {
            renglon = br.readLine();
        } catch (IOException ex) {
            Consola.mostrar("Error: Excepcion de E/S! (" + ex + ")\n");
            System.exit(1);
        }
        return renglon;
    }

    public void cerrar() {
        try {
            br.close();
        } catch (IOException ex) {
            Consola.mostrar("Error: Excepcion de E/S! (" + ex + ")\n");
            System.exit(1);
        }
    }
}
