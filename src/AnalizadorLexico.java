
public class AnalizadorLexico {

    private String restante;
    private String cad;
    private int numLinea;
    private Terminal s;
    private ArchivoListado archListado;
    private ArchivoFuente archFuente;

    public AnalizadorLexico(ArchivoFuente archFuente, ArchivoListado archListado) 
    {
        this.archFuente = archFuente;
        this.archListado = archListado;
        numLinea = 1;
        cad = "";
        restante = archFuente.leerRenglon();
        if (restante != null) 
        {
            archListado.escribirRenglon(String.format("%4d", numLinea++) + ": " + restante);
        }
    }

    public String getCad() {
        return cad;
    }

    public Terminal getS() {
        return s;
    }

    public void escanear() {
        int maximo = 0;
        boolean primerCharSirve = false;
        while (!primerCharSirve) 
        {
            if (restante == null) 
            {
                primerCharSirve = true;
            } 
            else 
            {
                while (restante.length() > 0 && Character.isWhitespace(restante.charAt(0))) 
                {
                    restante = restante.substring(1, restante.length());
                }
                if (restante.isEmpty()) 
                {
                    restante = archFuente.leerRenglon();
                    if (restante != null) 
                    {
                        archListado.escribirRenglon(String.format("%4d", numLinea++) + ": " + restante);
                    }
                } 
                else 
                {
                    primerCharSirve = true;
                }
            }
        }
        cad = "";
        if (restante == null) {
            s = Terminal.EOF;
        } else {
            char ch = restante.charAt(0);
            restante = restante.substring(1, restante.length());
            if (Character.isLetter(ch)) {
                cad = cad + ch;
                while (restante.length() > 0 && Character.isLetterOrDigit(restante.charAt(0))) {
                    ch = restante.charAt(0);
                    restante = restante.substring(1, restante.length());
                    cad = cad + ch;
                }
                if (cad.equalsIgnoreCase("VAR")) {
                    s = Terminal.VAR;
                } else if (cad.equalsIgnoreCase("CALL")) {
                    s = Terminal.CALL;
                } else if (cad.equalsIgnoreCase("CONST")) {
                    s = Terminal.CONST;
                } else if (cad.equalsIgnoreCase("PROCEDURE")) {
                    s = Terminal.PROCEDURE;
                } else if (cad.equalsIgnoreCase("BEGIN")) {
                    s = Terminal.BEGIN;
                } else if (cad.equalsIgnoreCase("END")) {
                    s = Terminal.END;
                } else if (cad.equalsIgnoreCase("IF")) {
                    s = Terminal.IF;
                } else if (cad.equalsIgnoreCase("THEN")) {
                    s = Terminal.THEN;
                } else if (cad.equalsIgnoreCase("WHILE")) {
                    s = Terminal.WHILE;
                } else if (cad.equalsIgnoreCase("DO")) {
                    s = Terminal.DO;
                } else if (cad.equalsIgnoreCase("ODD")) {
                    s = Terminal.ODD;
                } else if (cad.equalsIgnoreCase("READLN")) {
                    s = Terminal.READLN;
                } else if (cad.equalsIgnoreCase("WRITE")) {
                    s = Terminal.WRITE;
                } else if (cad.equalsIgnoreCase("WRITELN")) {
                    s = Terminal.WRITELN;
                }
               
                
                
                else {
                    if (cad.length() <= Constantes.MAX_LONG_IDENT) {
                        s = Terminal.IDENTIFICADOR;
                    } else {
                        archListado.escribirError(13, null);
                    }
                }
            } else if (Character.isDigit(ch)) {
                cad = cad + ch;
                while (restante.length() > 0 && Character.isDigit(restante.charAt(0))) {
                    ch = restante.charAt(0);
                    if (Character.isDigit(ch)) {
                        cad = cad + ch;
                        restante = restante.substring(1, restante.length());
                    }
                }
                if (cad.length() < 1 + Math.log(Constantes.MAX_TAM_NUM) && Integer.parseInt(cad) <= Constantes.MAX_TAM_NUM) {
                    s = Terminal.NUMERO;
                } else {
                    archListado.escribirError(15, null);
                }
            } else {
                switch (ch) {
                    case ':':
                        cad = ":";
                        if (restante.length() > 0) {
                            ch = restante.charAt(0);
                            if (ch == '=') {
                                cad = cad + ch;
                                restante = restante.substring(1, restante.length());
                                s = Terminal.ASIGNACION;
                            } else {
                                s = Terminal.NULO;
                            }
                        } else {
                            s = Terminal.NULO;
                        }
                        break;
                    case '+':
                        cad = "+";
                        s = Terminal.MAS;
                        break;
                    case '-':
                        cad = "-";
                        s = Terminal.MENOS;
                        break;
                    case '*':
                        cad = "*";
                        s = Terminal.PRODUCTO;
                        break;
                    case '/':
                        cad = "/";
                        s = Terminal.DIVISION;
                        break;
                    case '=':
                        cad = "=";
                        s = Terminal.IGUAL;
                        break;
                    case '.':
                        cad = ".";
                        s = Terminal.PUNTO;
                        break; 
                    case ',':
                        cad = ",";
                        s = Terminal.COMA;
                        break;
                    case ';':
                        cad = ";";
                        s = Terminal.PUNTO_Y_COMA;
                        break;
                    case '(':
                        cad = "(";
                        s = Terminal.ABRE_PARENTESIS;
                        break;
                    case ')':
                        cad = ")";
                        s = Terminal.CIERRA_PARENTESIS;
                        break;
                    case '>':
                        cad = ">";
                        if (restante.length() > 0) {
                            ch = restante.charAt(0);
                            if (ch == '=') {
                                cad = cad + ch;
                                restante = restante.substring(1, restante.length());
                                s = Terminal.MAYOR_IGUAL;
                            } else {
                                s = Terminal.MAYOR;
                            }
                        } else {
                            s = Terminal.MAYOR;
                        }
                        /////////////////////////
                        break;
                    case '<':
                        cad = "<";
                        if (restante.length() > 0) {
                            ch = restante.charAt(0);
                            if (ch == '=') {
                                cad = cad + ch;
                                restante = restante.substring(1, restante.length());
                                s = Terminal.MENOR_IGUAL;
                            } else if (ch == '>') {
                                cad = cad + ch;
                                restante = restante.substring(1, restante.length());
                                s = Terminal.DISTINTO;
                            } else {
                                s = Terminal.MENOR;
                            }
                        } else {
                            s = Terminal.MENOR;
                        }
                        break;
                    case '\'':
                        cad = "'";
                        if (restante.length() > 0) {
                            String cadena = cad;
                            do {
                                cadena = cadena + restante.charAt(0);
                                restante = restante.substring(1, restante.length());
                                maximo++;
                            } while (cadena.charAt(cadena.length() - 1) != '\'' && maximo <= 80 && restante.length() > 0);
                            if (maximo > 80) {
                                archListado.escribirError(14, null);
                            }
                            if (restante.length() <= 0) {
                                archListado.escribirError(16, null);
                            }
                            cad = cadena;
                            s = Terminal.CADENA_LITERAL;
                        } else {
                            s = Terminal.NULO;
                        }
                        break;
                    
                    default:
                        cad = "" + ch;
                        archListado.escribirError(17, cad);
                }
            }
        }

    }
}
