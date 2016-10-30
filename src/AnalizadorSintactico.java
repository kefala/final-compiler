/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Alumno
 */
public class AnalizadorSintactico 
{
    private AnalizadorLexico aLex;
    private AnalizadorSemantico aSem;
    private ArchivoListado archListado;
    private ArchivoEjecutable archEjecutable;
    private int proxVar;
    
    public AnalizadorSintactico(AnalizadorLexico aLex, AnalizadorSemantico aSem, ArchivoListado archListado, ArchivoEjecutable archEjecutable) 
    {
        this.aLex = aLex;
        this.aSem = aSem;
        this.archListado = archListado;
        this.archEjecutable = archEjecutable;
        proxVar = 0;
    }
    
    public void analizar()
    {
        aLex.escanear();
        /*while (aLex.getS() != Terminal.EOF)
        {
            Consola.mostrar(aLex.getS() + " " + aLex.getCad() + "\n");
            aLex.escanear();
        }*/
        
        programa();
        if (aLex.getS() == Terminal.EOF)
        {
            Consola.mostrar("OK");
            archEjecutable.generarEncabezado();
            archEjecutable.finalizarArchivo();
        }
        else Consola.mostrar("ERROR");
        
        
    }
    
    private void programa()
    {
        //La primera instrucción del código traducido será la inicialización 
        //del registro EDI para que apunte a la dirección a partir de la cual 
        //estarán alojados los valores de las variables. Como esta dirección aún no 
        //se conoce, deberá reservarse el lugar grabando BF 00 00 00 00.
        archEjecutable.cargarByteEnMemoria(0xbf);
        int movEDI = archEjecutable.getProxLibre();
        archEjecutable.cargarByteEnMemoria(0x00);
        archEjecutable.cargarByteEnMemoria(0x00);
        archEjecutable.cargarByteEnMemoria(0x00);
        archEjecutable.cargarByteEnMemoria(0x00);
        
        bloque(0);
        if (aLex.getS() == Terminal.PUNTO)
        {
            aLex.escanear();
            int aca = archEjecutable.getProxLibre();
            int alla = 0x588;
            int dist = alla - (aca + 5);
            archEjecutable.cargarByteEnMemoria(0xe9);
            archEjecutable.cargarEnteroEnMemoria(dist);
            
            int tam = archEjecutable.getProxLibre() - Constantes.TAM_HEADER;
            int valEDI = archEjecutable.componer(212) + archEjecutable.componer(216) + tam;
            
            archEjecutable.cargarEnteroEnMemoria(valEDI, movEDI);
          
            for (int i = 0; i < proxVar; i++) 
            {
                archEjecutable.cargarEnteroEnMemoria(0);
            }
            archEjecutable.cargarEnteroEnMemoria(archEjecutable.getProxLibre()-Constantes.TAM_HEADER, 0x01a0);
            int fAligment = archEjecutable.componer(0xDC);
            while (archEjecutable.getProxLibre() % fAligment != 0)
            {
                archEjecutable.cargarByteEnMemoria(0x00);
            }
            
            archEjecutable.cargarEnteroEnMemoria(archEjecutable.getProxLibre()-Constantes.TAM_HEADER, 0x00bc);
            archEjecutable.cargarEnteroEnMemoria(archEjecutable.getProxLibre()-Constantes.TAM_HEADER, 0x01a8);
            
            int sizeOfCodeSection = archEjecutable.componer(0x00bc);
            int sectionAlignment = archEjecutable.componer(0x00d8);
            int sizeOfRawData = archEjecutable.componer(0x01a8);
            
            archEjecutable.cargarEnteroEnMemoria((2 + sizeOfCodeSection / sectionAlignment) * sectionAlignment, 0x00f0);
            archEjecutable.cargarEnteroEnMemoria((2 + sizeOfRawData / sectionAlignment) * sectionAlignment, 0x00d0);
            
        }
        else archListado.escribirError(1, null);
    }
    
