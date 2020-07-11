/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import Controller.Estado;
import Controller.Transicion;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author crist
 */
public class AutomataAFD extends FiniteStateMachine {

    
    public List<String> detalleEstados ;  
    //nuevo atributo, se puede inicializar por constructor tambien, a conciderar
    public List<Transicion> parejas; 
    public List<Transicion> parejasno;
     public List<String> detalleEstadosNo ; 
    Scanner scan = new Scanner(System.in);

    public AutomataAFD() {
    }

    public AutomataAFD(String estadoInicial) {
        super(estadoInicial);
        detalleEstados = new ArrayList<>() ;
        detalleEstadosNo = new ArrayList<>() ;
        parejas = new ArrayList<>();
        parejasno = new ArrayList<>();
        detalleEstados.add(estadoInicial);
        detalleEstadosNo.add(estadoInicial);
    }

    public AutomataAFD(List<String> alfabeto, Estado estado, List<Estado> estados, List<Estado> estadosAceptacion) {
        super(alfabeto, estado, estados, estadosAceptacion);
    }

    public void AgregarEstado(String nombreEstado) {

        Estado existeEstado = new Estado(nombreEstado);
        if (!Estados.contains(existeEstado)) {
            Estados.add(existeEstado);
        }

    }

    public Estado GetEstadoByNombre(String nombreEstado) {

        Estado existeEstado = new Estado(nombreEstado);
        for (int i = 0; i < Estados.size(); i++) {
            if (Estados.get(i).nombre.equals(nombreEstado)) {
                return Estados.get(i);
            }

        }
        return null;

    }

    public Estado GetEstadoInicial() {
        return GetEstadoByNombre(EstadoInicial.nombre);
    }

    public void CrearAutomataAMano() {

        AgregarEstado("q0");
        AgregarEstado("q1");
        AgregarEstado("q2");

        Estado estadoQ0 = GetEstadoByNombre("q0");
        Estado estadoQ1 = GetEstadoByNombre("q1");
        Estado estadoQ2 = GetEstadoByNombre("q2");

        EstadosAceptacion.add(estadoQ1);

        estadoQ0.AgregarTransicion("a", estadoQ1);
      //  estadoQ1.AgregarTransicion("a", estadoQ1);
        estadoQ1.AgregarTransicion("a", estadoQ2);
        estadoQ2.AgregarTransicion("b", estadoQ1);
        
        for (int i = 0; i < Estados.size(); i++) {
            if(Estados.get(i).validarAFD(Estados.get(i), "a")== false){
             System.out.println("No es determinista");
             break;
         }
        }
         

       // estadoQ1.AgregarTransicion("a", estadoQ2);
        //estadoQ2.AgregarTransicion("b", estadoQ1);
        //estadoQ1.AgregarTransicion("a", estadoQ1);
    }

    public void PedirAutomata() {

        String read;
        int p = 0;
        System.out.println("Ingrese los estados, cuando termine ingrese 0 ");
        while (1 < 2) {
            read = scan.next();
            if (read.equals("0")) {
                break;
            }
            AgregarEstado(read);
           
        }
        
        System.out.println("Ingrese el o los estados de aceptacion cuando termine ingrese 0 ");
        
        while (1 < 2) {
            read = scan.next();
            if (read.equals("0")) {
                break;
            }
           EstadosAceptacion.add(GetEstadoByNombre(read));
           
        }
        System.out.println("Ingrese le alfabeto: ");
        read = scan.next();
        generarSigma(read);
        
        System.out.println("Ingrese las transiciones cuando termine ingrese 0 ");
        String actual;
        String voy;
        String termina;
         while (1 < 2) {
            System.out.println("Estoy en el estado:");
            actual = scan.next();
            if (actual.equals("0")) {
                break;
            }
             System.out.println("Paso con el caracter:");
             voy = scan.next();
             if (voy.equals("0")) {
                break;
            }
             System.out.println("paso al estado:");    
             termina= scan.next();
            if ( termina.equals("0")) {
                break;
            }
           
           GetEstadoByNombre(actual).AgregarTransicion(voy,GetEstadoByNombre(termina) );
           
        }
        
       /* GetEstadoByNombre("q0").AgregarTransicion("a",GetEstadoByNombre("q1") );
        GetEstadoByNombre("q1").AgregarTransicion("a", GetEstadoByNombre("q1"));
        GetEstadoByNombre("q1").AgregarTransicion("a", GetEstadoByNombre("q2"));
        GetEstadoByNombre("q2").AgregarTransicion("b", GetEstadoByNombre("q1"));
        GetEstadoByNombre("q1").AgregarTransicion("a", GetEstadoByNombre("q1"));*/

    }
    
