/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Entity.AutomataAFD;
import Entity.AutomataAFN;
import Entity.AutomataAFNL;
import Entity.FiniteStateMachine;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author andres
 */
public class NewFileReader {
    /*
    Expreciones regulares para evaluar las lineas
    del archivo correspondientes a cada seccion
    del formato
    */
    private static final String typeRegex = "^(#!)(nfe|dfa|nfa)$";
    private static final String alphabetRegex = "^([^\n\t\r$])$|^([^\n\t$\r])-([^\n\t$\r])$";
    private static final String statesRegex = "^([^;>\r\n\t ])([^;>\r\n\t]*)$";
    private static final String trasitionsiRegex = "^([^;>\r\n\t ])([^;>\r\n\t]*):([^\n\t\r])>([^;>\r\n\t ])([^;>\r\n\t]*)(;([^;>\r\n\t ])([^;>\r\n\t]*))*$";
    
    static FiniteStateMachine fsm;
    
    public NewFileReader() {
    }
        
    public static FiniteStateMachine generateAutomata(String fileRute){
        
        List<String> fileLines = new ArrayList<>();
        File file = null;
        FileReader fr = null;
        BufferedReader br = null;
        
        //Se lee las lineas del archivo, se guardan en una lista
        try {                        
            file = new File(fileRute);      // Apertura del fichero y creacion de BufferedReader para poder
            fr = new FileReader(file);      // hacer una lectura comoda (disponer del metodo readLine()).
            br = new BufferedReader(fr);

            // Lectura del fichero
            String linea;

            while ((linea = br.readLine()) != null) {
                //System.out.println(linea);
                fileLines.add(linea); // añade archivo a la lista 
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {            
            try {                       // En el finally cerramos el fichero, para asegurarnos                        
                if (null != fr) {       // que se cierra tanto si todo va bien como si salta 
                    fr.close();         // una excepcion.
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        
        Pattern patron = Pattern.compile(typeRegex);
        Matcher m = patron.matcher(fileLines.get(0));
        boolean match = m.matches();
                    
        if(match){
            if(fileLines.get(0).equals("#!dfa")){
                System.out.println("Linea: 0" + ";" + fileLines.get(0) + ";" + match);
                // crear afd
                fsm = new AutomataAFD();
            }else if(fileLines.get(0).equals("#!nfa")){
                System.out.println("Linea: 0" + ";" + fileLines.get(0) + ";" + match);
                // crear afn
                fsm = new AutomataAFN();
            }else if(fileLines.get(0).equals("#!nfe")){
                System.out.println("Linea: 0" + ";" + fileLines.get(0) + ";" + match);
                // crear afe
                fsm = new AutomataAFNL();
            }else{
                System.out.println("WARNING:    formato no aceptado");
            }
        }else{
            System.out.println("WARNING:    Primera linea mal formulada" + fileLines.get(0));
        }
        
        
        for (int i = 0; i < fileLines.size(); i++) {
            
            if(fileLines.get(i).equals("#alphabet")){
                System.out.println("generando alfabeto...");
                while(i <= fileLines.size() && !fileLines.get(i + 1).equals("#states")){
                    //System.out.println("Linea: " + (i+1) + ";" + fileLines.get(i + 1));
                    //generar alfabeto
                    generateAlphabet(fileLines.get(i + 1), fsm);
                    i++;
                }
            }
            
            if(fileLines.get(i).equals("#states")){
                System.out.println("generando estados...");
                while(i <= fileLines.size() && !fileLines.get(i + 1).equals("#initial")){
                    //System.out.println("Linea: " + (i+1) + ";" + fileLines.get(i + 1));
                    //generar estados
                    generateStates(fileLines.get(i + 1), fsm);
                    i++;
                }
            }
            
            if(fileLines.get(i).equals("#initial")){
                System.out.println("generando estado inicial...");
                while(i <= fileLines.size() && !fileLines.get(i + 1).equals("#accepting")){
                    //System.out.println("Linea: " + (i+1) + ";" + fileLines.get(i + 1));
                    //generar estado inicial                    
                    generateInitialState(fileLines.get(i + 1), fsm);
                    i++;
                }
            }
            
            if(fileLines.get(i).equals("#accepting")){
                System.out.println("generando estados de aceptacion...");
                while(i <= fileLines.size() && !fileLines.get(i + 1).equals("#transitions")){
                    //System.out.println("Linea: " + (i+1) + ";" + fileLines.get(i + 1));
                    //generar estados de aceptación
                    generateAcceptingStates(fileLines.get(i + 1), fsm);
                    i++;
                }
            }
            
            if(fileLines.get(i).equals("#transitions")){
                System.out.println("generando transiciones...");
                while(i < fileLines.size() - 1){
                    generateTransitions(fileLines.get(i + 1), fsm);
                    i++;
                }
            }
             
                
                
                
            //System.out.println(fileLines.get(i));
        }
    return fsm;
    }
    
    public static void generateAlphabet(String line, FiniteStateMachine fsm){
        Pattern alhabetPattern = Pattern.compile(alphabetRegex);
        Matcher m = alhabetPattern.matcher(line);
        boolean isAccepted = m.matches();
        System.out.println("- " + line + " ; " + isAccepted);
        
        if(isAccepted){
            if(line.length() > 1){
                int a = (int) line.charAt(0);                     // toma valores ASCII de los esctremos del intervalo
                int b = (int) line.charAt(2);  
                int i = 0;
                for (int x = a; x <= b; x++) {                          //ingresa caracteres al alfebeto
                System.out.println("- " + (char) x + " -");
                fsm.Alfabeto.add(String.valueOf(x));                
                }        
            }else{
                fsm.Alfabeto.add(line);
            }
        }else{
            System.out.println("Warning!!!  -" + line);
        
        }
    }
    
    public static void generateStates(String line, FiniteStateMachine fsm){
        Pattern statesPattern = Pattern.compile(statesRegex);
        Matcher m = statesPattern.matcher(line);
        boolean isAccepted = m.matches();
        System.out.println("- " + line + " ; " + isAccepted);
        
        if(isAccepted){
            Estado state = new Estado(line);
            fsm.Estados.add(state);
        }else{
            System.out.println("Warning!!!  -" + line);
        }
    }
        
    public static void generateAcceptingStates(String line, FiniteStateMachine fsm){
        Pattern statesPattern = Pattern.compile(statesRegex);
        Matcher m = statesPattern.matcher(line);
        boolean isAccepted = m.matches();
        System.out.println("- " + line + " ; " + isAccepted);
        
        if(isAccepted){
            Estado state = new Estado(line);
            fsm.EstadosAceptacion.add(state);
        }else{
            System.out.println("Warning!!!  -" + line);
        }
    }
    
    public static void generateInitialState(String line, FiniteStateMachine fsm){
        Pattern statesPattern = Pattern.compile(statesRegex);
        Matcher m = statesPattern.matcher(line);
        boolean isAccepted = m.matches();
        System.out.println("- " + line + " ; " + isAccepted);
        
        if(isAccepted){
            Estado state = new Estado(line);
            fsm.EstadoInicial = state;
        }else{
            System.out.println("Warning!!!  -" + line);
        }
    }
    
    public static void generateTransitions(String line, FiniteStateMachine fsm){
        Pattern transitionPattern = Pattern.compile(trasitionsiRegex);
        Matcher m = transitionPattern.matcher(line);
        boolean isAccepted = m.matches();
        System.out.println("- " + line + " ; " + isAccepted);
        
        Transicion t = new Transicion(null, null);
        // estado : u > estado;estado;estado;estado
        if(isAccepted){
            String [] estSimbTrans = line.split(":");
            System.out.println("-----" + estSimbTrans[0]);
            for (int i = 0; i < fsm.Estados.size(); i++) {
                if (fsm.Estados.get(i).nombre.equals(estSimbTrans[0])) {
                    System.out.println("agregar trasicion al estado -" + fsm.Estados.get(i).nombre);
                    String [] simbTrans = estSimbTrans[1].split(">");
                    String [] trans = simbTrans[1].split(";");                    
                    for (int j = 0; j < trans.length; j++){
                        System.out.println("--" + fsm.Estados.get(i).nombre + ">" + trans[j]);
                        Estado e = new Estado(trans[j]);
                        t = new Transicion(simbTrans[0], e);
                        fsm.Estados.get(i).Transiciones.add(t);
                    }
                }
            }
        }else{
            System.out.println("Warning!!!  -" + line);
        }   
    }
    
    
 // CLASS END   
}