    private void bloque (int base)
    {
        
        String nombreIdent = "";
        int valorIdent = 0;
        int desplazamiento = 0;
        archEjecutable.cargarByteEnMemoria(0xE9);
        archEjecutable.cargarByteEnMemoria(0x00);
        archEjecutable.cargarByteEnMemoria(0x00);
        archEjecutable.cargarByteEnMemoria(0x00);
        archEjecutable.cargarByteEnMemoria(0x00);
        int posActual = archEjecutable.getProxLibre();
        
        if (aLex.getS() == Terminal.CONST) 
        {
            aLex.escanear();
            if (aLex.getS() == Terminal.IDENTIFICADOR)
            {
                nombreIdent = aLex.getCad();
                aLex.escanear();
            }
            else archListado.escribirError(2, null);
            if (aLex.getS() == Terminal.IGUAL) aLex.escanear();
            else archListado.escribirError(3, null);
            if (aLex.getS() == Terminal.NUMERO)
            {
                valorIdent = Integer.parseInt(aLex.getCad());
                aLex.escanear();
            }
            else archListado.escribirError(4, null);
            
            if (!aSem.guardarIdentificador(nombreIdent, Terminal.CONST, valorIdent, base+desplazamiento-1, base))
            {
                archListado.escribirError(18, "Error en el identificador L91");
            }
            else desplazamiento++;
            
            while (aLex.getS() == Terminal.COMA)
            {
                aLex.escanear();
                if (aLex.getS() == Terminal.IDENTIFICADOR)
                {
                    nombreIdent = aLex.getCad();
                    aLex.escanear();
                }
                else archListado.escribirError(2, null);
                if (aLex.getS() == Terminal.IGUAL) aLex.escanear();
                else archListado.escribirError(3, null);
                if (aLex.getS() == Terminal.NUMERO)
                {
                    valorIdent = Integer.parseInt(aLex.getCad());
                    aLex.escanear();
                }
                else archListado.escribirError(4, null);
                
                if (!aSem.guardarIdentificador(nombreIdent, Terminal.CONST, valorIdent, base+desplazamiento-1, base))
                {
                    archListado.escribirError(18, "Error en el identificador L115");
                }
                else desplazamiento++;
            }
            
            if (aLex.getS() == Terminal.PUNTO_Y_COMA) aLex.escanear();
            else archListado.escribirError(5, null);
        }
        
        if (aLex.getS() == Terminal.VAR) 
        {
            aLex.escanear();
            if (aLex.getS() == Terminal.IDENTIFICADOR)
            {
                nombreIdent = aLex.getCad();
                aLex.escanear();
            }
            else archListado.escribirError(2, null);
            
            if (!aSem.guardarIdentificador(nombreIdent, Terminal.VAR, proxVar, base+desplazamiento-1, base))
            {
                archListado.escribirError(18, "Error en el identificador L136");
            }
            else
            {
                proxVar++;
                desplazamiento++;
            }
            
            while (aLex.getS() == Terminal.COMA)
            {
                aLex.escanear();
                if (aLex.getS() == Terminal.IDENTIFICADOR)
                {
                    nombreIdent = aLex.getCad();
                    aLex.escanear();
                }
                else archListado.escribirError(2, null);
                
                if (!aSem.guardarIdentificador(nombreIdent, Terminal.VAR, proxVar, base+desplazamiento-1, base))
                {
                    archListado.escribirError(18, "Error en el identificador L156");
                }
                else
                {
                    proxVar++;
                    desplazamiento++;
                }
            }
            
            if (aLex.getS() == Terminal.PUNTO_Y_COMA) aLex.escanear();
            else archListado.escribirError(5, null);
        }
        
        while (aLex.getS() == Terminal.PROCEDURE) 
        {
            aLex.escanear();
            if (aLex.getS() == Terminal.IDENTIFICADOR)
            {
                nombreIdent = aLex.getCad();
                aLex.escanear();
            }
            else archListado.escribirError(2, null);
            if (aLex.getS() == Terminal.PUNTO_Y_COMA) aLex.escanear();
            else archListado.escribirError(5, null);
            
            if (!aSem.guardarIdentificador(nombreIdent, Terminal.PROCEDURE, archEjecutable.getProxLibre(), base+desplazamiento-1, base))
            {
                archListado.escribirError(18, "Error en el identificador L183");
            }
            else desplazamiento++;
            
            bloque(base + desplazamiento);
            archEjecutable.cargarByteEnMemoria(0xC3);
            if (aLex.getS() == Terminal.PUNTO_Y_COMA) aLex.escanear();
            else archListado.escribirError(5, null);
        }
            int alla = posActual;
           int aca = archEjecutable.getProxLibre();
                        int dist = aca-alla;
                        archEjecutable.cargarEnteroEnMemoria(dist,posActual-4);
                        
        
        proposicion(base, desplazamiento);
        
        
    }
    
