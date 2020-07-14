/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import Controller.Estado;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author andre
 */
public class FiniteStateMachine {
    
    public List<String> Alfabeto;
    public Estado EstadoInicial;
    public List<Estado> Estados;
    public List<Estado> EstadosAceptacion;
    
    public FiniteStateMachine() {               
        EstadoInicial = new Estado();
        Alfabeto = new ArrayList<>();
        Estados = new ArrayList<>();
        Estados.add(EstadoInicial);
        EstadosAceptacion = new ArrayList<>();
    }
    
    public FiniteStateMachine(String estadoInicial) {
        EstadoInicial = new Estado(estadoInicial);

        Alfabeto = new ArrayList<>();
        Estados = new ArrayList<>();

        Estados.add(EstadoInicial);
        EstadosAceptacion = new ArrayList<>();
    }

    public FiniteStateMachine(List<String> alfabeto, Estado estado, List<Estado> estados, List<Estado> estadosAceptacion) {
        this.Alfabeto = alfabeto;
        this.EstadoInicial = estado;
        this.Estados = estados;
        this.EstadosAceptacion = estadosAceptacion;
    }
    
    public void printFsm(){
        System.out.println("#alphabet");
        for(int i = 0; i < Alfabeto.size(); i++){
            System.out.print(Alfabeto.get(i) + "-");
        }
        System.out.print('\n');
        
        System.out.println("#states");
        for(int i = 0; i < Estados.size(); i++){
            System.out.println("- " + Estados.get(i).nombre);
        }
        
        System.out.println("#initialState");
        System.out.println("- " + EstadoInicial.nombre);
        
        System.out.println("#accepting");
        for(int i = 0; i < EstadosAceptacion.size(); i++){
            System.out.println("- " + EstadosAceptacion.get(i).nombre);
        }
        
        System.out.println("#transitions");
        for(int i = 0; i < Estados.size(); i ++){
            for (int j = 0; j < Estados.get(i).Transiciones.size(); j++){
                System.out.println("- " + Estados.get(i).Transiciones.get(j).EstadosDestino.nombre);
            }
        }
    }

//class end
}
 