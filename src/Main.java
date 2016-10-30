
import java.io.FileNotFoundException;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        String nomArch = "";
        if (args.length == 0) {
            Consola.mostrar("Ingrese el nombre del archivo fuente en PL/0: ");
            nomArch = Consola.ingresar();
        } else {
            nomArch = args[0];
        }
        if (nomArch.isEmpty()) {
            Consola.mostrar("Error!\n");
            Consola.mostrar("Uso: java -jar \"Compilar.jar\" <archivo>\n");
        } else {
            ArchivoFuente archFuente = new ArchivoFuente(nomArch);
            int posPuntoFinal = nomArch.lastIndexOf('.');
	    nomArch = (posPuntoFinal == -1 ? nomArch : nomArch.substring(0, posPuntoFinal));
            ArchivoListado archListado = new ArchivoListado(nomArch + ".lst");
            ArchivoEjecutable archEjecutable = new ArchivoEjecutable(nomArch + ".exe");
            
            AnalizadorLexico aLex = new AnalizadorLexico(archFuente, archListado);
            
           
            AnalizadorSemantico aSem = new AnalizadorSemantico();
            AnalizadorSintactico aSint = new AnalizadorSintactico(aLex, aSem, archListado, archEjecutable);
            aSint.analizar();

            archFuente.cerrar();
            archListado.cerrar();
        }
    }
}
