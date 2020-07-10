/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author crist
 */
public class Estado {

    public String nombre;
    public List<Transicion> Transiciones;
    public boolean multi;

    public Estado() {
        nombre = new String();
        Transiciones = null;
    }

    public Estado(String nombre) {
        this.nombre = nombre;
        Transiciones = new ArrayList<>();
    }

    public void AgregarTransicion(String caracter, Estado estadoDestino) {
        Transicion añadir = new Transicion(caracter, estadoDestino);

        Transiciones.add(añadir);

    }

    public void BorrarTransicion(String caracter, Estado estadoDestino) {
        Transicion añadir = new Transicion(caracter, estadoDestino);

        Transiciones.remove(añadir);

    }

    public List<Transicion> BuscarTransicion(String caracter) {
        List<Transicion> DevuelveTransiciones = new ArrayList<>();
        for (int i = 0; i < Transiciones.size(); i++) {
            if (Transiciones.get(i).Sigma.equals(caracter)) {
                DevuelveTransiciones.add(Transiciones.get(i));
            }
        }
        return DevuelveTransiciones;
    }

    public List<String> posiblesEstados() {
        List<String> list = new ArrayList<String>(Arrays.asList(nombre.split(" ")));

        return list;
    }

    public boolean validarAFD(Estado actual, String caracter) {

        if (BuscarTransicion(caracter).size() > 1) {
            return false;
        }

        return true;
    }

}
