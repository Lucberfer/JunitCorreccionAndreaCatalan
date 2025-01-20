package org.example;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Mensaje de bienvenida
        System.out.println("Bienvenidx al sistema de control de gastos e ingresos.");
        String dni;

        // Bucle para solicitar un DNI válido
        while (true) {
            System.out.print("Por favor, introduzca su DNI: ");
            dni = scanner.nextLine().toUpperCase(); // Convertimos la letra a mayúscula

            // Validamos el formato del DNI
            if (BaseDatos.validarDNI(dni)) {
                break; // Si es válido, salimos del bucle
            } else {
                System.out.println("El DNI introducido no es válido. Inténtelo de nuevo.");
            }
        }

        // Verificamos si el usuario está registrado
        if (!BaseDatos.autenticarUsuario(dni)) {
            System.out.println("Usuario no encontrado. Se procederá a registrarlo.");
            if (!BaseDatos.registrarUsuario(dni)) {
                System.out.println("Error al registrar el usuario. Por favor, inténtelo de nuevo.");
                return; // Terminamos la ejecución si hay error en el registro
            }
        }

        // Usuario autenticado con éxito
        System.out.println("Autenticación completada. Bienvenidx.");
        Usuarios usuario = new Usuarios(dni); // Se crea un objeto de usuario con el DNI

        // Bucle principal para el menú de opciones
        boolean continuar = true;
        while (continuar) {
            // Mostrar las opciones del menú
            System.out.println("\n¿Qué desea realizar?\n1. Gasto\n2. Ingreso\n3. Salir");

            // Verificamos que la entrada sea un número válido
            if (!scanner.hasNextInt()) {
                System.out.println("Error de entrada. Debe introducir un número.");
                scanner.next(); // Limpiamos la entrada incorrecta
                continue;
            }

            int opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir el salto de línea

            switch (opcion) {
                case 1: // Manejar gastos
                    while (true) {
                        System.out.println("Seleccione un concepto de gasto:\n1. Vacaciones\n2. Alquiler\n3. Vicios\n4. Volver atrás");

                        if (!scanner.hasNextInt()) {
                            System.out.println("Error de entrada. Debe introducir un número entre 1 y 4.");
                            scanner.next();
                            continue;
                        }

                        int opcionGasto = scanner.nextInt();
                        scanner.nextLine();

                        if (opcionGasto == 4) break; // Volver atrás

                        if (opcionGasto < 1 || opcionGasto > 3) {
                            System.out.println("Error. Inténtelo de nuevo.");
                            continue;
                        }

                        System.out.print("Ingrese la cantidad: ");
                        if (!scanner.hasNextDouble()) {
                            System.out.println("Error de entrada. Debe introducir un número válido.");
                            scanner.next();
                            continue;
                        }

                        double gasto = scanner.nextDouble();
                        scanner.nextLine();
                        Gastos.agregarGasto(usuario, opcionGasto, gasto);
                        break;
                    }
                    break;
                case 2: // Manejar ingresos
                    while (true) {
                        System.out.println("Seleccione un concepto de ingreso:\n1. Nómina\n2. Venta en páginas de segunda mano\n3. Volver atrás");

                        if (!scanner.hasNextInt()) {
                            System.out.println("Error de entrada. Debe introducir un número entre 1 y 3.");
                            scanner.next();
                            continue;
                        }

                        int opcionIngreso = scanner.nextInt();
                        scanner.nextLine();

                        if (opcionIngreso == 3) break;

                        System.out.print("Ingrese la cantidad: ");
                        double ingreso = scanner.nextDouble();
                        scanner.nextLine();
                        Ingresos.agregarIngreso(usuario, opcionIngreso, ingreso);
                        break;
                    }
                    break;
                case 3:
                    System.out.println("Saliendo del sistema. Gracias.");
                    continuar = false;
                    break;
                default:
                    System.out.println("Opción errónea.");
            }
        }
    }
}