    private void proposicion(int base, int desplazamiento)
    {
        
        String a = "";
        
        switch (aLex.getS())
        {
            case IDENTIFICADOR:
                
                a = aLex.getCad();
                if(!aSem.esta(a, base+desplazamiento-1, 0)){
                archListado.escribirError(19, a);
                }else{
                    if(aSem.getTipo(a, base+desplazamiento-1)!=Terminal.VAR){
                        archListado.escribirError(20, a);                        
                    }
                }
                aLex.escanear();
                
                if (aLex.getS() == Terminal.ASIGNACION)
                {
                    
                    aLex.escanear();
                }
                else archListado.escribirError(6, null);
                expresion(base, desplazamiento);          
               
              
                        int donde = aSem.getValor(a, base+desplazamiento-1);
                        archEjecutable.cargarByteEnMemoria(0x58); // pop                        
                        archEjecutable.cargarByteEnMemoria(0x89); // MOV [EDI+abcdefgh], EAX
                        archEjecutable.cargarByteEnMemoria(0x87); // esto tmb es del mov
                        archEjecutable.cargarEnteroEnMemoria(donde*4);                    
            
                
                break;
                
            case CALL:
                
                
                aLex.escanear();
                
                if (aLex.getS() == Terminal.IDENTIFICADOR)
                {
                    a = aLex.getCad();
                    if (aSem.esta(a, base+desplazamiento-1, 0) )
                    {
                        int alla = aSem.getValor(a, base+desplazamiento-1);
                        int aca = archEjecutable.getProxLibre();
                        int dist = alla - (aca + 5);
                        archEjecutable.cargarByteEnMemoria(0xe8);
                        archEjecutable.cargarEnteroEnMemoria(dist);
                    }
                    else
                    {
                        archListado.escribirError(19, "Error en identificador L243");
                    }
                    
                    aLex.escanear();
                }
                else archListado.escribirError(2, null);
                break;
                
            case BEGIN:
                
                aLex.escanear();
                proposicion(base, desplazamiento);
                while (aLex.getS() == Terminal.PUNTO_Y_COMA)
                {
                    aLex.escanear();
                    proposicion(base, desplazamiento);
                }
                if (aLex.getS() == Terminal.END) aLex.escanear();
                else archListado.escribirError(7, null);
                break;
                
            case IF:
                
                aLex.escanear();
                condicion(base, desplazamiento);
                int proxLibre=archEjecutable.getProxLibre();
                
                if (aLex.getS() == Terminal.THEN) aLex.escanear();
                else archListado.escribirError(8, null);
                                
                proposicion(base, desplazamiento);
                aLex.escanear();
                
                int distancia = archEjecutable.getProxLibre()-proxLibre;
                archEjecutable.cargarEnteroEnMemoria(distancia,proxLibre-4);
                
            
                break;
                
            case WHILE:
                
                int proxLibreWhile = archEjecutable.getProxLibre();
                aLex.escanear();
                condicion(base, desplazamiento);
                int proxLibrePosCondi = archEjecutable.getProxLibre();
                if (aLex.getS() == Terminal.DO) aLex.escanear();
                else archListado.escribirError(9, null);
                proposicion(base, desplazamiento);
                
                distancia = proxLibreWhile-(archEjecutable.getProxLibre()+5);
                archEjecutable.cargarByteEnMemoria(0xE9);
                archEjecutable.cargarEnteroEnMemoria(distancia);
                
                int distancia2 = archEjecutable.getProxLibre()-proxLibrePosCondi;
                archEjecutable.cargarEnteroEnMemoria(distancia2,proxLibrePosCondi-4);
                break;
                
            case READLN:
                
                
                aLex.escanear();
                if (aLex.getS() == Terminal.ABRE_PARENTESIS) aLex.escanear();
                else archListado.escribirError(10, null);
                if (aLex.getS() == Terminal.IDENTIFICADOR)
                {
                    a = aLex.getCad();
                    if (aSem.esta(a, base+desplazamiento-1, 0))
                    {
                        if (aSem.getTipo(a, base+desplazamiento-1) == Terminal.VAR)
                        {
                            int alla = 0x590;
                            int aca = archEjecutable.getProxLibre();
                            int dist = alla - (aca + 5);
                            archEjecutable.cargarByteEnMemoria(0xe8); //call
                            archEjecutable.cargarEnteroEnMemoria(dist); //call
                            
                             donde = aSem.getValor(a, base+desplazamiento-1);
                            archEjecutable.cargarByteEnMemoria(0x89); //mov
                            archEjecutable.cargarByteEnMemoria(0x87);//mov
                            archEjecutable.cargarEnteroEnMemoria(donde*4);//mov
                        }
                        else archListado.escribirError(20, "Se esperaba una variable");
                    }
                    else
                    {
                        archListado.escribirError(19, "Error en asignacion L222");
                    }  
                    
                    aLex.escanear();
                } 
                else archListado.escribirError(2, null);
                while (aLex.getS() == Terminal.COMA)
                {
                   aLex.escanear(); 
                   if (aLex.getS() == Terminal.IDENTIFICADOR)
                   {
                        a = aLex.getCad();
                        if (aSem.esta(a, base+desplazamiento-1, 0))
                        {
                            if (aSem.getTipo(a, base+desplazamiento-1) == Terminal.VAR)
                            {
                                int alla = 0x590;
                                int aca = archEjecutable.getProxLibre();
                                int dist = alla - (aca + 5);
                                archEjecutable.cargarByteEnMemoria(0xe8); //call
                                archEjecutable.cargarEnteroEnMemoria(dist); //call

                                 donde = aSem.getValor(a, base+desplazamiento-1);
                                archEjecutable.cargarByteEnMemoria(0x89); //mov
                                archEjecutable.cargarByteEnMemoria(0x87);//mov
                                archEjecutable.cargarEnteroEnMemoria(donde*4);//mov
                            }
                            else archListado.escribirError(20, "Se esperaba una variable");
                        }
                        else
                        {
                            archListado.escribirError(19, "Error en asignacion L222");
                        }
                       aLex.escanear();
                   }
                   else archListado.escribirError(2, null);
                }
                if (aLex.getS() == Terminal.CIERRA_PARENTESIS) aLex.escanear();
                else archListado.escribirError(11, null);
                break;
                
            case WRITELN:
                
                aLex.escanear();
                if (aLex.getS() == Terminal.ABRE_PARENTESIS) 
                {
                   aLex.escanear();
                   if (aLex.getS() == Terminal.CADENA_LITERAL)
                   {
                        int BaseOfCode = archEjecutable.componer(0x00CC);
                        int ImageBase = archEjecutable.componer(0x00D4);
                        int DireccionDeCadena= archEjecutable.getProxLibre()+ 15 + BaseOfCode + ImageBase-Constantes.TAM_HEADER;
                        archEjecutable.cargarByteEnMemoria(0xb8);
                        archEjecutable.cargarEnteroEnMemoria(DireccionDeCadena);                       
                        int desde = archEjecutable.getProxLibre()+5;
                         donde = 0x03e0;
                        distancia = donde-desde;
                        archEjecutable.cargarByteEnMemoria(0xE8); 
                        archEjecutable.cargarEnteroEnMemoria(distancia);
                        archEjecutable.cargarByteEnMemoria(0xE9);
                        archEjecutable.cargarEnteroEnMemoria(aLex.getCad().length()-1);
                        for (int i = 1; i < aLex.getCad().length()-1; i++) {
                            
                            archEjecutable.cargarByteEnMemoria(aLex.getCad().charAt(i));
                            
                        }
                         archEjecutable.cargarByteEnMemoria(0x00);
                       aLex.escanear();
                   }
                   else 
                   {
                    expresion(base, desplazamiento);
                    archEjecutable.cargarByteEnMemoria(0x58); //pop
                    int alla = 0x420;// rutina
                    int aca = archEjecutable.getProxLibre();
                    int dist = alla - (aca + 5);
                    archEjecutable.cargarByteEnMemoria(0xe8); //call
                    archEjecutable.cargarEnteroEnMemoria(dist); //call
                   }
                   while (aLex.getS() == Terminal.COMA)
                   {
                       aLex.escanear();
                       if (aLex.getS() == Terminal.CADENA_LITERAL)
                       {
                            int BaseOfCode = archEjecutable.componer(0x00CC);
                        int ImageBase = archEjecutable.componer(0x00D4);
                        int DireccionDeCadena= archEjecutable.getProxLibre()+ 15 + BaseOfCode + ImageBase-Constantes.TAM_HEADER;
                        archEjecutable.cargarByteEnMemoria(0xb8);
                        archEjecutable.cargarEnteroEnMemoria(DireccionDeCadena);
                        int desde = archEjecutable.getProxLibre()+5;
                         donde = 0x03e0;
                        distancia = donde-desde;
                        archEjecutable.cargarByteEnMemoria(0xE8); 
                        archEjecutable.cargarEnteroEnMemoria(distancia);
                        archEjecutable.cargarByteEnMemoria(0xE9);
                        archEjecutable.cargarEnteroEnMemoria(aLex.getCad().length()-1);
                        for (int i = 1; i < aLex.getCad().length()-1; i++) {
                            
                            archEjecutable.cargarByteEnMemoria(aLex.getCad().charAt(i));
                            
                        }
                         archEjecutable.cargarByteEnMemoria(0x00);
                           aLex.escanear();
                       }
                       else{
                            expresion(base, desplazamiento);
                            archEjecutable.cargarByteEnMemoria(0x58); //pop
                            int alla = 0x420;// rutina
                            int aca = archEjecutable.getProxLibre();
                          int dist = alla - (aca + 5);
                           archEjecutable.cargarByteEnMemoria(0xe8); //call
                            archEjecutable.cargarEnteroEnMemoria(dist); //call
                       }
                           
                   }
                   if (aLex.getS() == Terminal.CIERRA_PARENTESIS) aLex.escanear();
                   else archListado.escribirError(11, null); 
                }
                   int alla = 0x410;// rutina
                            int aca = archEjecutable.getProxLibre();
                          int dist = alla - (aca + 5);
                           archEjecutable.cargarByteEnMemoria(0xe8); //call
                            archEjecutable.cargarEnteroEnMemoria(dist); //call
                break;
                
            case WRITE:

                aLex.escanear();
                if (aLex.getS() == Terminal.ABRE_PARENTESIS) aLex.escanear();
                else archListado.escribirError(10, null);
                if (aLex.getS() == Terminal.CADENA_LITERAL) 
                    {
                  
                        int BaseOfCode = archEjecutable.componer(0x00CC);
                        int ImageBase = archEjecutable.componer(0x00D4);
                        int DireccionDeCadena= archEjecutable.getProxLibre()+ 15 + BaseOfCode + ImageBase -Constantes.TAM_HEADER;
                        archEjecutable.cargarByteEnMemoria(0xb8);
                        archEjecutable.cargarEnteroEnMemoria(DireccionDeCadena);
                        int desde = archEjecutable.getProxLibre()+5;
                         donde = 0x03e0;
                        distancia = donde-desde;
                        archEjecutable.cargarByteEnMemoria(0xE8); 
                        archEjecutable.cargarEnteroEnMemoria(distancia);
                        archEjecutable.cargarByteEnMemoria(0xE9);
                        archEjecutable.cargarEnteroEnMemoria(aLex.getCad().length()-1);
                        for (int i = 1; i < aLex.getCad().length()-1; i++) {
                            
                            archEjecutable.cargarByteEnMemoria(aLex.getCad().charAt(i));
                            
                        }
                         archEjecutable.cargarByteEnMemoria(0x00);                        
                          aLex.escanear();
                    }
                else 
                {    
                    expresion(base, desplazamiento);
                    archEjecutable.cargarByteEnMemoria(0x58); //pop
                     alla = 0x420;// rutina
                     aca = archEjecutable.getProxLibre();
                     dist = alla - (aca + 5);
                    archEjecutable.cargarByteEnMemoria(0xe8); //call
                    archEjecutable.cargarEnteroEnMemoria(dist); //call
                }    
                while (aLex.getS() == Terminal.COMA)
                {
                    aLex.escanear();
                    if (aLex.getS() == Terminal.CADENA_LITERAL) 
                    {
                        int BaseOfCode = archEjecutable.componer(0x00CC);
                        int ImageBase = archEjecutable.componer(0x00D4);
                        int DireccionDeCadena= archEjecutable.getProxLibre()+ 15 + BaseOfCode + ImageBase -Constantes.TAM_HEADER;
                        archEjecutable.cargarByteEnMemoria(0xb8);
                        archEjecutable.cargarEnteroEnMemoria(DireccionDeCadena);
                        int desde = archEjecutable.getProxLibre()+5;
                        donde = 0x03e0;
                        distancia = donde-desde;
                        archEjecutable.cargarByteEnMemoria(0xE8); 
                        archEjecutable.cargarEnteroEnMemoria(distancia);
                        archEjecutable.cargarByteEnMemoria(0xE9);
                        archEjecutable.cargarEnteroEnMemoria(aLex.getCad().length()-1);
                        for (int i = 1; i < aLex.getCad().length()-1; i++) {
                            
                            archEjecutable.cargarByteEnMemoria(aLex.getCad().charAt(i));
                            
                        }
                         archEjecutable.cargarByteEnMemoria(0x00);
                        aLex.escanear();
                        
                    }
                        
                    else
                    {    
                        expresion(base, desplazamiento);
                        archEjecutable.cargarByteEnMemoria(0x58); //call
                         alla = 0x420;
                         aca = archEjecutable.getProxLibre();
                         dist = alla - (aca + 5);
                        archEjecutable.cargarByteEnMemoria(0xe8); //call
                        archEjecutable.cargarEnteroEnMemoria(dist); //call
                    }
                }
                if (aLex.getS() == Terminal.CIERRA_PARENTESIS) aLex.escanear();
                else archListado.escribirError(11, null);
                break;
                
        }
    }
    
