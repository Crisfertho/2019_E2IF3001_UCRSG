package TelExpress_SA;

import java.util.ArrayList;
import java.util.List;

public class TreeBinary {
    NodoArbol raiz;

    public void insertar(Client cliente) {
        raiz = insertarRecursivo(raiz, cliente);
    }

    private NodoArbol insertarRecursivo(NodoArbol nodo, Client cliente) {
        if (nodo == null) {
            return new NodoArbol(cliente);
        }
        if (cliente.telefonoHash.compareTo(nodo.cliente.telefonoHash) < 0) {
            nodo.izquierda = insertarRecursivo(nodo.izquierda, cliente);
        } else {
            nodo.derecha = insertarRecursivo(nodo.derecha, cliente);
        }
        return nodo;
    }

    public Client buscar(String telefonoHash) {
        return buscarRecursivo(raiz, telefonoHash);
    }

    private Client buscarRecursivo(NodoArbol nodo, String telefonoHash) {
        if (nodo == null) return null;
        if (telefonoHash.equals(nodo.cliente.telefonoHash)) return nodo.cliente;
        if (telefonoHash.compareTo(nodo.cliente.telefonoHash) < 0) {
            return buscarRecursivo(nodo.izquierda, telefonoHash);
        }
        return buscarRecursivo(nodo.derecha, telefonoHash);
    }

    public List<Client> buscarPorBarrio(String barrio) {
        List<Client> resultados = new ArrayList<>();
        buscarPorBarrioRecursivo(raiz, barrio, resultados);
        return resultados;
    }

    private void buscarPorBarrioRecursivo(NodoArbol nodo, String barrio, List<Client> resultados) {
        if (nodo != null) {
            if (nodo.cliente.barrio.equals(barrio)) {
                resultados.add(nodo.cliente);
            }
            buscarPorBarrioRecursivo(nodo.izquierda, barrio, resultados);
            buscarPorBarrioRecursivo(nodo.derecha, barrio, resultados);
        }
    }
}
