package org.example;

public class Usuarios {
    private final String dni;

    public Usuarios(String dni) {
        this.dni = dni;
    }

    public double obtenerSaldo() {
        return BaseDatos.obtenerSaldo(dni);
    }

    public void agregarIngreso(double cantidad) {
        double saldoActual = BaseDatos.obtenerSaldo(dni); // Obtener saldo actual desde la base de datos
        double nuevoSaldo = saldoActual + cantidad; // Sumar el ingreso neto al saldo actual
        BaseDatos.actualizarSaldo(dni, nuevoSaldo); // Actualizar saldo en la base de datos
    }

    public boolean agregarGasto(double cantidad) {
        double saldoActual = BaseDatos.obtenerSaldo(dni); // Obtener saldo actual desde la base de datos
        if (cantidad > saldoActual) {
            return false; // No permitir el gasto si el saldo es insuficiente
        }
        double nuevoSaldo = saldoActual - cantidad; // Calcular el nuevo saldo
        BaseDatos.actualizarSaldo(dni, nuevoSaldo); // Actualizar el saldo en la base de datos
        return true; // Confirmar que el gasto se realizó correctamente
    }
}
