package org.example;

public class Ingresos {

    public static void agregarIngreso(Usuarios usuario, int concepto, double cantidad) {
        String descripcion;
        double montoNeto = cantidad;

        // Identificar el concepto del ingreso según el número ingresado
        switch (concepto) {
            case 1: // Nómina
                descripcion = "Nómina";
                double irpf = cantidad * 0.15; // Calcular 15% de IRPF
                montoNeto = cantidad - irpf; // Calcular el monto neto después del descuento
                // Mostrar mensajes al usuario con el detalle del descuento aplicado
                System.out.printf("Se ha descontado un 15%% de IRPF (%.2f) sobre la nómina de %.2f.%n", irpf, cantidad);
                System.out.printf("Se queda en %.2f.%n", montoNeto);
                break;

            case 2: // Venta en páginas de segunda mano
                descripcion = "Venta en páginas de segunda mano";
                // Mostrar mensaje indicando el ingreso registrado
                System.out.printf("Se ha realizado un ingreso en: %s por %.2f.%n", descripcion, cantidad);
                break;

            default: // Manejar conceptos inválidos
                throw new IllegalArgumentException("Concepto de ingreso inválido."); // Lanza una excepción si el concepto no es válido
        }

        // Actualizar el saldo del usuario con el monto neto calculado
        usuario.agregarIngreso(montoNeto);
    }
}
