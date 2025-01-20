package org.example;


public class Gastos {


    public static void agregarGasto(Usuarios usuario, int concepto, double cantidad) {
        // Verificar que la cantidad sea mayor a cero
        if (cantidad <= 0) {
            System.out.println("Error: La cantidad debe ser mayor a cero.");
            return; // Terminar el método si la cantidad no es válida
        }

        String descripcion; // Variable para guardar la descripción del concepto

        // Identificar el concepto del gasto según el número ingresado
        switch (concepto) {
            case 1:
                descripcion = "Vacaciones";
                break;
            case 2:
                descripcion = "Alquiler";
                break;
            case 3:
                descripcion = "Vicios";
                break;
            default: {
                // Si el concepto no es válido, mostrar mensaje y terminar el método
                System.out.println("Concepto de gasto inválido.");
                return;
            }
        }

        // Obtener el saldo actual del usuario
        double saldoActual = usuario.obtenerSaldo();

        // Verificar si el saldo es suficiente para realizar el gasto
        if (cantidad > saldoActual) {
            // Mostrar mensaje si el saldo no es suficiente y terminar el método
            System.out.printf("Error: No puedes gastar %.2f. Saldo disponible: %.2f.%n", cantidad, saldoActual);
            return;
        }

        // Si el saldo es suficiente, registrar el gasto
        if (usuario.agregarGasto(cantidad)) {
            // Mostrar mensaje de éxito y el saldo actual
            System.out.printf("Se ha realizado un gasto en: %s por un valor de %.2f.%n", descripcion, cantidad);
            System.out.printf("Saldo actual: %.2f.%n", usuario.obtenerSaldo());
        } else {
            // Si ocurre un error al registrar el gasto, mostrar mensaje
            System.out.println("No se pudo registrar el gasto.");
        }
    }
}