    private void condicion(int base, int desplazamiento)
    {
        if (aLex.getS() == Terminal.ODD)
        {
            aLex.escanear();
            expresion(base, desplazamiento); 
            archEjecutable.cargarByteEnMemoria(0x58); //POP EAX 
            archEjecutable.cargarByteEnMemoria(0xa8); //TEST AL, ab 
            archEjecutable.cargarByteEnMemoria(0x01); //
            archEjecutable.cargarByteEnMemoria(0x7b); //JPO dir 
            archEjecutable.cargarByteEnMemoria(0x05); //
            archEjecutable.cargarByteEnMemoria(0xe9); //JMP dir 
            archEjecutable.cargarByteEnMemoria(0x00); //
            archEjecutable.cargarByteEnMemoria(0x00); //
            archEjecutable.cargarByteEnMemoria(0x00); //
            archEjecutable.cargarByteEnMemoria(0x00); //
        }
        else
        {
            expresion(base, desplazamiento);
            
            Terminal op = aLex.getS();
            
            switch (op)
            {
                case IGUAL:
                    aLex.escanear();
                    break;
                    
                case DISTINTO:
                    aLex.escanear();
                    break;
                    
                case MENOR:
                    aLex.escanear();
                    break;
                    
                case MENOR_IGUAL:
                    aLex.escanear();
                    break;
                    
                case MAYOR:
                    aLex.escanear();
                    break;
                    
                case MAYOR_IGUAL:
                    aLex.escanear();
                    break;
                    
                default:
                    archListado.escribirError(12, null);
                    break;    
            }
            
            expresion(base, desplazamiento);
            
            archEjecutable.cargarByteEnMemoria(0x58); //POP EAX 
            archEjecutable.cargarByteEnMemoria(0x5b); //POP EBX 
            archEjecutable.cargarByteEnMemoria(0x39); //CMP EBX, EAX 
            archEjecutable.cargarByteEnMemoria(0xc3); // instrucción RET
            
            switch (op)
            {
                case IGUAL:
                    archEjecutable.cargarByteEnMemoria(0x74);
                    archEjecutable.cargarByteEnMemoria(0x05);
                    break;
                    
                case DISTINTO:
                    archEjecutable.cargarByteEnMemoria(0x75);
                    archEjecutable.cargarByteEnMemoria(0x05);
                    break;
                    
                case MENOR:
                    archEjecutable.cargarByteEnMemoria(0x7c);
                    archEjecutable.cargarByteEnMemoria(0x05);
                    break;
                    
                case MENOR_IGUAL:
                    archEjecutable.cargarByteEnMemoria(0x7e);
                    archEjecutable.cargarByteEnMemoria(0x05);
                    break;
                    
                case MAYOR:
                    archEjecutable.cargarByteEnMemoria(0x7f); //JG dir
                    archEjecutable.cargarByteEnMemoria(0x05); //
                    break;
                    
                case MAYOR_IGUAL:
                    archEjecutable.cargarByteEnMemoria(0x7d);
                    archEjecutable.cargarByteEnMemoria(0x05);
                    break;
            }
            
            archEjecutable.cargarByteEnMemoria(0xe9); //JMP dir 
            archEjecutable.cargarByteEnMemoria(0x00); //
            archEjecutable.cargarByteEnMemoria(0x00); //
            archEjecutable.cargarByteEnMemoria(0x00); //
            archEjecutable.cargarByteEnMemoria(0x00); //
        }
    }
    
