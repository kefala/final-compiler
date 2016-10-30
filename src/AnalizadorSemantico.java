/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.ArrayList;

/**
 *
 * @author Alumno
 */
public class AnalizadorSemantico 
{
    private Identificador[] tabla;

    public AnalizadorSemantico() 
    {
        this.tabla = new Identificador[Constantes.MAX_CANT_IDENT];
    }
    
    public boolean esta(String nombre, int posI, int posF)
    {
        
        for (int i = posI; i >= posF; i--) 
        {
            if ( tabla[i].getNombre().equals(nombre))            
            {
                return true;
            }
            
        }
        
        return false;
    }
    
    public boolean guardarIdentificador(String nombre, Terminal tipo, int valor, int posI, int posF)
    {
        if ( esta(nombre,posI,posF) )
        {
            return false;
        }
        else
        {
            tabla[posI+1] =  new Identificador(nombre,tipo,valor);
            return true;
        }
    }

    public int getValor(String a, int i) 
    {
        for (int j = i; j >= 0; j--) 
        {
            if ( tabla[j].getNombre().equals(a)) return tabla[j].getValor();
        }
        
        return 0;
    }
    
    public Terminal getTipo(String a, int i) 
    {
        for (int j = i; j >= 0; j--) 
        {
            if ( tabla[j].getNombre().equals(a)){
                return tabla[j].getTipo();
            }
        }
        
        return Terminal.NULO;
    }
    
    public void recorrerTabla(int posI, int posF)
    {
        if (posI != -1)
        {
            for (int i = posI; i >= posF; i--) 
            {
                System.out.println(i+"-"+tabla[i].getNombre());
            }
        }
    }
    
    
    
}
