
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class ArchivoListado {

    private String nombre;
    private BufferedWriter bw;

    public ArchivoListado(String nomArch) {
        nombre = nomArch;
        try {
            bw = new BufferedWriter(new FileWriter(nomArch));

        } catch (IOException ex) {
            Consola.mostrar("Error: Excepcion de E/S! (" + ex + ")\n");
            System.exit(1);
        }
    }

    public String getNombre() {
        return nombre;
    }

    public void escribirRenglon(String renglon) {
        try {
            bw.write(renglon);
            bw.newLine();
        } catch (IOException ex) {
            Consola.mostrar("Error: Excepcion de E/S! (" + ex + ")\n");
            System.exit(1);
        }
    }

    public void escribirError(int cod, String cad) {
        try {
            Consola.mostrar("¡¡¡ERROR!!!\n");
            Consola.mostrar("Se ha generado un archivo de listado, indicando el error\n");
            bw.write("ERROR: ");
            switch (cod) {
                case 1:
                    bw.write("Se esperaba un punto (.)");
                    break;
                case 2:
                    bw.write("Se esperaba IDENTIFICADOR");
                    break;
                case 3:
                    bw.write("Se esperaba un signo igual (=)");
                    break;
                case 4:
                    bw.write("Se esperaba un número");
                    break;
                case 5:
                    bw.write("Se esperaba un punto y coma (;)");
                    break;
                case 6:
                    bw.write("Se esperaba una asignación (:=)");
                    break;
                case 7:
                    bw.write("Se esperaba punto y coma (;) o END");
                    break;
                case 8:
                    bw.write("Se esperaba THEN");
                    break;
                case 9:
                    bw.write("Se esperaba DO");
                    break;
                case 10:
                    bw.write("Se esperaba (");
                    break;
                case 11:
                    bw.write("Se esperaba )");
                    break;
                case 12:
                    bw.write("Se esperaba un operador de comparación (= , <> , < , <= , > , >=)");
                    break;
                case 13:
                    bw.write("Identificador demasiado largo");
                    break;
                case 14:
                    bw.write("Cadena demasiado larga");
                    break;
                case 15:
                    bw.write("Número fuera de rango");
                    break;    
                case 16:
                    bw.write("Falta cerrar cadena");
                    break;
                case 17:
                    bw.write("Caracter desconocido: " + cad);
                    break;
                case 18:
                    bw.write("Identificador duplicado: " + cad);
                    break;
                case 19:
                    bw.write("Identificador NO declarado: " + cad);
                    break; 
                case 20:
                    bw.write(cad+ " deberia estar declarado como variable.");
            }
            bw.newLine();
            cerrar();
            System.exit(1);

        } catch (IOException ex) {
            Consola.mostrar("Error: Excepcion de E/S! (" + ex + ")\n");
            System.exit(1);
        }
    }

    public void cerrar() {
        try {
            bw.close();
        } catch (IOException ex) {
            Consola.mostrar("Error: Excepcion de E/S! (" + ex + ")\n");
            System.exit(1);
        }
    }
}