    private void expresion(int base, int desplazamiento)
    {
        Terminal op = null; 
        if (aLex.getS() == Terminal.MAS || aLex.getS() == Terminal.MENOS)
        {    
            op = aLex.getS();   
            aLex.escanear();
        }
        termino(base, desplazamiento);
        if (op == Terminal.MENOS) 
        {
            archEjecutable.cargarByteEnMemoria(0x58); //POP EAX 
            archEjecutable.cargarByteEnMemoria(0xf7); //NEG EAX 
            archEjecutable.cargarByteEnMemoria(0xd8); //NEG EAX
            archEjecutable.cargarByteEnMemoria(0x50); //PUSH EAX
        }
        while (aLex.getS() == Terminal.MAS || aLex.getS() == Terminal.MENOS)
        {
            op = aLex.getS();
            aLex.escanear();
            termino(base, desplazamiento);
            if (op == Terminal.MAS) 
            {
                archEjecutable.cargarByteEnMemoria(0x58); //POP EAX 
                archEjecutable.cargarByteEnMemoria(0x5b); //POP EBX 
                archEjecutable.cargarByteEnMemoria(0x01); //ADD EAX, EBX 
                archEjecutable.cargarByteEnMemoria(0xd8); //ADD EAX, EBX 
                archEjecutable.cargarByteEnMemoria(0x50); //PUSH EAX 
            }
            else // Si es MENOS
            {
                archEjecutable.cargarByteEnMemoria(0x58); //POP EAX 
                archEjecutable.cargarByteEnMemoria(0x5b); //POP EBX 
                archEjecutable.cargarByteEnMemoria(0x93); //XCHG EAX, EBX 
                archEjecutable.cargarByteEnMemoria(0x29); //SUB EAX, EBX  
                archEjecutable.cargarByteEnMemoria(0xd8); //SUB EAX, EBX  
                archEjecutable.cargarByteEnMemoria(0x50); //PUSH EAX
            }
        }
    }
    
