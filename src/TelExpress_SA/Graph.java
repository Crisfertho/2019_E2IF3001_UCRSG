
package TelExpress_SA;

import java.util.HashMap;
import java.util.Map;

public class Graph {
    Map<String, Map<String, Integer>> adyacencias;

    public Graph() {
        adyacencias = new HashMap<>();
    }

    public void agregarBarrio(String barrio) {
        adyacencias.putIfAbsent(barrio, new HashMap<>());
    }

    public void agregarConexion(String origen, String destino, int distancia) {
        adyacencias.get(origen).put(destino, distancia);
        adyacencias.get(destino).put(origen, distancia);
    }

    public String mostrarGrafo() {
        StringBuilder builder = new StringBuilder();
        for (var origen : adyacencias.keySet()) {
            builder.append(origen).append(" -> ").append(adyacencias.get(origen)).append("\n");
        }
        return builder.toString();
    }
}
