package org.example;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class GastosTest {

    private Usuarios usuario;

    @BeforeEach
    void setUp() {
        // Crear un usuario para las pruebas
        usuario = new Usuarios("87654321A");
        BaseDatos.registrarUsuario("87654321A");
    }

    @AfterEach
    void limpiarBaseDatos() {
        // Limpiar los datos del usuario después de cada prueba
        try (var conn = java.sql.DriverManager.getConnection("jdbc:sqlite:usuarios.db");
             var stmt = conn.createStatement()) {
            stmt.execute("DELETE FROM usuarios");
        } catch (java.sql.SQLException e) {
            throw new RuntimeException("Error limpiando la base de datos", e);
        }
    }

    @Test
    void agregarGastoExitoso() {
        // Se asume que el saldo inicial es 0
        usuario.agregarIngreso(100); // Ingreso de 100 para cubrir el gasto

        // Realizar un gasto válido
        Gastos.agregarGasto(usuario, 1, 50); // Gasto de 50 en "Vacaciones"
        assertEquals(50, usuario.obtenerSaldo());
    }

    @Test
    void agregarGastoSaldoInsuficiente() {
        // Intentar un gasto mayor al saldo disponible
        usuario.agregarIngreso(30); // Ingreso de 30

        Gastos.agregarGasto(usuario, 2, 50); // Gasto de 50 en "Alquiler"
        assertEquals(30, usuario.obtenerSaldo()); // No debe haberse modificado
    }

    @Test
    void agregarGastoConceptoInvalido() {
        // Intentar registrar un gasto con concepto no válido
        Gastos.agregarGasto(usuario, 999, 10); // Concepto inválido
        assertEquals(0, usuario.obtenerSaldo());
    }
}