    public boolean procesarCadena(String Cadena, Estado estadoActual) {
        if (Cadena.length() == 0 || Cadena.equals("$")) {
            for (int i = 0; i < EstadosAceptacion.size(); i++) {
                if (EstadosAceptacion.get(i) == estadoActual) {
                    
                    return true;
                }
            }
        } else {
            String caracterEvaluar = Cadena.substring(0, 1);
            String cadenaRestante = Cadena.substring(1, Cadena.length());

            //Si la cadena que se recibe por parmaetro es de longitud 1 al buscar la transicion se debe evaluar si el estado destino es valido, retorna true, sino false.
            List<Transicion> transicionEncontrada = estadoActual.BuscarTransicion(caracterEvaluar);
            if (transicionEncontrada == null || transicionEncontrada.isEmpty()) {
                return false;
            }

            for (int j = 0; j < transicionEncontrada.size(); j++) {
                boolean respuesta = procesarCadena(cadenaRestante, transicionEncontrada.get(j).EstadosDestino);
                if (respuesta) {
                    return true;
                }
            }

        }
        return false;

    }
    
    // creo que este metodo no se está usando
    public void generarSigma(String Sigma) {
        
         List<Character> sigma = new ArrayList<>();
         List<String> alfa = new ArrayList<>();
        
        String type = new String();

        for (int i = 0; i < Sigma.length(); i++) {
            if (Sigma.charAt(i) == ',') {
                type = "lista";
            } else if (Sigma.charAt(i) == '-') {
                type = "intervalo";
            }
        }
        if (type.equals("lista")) {
            sigma.add(Sigma.charAt(0));     // LOS ARCHIVOS ACEPTADOS NO TIENEN LISTA DE
            System.out.println(sigma.get(0));   // CARACTERES EN UNA SOLA LINEA
            sigma.add(Sigma.charAt(2));
            System.out.println(sigma.get(1));
        } else if (type.equals("intervalo")) {
            int a = (int) Sigma.charAt(0);
            int b = (int) Sigma.charAt(2);
            int i = 0;
            for (int x = a; x <= b; x++) {
                sigma.add((char) x);
                //System.out.println(sigma.get(i));
                i++;
            }
        }
        for (int i = 0; i < sigma.size(); i++) {
           Alfabeto.add(sigma.get(i).toString());
        }
        
        
        for (int i = 0; i < Alfabeto.size(); i++) {
            System.out.println( Alfabeto.get(i));
            
        }
        
    }
    
    public boolean procesarCadenaConDetalles(String Cadena, Estado estadoActual) {
        if (Cadena.length() == 0 || Cadena.equals("$")) {
            for (int i = 0; i < EstadosAceptacion.size(); i++) {
                if (EstadosAceptacion.get(i) == estadoActual) {
                    detalleEstados.add(estadoActual.nombre) ;
                    return true;
                }
            }
        } else {
            String caracterEvaluar = Cadena.substring(0, 1);
            String cadenaRestante = Cadena.substring(1, Cadena.length());

            //Si la cadena que se recibe por parmaetro es de longitud 1 al buscar la transicion se debe evaluar si el estado destino es valido, retorna true, sino false.
            List<Transicion> transicionEncontrada = estadoActual.BuscarTransicion(caracterEvaluar);
            if (transicionEncontrada == null || transicionEncontrada.isEmpty()) {
                Transicion fallida = new Transicion(caracterEvaluar,estadoActual);
                detalleEstadosNo.add(estadoActual.nombre);
                return false;
            }

            for (int j = 0; j < transicionEncontrada.size(); j++) {
                boolean respuesta = procesarCadenaConDetalles(cadenaRestante, transicionEncontrada.get(j).EstadosDestino);
                if (respuesta) {
                    detalleEstados.add(transicionEncontrada.get(j).EstadosDestino.nombre);
                    parejas.add(transicionEncontrada.get(j));
                    return true;
                }
            }

        }
        return false;

    }
    public void procesarListaCadenas (List <String >cadenas){
        for (int i = 0; i < cadenas.size(); i++) {
           boolean aux= procesarCadenaConDetalles(cadenas.get(i),EstadoInicial);
           if(aux){
               listaEstados();
               System.out.println("si");
           }else{
               listaEstados();
               System.out.println("no");
           }
        }
    }
    
    public void listaEstados(){
        for (int i = 0; i < detalleEstados.size(); i++) {
            System.out.println(detalleEstados.get(i));
        }
        detalleEstados.clear();
        detalleEstados.add(EstadoInicial.nombre);
        System.out.print("\t");
        for (int i = 0; i < parejas.size(); i++) {
            System.out.print(parejas.get(i).Sigma);
            System.out.println(",");
            System.out.print(parejas.get(i).EstadosDestino.nombre);
        }
        System.out.print("\t");
        parejas.clear();
    }
    public void listaEstadosNo(){
        for (int i = 0; i < detalleEstadosNo.size(); i++) {
            System.out.println(detalleEstadosNo.get(i));
        }
        detalleEstados.clear();
        detalleEstados.add(EstadoInicial.nombre);
        System.out.print("\t");
        for (int i = 0; i < parejasno.size(); i++) {
            System.out.print(parejasno.get(i).Sigma);
            System.out.println(",");
            System.out.print(parejas.get(i).EstadosDestino.nombre);
        }
        System.out.print("\t");
        parejasno.clear();
    }

}
