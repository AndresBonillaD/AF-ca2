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
    
    
    
}
