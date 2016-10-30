
import java.util.Scanner;

public class Consola {

    private static final Scanner ENTRADA = new Scanner(System.in);

    public static String ingresar() {
        return ENTRADA.nextLine();
    }

    public static void mostrar(String s) {
        System.out.print(s);
    }
}
