/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Automata;

import Controller.NewFileReader;
import Entity.AutomataAFD;
import Entity.AutomataAFN;
import Entity.AutomataAFNL;
import Entity.FiniteStateMachine;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Automatas {

    public static AutomataAFN automataAFN;
    public static AutomataAFD automataAFD;
    public static AutomataAFNL automataAFNL;

    /**
     * @param args the command line arguments
     */

    public static void main(String[] args) {
        // TODO code application logic here

        automataAFD = new AutomataAFD("q0");
        automataAFN = new AutomataAFN("q0");
        automataAFNL = new AutomataAFNL("q0");

        Scanner sn = new Scanner(System.in);
        int option, optionIn;
        boolean exit = false;
        boolean result = false;
        String cadena = new String();
        while (!exit) {

            System.out.println("Automatas");
            System.out.println(" ");
            System.out.println("1.Leer automata desde archivo");
            System.out.println("2.AFD");
            System.out.println("3.AFN");
            System.out.println("4.AFN-lambda");
            System.out.println("5. Salir");

            try {

                option = sn.nextInt();

                switch (option) {
                    case 1:
                        System.out.println("Ingrese la ruta del archivo .txt:");
                        String ruta = sn.next();
                        // por el momneto el automata es null el metodo retorna un automata null
                        FiniteStateMachine automata = new FiniteStateMachine();
                        automata = NewFileReader.generateAutomata(ruta);

                        break;
                    case 2:
                        System.out.println("Autómata determinista");
                        System.out.println("Por favor ingrese manualmente el automata");
                        automataAFD.CrearAutomataAMano();

                        System.out.println("Automáta correctamente creado");
                        //archivoController.generarArchivo(automataFinito);
                        System.out.println("Por favor elija entre las opciones:");
                        System.out.println("1. Procesar cadena");
                        System.out.println("2. Procesar cadena con detalle");
                        System.out.println("3. Procesar lista de cadenas");
                        optionIn = sn.nextInt();
                        switch (optionIn) {
                            case 1:
                                System.out.println("Por favor ingrese la cadena a evaluar:");
                                cadena = sn.next();
                                result=automataAFD.procesarCadena(cadena, automataAFD.EstadoInicial);
                                if (result) {
                                    System.out.println("La cadena es aceptada");
                                } else {
                                    System.out.println("La cadena NO es aceptada");
                                }
                                break;
                            case 2:
                                System.out.println("Por favor ingrese la cadena a evaluar:");
                                cadena = sn.next();
                                boolean aux = automataAFD.procesarCadenaConDetalles(cadena, automataAFD.EstadoInicial);
                                if(aux){
                                    automataAFD.listaEstados();
                                }
                                if (aux) {
                                    System.out.println("La cadena es aceptada");
                                } else {
                                    System.out.println("La cadena NO es aceptada");
                                }
                                break;
                            case 3:

                                break;
                        }
                        break;
                    case 3:
                        System.out.println("Autómata no determinista");
                        System.out.println("Se ingresa manualmente el automata");
                        automataAFN.PedirAutomata();
                        System.out.println("Automáta correctamente creado");
                        System.out.println("");
                        System.out.println("Por favor elija entre las opciones:");
                        System.out.println("1. Procesar cadena");
                        System.out.println("2. Procesar cadena con detalle");
                        System.out.println("3. Procesar lista de cadenas");
                        optionIn = sn.nextInt();
                        switch (optionIn) {
                            case 1:
                                System.out.println("Por favor ingrese la cadena a evaluar:");
                                cadena = sn.next();
                                result = automataAFN.procesarCadena(cadena, automataAFN.GetEstadoInicial());
                                if (result) {
                                    System.out.println("La cadena es aceptada");
                                } else {
                                    System.out.println("La cadena NO es aceptada");
                                }
                                break;
                            case 2:
                                System.out.println("Por favor ingrese la cadena a evaluar:");
                                cadena = sn.next();
                                //result = afnController.procesarCadenaConDetalle(cadena);
                                if (result) {
                                    System.out.println("La cadena es aceptada");
                                } else {
                                    System.out.println("La cadena NO es aceptada");
                                }
                                break;
                            case 3:
                               // result = afnController.procesarListaDeCadenas();

                                break;
                        }
                        break;
                    case 4:
                        System.out.println("Autómata lambda no determinista");

                        System.out.println("Por favor ingrese manualmente el automata");
                        automataAFNL.CrearAutomataAMano();
                        
                        System.out.println("Automáta correctamente creado");
                        System.out.println("Por favor elija entre las opciones:");
                        System.out.println("1. Procesar cadena");
                        System.out.println("2. Procesar cadena con detalle");
                        System.out.println("3. Procesar lista de cadenas");
                        optionIn = sn.nextInt();
                        switch (optionIn) {
                            case 1:
                                System.out.println("Por favor ingrese la cadena a evaluar:");
                                cadena = sn.next();
                                
                                //result = afnController.procesarCadenaasd(cadena,afnController.initialState);
                                result = automataAFNL.procesarCadena(cadena, automataAFNL.GetEstadoInicial());
                                if (result) {
                                    System.out.println("La cadena es aceptada");
                                    System.out.println("-----------");
                                    System.out.println("-----------");
                                } else {
                                    System.out.println("La cadena NO es aceptada");
                                }
                                break;
                            case 2:
                                System.out.println("Por favor ingrese la cadena a evaluar:");
                                cadena = sn.next();

                                if (result) {
                                    System.out.println("La cadena es aceptada");
                                } else {
                                    System.out.println("La cadena NO es aceptada");
                                }
                                break;
                            case 3:

                                break;
                        }

                        break;
                    case 5:
                        exit = true;
                        break;
                    case 6: //solo para pruebas

                    default:
                        System.out.println("Solo números entre 1 y 5");
                }
            } catch (InputMismatchException e) {
                System.out.println("Debes insertar un número");
                sn.next();
            }
        }

    }

}