    private void termino(int base, int desplazamiento)
    {
        factor(base, desplazamiento);
        while (aLex.getS() == Terminal.PRODUCTO || aLex.getS() == Terminal.DIVISION)
        {
            Terminal op = aLex.getS();
            aLex.escanear();
            factor(base, desplazamiento);
            if (op == Terminal.PRODUCTO) 
            {
                archEjecutable.cargarByteEnMemoria(0x58); //POP EAX 
                archEjecutable.cargarByteEnMemoria(0x5b); //POP EBX 
                archEjecutable.cargarByteEnMemoria(0xf7); //IMUL EBX
                archEjecutable.cargarByteEnMemoria(0xeb); //IMUL EBX
                archEjecutable.cargarByteEnMemoria(0x50); //PUSH EAX 
            }
            else // Si es DIVIDIDO
            {
                archEjecutable.cargarByteEnMemoria(0x58); //POP EAX 
                archEjecutable.cargarByteEnMemoria(0x5b); //POP EBX 
                archEjecutable.cargarByteEnMemoria(0x93); //XCHG EAX, EBX 
                archEjecutable.cargarByteEnMemoria(0x99); //CDQ 
                archEjecutable.cargarByteEnMemoria(0xf7); //IDIV EBX 
                archEjecutable.cargarByteEnMemoria(0xfb); //IDIV EBX 
                archEjecutable.cargarByteEnMemoria(0x50); //PUSH EAX
            }
        }
    }
    
