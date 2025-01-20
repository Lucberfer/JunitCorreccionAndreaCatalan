package org.example;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class UsuariosTest {

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
    void agregarIngreso() {
        // Ingresar dinero
        usuario.agregarIngreso(100);
        assertEquals(100, usuario.obtenerSaldo());
    }

    @Test
    void agregarGasto() {
        // Ingresar dinero y luego hacer un gasto
        usuario.agregarIngreso(100);
        assertTrue(usuario.agregarGasto(50)); // Gasto válido
        assertEquals(50, usuario.obtenerSaldo());
    }

    @Test
    void agregarGastoSaldoInsuficiente() {
        // Intentar hacer un gasto superior al saldo disponible
        usuario.agregarIngreso(30);
        assertFalse(usuario.agregarGasto(50)); // Gasto no permitido por saldo insuficiente
        assertEquals(30, usuario.obtenerSaldo());
    }
}
