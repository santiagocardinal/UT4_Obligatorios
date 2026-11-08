
package com.example;

import com.example.model.DirectedGraph;
import com.example.model.DirectedGraphAlgorithms;
import com.example.model.IDirectedIGraph;
import com.example.model.edge.WeightedEdge;
import com.example.model.result.IFloydWarshallResult;

import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        IDirectedIGraph<String, WeightedEdge> grafo =
                new DirectedGraph<>();

        DirectedGraphAlgorithms algorithms =
                new DirectedGraphAlgorithms();

        String rutaAeropuertos = "aeropuertos.txt";
        String rutaConexiones = "conexiones.txt";

        GraphLoader.cargarGrafoDesdeArchivos(
                grafo,
                rutaAeropuertos,
                rutaConexiones
        );

        System.out.println(
                "\n--- Grafo de VUELE SEGURO cargado exitosamente ---\n"
        );

        // Floyd: caminos mínimos entre todos los pares de aeropuertos
        IFloydWarshallResult<String> floydResult =
                algorithms.floyd(grafo);

        // Pruebas de Warshall
        System.out.println("--- Pruebas de Warshall ---");

        IFloydWarshallResult<String> warshallResult =
                algorithms.warshall(grafo);

        // Caso 1: existe un camino de Montevideo a Curitiba
        System.out.println(
                "Montevideo -> Curitiba: "
                        + warshallResult.connected(
                                "Montevideo", "Curitiba"
                        )
        );

        // Caso 2: un aeropuerto es alcanzable desde sí mismo
        System.out.println(
                "Montevideo -> Montevideo: "
                        + warshallResult.connected(
                                "Montevideo", "Montevideo"
                        )
        );

        // Caso 3: mostrar un camino de alcanzabilidad
        if (warshallResult.connected("Montevideo", "Curitiba")) {
            List<String> camino = warshallResult.getPath(
                    "Montevideo", "Curitiba"
            );

            System.out.println("Camino encontrado: " + camino);
        }

        System.out.println("--- Fin de las pruebas de Warshall ---\n");

        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("\n===== VUELE SEGURO =====");
            System.out.println(
                    "1. Consultar costo mínimo entre dos aeropuertos"
            );
            System.out.println(
                    "2. Consultar itinerario de costo mínimo"
            );
            System.out.println(
                    "3. Consultar centro de mantenimiento"
            );
            System.out.println("4. Salir");
            System.out.print("Seleccione una opción: ");

            while (!scanner.hasNextInt()) {
                System.out.print("Ingrese un número válido: ");
                scanner.next();
            }

            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1: {
                    System.out.print("Aeropuerto de origen: ");
                    String origen = scanner.nextLine().trim();

                    System.out.print("Aeropuerto de destino: ");
                    String destino = scanner.nextLine().trim();

                    if (floydResult.connected(origen, destino)) {
                        System.out.println(
                                "Costo mínimo: "
                                        + floydResult.getCost(
                                                origen, destino
                                        )
                        );
                    } else {
                        System.out.println(
                                "No existe una conexión entre esos aeropuertos."
                        );
                    }
                    break;
                }

                case 2: {
                    System.out.print("Aeropuerto de origen: ");
                    String origen = scanner.nextLine().trim();

                    System.out.print("Aeropuerto de destino: ");
                    String destino = scanner.nextLine().trim();

                    if (floydResult.connected(origen, destino)) {
                        List<String> itinerario =
                                floydResult.getPath(origen, destino);

                        System.out.println("Itinerario: " + itinerario);
                        System.out.println(
                                "Costo total: "
                                        + floydResult.getCost(
                                                origen, destino
                                        )
                        );
                    } else {
                        System.out.println(
                                "No existe un itinerario entre esos aeropuertos."
                        );
                    }
                    break;
                }

                case 3: {
                    String mejorCentro =
                            algorithms.obtenerCentroGrafo(grafo);

                    if (mejorCentro != null) {
                        System.out.println(
                                "Aeropuerto recomendado para el centro de mantenimiento: "
                                        + mejorCentro
                        );
                    } else {
                        System.out.println(
                                "No se pudo determinar un centro de mantenimiento."
                        );
                    }
                    break;
                }

                case 4:
                    System.out.println("Saliendo del sistema...");
                    break;

                default:
                    System.out.println(
                            "Opción inválida. Intente nuevamente."
                    );
            }

        } while (opcion != 4);

        scanner.close();
    }
}