    private void factor(int base, int desplazamiento)
    {
        switch (aLex.getS())
        {
            case IDENTIFICADOR:
                    //falla get tipo al buscar la constante
                if (aSem.getTipo(aLex.getCad(), base + desplazamiento-1) == Terminal.CONST) 
                {
                    archEjecutable.cargarByteEnMemoria(0xB8);// MOV EAX, abcdefgh  
                   
                    if (aSem.esta(aLex.getCad(), base+desplazamiento-1, 0) )
                    {
                        int numero = aSem.getValor(aLex.getCad(), base + desplazamiento-1);
                        archEjecutable.cargarEnteroEnMemoria(numero);
                        archEjecutable.cargarByteEnMemoria(0x50);//push
                    }
                    else
                    {
                        archListado.escribirError(19, "Error en identificador L552");
                    }
                     
                } else if (aSem.getTipo(aLex.getCad(), base + desplazamiento-1) == Terminal.VAR) 
                {
                    archEjecutable.cargarByteEnMemoria(0x8B); // MOV EAX, [EDI+abcdefgh]
                    archEjecutable.cargarByteEnMemoria(0x87); // arriba ^
                    if (aSem.esta(aLex.getCad(), base+desplazamiento-1, 0) )
                    {
                        int numero = aSem.getValor(aLex.getCad(), base + desplazamiento-1);
                        archEjecutable.cargarEnteroEnMemoria(numero*4);
                        archEjecutable.cargarByteEnMemoria(0x50);//push
                    }
                    else
                    {
                        archListado.escribirError(19, "Error en VAR L570");
                    }
                    
                } 
                else 
                {
                    archListado.escribirError(14, null);
                }
                aLex.escanear();
                break;
                
            case NUMERO:
                archEjecutable.cargarByteEnMemoria(0xB8); // MOV EAX, abcdefgh 
                int numero = Integer.parseInt(aLex.getCad());
                archEjecutable.cargarEnteroEnMemoria(numero);
                archEjecutable.cargarByteEnMemoria(0x50); // PUSH EAX 
                aLex.escanear();
                break;
                
            case ABRE_PARENTESIS:
                aLex.escanear();
                expresion(base, desplazamiento);
                if (aLex.getS() == Terminal.CIERRA_PARENTESIS) aLex.escanear();
                else archListado.escribirError(11, null);
                break;
        }
    }

}
