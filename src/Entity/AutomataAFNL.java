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
public class AutomataAFNL {

    public List<String> Alfabeto;
    public Estado EstadoInicial;
    public List<Estado> Estados;
    public List<Estado> EstadosAceptacion;
    Scanner scan = new Scanner(System.in);

    public AutomataAFNL(String estadoInicial) {
        EstadoInicial = new Estado(estadoInicial);

        Alfabeto = new ArrayList<>();
        Estados = new ArrayList<>();

        Estados.add(EstadoInicial);
        EstadosAceptacion = new ArrayList<>();
    }

    public AutomataAFNL(List<String> alfabeto, Estado estado, List<Estado> estados, List<Estado> estadosAceptacion) {
        this.Alfabeto = alfabeto;
        this.EstadoInicial = estado;
        this.Estados = estados;
        this.EstadosAceptacion = estadosAceptacion;
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

        /*AgregarEstado("q0");
         AgregarEstado("q1");
         AgregarEstado("q2");

         Estado estadoQ0 = GetEstadoByNombre("q0");
         Estado estadoQ1 = GetEstadoByNombre("q1");
         Estado estadoQ2 = GetEstadoByNombre("q2");

         EstadosAceptacion.add(estadoQ1);

         estadoQ0.AgregarTransicion("$", estadoQ1);
         estadoQ1.AgregarTransicion("a", estadoQ1);
         estadoQ1.AgregarTransicion("a", estadoQ2);
         estadoQ2.AgregarTransicion("b", estadoQ1);
         // estadoQ1.AgregarTransicion("a", estadoQ1);

         // estadoQ1.AgregarTransicion("a", estadoQ2);
         //estadoQ2.AgregarTransicion("b", estadoQ1);
         //estadoQ1.AgregarTransicion("a", estadoQ1);*/
        AgregarEstado("q0");
        AgregarEstado("q1");
        AgregarEstado("q2");
        AgregarEstado("q3");
        AgregarEstado("q4");

        Estado estadoQ0 = GetEstadoByNombre("q0");
        Estado estadoQ1 = GetEstadoByNombre("q1");
        Estado estadoQ2 = GetEstadoByNombre("q2");
        Estado estadoQ3 = GetEstadoByNombre("q3");
        Estado estadoQ4 = GetEstadoByNombre("q4");

        EstadosAceptacion.add(estadoQ2);
        EstadosAceptacion.add(estadoQ4);

        estadoQ0.AgregarTransicion("a", estadoQ1);
        estadoQ0.AgregarTransicion("$", estadoQ3);
        estadoQ1.AgregarTransicion("a", estadoQ1);
        estadoQ1.AgregarTransicion("$", estadoQ2);
        estadoQ2.AgregarTransicion("b", estadoQ2);
        estadoQ3.AgregarTransicion("b", estadoQ4);
        estadoQ4.AgregarTransicion("a", estadoQ3);

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
            termina = scan.next();
            if (termina.equals("0")) {
                break;
            }

            GetEstadoByNombre(actual).AgregarTransicion(voy, GetEstadoByNombre(termina));

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
                }else{
                   boolean bus =  buscarL(estadoActual);
                   if(bus){
                       return true;
                   }
                }
            }
        } else {

            String caracterEvaluar = Cadena.substring(0, 1);
            String cadenaRestante = Cadena.substring(1, Cadena.length());
            String cadenaaux = Cadena.substring(1, Cadena.length());

            List<Transicion> transicionEncontrada1 = estadoActual.BuscarTransicion("$");
            if (!transicionEncontrada1.isEmpty()) {
                cadenaRestante = Cadena.substring(0, Cadena.length());
                //System.out.println("encontro lambda ");
                //System.out.println("la cadena es: "+cadenaRestante );
                for (int j = 0; j < transicionEncontrada1.size(); j++) {
                    // System.out.println("y encontro:");
                    // System.out.println(transicionEncontrada1.get(j).EstadosDestino.nombre);
                    boolean respuesta = procesarCadena(cadenaRestante, transicionEncontrada1.get(j).EstadosDestino);
                    if (respuesta == true) {
                        return respuesta;
                    }

                }
            }

            List<Transicion> transicionEncontrada = estadoActual.BuscarTransicion(caracterEvaluar);
            if (transicionEncontrada == null || transicionEncontrada.isEmpty()) {
                return false;
            }

            for (int j = 0; j < transicionEncontrada.size(); j++) {

                boolean respuesta = procesarCadena(cadenaaux, transicionEncontrada.get(j).EstadosDestino);
                if (respuesta) {
                    return true;
                }
            }

            //Si la cadena que se recibe por parmaetro es de longitud 1 al buscar la transicion se debe evaluar si el estado destino es valido, retorna true, sino false.
           /* List<Transicion> transicionEncontrada = estadoActual.BuscarTransicion(caracterEvaluar);
             if (transicionEncontrada == null || transicionEncontrada.isEmpty()) {
             return false;
             }

             for (int j = 0; j < transicionEncontrada.size(); j++) {
                
             boolean respuesta = procesarCadena(cadenaRestante, transicionEncontrada.get(j).EstadosDestino);
             if (respuesta) {
             return true;
             }
             }*/
        }
        return false;
    }

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
            System.out.println(Alfabeto.get(i));

        }

    }

    public boolean buscarL(Estado estado) {
        int lim = estado.BuscarTransicion("$").size();
        if (lim == 0) {
            return false;
        }
        for (int i = 0; i < lim; i++) {
            for (int j = 0; j < EstadosAceptacion.size(); j++) {
                if (estado.BuscarTransicion("$").get(i).EstadosDestino == EstadosAceptacion.get(i)) {
                    return true;
                }else{
                    boolean bus = buscarL(estado.BuscarTransicion("$").get(i).EstadosDestino);
                    if(bus){
                        return true;
                    }
                }

            }

        }

        return false;
    }

}
