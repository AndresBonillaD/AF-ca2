/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

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
    
    static String typeRegex = "^(#!)(nfe|nfd|nfn)$";
    static String alphabetRegex = "^([^\n\t\r$])$|^([^\n\t$\r])-([^\n\t$\r])$";
    static String statesRegex = "^([^;>\r\n\t ])([^;>\r\n\t]*)$";
    static String tratitionsiiRegex = "^([^;>\r\n\t ])([^;>\r\n\t]*):([^\n\t\r])>([^;>\r\n\t ])([^;>\r\n\t]*)(;([^;>\r\n\t ])([^;>\r\n\t]*))*$";
    
    public NewFileReader() {
    }
        
    public static FiniteStateMachine generateAutomata(String fileRute){
        
        
        
        
        FiniteStateMachine automataFinito = new FiniteStateMachine();
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
        
        int readerHead = 0;
    
        if(match){
            if(fileLines.get(0).equals("!dfa")){
                System.out.println("Linea: 0" + ";" + fileLines.get(0) + ";" + match);
                // crear afd
            }else if(fileLines.get(0).equals("!nfa")){
                System.out.println("Linea: 0" + ";" + fileLines.get(0) + ";" + match);
                // crear afn
            }else if(fileLines.get(0).equals("!nfe")){
                System.out.println("Linea: 0" + ";" + fileLines.get(0) + ";" + match);
                // crear afdl
            }else{
                System.out.println("WARNING:    formato no aceptado");
            }
        }else{
            System.out.println("WARNING:    Primera linea mal formulada");
        }
        
        for (int i = 0; i < fileLines.size(); i++) {
            
            if(fileLines.get(i).equals("#alphabet")){
                System.out.println("generando alfabeto...");
                while(i <= fileLines.size() && !fileLines.get(i + 1).equals("#states")){
                    System.out.println("Linea: " + (i+1) + ";" + fileLines.get(i + 1));
                    //generar alfabeto
                    //generateAlphabet();
                    i++;
                }
            }
            
            if(fileLines.get(i).equals("#states")){
                System.out.println("generando estados...");
                while(i <= fileLines.size() && !fileLines.get(i + 1).equals("#initial")){
                    System.out.println("Linea: " + (i+1) + ";" + fileLines.get(i + 1));
                    //generar estados
                    i++;
                }
            }
            
            if(fileLines.get(i).equals("#inital")){
                System.out.println("generando estado inicial...");
                while(i <= fileLines.size() && !fileLines.get(i + 1).equals("#accepting")){
                    System.out.println("Linea: " + (i+1) + ";" + fileLines.get(i + 1));
                    //generar estado inicial
                    i++;
                }
            }
            
            if(fileLines.get(i).equals("#accepting")){
                System.out.println("generando estados de aceptacion...");
                while(i <= fileLines.size() && !fileLines.get(i + 1).equals("#transitions")){
                    System.out.println("Linea: " + (i+1) + ";" + fileLines.get(i + 1));
                    //generar estados de aceptación
                    i++;
                }
            }
            
            if(fileLines.get(i).equals("#transitions")){
                System.out.println("generando transiciones...");
                i++;
                while(i < fileLines.size()){
                    System.out.println("Linea: " + (i) + ";" + fileLines.get(i));
                    //generar trasiciones
                    i++;
                }
            }
             
                
                
                
            //System.out.println(fileLines.get(i));
        }
        
        
        
        
        
        
        
        
        
    
    return automataFinito;
    }
    
    public static void generateAlphabet(String line){
        Pattern alhabetPattern = Pattern.compile(alphabetRegex);
        Matcher m = alhabetPattern.matcher(line);
        boolean isAcceoted = m.matches();
        System.out.println("-" + line + "," + isAcceoted);
    }
    
    
    
 // CLASS END   
}
