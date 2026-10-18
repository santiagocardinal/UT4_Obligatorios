
package com.example;

import com.example.model.IDirectedIGraph;
import com.example.model.edge.WeightedEdge;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class GraphLoader {

    public static void cargarGrafoDesdeArchivos(
            IDirectedIGraph<String, WeightedEdge> grafo,
            String rutaAeropuertos,
            String rutaConexiones) {

        try (BufferedReader br =
                     new BufferedReader(new FileReader(rutaAeropuertos))) {

            String linea;

            while ((linea = br.readLine()) != null) {
                linea = linea.trim();

                if (!linea.isEmpty()) {
                    grafo.agregarVertice(linea);
                }
            }

            System.out.println(
                    "Aeropuertos cargados con éxito. Total: "
                            + grafo.cantidadDeVertices()
            );

        } catch (IOException e) {
            System.err.println(
                    "Error leyendo aeropuertos.txt: " + e.getMessage()
            );
            return;
        }

        try (BufferedReader br =
                     new BufferedReader(new FileReader(rutaConexiones))) {

            String linea;
            int numeroLinea = 0;

            while ((linea = br.readLine()) != null) {
                numeroLinea++;
                linea = linea.trim();

                if (linea.isEmpty() || linea.startsWith("#")) {
                    continue;
                }

                String[] partes = linea.split(",", -1);

                if (partes.length != 3
                        || partes[0].trim().isEmpty()
                        || partes[1].trim().isEmpty()
                        || partes[2].trim().isEmpty()) {

                    System.err.println(
                            "Línea " + numeroLinea
                                    + " inválida en conexiones.txt: " + linea
                    );
                    continue;
                }

                try {
                    String origen = partes[0].trim();
                    String destino = partes[1].trim();
                    double costo = Double.parseDouble(partes[2].trim());

                    if (!Double.isFinite(costo) || costo < 0) {
                        System.err.println(
                                "Costo inválido en la línea "
                                        + numeroLinea + ": " + costo
                        );
                        continue;
                    }

                    WeightedEdge datoArista = new WeightedEdge(costo);

                    grafo.agregarArista(origen, destino, datoArista);

                } catch (NumberFormatException e) {
                    System.err.println(
                            "Costo no numérico en la línea "
                                    + numeroLinea + ": " + linea
                    );
                }
            }

            System.out.println(
                    "Conexiones cargadas con éxito. Total aristas: "
                            + grafo.cantidadDeAristas()
            );

        } catch (IOException e) {
            System.err.println(
                    "Error leyendo conexiones.txt: " + e.getMessage()
            );
        }
    }
}