/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Entity.AutomataAFD;
import Entity.AutomataAFN;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author crist
 */
public class Conversor {

    public AutomataAFN AFN;
    public AutomataAFD AFD;

    public Conversor() {

    }

    public AutomataAFD AFNtoAFD(AutomataAFN afn) {
        AutomataAFD vuelveAFD = new AutomataAFD("q0");
        List<Transicion> transito = new ArrayList<>();
        String estadoc = "";

        vuelveAFD.Estados = afn.Estados;
        vuelveAFD.EstadosAceptacion = afn.EstadosAceptacion;
        List<String> pos = new ArrayList<>();

        for (int i = 0; i < vuelveAFD.Estados.size(); i++) { //// recorre todo los estados 
            if (vuelveAFD.Estados.get(i).multi == true) {
                pos = vuelveAFD.Estados.get(i).posiblesEstados();
                for (int j = 0; j < vuelveAFD.Alfabeto.size(); j++) {
                    for (int k = 0; k < pos.size(); k++) {
                        transito = afn.GetEstadoByNombre(pos.get(i)).BuscarTransicion(vuelveAFD.Alfabeto.get(j));
                        for (int l = 0; l < transito.size(); l++) {
                            estadoc += transito.get(k).EstadosDestino.nombre + " ";
                        }
                        vuelveAFD.AgregarEstado(estadoc);
                        vuelveAFD.GetEstadoByNombre(estadoc).multi = true;
                    }
                    estadoc = "";
                    transito.clear();//
                }

            } else {
                for (int j = 0; j < vuelveAFD.Alfabeto.size(); j++) { // ahora recorre cada estado con el alfa beto
                    transito = vuelveAFD.Estados.get(i).BuscarTransicion(vuelveAFD.Alfabeto.get(j)); // busca las transiciones 
                    if (1 < transito.size()) { /// Si llega a mas dos estados , haga lo siguiente 
                        for (int k = 0; k < transito.size(); k++) {
                            estadoc += transito.get(k).EstadosDestino.nombre + " "; // se concatenan los estados a los que iba par crear el nombre de un solo estado
                        }
                        vuelveAFD.AgregarEstado(estadoc);
                        vuelveAFD.GetEstadoByNombre(estadoc).multi = true;
                        for (int k = 0; k < transito.size(); k++) {
                            vuelveAFD.Estados.get(i).BorrarTransicion(vuelveAFD.Alfabeto.get(j), transito.get(i).EstadosDestino);
                        }
                        vuelveAFD.Estados.get(i).AgregarTransicion(vuelveAFD.Alfabeto.get(j), vuelveAFD.GetEstadoByNombre(estadoc));
                    }
                    estadoc = "";
                    transito.clear();// se deja la variable vacia para ser reutilizada 
                }
            }

        }

        return vuelveAFD;
    }

}
